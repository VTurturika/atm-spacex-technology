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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {

    private AtmClientSingleton atm;

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

    private void loadWidget(String widgetName) {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/client-widgets/" +  widgetName + ".fxml"));
            Parent widget =  loader.load();
            container.getChildren().clear();
            container.getChildren().add(widget);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void showBalanceAction(ActionEvent event) {

        loadWidget("showBalance");
        System.out.println("showBalanceAction");
    }

    @FXML
    private void addCashAction(ActionEvent event) {

        loadWidget("addCash");
        System.out.println("addCash started");
    }

    @FXML
    private void changePinAction(ActionEvent event) {
       loadWidget("changePin");
        System.out.println("changePinAction");
    }


    @FXML
    private void withdrawCashAction(ActionEvent event) {
        loadWidget("withdrawCash");
        System.out.println("withdrawCashAction");
    }
}
