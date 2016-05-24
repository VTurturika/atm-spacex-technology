package userlevel;

import apilevel.AtmClientSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class WorkerController implements Initializable {

    @FXML VBox container;

    private AtmClientSingleton atm;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        atm = AtmClientSingleton.getInstance();
        System.out.println(atm.getServiceWorker().getServiceKey());
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
    }
}
