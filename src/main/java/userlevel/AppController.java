package userlevel;
//package apilevel;

import apilevel.AtmClientSingleton;
import apilevel.CreditCard;
import datalevel.DatabaseConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * <what class do>
 *
 * @author Kyrylo Havrylenko
 * @see
 */
public class AppController implements Initializable {

    @FXML Button login;
    @FXML Button service;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void loginUser(ActionEvent e) {
        
    }

    @FXML
    private void swView(ActionEvent e) {

        System.out.println("sw");
    }
}
