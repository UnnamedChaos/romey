package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Romeybot");
        primaryStage.setScene(new Scene(root, 810, 800));
        primaryStage.show();
        Platform.setImplicitExit(false);


    }


    public static void main(String[] args) {
        launch(args);
    }
}
