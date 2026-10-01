package com.gym.dao;

import com.gym.config.DatabaseConnection;
import com.gym.model.Member;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {

    public boolean addMember(Member member) {
        String sql = "INSERT INTO members (full_name, phone, plan_type, join_date, expiry_date) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, member.getFullName());
            stmt.setString(2, member.getPhone());
            stmt.setString(3, member.getPlanType());
            stmt.setDate(4, Date.valueOf(member.getJoinDate()));
            stmt.setDate(5, Date.valueOf(member.getExpiryDate()));

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List getAllMembers() {
        List members = new ArrayList<>();
        String sql = "SELECT * FROM members";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Member member = new Member(
                        rs.getInt("member_id"),
                        rs.getString("full_name"),
                        rs.getString("phone"),
                        rs.getString("plan_type"),
                        rs.getDate("join_date").toLocalDate(),
                        rs.getDate("expiry_date").toLocalDate()
                );
                members.add(member);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }
}