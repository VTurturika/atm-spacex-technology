package userlevel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class AppController implements Initializable {

    @FXML Button login;
    @FXML Button service;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void loginUser(ActionEvent event) {

        loadScene("Client", event);
    }

    @FXML
    private void swView(ActionEvent event) {

        loadScene("ServiceWorker", event);
    }

    private void loadScene(String sceneName, ActionEvent event) {

        Stage stage = ((Stage)((Node)event.getSource()).getScene().getWindow());
        try {
            Parent root =  FXMLLoader.load(getClass().getResource("/" + sceneName +".fxml"));
            Scene scene= new Scene(root);
            scene.getStylesheets().add("test.css");
            stage.setScene(scene);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
