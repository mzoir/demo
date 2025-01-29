package com.example.demo.controller;

import com.example.demo.util.DatabaseUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class LoginUserController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorMessage;

    /**
     * Event handler for the Login button.
     */
    @FXML
    public void handleLoginadmi(ActionEvent actionEvent) {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        // Input validation
        if (username.isEmpty() || password.isEmpty()) {
            errorMessage.setText("Username and password cannot be empty.");
            return;
        }

        try (Connection connection = DatabaseUtil.getConnection()) {
            String sql = "SELECT * FROM utilisateur WHERE username = ? AND password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        // Check if role is "administrateur"
                        String role = resultSet.getString("role"); // Assuming the role column is named "role"
                        if ("admin".equals(role)) {
                            // Load the Admin Console
                            loadAdminConsole(actionEvent);
                        } else {
                            errorMessage.setText("You do not have permission to access the admin console.");
                        }
                    } else {
                        errorMessage.setText("Invalid username or password.");
                    }
                }
            }
        } catch (SQLException e) {
            errorMessage.setText("Database connection error.");
            e.printStackTrace();
        }
    }

    @FXML
    public void handleLogin(ActionEvent actionEvent) {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        // Input validation
        if (username.isEmpty() || password.isEmpty()) {
            errorMessage.setText("Username and password cannot be empty.");
            return;
        }

        try (Connection connection = DatabaseUtil.getConnection()) {
            String sql = "SELECT * FROM utilisateur WHERE username = ? AND password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        // Check if role is "secretaire"
                        String role = resultSet.getString("role"); // Assuming the role column is named "role"
                        if ("secretaire".equals(role)) {
                            // Load the Admin Console
                            loadSecrConsole(actionEvent);
                        } else {
                            errorMessage.setText("You do not have permission to access the admin console.");
                        }
                    } else {
                        errorMessage.setText("Invalid username or password.");
                    }
                }
            }
        } catch (SQLException e) {
            errorMessage.setText("Database connection error.");
            e.printStackTrace();
        }
    }
    private void loadSecrConsole(ActionEvent actionEvent) {
        try {
            System.out.println("Logging in as secrétaire...");
            // Charger le fichier SecretaireControle.fxml
            Parent secrConsoleParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/demo/SecriteControle.fxml")));

            // Créer une nouvelle scène avec le layout chargé
            Scene secrConsoleScene = new Scene(secrConsoleParent);

            // Obtenir la fenêtre actuelle depuis l'événement
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Appliquer la nouvelle scène à la fenêtre actuelle
            window.setScene(secrConsoleScene);

            // Afficher la mise à jour
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadAdminConsole(ActionEvent actionEvent) {
        try {
            System.out.println("Logging in as administrator...");
            // Load the adminconsole.fxml file
            Parent adminConsoleParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/demo/adminconsole.fxml")));

            // Create a new scene with the loaded layout
            Scene adminConsoleScene = new Scene(adminConsoleParent);

            // Get the current stage (window) from the event source
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Set the new scene to the current stage
            window.setScene(adminConsoleScene);

            // Show the updated stage
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Validates user credentials.
     *
     * @param username the entered username
     * @param password the entered password
     * @return true if credentials are valid, false otherwise
     */
    private boolean isValidCredentials(String username, String password) {
        // For demonstration, using hardcoded credentials
        return "admin".equals(username) && "password".equals(password);
    }

    /**
     * Navigates back to the main page.
     *
     * @param actionEvent the action event triggered by the button
     */
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

}
