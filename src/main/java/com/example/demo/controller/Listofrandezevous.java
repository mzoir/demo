package com.example.demo.controller;

import com.example.demo.util.DatabaseUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Listofrandezevous {

    @FXML
    private Button confirmRendezvousButton;

    @FXML
    private ListView<String> rendezvousListView; // Reference to the ListView in FXML

    // Method to get all appointments from the database
    public void getAllRendezvous() {
        String sql = "SELECT r.id, r.date, r.statut, r.heure, r.CIN, r.nom_prenom " +
                "FROM rendezvous r JOIN patients p ON r.patient_id = p.id";  // Modified query to join with patient table
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();

            // Clear the list view before adding new data
            rendezvousListView.getItems().clear();

            // Check if the result set is not empty and populate the ListView
            while (rs.next()) {
                // Fetch actual details (updated with CIN and nom_prenom)
                String id = rs.getString("id");
                String date = rs.getString("date");
                String status = rs.getString("statut");
                String appointmentTime = rs.getString("heure");
                String cin = rs.getString("CIN");
                String nomPrenom = rs.getString("nom_prenom");

                // Construct the string for each appointment
                String rendezvousDetails = String.format("ID: %s | Date: %s | Status: %s | Time: %s | CIN: %s | Nom: %s",
                        id, date, status, appointmentTime, cin, nomPrenom);

                // Add item to the ListView
                rendezvousListView.getItems().add(rendezvousDetails);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erreur", "Erreur lors de la récupération des rendez-vous.");
        }
    }

    // Initialize method to load appointments when the scene is loaded
    @FXML
    public void initialize() {
        getAllRendezvous();  // Fetch and display appointments when the scene is loaded
    }

    // Method to handle confirmation of a selected appointment
    @FXML
    public void handleConfirmRendezvous(ActionEvent event) {
        String selectedItem = rendezvousListView.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            showAlert("Erreur", "Veuillez sélectionner un rendez-vous.");
            return;
        }

        // Extract the appointment ID from the selected item (assuming ID is the first part)
        String id = selectedItem.split("\\|")[0].replace("ID: ", "").trim();

        String sql = "UPDATE rendezvous SET statut = 'confirmer' WHERE id = ? AND statut = 'En attente'";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                showAlert("Succès", "Le rendez-vous a été confirmé.");
                getAllRendezvous(); // Refresh the list
            } else {
                showAlert("Info", "Aucun rendez-vous en attente trouvé pour cet ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erreur", "Une erreur s'est produite lors de la confirmation du rendez-vous.");
        }
    }

    // Utility method to show alerts
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
