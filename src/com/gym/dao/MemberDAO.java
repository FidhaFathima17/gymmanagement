package com.gym.dao;

import com.gym.config.DatabaseConnection;
import com.gym.model.Member;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {

    // ==========================================
    // ADD MEMBER
    // ==========================================
    public boolean addMember(Member member, int userId) {

        String sql =
                "INSERT INTO member_profiles " +
                        "(user_id, full_name, date_of_birth, gender, address, emergency_contact) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, userId);
            stmt.setString(2, member.getFullName());

            if (member.getJoinDate() != null) {
                stmt.setDate(3, Date.valueOf(member.getJoinDate()));
            } else {
                stmt.setNull(3, java.sql.Types.DATE);
            }

            stmt.setString(4, null);
            stmt.setString(5, null);
            stmt.setString(6, member.getPhone());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println("Error adding member:");
            e.printStackTrace();

            return false;
        }
    }

    // ==========================================
    // GET ALL MEMBERS
    // ==========================================
    public List<Member> getAllMembers() {

        List<Member> members = new ArrayList<>();

        String sql =
                "SELECT " +
                        "mp.member_id, " +
                        "mp.full_name, " +
                        "u.phone, " +
                        "mp.date_of_birth " +
                        "FROM member_profiles mp " +
                        "JOIN users u ON mp.user_id = u.user_id " +
                        "ORDER BY mp.member_id DESC";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {

            while (rs.next()) {

                int id =
                        rs.getInt("member_id");

                String fullName =
                        rs.getString("full_name");

                String phone =
                        rs.getString("phone");

                Date dob =
                        rs.getDate("date_of_birth");

                java.time.LocalDate date =
                        dob != null
                                ? dob.toLocalDate()
                                : null;

                Member member =
                        new Member(
                                id,
                                fullName,
                                phone,
                                "N/A",
                                date,
                                null
                        );

                members.add(member);
            }

        } catch (SQLException e) {

            System.err.println("Error loading members:");
            e.printStackTrace();
        }

        return members;
    }

    // ==========================================
    // DELETE MEMBER
    // ==========================================
    public boolean deleteMember(int memberId) {

        String sql =
                "DELETE FROM member_profiles " +
                        "WHERE member_id = ?";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, memberId);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println("Error deleting member:");
            e.printStackTrace();

            return false;
        }
    }

    // ==========================================
    // SEARCH MEMBERS
    // ==========================================
    public List<Member> searchMembers(String keyword) {

        List<Member> members = new ArrayList<>();

        String sql =
                "SELECT " +
                        "mp.member_id, " +
                        "mp.full_name, " +
                        "u.phone, " +
                        "mp.date_of_birth " +
                        "FROM member_profiles mp " +
                        "JOIN users u ON mp.user_id = u.user_id " +
                        "WHERE mp.full_name LIKE ? " +
                        "OR u.phone LIKE ? " +
                        "ORDER BY mp.member_id DESC";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            String search =
                    "%" + keyword + "%";

            stmt.setString(1, search);
            stmt.setString(2, search);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {

                    Date dob =
                            rs.getDate("date_of_birth");

                    java.time.LocalDate date =
                            dob != null
                                    ? dob.toLocalDate()
                                    : null;

                    Member member =
                            new Member(
                                    rs.getInt("member_id"),
                                    rs.getString("full_name"),
                                    rs.getString("phone"),
                                    "N/A",
                                    date,
                                    null
                            );

                    members.add(member);
                }
            }

        } catch (SQLException e) {

            System.err.println("Error searching members:");
            e.printStackTrace();
        }

        return members;
    }
}