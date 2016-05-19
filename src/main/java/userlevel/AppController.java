package userlevel;
//package apilevel;

import apilevel.AtmClientSingleton;
import apilevel.ServiceWorker;
import apilevel.Person;
import apilevel.BankAccount;
import apilevel.CreditCard;
import datalevel.DatabaseConnector;
import datalevel.RequestException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * <what class do>
 *
 * @author Kyrylo Havrylenko
 * @see
 */
public class AppController implements Initializable {

    // Client
    @FXML Button showBalance;
    @FXML Button withdrawCash;
    @FXML Button changePin;
    @FXML TextField newPinField;
    @FXML Button addCash;
    @FXML TextField cashAddField;
    // ServiceWorker
    @FXML Button addCashSW;
    @FXML TextField cashAddFieldSW;
    @FXML Button createNewBankAccount;
    @FXML TextField bankAccountId;
    @FXML TextField lastName;
    @FXML TextField firstName;
    @FXML TextField middleName;
    @FXML TextField adress;
    @FXML TextField age;
    @FXML Button addNewCreditCard;
    @FXML Button unlockCard;
    @FXML TextField cardId;
    @FXML TextField pin;
    @FXML TextField cardNumber;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AtmClientSingleton client = AtmClientSingleton.getInstance();
        client.setConnector(new DatabaseConnector());
        CreditCard currentCard = new CreditCard(cardNumber.getText(), pin.getText());
        client.setCurrentCard(currentCard);
        // Client
        showBalance.setOnAction(e -> {
            try {
                client.showBalance();
            }catch (RequestException error){
                error.printStackTrace();
            }
        });

        /*withdrawCash.setOnAction(e -> {
            client.withdrawCash();
        });

        changePin.setOnAction(e -> {
            client.changePin(Integer.parseInt(newPinField.getText()));
        });

        addCash.setOnAction(e -> {
            client.addCash(Double.parseDouble(cashAddField.getText()));
        });

        // ServiceWorker
        addCash.setOnAction(e -> {
            currentWorker.addCash(Double.parseDouble(cashAddFieldSW.getText()));
        });

        createNewBankAccount.setOnAction(e -> {
            Person newPerson = new Person(lastName.getText(), firstName.getText(), middleName.getText(), adress.getText(), Integer.parseInt(age.getText()));
            BankAccount newAccount = new BankAccount(Integer.parseInt(bankAccountId.getText()), newPerson);
            currentWorker.createNewAccount(newPerson, connector);
        });

        addNewCreditCard.setOnAction(e -> {
            Person currentPerson = new Person(lastName.getText(), firstName.getText(), middleName.getText(), adress.getText(), Integer.parseInt(age.getText()));
            BankAccount currentAccount = new BankAccount(Integer.parseInt(bankAccountId.getText()), currentPerson);
            addNewCreditCard(currentAccount, connector);

        });

        unlockCard.setOnAction(e -> {
            CreditCard currentCard = new CreditCard(cardId.getText());
            currentWorker.unlockCard(currentCard, connector);
        });*/

    }
}
