package userlevel;

import apilevel.AtmClientSingleton;
import apilevel.MoneyVault;
import datalevel.DatabaseConnector;
import datalevel.RequestErrorCode;
import datalevel.RequestException;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.text.TabExpander;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


public class AppController implements Initializable {

    @FXML Button login;
    @FXML Button service;
    @FXML Button insert;
    @FXML PasswordField pin;
    @FXML HBox container;

    private Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    private AtmClientSingleton atm;

    private Service<Void> loginTask = new Service<Void>() {
        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {

                    try {
                        atm.setConnector(new DatabaseConnector());

                    } catch (RequestException e) {

                        throw e;
                    }
                    return null;
                }
            };
        }
    };


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        atm = AtmClientSingleton.getInstance();
        MoneyVault moneyVault = new MoneyVault();
        moneyVault.addCashToVault(10000.);
        atm.setVault(moneyVault);

        container.getChildren().remove(pin);
        container.getChildren().remove(login);

        loginTask.start();
        insert.setDisable(true);
        insert.setText("Connecting...");

        loginTask.setOnSucceeded(event -> {
            insert.setDisable(false);
            insert.setOnAction(event1 -> insertCard(event1));
            insert.setText("Insert card");
        });

        loginTask.setOnFailed(event -> {
            System.out.println(event.getSource().getException().getMessage());
            insert.setText("Retry connect");
            insert.setDisable(false);

            insert.setOnAction(event1 -> {
                insert.setDisable(true);
                insert.setText("Connecting...");
                loginTask.reset();
                loginTask.start();
            });
        });

        TextFormatter<String> textFormatter = new TextFormatter<>(change -> {
            String text = change.getText();
            if (text.matches("[0-9]{0,4}")) {
                return change;
            }
            return null;
        });


        pin.textProperty().addListener((observable, oldValue, newValue) -> {
            if(pin.getText().length() > 4) {
                pin.setText(pin.getText().substring(0,4));
            }
        });

        pin.setTextFormatter(textFormatter);
    }

    @FXML
    private void insertCard(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        Stage stage = (Stage) insert.getScene().getWindow();

        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Spacex-Technology card (*.sxcard)", "*.sxcard"));
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {

            try {
                atm.setCurrentCardFromFile(file);
                container.getChildren().remove(insert);
                container.getChildren().addAll(pin, login);
            } catch (RequestException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void loginUser(ActionEvent event) {
        try {
            atm.getCurrentCard().setPinCode(pin.getText());
            atm.showBalance();
            loadScene("Client", event);
        } catch (RequestException e) {

            if(e.getErrorCode() == RequestErrorCode.LOGIN_ERROR) {

               errorAlert.setHeaderText("Login error");
               errorAlert.getButtonTypes().clear();
               errorAlert.getButtonTypes().addAll(ButtonType.OK);
               errorAlert.showAndWait();
            }

        }
    }

    @FXML
    private void serviceWorkerScene(ActionEvent event) {

        loadScene("ServiceWorker", event);
    }

    private void loadScene(String sceneName, ActionEvent event) {

        Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/scenes/" + sceneName + ".fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            if (sceneName.equals("Client")) {
                scene.getStylesheets().add("/css/client.css");
            } else {
                if (sceneName.equals("ServiceWorker")) {
                    scene.getStylesheets().add("/css/service.css");
                }
                else {
                    scene.getStylesheets().add("test.css");
                }
            }
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
