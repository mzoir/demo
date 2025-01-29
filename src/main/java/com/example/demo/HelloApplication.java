package com.example.demo;

import com.example.demo.util.DatabaseUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MainPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        boolean isConnected = DatabaseUtil.isDatabaseConnected();
        if (isConnected) {
            System.out.println("Proceed with your database operations.");
        } else {
            System.out.println("Cannot proceed, database is not connected.");
        }
        launch();
    }
}