package userlevel;

import apilevel.AtmClientSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class WorkerController implements Initializable {

    private AtmClientSingleton atm;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setAtm(AtmClientSingleton atm) {
        this.atm = atm;
    }

    @FXML
    private void logout(ActionEvent event) {

        Stage stage = ((Stage)((Node)event.getSource()).getScene().getWindow());
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/app.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add("test.css");
            stage.setScene(scene);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
