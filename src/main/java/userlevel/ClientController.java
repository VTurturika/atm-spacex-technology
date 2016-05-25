package userlevel;

import apilevel.AtmClientSingleton;
import datalevel.RequestException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {

    private AtmClientSingleton atm;
    private Alert alert = new Alert(Alert.AlertType.INFORMATION);

    @FXML Button changePin;
    @FXML Button showBalance;
    @FXML Button withdrawCash;
    @FXML Button addCash;
    @FXML Button logout;
    @FXML VBox container;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        atm = AtmClientSingleton.getInstance();
    }

    @FXML
    private void logoutAction(ActionEvent event) {

        Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/scenes/app.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/css/test.css");
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String message, String headed, String type) {

        alert.setHeaderText(headed);
        alert.setContentText(message);

        if(type.equals("error")) {
            alert.setAlertType(Alert.AlertType.ERROR);
        }
        else {
            alert.setAlertType(Alert.AlertType.INFORMATION);
        }

        alert.showAndWait();
    }

    private void loadWidget(String widgetName) {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/client-widgets/" + widgetName + ".fxml"));
            Parent widget = loader.load();
            container.getChildren().clear();
            container.getChildren().add(widget);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void showBalanceAction(ActionEvent event) {

        loadWidget("showBalance");

        Scene scene = ((Node) event.getSource()).getScene();
        TextField textField = (TextField) scene.lookup("#userBalance");

        try {
            double balance = atm.showBalance();
            textField.setText(String.valueOf(balance));
        } catch (RequestException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addCashAction(ActionEvent event) {

        loadWidget("addCash");

        TextField addCash = (TextField) ((Pane)container.getChildren().get(0)).getChildren().get(0);
        Button addCashButton =  (Button) ((Pane)container.getChildren().get(0)).getChildren().get(1);

        addCashButton.setOnAction(event1 -> {
            try {
                double addValue = Double.valueOf(addCash.getText());
                double currentBalance = atm.addCash(addValue);
                showAlert("Successfully added\nCurrent balance: " + String.valueOf(currentBalance), "OK", "info");
            }
            catch (RequestException e) {
                showAlert(e.getMessage(), "Error", "error");
            }
        });
    }

    @FXML
    private void changePinAction(ActionEvent event) {

        loadWidget("changePin");

        Scene scene = ((Node) event.getSource()).getScene();
        PasswordField userPin = (PasswordField) scene.lookup("#userPin");
        PasswordField userNewPin = (PasswordField) scene.lookup("#userNewPin");
        Button changePin =  (Button) ((Pane)container.getChildren().get(0)).getChildren().get(2);

        changePin.setOnAction(event1 -> {
            try {
                atm.changePin(userNewPin.getText());
                showAlert("Successfully changed PIN code", "OK", "info");
            }
            catch (RequestException e) {
                showAlert(e.getMessage(), "Error", "error");
            }
        });
    }

    @FXML
    private void withdrawCashAction(ActionEvent event) {
        loadWidget("withdrawCash");
        System.out.println("withdrawCashAction");

        TextField withdrawCash = (TextField) ((Pane)container.getChildren().get(0)).getChildren().get(0);
        Button withdrawCashButton = (Button) ((Pane)container.getChildren().get(0)).getChildren().get(1);
        Button changeNominal = (Button) ((Pane)container.getChildren().get(0)).getChildren().get(2);

        withdrawCashButton.setOnAction(event1 -> {

            try {
                double withdraw = Double.valueOf(withdrawCash.getText());
                double currentBalance = atm.withdrawCash(withdraw);
                showAlert("Successfully withdraw\nCurrent balance: " + String.valueOf(currentBalance), "OK", "info");
            }
            catch (RequestException e) {
                showAlert(e.getMessage(), "Error", "error");
            }
        });

        changeNominal.setOnAction(event1 -> {
            loadWidget("withdrawCash_pro");


            TextField withdrawCashField = (TextField) ((Pane)container.getChildren().get(0)).getChildren().get(0);

            TextField extradited500 = (TextField) ((Pane)container.getChildren().get(0)).getChildren().get(1);
            TextField extradited200 = (TextField) ((Pane)container.getChildren().get(0)).getChildren().get(2);
            TextField extradited100 = (TextField) ((Pane)container.getChildren().get(0)).getChildren().get(3);
            TextField extradited50 = (TextField) ((Pane)container.getChildren().get(0)).getChildren().get(4);
            TextField extradited20 = (TextField) ((Pane)container.getChildren().get(0)).getChildren().get(5);
            TextField extradited10 = (TextField) ((Pane)container.getChildren().get(0)).getChildren().get(6);
            TextField extradited5 = (TextField) ((Pane)container.getChildren().get(0)).getChildren().get(7);
            TextField extradited2 = (TextField) ((Pane)container.getChildren().get(0)).getChildren().get(8);
            TextField extradited1 = (TextField) ((Pane)container.getChildren().get(0)).getChildren().get(9);

            Button withdraw = (Button) ((Pane)container.getChildren().get(0)).getChildren().get(10);
            Button clear = (Button) ((Pane)container.getChildren().get(0)).getChildren().get(11);
            Button recalcButton = (Button) ((Pane)container.getChildren().get(0)).getChildren().get(12);

            recalcButton.setOnAction(event2 -> {

                int n500 = Integer.valueOf(extradited500.getText());
                int n200 = Integer.valueOf(extradited200.getText());
                int n100 = Integer.valueOf(extradited100.getText());
                int n50 = Integer.valueOf(extradited50.getText());
                int n20 = Integer.valueOf(extradited20.getText());
                int n10 = Integer.valueOf(extradited10.getText());
                int n5 = Integer.valueOf(extradited5.getText());
                int n2 = Integer.valueOf(extradited2.getText());
                int n1 = Integer.valueOf(extradited1.getText());

                withdrawCashField.setText(String.valueOf(n500*500 + n200*200 +n100*100 +n50*50 +n20*20 +n10*10 +n5*5 +n2*2 +n1));
            });

            clear.setOnAction(event2 -> {
                extradited1.setText("0");
                extradited2.setText("0");
                extradited5.setText("0");
                extradited10.setText("0");
                extradited20.setText("0");
                extradited50.setText("0");
                extradited100.setText("0");
                extradited200.setText("0");
                extradited500.setText("0");
                withdrawCashField.setText("0");
            });

            withdraw.setOnAction(event2 ->  {
                try {
                    double w = Double.valueOf(withdrawCashField.getText());
                    double currentBalance = atm.withdrawCash(w);
                    showAlert("Successfully withdraw\nCurrent balance: " + String.valueOf(currentBalance), "OK", "info");
                }
                catch (RequestException e) {
                    showAlert(e.getMessage(), "Error", "error");
                }
            });

        });


    }
}
