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

public class ClientController implements Initializable {

    // Client
    @FXML Button showBalanceButton;
    @FXML Button withdrawCashButton;
    @FXML Button changePinButton;
    @FXML Button addCashButton;

    @FXML TextField newPinField;
    @FXML TextField cashAddField;
    @FXML TextField cashWithDrawField;
    @FXML TextField showBalanceField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        AtmClientSingleton client = App.getClient();

        // Client
        showBalanceButton.setOnAction(e -> {
            try {
                client.showBalance();
                showBalanceField.setText(""+client.showBalance());
                System.out.println("Balance:" + client.showBalance());
            }catch (RequestException error){
                error.printStackTrace();
            }
        });

        withdrawCashButton.setOnAction(e -> {
            try {
                Double needToWithDraw = Double.parseDouble(cashWithDrawField.getText());
                System.out.println("Need to withdraw:" + needToWithDraw);
                client.withdrawCash(needToWithDraw);
            }catch (RequestException error){
                error.printStackTrace();
            }
        });

        changePinButton.setOnAction(e -> {
            try {
                client.changePin(newPinField.getText());
                System.out.println("Setting new pin");
            }catch (RequestException error){
                error.printStackTrace();
            }
        });

        addCashButton.setOnAction(e -> {
            try {
                client.addCash(Double.parseDouble(cashAddField.getText()));
                System.out.println("Add " + cashAddField.getText() + " cash");
            }catch (RequestException error){
                error.printStackTrace();
            }
        });
/*
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
