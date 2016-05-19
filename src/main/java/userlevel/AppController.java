package userlevel;
//package apilevel;

import apilevel.AtmClientSingleton;
import apilevel.ServiceWorker;
import apilevel.Person;
import apilevel.BankAccount;
import apilevel.CreditCard;
import datalevel.DatabaseConnector;
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
    @FXML
    // Client
    Button showBalance;
    Button withdrawCash;
    Button changePin;
    TextField newPinField;
    Button addCash;
    TextField cashAddField;
    // ServiceWorker
    Button addCashSW;
    TextField cashAddFieldSW;
    Button createNewBankAccount;
    TextField bankAccountId;
    TextField lastName;
    TextField firstName;
    TextField middleName;
    TextField adress;
    TextField age;
    Button addNewCreditCard;
    Button unlockCard;
    TextField cardId;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Client
        showBalance.setOnAction(e -> {
            currentCard.showBalance();
        });

        withdrawCash.setOnAction(e -> {
            currentCard.withdrawCash();
        });

        changePin.setOnAction(e -> {
            currentCard.changePin(Integer.parseInt(newPinField.getText()));
        });

        addCash.setOnAction(e -> {
            currentCard.addCash(Double.parseDouble(cashAddField.getText()));
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
        });

    }
}
