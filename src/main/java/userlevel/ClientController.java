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
    @FXML VBox container;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        container.setVisible(false);
    }


    public void setAtm(AtmClientSingleton atm) {
        this.atm = atm;
        System.out.println(atm.getCurrentCard().getCardId());
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

    @FXML
    private void showBalance(ActionEvent event) {

        try {
            System.out.println("showBalance started");

            double balance = atm.showBalance();

            System.out.println("db query completed");

            container.getChildren().clear();
            container.setVisible(true);

            Label result = new Label();
            result.setText("Your balance:\n" + String.valueOf(balance));
            result.setStyle("-fx-font-family: \"Arial\";\n" +
                    "    -fx-text-alignment: center;\n" +
                    "    -fx-font-size: 20px;");
            container.getChildren().add(result);
        }
        catch (RequestException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addCash(ActionEvent event) {

        System.out.println("addCash started");

        container.getChildren().clear();
        container.setVisible(true);

        TextField howMuch = new TextField();
        Label label = new Label("Add your cash:");
        Button button = new Button("Submit");

        VBox vBox = new VBox(label, howMuch, button);
        vBox.setSpacing(5);
        vBox.setPadding(new Insets(5));
        vBox.setAlignment(Pos.CENTER);
        howMuch.setAlignment(Pos.CENTER);

        button.setOnAction(event1 -> {

            try {
                System.out.println("submit callback started");
                button.setDisable(true);
                label.setText("Wait");
                double newBalance = atm.addCash(Double.valueOf(howMuch.getText()));
                System.out.println("db query completed");
                label.setText("Current balance:");
                howMuch.setText(String.valueOf(newBalance));
               // vBox.getChildren().remove(button);

            }
            catch (RequestException e) {
                e.printStackTrace();
            }
        });

        button.getStyleClass().add("buttonsx");

        container.getChildren().add(vBox);
    }

}
