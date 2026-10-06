package com.gym.dao;

import com.gym.config.DatabaseConnection;
import com.gym.model.WorkoutExercise;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkoutPlanDAO {

    // =========================================================
    // ASSIGNED MEMBER CLASS
    // =========================================================

    public static class AssignedMember {

        private int memberId;
        private String memberName;

        public AssignedMember(int memberId, String memberName) {
            this.memberId = memberId;
            this.memberName = memberName;
        }

        public int getMemberId() {
            return memberId;
        }

        public String getMemberName() {
            return memberName;
        }

        @Override
        public String toString() {
            return memberName + " (ID: " + memberId + ")";
        }
    }

    // =========================================================
    // WORKOUT PLAN INFO CLASS
    // =========================================================

    public static class WorkoutPlanInfo {

        private int planId;
        private int memberId;
        private String memberName;
        private String planName;
        private String description;
        private Date createdDate;
        private String status;

        public WorkoutPlanInfo(
                int planId,
                int memberId,
                String memberName,
                String planName,
                String description,
                Date createdDate,
                String status) {

            this.planId = planId;
            this.memberId = memberId;
            this.memberName = memberName;
            this.planName = planName;
            this.description = description;
            this.createdDate = createdDate;
            this.status = status;
        }

        public int getPlanId() {
            return planId;
        }

        public int getMemberId() {
            return memberId;
        }

        public String getMemberName() {
            return memberName;
        }

        public String getPlanName() {
            return planName;
        }

        public String getDescription() {
            return description;
        }

        public Date getCreatedDate() {
            return createdDate;
        }

        public String getStatus() {
            return status;
        }

        // Important for JComboBox
        @Override
        public String toString() {
            return planName
                    + " - "
                    + memberName
                    + " ["
                    + status
                    + "]";
        }
    }

    // =========================================================
    // GET ASSIGNED MEMBERS
    // =========================================================

    public List<AssignedMember> getAssignedMembers(int trainerId) {

        List<AssignedMember> members = new ArrayList<>();

        String sql =
                "SELECT DISTINCT mp.member_id, mp.full_name " +
                        "FROM member_trainers mt " +
                        "JOIN member_profiles mp " +
                        "ON mt.member_id = mp.member_id " +
                        "WHERE mt.trainer_id = ? " +
                        "AND mt.status = 'ACTIVE' " +
                        "ORDER BY mp.full_name";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, trainerId);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {

                    members.add(
                            new AssignedMember(
                                    rs.getInt("member_id"),
                                    rs.getString("full_name")
                            )
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return members;
    }

    // =========================================================
    // CREATE WORKOUT PLAN
    // =========================================================

    public int createPlan(
            int trainerId,
            int memberId,
            String planName,
            String description) {

        String sql =
                "INSERT INTO workout_plans " +
                        "(trainer_id, member_id, plan_name, description, " +
                        "created_date, status) " +
                        "VALUES (?, ?, ?, ?, CURDATE(), 'ACTIVE')";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt =
                     conn.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS
                     )) {

            stmt.setInt(1, trainerId);
            stmt.setInt(2, memberId);
            stmt.setString(3, planName);
            stmt.setString(4, description);

            int affected = stmt.executeUpdate();

            if (affected == 0) {
                return -1;
            }

            try (ResultSet rs = stmt.getGeneratedKeys()) {

                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    // =========================================================
    // GET ALL PLANS FOR TRAINER
    // =========================================================

    public List<WorkoutPlanInfo> getPlansForTrainer(int trainerId) {

        List<WorkoutPlanInfo> plans = new ArrayList<>();

        String sql =
                "SELECT wp.plan_id, " +
                        "wp.member_id, " +
                        "mp.full_name, " +
                        "wp.plan_name, " +
                        "wp.description, " +
                        "wp.created_date, " +
                        "wp.status " +
                        "FROM workout_plans wp " +
                        "JOIN member_profiles mp " +
                        "ON wp.member_id = mp.member_id " +
                        "WHERE wp.trainer_id = ? " +
                        "ORDER BY wp.created_date DESC, wp.plan_id DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, trainerId);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {

                    plans.add(
                            new WorkoutPlanInfo(
                                    rs.getInt("plan_id"),
                                    rs.getInt("member_id"),
                                    rs.getString("full_name"),
                                    rs.getString("plan_name"),
                                    rs.getString("description"),
                                    rs.getDate("created_date"),
                                    rs.getString("status")
                            )
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return plans;
    }

    // =========================================================
    // ADD EXERCISE
    // =========================================================

    public boolean addExercise(
            int planId,
            String exerciseName,
            Integer sets,
            Integer repetitions,
            Integer durationMinutes,
            String instructions) {

        String sql =
                "INSERT INTO workout_exercises " +
                        "(plan_id, exercise_name, sets, repetitions, " +
                        "duration_minutes, instructions) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, planId);
            stmt.setString(2, exerciseName);

            if (sets == null) {
                stmt.setNull(3, Types.INTEGER);
            } else {
                stmt.setInt(3, sets);
            }

            if (repetitions == null) {
                stmt.setNull(4, Types.INTEGER);
            } else {
                stmt.setInt(4, repetitions);
            }

            if (durationMinutes == null) {
                stmt.setNull(5, Types.INTEGER);
            } else {
                stmt.setInt(5, durationMinutes);
            }

            if (instructions == null || instructions.trim().isEmpty()) {
                stmt.setNull(6, Types.VARCHAR);
            } else {
                stmt.setString(6, instructions);
            }

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // =========================================================
    // GET EXERCISES
    // =========================================================

    public List<WorkoutExercise> getExercises(int planId) {

        List<WorkoutExercise> exercises = new ArrayList<>();

        String sql =
                "SELECT exercise_id, " +
                        "plan_id, " +
                        "exercise_name, " +
                        "sets, " +
                        "repetitions, " +
                        "duration_minutes, " +
                        "instructions " +
                        "FROM workout_exercises " +
                        "WHERE plan_id = ? " +
                        "ORDER BY exercise_id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, planId);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {

                    Integer sets = null;
                    Integer repetitions = null;
                    Integer duration = null;

                    Object setsObject = rs.getObject("sets");

                    if (setsObject != null) {
                        sets = rs.getInt("sets");
                    }

                    Object repetitionsObject =
                            rs.getObject("repetitions");

                    if (repetitionsObject != null) {
                        repetitions = rs.getInt("repetitions");
                    }

                    Object durationObject =
                            rs.getObject("duration_minutes");

                    if (durationObject != null) {
                        duration = rs.getInt("duration_minutes");
                    }

                    exercises.add(
                            new WorkoutExercise(
                                    rs.getInt("exercise_id"),
                                    rs.getInt("plan_id"),
                                    rs.getString("exercise_name"),
                                    sets,
                                    repetitions,
                                    duration,
                                    rs.getString("instructions")
                            )
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exercises;
    }

    // =========================================================
    // DELETE EXERCISE
    // =========================================================

    public boolean deleteExercise(int exerciseId) {

        String sql =
                "DELETE FROM workout_exercises " +
                        "WHERE exercise_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, exerciseId);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // =========================================================
    // UPDATE PLAN STATUS
    // =========================================================

    public boolean updatePlanStatus(
            int planId,
            String status) {

        String sql =
                "UPDATE workout_plans " +
                        "SET status = ? " +
                        "WHERE plan_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setInt(2, planId);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // =========================================================
    // DELETE WORKOUT PLAN
    // =========================================================

    public boolean deletePlan(int planId) {

        Connection conn = null;

        try {

            conn = DatabaseConnection.getConnection();

            conn.setAutoCommit(false);

            // Delete exercises first
            String exerciseSql =
                    "DELETE FROM workout_exercises " +
                            "WHERE plan_id = ?";

            try (PreparedStatement stmt =
                         conn.prepareStatement(exerciseSql)) {

                stmt.setInt(1, planId);
                stmt.executeUpdate();
            }

            // Then delete plan
            String planSql =
                    "DELETE FROM workout_plans " +
                            "WHERE plan_id = ?";

            int affected;

            try (PreparedStatement stmt =
                         conn.prepareStatement(planSql)) {

                stmt.setInt(1, planId);

                affected = stmt.executeUpdate();
            }

            conn.commit();

            return affected > 0;

        } catch (SQLException e) {

            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackError) {
                    rollbackError.printStackTrace();
                }
            }

            e.printStackTrace();

        } finally {

            if (conn != null) {

                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException closeError) {
                    closeError.printStackTrace();
                }
            }
        }

        return false;
    }
}