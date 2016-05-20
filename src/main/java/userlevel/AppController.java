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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


public class AppController implements Initializable {

    @FXML Button login;
    @FXML Button service;
    private AtmClientSingleton atm;

    private Service<Void>  loginTask = new Service<Void>() {
        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {

                    try{
                        atm.setConnector(new DatabaseConnector());
                        login.setDisable(false);
                    }
                    catch (RequestException e) {
                        login.setDisable(true);
                        login.setText(e.getMessage());
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

        login.setDisable(true);
        loginTask.start();
    }

    @FXML
    private void loginUser(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        Stage stage = (Stage) login.getScene().getWindow();

        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Spacex-Technology card (*.sxcard)", "*.sxcard"));
        File file = fileChooser.showOpenDialog(stage);

        if(file != null) {

            try {
                atm.setCurrentCardFromFile(file);
                loadScene("Client",event);
            }
            catch (RequestException e) {

            }

        }


        //loadScene("Client", event);
    }

    @FXML
    private void swView(ActionEvent event) {

        loadScene("ServiceWorker", event);
    }

    private void loadScene(String sceneName, ActionEvent event) {

        Stage stage = ((Stage)((Node)event.getSource()).getScene().getWindow());
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/" + sceneName +".fxml"));
            Parent root =  loader.load();

            if(sceneName.equals("Client")) {
                ClientController clientController = ((ClientController)loader.getController());
                clientController.setAtm(atm);
            }
            else {
                WorkerController workerController = ((WorkerController)loader.getController());
                workerController.setAtm(atm);
            }

            Scene scene= new Scene(root);
            scene.getStylesheets().add("test.css");
            stage.setScene(scene);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
