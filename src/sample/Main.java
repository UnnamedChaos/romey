package sample;

import com.sun.deploy.ref.Helpers;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;

public class Main extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Romeybot");
        primaryStage.setScene(new Scene(root, 810, 800));
        primaryStage.show();


    }




    public static void main(String[] args) {
        launch(args);
    }
}
