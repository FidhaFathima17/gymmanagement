package com.gym.dao;

import com.gym.config.DatabaseConnection;
import com.gym.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public User authenticateUser(String username, String password) {

        String sql =
                "SELECT user_id, username, password, role " +
                        "FROM users " +
                        "WHERE username = ? " +
                        "AND password = ? " +
                        "AND status = 'ACTIVE'";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    return new User(
                            rs.getInt("user_id"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("role")
                    );
                }
            }

        } catch (SQLException e) {

            System.err.println("=================================");
            System.err.println("DATABASE / LOGIN ERROR");
            System.err.println("=================================");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

        return null;
    }
}