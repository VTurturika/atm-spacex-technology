package userlevel;

import apilevel.AtmClientSingleton;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;


public class WorkerController implements Initializable {

    private AtmClientSingleton atm;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setAtm(AtmClientSingleton atm) {
        this.atm = atm;
    }
}
