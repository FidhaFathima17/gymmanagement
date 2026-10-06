package com.gym.dao;

import com.gym.config.DatabaseConnection;
import com.gym.model.MemberTrainer;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MemberTrainerDAO {

    // =========================================================
    // ASSIGN TRAINER
    // =========================================================

    public boolean assignTrainer(
            int memberId,
            int trainerId
    ) {

        Connection conn = null;

        try {

            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // -------------------------------------------------
            // Deactivate current trainer
            // -------------------------------------------------

            String deactivateSql =
                    "UPDATE member_trainers " +
                            "SET status = 'INACTIVE' " +
                            "WHERE member_id = ? " +
                            "AND status = 'ACTIVE'";

            try (PreparedStatement stmt =
                         conn.prepareStatement(deactivateSql)) {

                stmt.setInt(1, memberId);
                stmt.executeUpdate();
            }

            // -------------------------------------------------
            // Create new assignment
            // -------------------------------------------------

            String insertSql =
                    "INSERT INTO member_trainers " +
                            "(member_id, trainer_id, assigned_date, status) " +
                            "VALUES (?, ?, ?, 'ACTIVE')";

            try (PreparedStatement stmt =
                         conn.prepareStatement(insertSql)) {

                stmt.setInt(1, memberId);
                stmt.setInt(2, trainerId);
                stmt.setDate(
                        3,
                        Date.valueOf(LocalDate.now())
                );

                stmt.executeUpdate();
            }

            conn.commit();

            return true;

        } catch (SQLException e) {

            if (conn != null) {

                try {
                    conn.rollback();
                } catch (SQLException ignored) {
                }
            }

            e.printStackTrace();

            return false;

        } finally {

            if (conn != null) {

                try {
                    conn.close();
                } catch (SQLException ignored) {
                }
            }
        }
    }

    // =========================================================
    // GET ACTIVE TRAINER FOR MEMBER
    // =========================================================

    public MemberTrainer getActiveTrainerForMember(
            int memberId
    ) {

        String sql =
                "SELECT mt.assignment_id, " +
                        "mt.member_id, " +
                        "mt.trainer_id, " +
                        "mp.full_name AS member_name, " +
                        "tp.full_name AS trainer_name, " +
                        "tp.specialization, " +
                        "mt.assigned_date, " +
                        "mt.status " +
                        "FROM member_trainers mt " +
                        "JOIN member_profiles mp " +
                        "ON mt.member_id = mp.member_id " +
                        "JOIN trainer_profiles tp " +
                        "ON mt.trainer_id = tp.trainer_id " +
                        "WHERE mt.member_id = ? " +
                        "AND mt.status = 'ACTIVE' " +
                        "LIMIT 1";

        try (
                Connection conn =
                        DatabaseConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, memberId);

            try (ResultSet rs =
                         stmt.executeQuery()) {

                if (rs.next()) {

                    return new MemberTrainer(
                            rs.getInt("assignment_id"),
                            rs.getInt("member_id"),
                            rs.getInt("trainer_id"),
                            rs.getString("member_name"),
                            rs.getString("trainer_name"),
                            rs.getString("specialization"),
                            rs.getDate("assigned_date")
                                    .toLocalDate(),
                            rs.getString("status")
                    );
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return null;
    }

    // =========================================================
    // GET ALL ASSIGNMENTS
    // =========================================================

    public List<MemberTrainer> getAllAssignments() {

        List<MemberTrainer> list =
                new ArrayList<>();

        String sql =
                "SELECT mt.assignment_id, " +
                        "mt.member_id, " +
                        "mt.trainer_id, " +
                        "mp.full_name AS member_name, " +
                        "tp.full_name AS trainer_name, " +
                        "tp.specialization, " +
                        "mt.assigned_date, " +
                        "mt.status " +
                        "FROM member_trainers mt " +
                        "JOIN member_profiles mp " +
                        "ON mt.member_id = mp.member_id " +
                        "JOIN trainer_profiles tp " +
                        "ON mt.trainer_id = tp.trainer_id " +
                        "ORDER BY mt.assignment_id DESC";

        try (
                Connection conn =
                        DatabaseConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql);

                ResultSet rs =
                        stmt.executeQuery()
        ) {

            while (rs.next()) {

                list.add(
                        new MemberTrainer(
                                rs.getInt("assignment_id"),
                                rs.getInt("member_id"),
                                rs.getInt("trainer_id"),
                                rs.getString("member_name"),
                                rs.getString("trainer_name"),
                                rs.getString("specialization"),
                                rs.getDate("assigned_date")
                                        .toLocalDate(),
                                rs.getString("status")
                        )
                );
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return list;
    }

    // =========================================================
    // REMOVE TRAINER
    // =========================================================

    public boolean removeTrainer(int memberId) {

        String sql =
                "UPDATE member_trainers " +
                        "SET status = 'INACTIVE' " +
                        "WHERE member_id = ? " +
                        "AND status = 'ACTIVE'";

        try (
                Connection conn =
                        DatabaseConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, memberId);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }
}