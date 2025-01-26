package com.example.demo.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainController {

    @FXML
    private Button loginButton;

    @FXML
    private Button appointmentsButton;

    @FXML
    private void goToLoginPage(ActionEvent event) {
        try {
            Parent loginPageParent = FXMLLoader.load(getClass().getResource("/com/example/demo/LoginPage.fxml"));
            Scene loginPageScene = new Scene(loginPageParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(loginPageScene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToAppointmentsPage() {
        System.out.println("Navigating to Appointments Page");
        // Add your navigation logic here
    }

    @FXML
    private void goToLoginPageAdmi(ActionEvent event) {
        try {
            Parent loginPageParent = FXMLLoader.load(getClass().getResource("/com/example/demo/LoginPage.fxml"));
            Scene loginPageScene = new Scene(loginPageParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(loginPageScene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
