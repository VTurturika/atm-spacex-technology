package userlevel;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        Pane mainLayout = new Pane();
        Scene mainScene = new Scene(mainLayout, 400, 400);
        primaryStage.setScene(mainScene);
        primaryStage.show();

    }
}
