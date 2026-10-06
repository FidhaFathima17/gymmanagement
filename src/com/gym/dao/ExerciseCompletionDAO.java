package com.gym.dao;

import com.gym.config.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExerciseCompletionDAO {

    // =========================================================
    // EXERCISE ITEM
    // =========================================================

    public static class ExerciseItem {

        private int exerciseId;
        private String exerciseName;
        private Integer sets;
        private Integer repetitions;
        private Integer durationMinutes;
        private boolean completed;

        public ExerciseItem(
                int exerciseId,
                String exerciseName,
                Integer sets,
                Integer repetitions,
                Integer durationMinutes,
                boolean completed
        ) {
            this.exerciseId = exerciseId;
            this.exerciseName = exerciseName;
            this.sets = sets;
            this.repetitions = repetitions;
            this.durationMinutes = durationMinutes;
            this.completed = completed;
        }

        public int getExerciseId() {
            return exerciseId;
        }

        public String getExerciseName() {
            return exerciseName;
        }

        public Integer getSets() {
            return sets;
        }

        public Integer getRepetitions() {
            return repetitions;
        }

        public Integer getDurationMinutes() {
            return durationMinutes;
        }

        public boolean isCompleted() {
            return completed;
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }
    }

    // =========================================================
    // WEEKLY PROGRESS DATA
    // =========================================================

    public static class WeeklyProgress {

        private String dayName;
        private String date;
        private int completed;
        private int total;
        private int percentage;

        public WeeklyProgress(
                String dayName,
                String date,
                int completed,
                int total
        ) {
            this.dayName = dayName;
            this.date = date;
            this.completed = completed;
            this.total = total;

            if (total > 0) {
                this.percentage =
                        (completed * 100) / total;
            } else {
                this.percentage = 0;
            }
        }

        public String getDayName() {
            return dayName;
        }

        public String getDate() {
            return date;
        }

        public int getCompleted() {
            return completed;
        }

        public int getTotal() {
            return total;
        }

        public int getPercentage() {
            return percentage;
        }
    }

    // =========================================================
    // GET TODAY'S EXERCISES
    // =========================================================

    public List<ExerciseItem> getTodayExercises(
            int memberId
    ) {

        List<ExerciseItem> exercises =
                new ArrayList<>();

        String sql =
                "SELECT " +
                        "we.exercise_id, " +
                        "we.exercise_name, " +
                        "we.sets, " +
                        "we.repetitions, " +
                        "we.duration_minutes, " +
                        "COALESCE(ec.completed, FALSE) AS completed " +

                        "FROM workout_exercises we " +

                        "JOIN workout_plans wp " +
                        "ON we.plan_id = wp.plan_id " +

                        "LEFT JOIN exercise_completion ec " +
                        "ON we.exercise_id = ec.exercise_id " +
                        "AND ec.member_id = ? " +
                        "AND ec.completion_date = CURDATE() " +

                        "WHERE wp.member_id = ? " +
                        "AND wp.status = 'ACTIVE' " +

                        "ORDER BY we.exercise_id";

        try (
                Connection conn =
                        DatabaseConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, memberId);
            stmt.setInt(2, memberId);

            try (
                    ResultSet rs =
                            stmt.executeQuery()
            ) {

                while (rs.next()) {

                    Object setsObject =
                            rs.getObject("sets");

                    Object repetitionsObject =
                            rs.getObject("repetitions");

                    Object durationObject =
                            rs.getObject("duration_minutes");

                    Integer sets =
                            setsObject == null
                                    ? null
                                    : rs.getInt("sets");

                    Integer repetitions =
                            repetitionsObject == null
                                    ? null
                                    : rs.getInt("repetitions");

                    Integer duration =
                            durationObject == null
                                    ? null
                                    : rs.getInt("duration_minutes");

                    exercises.add(
                            new ExerciseItem(
                                    rs.getInt("exercise_id"),
                                    rs.getString("exercise_name"),
                                    sets,
                                    repetitions,
                                    duration,
                                    rs.getBoolean("completed")
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
    // MARK EXERCISE COMPLETED
    // =========================================================

    public boolean markCompleted(
            int memberId,
            int exerciseId
    ) {

        String sql =
                "INSERT INTO exercise_completion " +
                        "(member_id, exercise_id, completion_date, completed) " +
                        "VALUES (?, ?, CURDATE(), TRUE) " +
                        "ON DUPLICATE KEY UPDATE completed = TRUE";

        try (
                Connection conn =
                        DatabaseConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, memberId);
            stmt.setInt(2, exerciseId);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return false;
    }

    // =========================================================
    // MARK EXERCISE NOT COMPLETED
    // =========================================================

    public boolean markNotCompleted(
            int memberId,
            int exerciseId
    ) {

        String sql =
                "INSERT INTO exercise_completion " +
                        "(member_id, exercise_id, completion_date, completed) " +
                        "VALUES (?, ?, CURDATE(), FALSE) " +
                        "ON DUPLICATE KEY UPDATE completed = FALSE";

        try (
                Connection conn =
                        DatabaseConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, memberId);
            stmt.setInt(2, exerciseId);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return false;
    }

    // =========================================================
    // TODAY COMPLETED COUNT
    // =========================================================

    public int getCompletedCount(
            int memberId
    ) {

        String sql =
                "SELECT COUNT(*) " +
                        "FROM exercise_completion " +
                        "WHERE member_id = ? " +
                        "AND completion_date = CURDATE() " +
                        "AND completed = TRUE";

        try (
                Connection conn =
                        DatabaseConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, memberId);

            try (
                    ResultSet rs =
                            stmt.executeQuery()
            ) {

                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return 0;
    }

    // =========================================================
    // GET LAST 7 DAYS PROGRESS
    // =========================================================

    public List<WeeklyProgress> getWeeklyProgress(
            int memberId
    ) {

        List<WeeklyProgress> result =
                new ArrayList<>();

        String sql =
                "SELECT " +
                        "d.work_date, " +
                        "DATE_FORMAT(d.work_date, '%a') AS day_name, " +

                        "COUNT(DISTINCT we.exercise_id) " +
                        "AS total_exercises, " +

                        "COUNT(DISTINCT CASE " +
                        "WHEN ec.completed = TRUE " +
                        "THEN we.exercise_id END) " +
                        "AS completed_exercises " +

                        "FROM ( " +

                        "SELECT CURDATE() AS work_date " +

                        "UNION ALL " +
                        "SELECT DATE_SUB(CURDATE(), INTERVAL 1 DAY) " +

                        "UNION ALL " +
                        "SELECT DATE_SUB(CURDATE(), INTERVAL 2 DAY) " +

                        "UNION ALL " +
                        "SELECT DATE_SUB(CURDATE(), INTERVAL 3 DAY) " +

                        "UNION ALL " +
                        "SELECT DATE_SUB(CURDATE(), INTERVAL 4 DAY) " +

                        "UNION ALL " +
                        "SELECT DATE_SUB(CURDATE(), INTERVAL 5 DAY) " +

                        "UNION ALL " +
                        "SELECT DATE_SUB(CURDATE(), INTERVAL 6 DAY) " +

                        ") d " +

                        "LEFT JOIN workout_plans wp " +
                        "ON wp.member_id = ? " +
                        "AND wp.status = 'ACTIVE' " +

                        "LEFT JOIN workout_exercises we " +
                        "ON we.plan_id = wp.plan_id " +

                        "LEFT JOIN exercise_completion ec " +
                        "ON ec.member_id = ? " +
                        "AND ec.exercise_id = we.exercise_id " +
                        "AND ec.completion_date = d.work_date " +

                        "GROUP BY d.work_date " +

                        "ORDER BY d.work_date";

        try (
                Connection conn =
                        DatabaseConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, memberId);
            stmt.setInt(2, memberId);

            try (
                    ResultSet rs =
                            stmt.executeQuery()
            ) {

                while (rs.next()) {

                    Date workDate =
                            rs.getDate("work_date");

                    String date =
                            workDate.toString();

                    int completed =
                            rs.getInt(
                                    "completed_exercises"
                            );

                    int total =
                            rs.getInt(
                                    "total_exercises"
                            );

                    result.add(
                            new WeeklyProgress(
                                    rs.getString("day_name"),
                                    date,
                                    completed,
                                    total
                            )
                    );
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return result;
    }
}