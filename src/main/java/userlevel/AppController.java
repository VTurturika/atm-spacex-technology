package userlevel;
//package apilevel;

import apilevel.AtmClientSingleton;
import apilevel.CreditCard;
import datalevel.DatabaseConnector;
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

    // App
    @FXML Button login;
    @FXML TextField pin;
    @FXML TextField cardNumber;
    @FXML TextField workerKey;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        AtmClientSingleton client = App.getClient();

        //App
        login.setOnAction(e -> {
            CreditCard currentCard;
            client.setConnector(new DatabaseConnector());

            //switch scene to client scene
            if ( (cardNumber.getText().length() != 0) && (pin.getText().length() != 0) ) {
                currentCard = new CreditCard(cardNumber.getText(), pin.getText());

                client.setCurrentCard(currentCard);

                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/Client.fxml"));
                    Scene clientScene = new Scene(root);
                    App.getPrimaryStage().setTitle("Client");
                    App.getPrimaryStage().setScene(clientScene);
                    App.getPrimaryStage().show();

                    System.out.println("Logined as CLIENT");

                } catch (IOException error) {

                }

            } else {
                System.out.println("Wrong user data");
            }

            //switch scene to worker scene
            if (workerKey.getText().length() != 0)  {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/ServiceWorker.fxml"));
                    Scene workerScene = new Scene(root);
                    App.getPrimaryStage().setTitle("Worker");
                    App.getPrimaryStage().setScene(workerScene);
                    App.getPrimaryStage().show();

                    System.out.println("Logined as WORKER");
                } catch (IOException error) {

                }

            } else {
                System.out.println("Wrong worker data");
            }


        });

    }
}
