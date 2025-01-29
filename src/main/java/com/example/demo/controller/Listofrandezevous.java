package com.example.demo.controller;

import com.example.demo.util.DatabaseUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Listofrandezevous {

    @FXML
    private ListView<String> rendezvousListView; // Reference to the ListView in FXML

    // Method to get all appointments from the database
    public void getAllRendezvous() {
        String sql = "SELECT * FROM rendezvous";  // Query to get all appointments
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();

            // Clear the list view before adding new data
            rendezvousListView.getItems().clear();

            // Check if the result set is not empty and populate the ListView
            while (rs.next()) {
                // Fetch actual details (change these based on your column names)
                String id = rs.getString("id");
                String date = rs.getString("date");
                String status = rs.getString("statut");  // Assuming you have 'patient_name' column
                String appointmentTime = rs.getString("heure");  // Assuming 'heure' is the time of the appointment

                // Construct the string for each appointment
                String rendezvousDetails = "ID: " + id + " | Date: " + date
                        + " | Patient: " + status + " | Time: " + appointmentTime;

                // Add item to the ListView
                rendezvousListView.getItems().add(rendezvousDetails);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Initialize method to load appointments when the scene is loaded
    @FXML
    public void initialize() {
        getAllRendezvous();  // Fetch and display appointments when the scene is loaded
    }

    public void handleConfirmRendezvous(ActionEvent actionEvent) {
    }
}
