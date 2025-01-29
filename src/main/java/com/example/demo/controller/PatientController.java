package com.example.demo.controller;

import com.example.demo.model.Patient;
import com.example.demo.util.DatabaseUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.*;
import java.util.Objects;

public class PatientController {
    @FXML
    private TextField nomField, prenomField, numTelField;
    @FXML
    private DatePicker dateNaissanceField;
    @FXML
    private ComboBox<String> sexeField;
    @FXML
    private TextArea adresseField;
    @FXML
    private TableView<Patient> patientTable;
    @FXML
    private TableColumn<Patient, String> nomColumn, prenomColumn, numTelColumn, adresseColumn, sexeColumn, dateNaissanceColumn;
    @FXML
    private TableColumn<Patient, Integer> idColumn;

    private Connection conn;
    private ObservableList<Patient> patientList = FXCollections.observableArrayList();

    @FXML
    public void initialize() throws SQLException {
        conn = DatabaseUtil.getConnection();
        sexeField.getItems().addAll("Homme", "Femme");
        loadPatients();
    }

    private void loadPatients() {
        patientList.clear();
        String sql = "SELECT * FROM patients";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                patientList.add(new Patient(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("adresse"),
                        rs.getString("num_tel"),
                        rs.getString("sexe"),
                        rs.getString("date_naissance")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        patientTable.setItems(patientList);
    }

    public void ajouterPatient(ActionEvent actionEvent) {
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String telephone = numTelField.getText();
        String adresse = adresseField.getText();
        String sexe = sexeField.getValue();
        String dateNaissance = dateNaissanceField.getValue().toString();

        if (nom.isEmpty() || prenom.isEmpty() || telephone.isEmpty() || adresse.isEmpty() || sexe == null || dateNaissance.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        String sql = "INSERT INTO patients (nom, prenom, adresse, num_tel, sexe, date_naissance) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nom);
            pstmt.setString(2, prenom);
            pstmt.setString(3, adresse);
            pstmt.setString(4, telephone);
            pstmt.setString(5, sexe);
            pstmt.setString(6, dateNaissance);
            pstmt.executeUpdate();
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Patient ajouté avec succès.");
            loadPatients();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void supprimerPatient(ActionEvent actionEvent) {
        Patient selected = patientTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez sélectionner un patient.");
            return;
        }

        String sql = "DELETE FROM patients WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String nom = "";
            pstmt.setInt(1, Patient.getId(nom));
            pstmt.executeUpdate();
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Patient supprimé.");
            loadPatients();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void backMain(ActionEvent actionEvent) {

        try {
            System.out.println("Back button clicked!");
            // Load the MainPage.fxml file
            Parent mainPageParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/demo/SecriteControle.fxml")));

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
}
