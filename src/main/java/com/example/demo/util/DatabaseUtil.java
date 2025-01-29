package com.example.demo.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {

    // Database connection details
    private static final String URL = "jdbc:mysql://localhost:3306/kine_centre"; // Replace with your database URL
    private static final String USER = "mazoir"; // Replace with your database username
    private static final String PASSWORD = "00001"; // Replace with your database password

    /**
     * Get database connection
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * Check if database connection is successful
     */
    public static boolean isDatabaseConnected() {
        try (Connection connection = getConnection()) {
            // If the connection is successful, return true
            if (connection != null && !connection.isClosed()) {
                System.out.println("Database connected successfully.");
                return true;
            }
        } catch (SQLException e) {
            // Handle the exception and show error message
            System.out.println("Error: Unable to connect to the database.");
            e.printStackTrace();
        }
        return false;
    }
}
