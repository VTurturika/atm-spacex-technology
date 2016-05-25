package userlevel;

import apilevel.AtmClientSingleton;
import apilevel.BankAccount;
import apilevel.CreditCard;
import apilevel.Person;
import datalevel.RequestException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;


public class WorkerController implements Initializable {

    @FXML
    Button addUser;
    @FXML
    Button addCard;
    @FXML
    Button addCash;
    @FXML
    Button unlockCard;
    @FXML
    Button logout;
    @FXML
    VBox container;

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

        if (type.equals("error")) {
            alert.setAlertType(Alert.AlertType.ERROR);
        } else {
            alert.setAlertType(Alert.AlertType.INFORMATION);
        }

        alert.showAndWait();
    }


    private void loadWidget(String widgetName) {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/service-worker-widgets/" + widgetName + ".fxml"));

            Parent widget = loader.load();
            container.getChildren().clear();
            container.getChildren().add(widget);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    @FXML
    private void addUserAction(ActionEvent event) {
        loadWidget("createNewBankAccount");

        Button createNewBankAccount = (Button) ((Pane) container.getChildren().get(0)).getChildren().get(0);
        TextField lastName = (TextField) ((Pane) container.getChildren().get(0)).getChildren().get(1);
        TextField firstName = (TextField) ((Pane) container.getChildren().get(0)).getChildren().get(2);
        TextField middleName = (TextField) ((Pane) container.getChildren().get(0)).getChildren().get(3);
        TextField adress = (TextField) ((Pane) container.getChildren().get(0)).getChildren().get(4);
        TextField age = (TextField) ((Pane) container.getChildren().get(0)).getChildren().get(5);

        createNewBankAccount.setOnAction(event1 -> {

            try {
                Person p = new Person(lastName.getText(), firstName.getText(),
                        middleName.getText(), adress.getText(), Integer.valueOf(age.getText()));
                BankAccount account = atm.getServiceWorker().createNewAccount(p, atm.getConnector());
                showAlert("Successfully create new user\nAccountId = " + account.getAccountId(), "OK", "info");
            } catch (RequestException e) {
                showAlert(e.getMessage(), "Error", "error");
            }
        });
    }

    @FXML
    private void addCardAction(ActionEvent event) {

        loadWidget("addNewCreditCard");

        Button addNewCreditCard = (Button) ((Pane) container.getChildren().get(0)).getChildren().get(0);
        TextField accountId = (TextField) ((Pane) container.getChildren().get(0)).getChildren().get(1);

        addNewCreditCard.setOnAction(event1 -> {

            try {
                BankAccount b = new BankAccount(Integer.valueOf(accountId.getText()), new Person("", "", "", "", 18));
                CreditCard c = atm.getServiceWorker().addNewCreditCard(b, atm.getConnector());


                FileChooser fileChooser = new FileChooser();
                Stage stage = (Stage) container.getScene().getWindow();
                fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
                fileChooser.getExtensionFilters().clear();
                fileChooser.getExtensionFilters()
                           .add(new FileChooser.ExtensionFilter("Spacex-Technology card (*.sxcard)", "*.sxcard"));
                File file = fileChooser.showSaveDialog(stage);

                if(file != null) {
                    atm.getServiceWorker().createFileForCreditCard(c, file);
                }

            }
            catch (RequestException e) {
                showAlert(e.getMessage(), "Error", "error");
            }

        });
    }

    @FXML
    private void unlockCardAction(ActionEvent event) {
        loadWidget("unlockCard");

        Button getCardList = (Button) ((Pane) container.getChildren().get(0)).getChildren().get(0);
        VBox blockedCardsContainer = (VBox) ((ScrollPane)container.getChildren().get(1)).getContent();

        getCardList.setOnAction(event1 -> {
            try {
                List<String> blockedCards = atm.getListOfBlockedCards();
                if (blockedCards != null ) {
                    int n = blockedCards.size();

                    Map<Button, TextField> map = new HashMap<>(n);
                    for(int i = 0; i<n; i++) {
                        HBox hBox = FXMLLoader.load(getClass().getResource("/service-worker-widgets/blockedCardWidget.fxml"));

                        TextField textField = (TextField) hBox.getChildren().get(0);
                        Button button =  (Button) hBox.getChildren().get(1);

                        button.setOnAction(event2 -> {
                            String cardId =map.get(button).getText();
                            try {
                                atm.getServiceWorker().unlockCard(new CreditCard(cardId), atm.getConnector());
                                //HBox parent = atm.
                            }
                            catch (RequestException e) {

                            }
                        });

                        map.put(button, textField);
                    }

                }
            }
            catch (Exception e) {
                showAlert(e.getMessage(), "Error", "error");
            }
        });

    }

    @FXML
    private void addCashAction(ActionEvent event) {

        loadWidget("addCash");

        TextField available500 = (TextField) ((Pane) container.getChildren().get(0)).getChildren().get(0);
        TextField available200 = (TextField) ((Pane) container.getChildren().get(0)).getChildren().get(1);
        TextField available100 = (TextField) ((Pane) container.getChildren().get(0)).getChildren().get(2);
        TextField available50 = (TextField) ((Pane) container.getChildren().get(0)).getChildren().get(3);
        TextField available20 = (TextField) ((Pane) container.getChildren().get(0)).getChildren().get(4);
        TextField available10 = (TextField) ((Pane) container.getChildren().get(0)).getChildren().get(5);
        TextField available5 = (TextField) ((Pane) container.getChildren().get(0)).getChildren().get(6);
        TextField available2 = (TextField) ((Pane) container.getChildren().get(0)).getChildren().get(7);
        TextField available1 = (TextField) ((Pane) container.getChildren().get(0)).getChildren().get(8);

        TextField count500 = (TextField) ((Pane) container.getChildren().get(0)).getChildren().get(9);
        TextField count200 = (TextField) ((Pane) container.getChildren().get(0)).getChildren().get(10);
        TextField count100 = (TextField) ((Pane) container.getChildren().get(0)).getChildren().get(11);
        TextField count50 = (TextField) ((Pane) container.getChildren().get(0)).getChildren().get(12);
        TextField count20 = (TextField) ((Pane) container.getChildren().get(0)).getChildren().get(13);
        TextField count10 = (TextField) ((Pane) container.getChildren().get(0)).getChildren().get(14);
        TextField count5 = (TextField) ((Pane) container.getChildren().get(0)).getChildren().get(15);
        TextField count2 = (TextField) ((Pane) container.getChildren().get(0)).getChildren().get(16);
        TextField count1 = (TextField) ((Pane) container.getChildren().get(0)).getChildren().get(17);

        Button addCash = (Button) ((Pane) container.getChildren().get(0)).getChildren().get(18);

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
            atm.getVault().addCashToVault(count500.getLength() > 0 ? 500 * Double.valueOf(count500.getText()) : 0);
            atm.getVault().addCashToVault(count200.getLength() > 0 ? 200 * Double.valueOf(count200.getText()) : 0);
            atm.getVault().addCashToVault(count100.getLength() > 0 ? 100 * Double.valueOf(count100.getText()) : 0);
            atm.getVault().addCashToVault(count50.getLength() > 0 ? 50 * Double.valueOf(count50.getText()) : 0);
            atm.getVault().addCashToVault(count20.getLength() > 0 ? 20 * Double.valueOf(count20.getText()) : 0);
            atm.getVault().addCashToVault(count10.getLength() > 0 ? 10 * Double.valueOf(count10.getText()) : 0);
            atm.getVault().addCashToVault(count5.getLength() > 0 ? 5 * Double.valueOf(count5.getText()) : 0);
            atm.getVault().addCashToVault(count2.getLength() > 0 ? 2 * Double.valueOf(count2.getText()) : 0);
            atm.getVault().addCashToVault(count1.getLength() > 0 ? 1 * Double.valueOf(count1.getText()) : 0);

            showAlert("Cash successfully added", "OK", "info");
        });

    }
}
