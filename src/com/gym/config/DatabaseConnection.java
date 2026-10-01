package com.gym.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/gym_db";
    private static final String USER = "root";     // Replace with your local MySQL username
    private static final String PASSWORD = "password"; // Replace with your local MySQL password

    private static Connection connection = null;

    private DatabaseConnection() {} // Private constructor prevents direct instantiation

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Database Connection Established Successfully!");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL Driver class missing! Make sure mysql-connector JAR is in lib.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Database connection failed! Ensure MySQL service is running.");
            e.printStackTrace();
        }
        return connection;
    }
}