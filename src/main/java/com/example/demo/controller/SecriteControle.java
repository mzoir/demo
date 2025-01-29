package com.example.demo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SecriteControle {
    public void handleRendezVous(ActionEvent actionEvent) {

        try {
            Parent loginPageParent = FXMLLoader.load(getClass().getResource("/com/example/demo/RendezVousPage.fxml"));
            Scene loginPageScene = new Scene(loginPageParent);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(loginPageScene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }





    }

    public void goToMedicalRecordPage(ActionEvent actionEvent) {
        try {
            System.out.println("Back button clicked!");
            // Load the MainPage.fxml file
            Parent mainPageParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/demo/ficheajout.fxml")));

            // Create a new scene with the loaded layout
            Scene mainPageScene = new Scene(mainPageParent);

            // Get the current stage (window) from the event source
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Set the new scene to the current stage
            window.setScene(mainPageScene);

            // Show the updated stage
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void goToPatientPage(ActionEvent actionEvent) {
    }
    // Method to handle "Gérer Paiements"
    public void handlePaiements() {
        // Add your logic for managing payments
    }

    // Method to handle "Gérer Rapport"
    public void handleRapport() {
        // Add your logic for generating reports
    }

    // Method to handle "Créer Secrétaire"
    public void handleCreateSecretary() {
        // Add your logic for creating a new secretary
    }
    @FXML
    public void backMain(ActionEvent actionEvent) {
        try {
            System.out.println("Back button clicked!");
            // Load the MainPage.fxml file
            Parent mainPageParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/demo/MainPage.fxml")));

            // Create a new scene with the loaded layout
            Scene mainPageScene = new Scene(mainPageParent);

            // Get the current stage (window) from the event source
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Set the new scene to the current stage
            window.setScene(mainPageScene);

            // Show the updated stage
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleRendezVousList(ActionEvent actionEvent) {
        try {
            System.out.println("Back button clicked!");
            // Load the MainPage.fxml file
            Parent mainPageParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/demo/Listofrandezevous.fxml")));

            // Create a new scene with the loaded layout
            Scene mainPageScene = new Scene(mainPageParent);

            // Get the current stage (window) from the event source
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Set the new scene to the current stage
            window.setScene(mainPageScene);

            // Show the updated stage
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void gotoPatients(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/Patientsajout.fxml"));
            Parent root = loader.load();

            // Get the current stage from the button click event
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Switch scene
            stage.setScene(new Scene(root));
            stage.setTitle("Gestion des Patients");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();}
    }
}