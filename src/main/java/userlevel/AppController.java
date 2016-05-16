package userlevel;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

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
    Button firstButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        firstButton.setOnAction(e -> {
            System.out.println("Button pressed");
        });
    }
}
