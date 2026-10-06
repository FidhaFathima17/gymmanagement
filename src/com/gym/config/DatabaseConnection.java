package com.gym.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/gym_db";
    private static final String USER = "root";

    // IMPORTANT:
    // Replace "" with your actual MySQL password.
    // If your MySQL root user has NO password, keep it as "".
    private static final String PASSWORD = "";

    private DatabaseConnection() {
        // Prevent object creation
    }

    public static Connection getConnection() throws SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException(
                    "MySQL JDBC Driver not found. Check mysql-connector-j in the lib folder.",
                    e
            );
        }

        return DriverManager.getConnection(
                URL,
                USER,
                PASSWORD
        );
    }
}