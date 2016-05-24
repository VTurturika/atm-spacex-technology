package userlevel;

import apilevel.AtmClientSingleton;
import apilevel.MoneyVault;
import datalevel.DatabaseConnector;
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

    private AtmClientSingleton atm;

    private Service<Void> loginTask = new Service<Void>() {
        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {

                    try {
                        atm.setConnector(new DatabaseConnector());
                        insert.setDisable(false);
                    } catch (RequestException e) {
                        insert.setDisable(true);
                        insert.setText(e.getMessage());
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

        insert.setDisable(true);

        container.getChildren().remove(pin);
        container.getChildren().remove(login);

        loginTask.start();

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
            e.printStackTrace();
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
                ClientController clientController = ((ClientController) loader.getController());
                clientController.setAtm(atm);
                scene.getStylesheets().add("/css/client.css");
            } else {
                if (sceneName.equals("ServiceWorker")) {
                    WorkerController workerController = ((WorkerController) loader.getController());
                    workerController.setAtm(atm);
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
