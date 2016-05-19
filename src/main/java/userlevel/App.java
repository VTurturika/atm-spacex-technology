package userlevel;

import apilevel.AtmClientSingleton;
import apilevel.ServiceWorker;
import datalevel.RequestException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sun.security.jca.GetInstance;

import java.io.IOException;

public class App extends Application {

    public static Stage PRIMARY_STAGE;
    public static String WORKER_KEY;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        PRIMARY_STAGE = primaryStage;

        Parent root = FXMLLoader.load(getClass().getResource("/ServiceWorker.fxml"));
        Scene mainScene = new Scene(root);
        mainScene.getStylesheets().add("test.css");
        primaryStage.setResizable(false);
        primaryStage.setTitle("ATM");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }
}
