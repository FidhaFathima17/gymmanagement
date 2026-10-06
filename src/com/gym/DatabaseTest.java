package com.gym;

import com.gym.config.DatabaseConnection;

import java.sql.Connection;

public class DatabaseTest {

    public static void main(String[] args) {

        try {

            Connection conn = DatabaseConnection.getConnection();

            System.out.println("=================================");
            System.out.println("DATABASE CONNECTION SUCCESSFUL");
            System.out.println("=================================");
            System.out.println("Connected to: " + conn.getCatalog());

            conn.close();

        } catch (Exception e) {

            System.out.println("=================================");
            System.out.println("DATABASE CONNECTION FAILED");
            System.out.println("=================================");

            e.printStackTrace();
        }
    }
}
