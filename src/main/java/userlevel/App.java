package userlevel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/app.fxml"));
        Scene mainScene = new Scene(root);
        primaryStage.setTitle("ATM");
        primaryStage.setScene(mainScene);


       // Parent client = FXMLLoader.load(getClass().getResource("/Client.fxml"));
       // Scene clientScene = new Scene(client);
       // primaryStage.setScene(clientScene);

      //  Parent serviceWorker = FXMLLoader.load(getClass().getResource("/ServiceWorker.fxml"));
       // Scene serviceWorkerScene = new Scene(serviceWorker);
        //primaryStage.setScene(serviseWorkerScene);
        primaryStage.show();
    }


}
