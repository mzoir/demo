package com.example.demo.controller;

import com.example.demo.model.FicheMedicale;
import com.example.demo.util.DatabaseUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

public class FicheajouController {
    // FXML UI components
    @FXML
    private TableView<FicheMedicale> tableFiches;
    @FXML
    private TableColumn<FicheMedicale, Integer> colId;
    @FXML
    private TableColumn<FicheMedicale, Integer> colPatientId;
    @FXML
    private TableColumn<FicheMedicale, String> colDescription;
    @FXML
    private TableColumn<FicheMedicale, String> colDateCreation;

    @FXML
    private TextField patientIdTextField;
    @FXML
    private TextField descriptionTextField;
    @FXML
    private TextField dossierMedicaleTextField;
    @FXML
    private TextField cinTextField;
    @FXML
    private TextField nomPrenomTextField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField heureTextField;
    @FXML
    private ComboBox<String> statutComboBox;

    @FXML
    private Button btnAjouter;
    @FXML
    private Button btnModifier;
    @FXML
    private Button btnSupprimer;

    private ObservableList<FicheMedicale> ficheList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPatientId.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDateCreation.setCellValueFactory(new PropertyValueFactory<>("dateCreation"));

        loadFichesMedicales();
    }

    @FXML
    private void insertFicheMedicale() {
        String checkQuery = "SELECT COUNT(*) FROM rendezvous WHERE CIN = ?";
        String query = "INSERT INTO fiches_medicales (patient_id, description, dossier_medicale, cin, nom_prenom, date, heure, statut) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {

            checkStmt.setString(1, cinTextField.getText());
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            if (count == 0) {
                showAlert("Aucun rendez-vous trouvé pour ce CIN. L'ajout de la fiche est annulé.");
                return;
            }

            try (PreparedStatement insertStmt = conn.prepareStatement(query)) {
                insertStmt.setInt(1, Integer.parseInt(patientIdTextField.getText()));
                insertStmt.setString(2, descriptionTextField.getText());
                insertStmt.setString(3, dossierMedicaleTextField.getText());
                insertStmt.setString(4, cinTextField.getText());
                insertStmt.setString(5, nomPrenomTextField.getText());
                insertStmt.setDate(6, Date.valueOf(datePicker.getValue()));
                insertStmt.setString(7, heureTextField.getText());
                insertStmt.setString(8, statutComboBox.getValue());

                int rowsAffected = insertStmt.executeUpdate();

                if (rowsAffected > 0) {
                    showAlert("Fiche médicale ajoutée avec succès.");
                    clearFields();
                    loadFichesMedicales(); // Refresh the table
                } else {
                    showAlert("Aucune fiche médicale ajoutée.");
                }
            }
        } catch (SQLException e) {
            showAlert("Erreur lors de l'ajout de la fiche médicale.");
            e.printStackTrace();
        }
    }

    private void loadFichesMedicales() {
        String query = "SELECT id, patient_id, description, dossier_medicale, date FROM fiches_medicales";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            ficheList.clear(); // Clear the list before filling it

            while (rs.next()) {
                ficheList.add(new FicheMedicale(
                        rs.getInt("id"),
                        rs.getInt("patient_id"),
                        rs.getString("description"),
                        rs.getString("dossier_medicale"),
                        rs.getDate("date").toLocalDate() // Assuming you have a LocalDate field in your model
                ));
            }

            tableFiches.setItems(ficheList);
            System.out.println("Fiches médicales chargées avec succès.");

        } catch (SQLException e) {
            System.err.println("Erreur lors du chargement des fiches médicales.");
            e.printStackTrace();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        patientIdTextField.clear();
        descriptionTextField.clear();
        dossierMedicaleTextField.clear();
        cinTextField.clear();
        nomPrenomTextField.clear();
        datePicker.setValue(null);
        heureTextField.clear();
        statutComboBox.getSelectionModel().clearSelection();
    }
}