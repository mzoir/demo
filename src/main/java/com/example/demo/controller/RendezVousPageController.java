package com.example.demo.controller;

import com.example.demo.util.DatabaseUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import com.example.demo.model.Patient;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RendezVousPageController {

    @FXML
    private TextField nomPatient;

    @FXML
    private DatePicker dateRendezVous;

    @FXML
    private TextField heureRendezVous;

    /** Confirmer et enregistrer le rendez-vous */

    private int getPatientIdByName(String nom) {
        String sql = "SELECT id FROM patients WHERE nom = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nom);  // Set the patient name parameter
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id"); // Return the ID if found
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if no patient is found
    }

    /** Retourner à la page de connexion */
    @FXML
    public void retournerALogin(ActionEvent event) {
        try {
            Parent loginPage = FXMLLoader.load(getClass().getResource("/com/example/demo/SecriteControle.fxml"));
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(loginPage));
            stage.show();
        } catch (IOException e) {
            showAlert("Erreur", "Impossible de charger la page de connexion.");
            e.printStackTrace();
        }
    }

    /** Afficher les alertes */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void confirmerRendezVous(ActionEvent actionEvent) {
    }
}