package userlevel;
//package apilevel;

import apilevel.*;
import datalevel.DatabaseConnector;
import datalevel.RequestException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

import static userlevel.App.client;

public class WorkerController implements Initializable {

    // Worker
    @FXML Button addCashSW;
    @FXML TextField cashAddFieldSW;


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        ServiceWorker currentWorker = new ServiceWorker(App.getWorkerKey());

        // ServiceWorker
        addCashSW.setOnAction(e -> {
            currentWorker.addCash(Double.parseDouble(cashAddFieldSW.getText()));
        });

/*
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
