package userlevel;

import apilevel.AtmClientSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;


public class WorkerController implements Initializable {

    @FXML Button addUser;
    @FXML Button addCard;
    @FXML Button addCash;
    @FXML Button unlockCard;
    @FXML Button logout;
    @FXML VBox container;

    private AtmClientSingleton atm;
    private Alert alert = new Alert(Alert.AlertType.INFORMATION);

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        atm = AtmClientSingleton.getInstance();
        System.out.println(atm.getServiceWorker().getServiceKey());
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
            loader.setLocation(getClass().getResource("/service-worker-widgets/" +  widgetName + ".fxml"));

            Parent widget =  loader.load();
            container.getChildren().clear();
            container.getChildren().add(widget);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void logoutAction(ActionEvent event) {

        Stage stage = ((Stage)((Node)event.getSource()).getScene().getWindow());
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/scenes/app.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/css/test.css");
            stage.setScene(scene);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addUserAction(ActionEvent event) {
        loadWidget("createNewBankAccount");
    }

    @FXML
    private void addCardAction(ActionEvent event) {
        loadWidget("addNewCreditCard");
    }

    @FXML
    private void unlockCardAction(ActionEvent event) {
        loadWidget("unlockCard");
    }

    @FXML
    private void addCashAction(ActionEvent event) {

        loadWidget("addCash");

        TextField available500 = (TextField) ((Pane)container.getChildren().get(0)).getChildren().get(0);
        TextField available200 = (TextField) ((Pane)container.getChildren().get(0)).getChildren().get(1);
        TextField available100 = (TextField) ((Pane)container.getChildren().get(0)).getChildren().get(2);
        TextField available50  = (TextField) ((Pane)container.getChildren().get(0)).getChildren().get(3);
        TextField available20  = (TextField) ((Pane)container.getChildren().get(0)).getChildren().get(4);
        TextField available10  = (TextField) ((Pane)container.getChildren().get(0)).getChildren().get(5);
        TextField available5   = (TextField) ((Pane)container.getChildren().get(0)).getChildren().get(6);
        TextField available2   = (TextField) ((Pane)container.getChildren().get(0)).getChildren().get(7);
        TextField available1   = (TextField) ((Pane)container.getChildren().get(0)).getChildren().get(8);

        TextField count500 =   (TextField) ((Pane)container.getChildren().get(0)).getChildren().get(9);
        TextField count200  =   (TextField) ((Pane)container.getChildren().get(0)).getChildren().get(10);
        TextField count100 =   (TextField) ((Pane)container.getChildren().get(0)).getChildren().get(11);
        TextField count50  =   (TextField) ((Pane)container.getChildren().get(0)).getChildren().get(12);
        TextField count20  =   (TextField) ((Pane)container.getChildren().get(0)).getChildren().get(13);
        TextField count10  =   (TextField) ((Pane)container.getChildren().get(0)).getChildren().get(14);
        TextField count5   =   (TextField) ((Pane)container.getChildren().get(0)).getChildren().get(15);
        TextField count2   =   (TextField) ((Pane)container.getChildren().get(0)).getChildren().get(16);
        TextField count1   =   (TextField) ((Pane)container.getChildren().get(0)).getChildren().get(17);

        Button addCash =   (Button) ((Pane)container.getChildren().get(0)).getChildren().get(18);

        Map<Integer, Integer> money = atm.getVault().getVault();

        available1.setText(String.valueOf(money.get(1)));
        available2.setText(String.valueOf(money.get(2)));
        available5.setText(String.valueOf(money.get(5)));
        available10.setText(String.valueOf(money.get(10)));
        available20.setText(String.valueOf(money.get(20)));
        available50.setText(String.valueOf(money.get(50)));
        available100.setText(String.valueOf(money.get(100)));
        available200.setText(String.valueOf(money.get(200)));
        available500.setText(String.valueOf(money.get(500)));

        addCash.setOnAction(event1 -> {
            atm.getVault().addCashToVault(count500.getLength() > 0 ? 500*Double.valueOf(count500.getText()) : 0);
            atm.getVault().addCashToVault(count200.getLength() > 0 ? 200*Double.valueOf(count200.getText()) : 0);
            atm.getVault().addCashToVault(count100.getLength() > 0 ? 100*Double.valueOf(count100.getText()) : 0);
            atm.getVault().addCashToVault(count50.getLength() > 0 ? 50*Double.valueOf(count50.getText()) : 0);
            atm.getVault().addCashToVault(count20.getLength() > 0 ? 20*Double.valueOf(count20.getText()) : 0);
            atm.getVault().addCashToVault(count10.getLength() > 0 ? 10*Double.valueOf(count10.getText()) : 0);
            atm.getVault().addCashToVault(count5.getLength() > 0 ? 5*Double.valueOf(count5.getText()) : 0);
            atm.getVault().addCashToVault(count2.getLength() > 0 ? 2*Double.valueOf(count2.getText()) : 0);
            atm.getVault().addCashToVault(count1.getLength() > 0 ? 1*Double.valueOf(count1.getText()) : 0);

            showAlert("Cash successfully added", "OK", "info");
        });

    }
}
