package com.gym.dao;

import com.gym.config.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrainerDAO {

    // =========================================================
    // TRAINER REGISTRATION
    // =========================================================

    public boolean registerTrainer(
            String username,
            String password,
            String email,
            String phone,
            String fullName,
            String dateOfBirth,
            String gender,
            String address,
            String qualification,
            String specialization,
            int experienceYears,
            String certification
    ) {

        Connection conn = null;

        try {

            conn = DatabaseConnection.getConnection();

            conn.setAutoCommit(false);

            // -------------------------------------------------
            // CREATE USER
            // -------------------------------------------------

            String userSQL =
                    "INSERT INTO users " +
                            "(username, password, email, phone, role, status) " +
                            "VALUES (?, ?, ?, ?, 'TRAINER', 'PENDING')";

            int userId;

            try (
                    PreparedStatement stmt =
                            conn.prepareStatement(
                                    userSQL,
                                    java.sql.Statement.RETURN_GENERATED_KEYS
                            )
            ) {

                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.setString(3, email);
                stmt.setString(4, phone);

                stmt.executeUpdate();

                try (
                        ResultSet rs =
                                stmt.getGeneratedKeys()
                ) {

                    if (!rs.next()) {

                        throw new SQLException(
                                "Could not create trainer user."
                        );
                    }

                    userId = rs.getInt(1);
                }
            }

            // -------------------------------------------------
            // CREATE TRAINER PROFILE
            // -------------------------------------------------

            String profileSQL =
                    "INSERT INTO trainer_profiles " +
                            "(user_id, full_name, date_of_birth, gender, " +
                            "address, qualification, specialization, " +
                            "experience_years, certification) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            int trainerId;

            try (
                    PreparedStatement stmt =
                            conn.prepareStatement(
                                    profileSQL,
                                    java.sql.Statement.RETURN_GENERATED_KEYS
                            )
            ) {

                stmt.setInt(1, userId);

                stmt.setString(
                        2,
                        fullName
                );

                if (
                        dateOfBirth == null ||
                                dateOfBirth.trim().isEmpty()
                ) {

                    stmt.setNull(
                            3,
                            java.sql.Types.DATE
                    );

                } else {

                    stmt.setDate(
                            3,
                            java.sql.Date.valueOf(
                                    dateOfBirth
                            )
                    );
                }

                stmt.setString(
                        4,
                        gender
                );

                stmt.setString(
                        5,
                        address
                );

                stmt.setString(
                        6,
                        qualification
                );

                stmt.setString(
                        7,
                        specialization
                );

                stmt.setInt(
                        8,
                        experienceYears
                );

                stmt.setString(
                        9,
                        certification
                );

                stmt.executeUpdate();

                try (
                        ResultSet rs =
                                stmt.getGeneratedKeys()
                ) {

                    if (!rs.next()) {

                        throw new SQLException(
                                "Could not create trainer profile."
                        );
                    }

                    trainerId = rs.getInt(1);
                }
            }

            // -------------------------------------------------
            // CREATE APPLICATION
            // -------------------------------------------------

            String applicationSQL =
                    "INSERT INTO trainer_applications " +
                            "(trainer_id, status) " +
                            "VALUES (?, 'PENDING')";

            try (
                    PreparedStatement stmt =
                            conn.prepareStatement(
                                    applicationSQL
                            )
            ) {

                stmt.setInt(
                        1,
                        trainerId
                );

                stmt.executeUpdate();
            }

            conn.commit();

            return true;

        } catch (Exception e) {

            if (conn != null) {

                try {

                    conn.rollback();

                } catch (SQLException rollbackError) {

                    rollbackError.printStackTrace();
                }
            }

            System.err.println(
                    "================================="
            );

            System.err.println(
                    "TRAINER REGISTRATION ERROR"
            );

            System.err.println(
                    "================================="
            );

            e.printStackTrace();

            return false;

        } finally {

            if (conn != null) {

                try {

                    conn.setAutoCommit(true);

                    conn.close();

                } catch (SQLException e) {

                    e.printStackTrace();
                }
            }
        }
    }


    // =========================================================
    // CHECK USERNAME
    // =========================================================

    public boolean usernameExists(
            String username
    ) {

        String sql =
                "SELECT user_id " +
                        "FROM users " +
                        "WHERE username = ?";

        try (
                Connection conn =
                        DatabaseConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setString(
                    1,
                    username
            );

            try (
                    ResultSet rs =
                            stmt.executeQuery()
            ) {

                return rs.next();
            }

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }


    // =========================================================
    // CHECK EMAIL
    // =========================================================

    public boolean emailExists(
            String email
    ) {

        String sql =
                "SELECT user_id " +
                        "FROM users " +
                        "WHERE email = ?";

        try (
                Connection conn =
                        DatabaseConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setString(
                    1,
                    email
            );

            try (
                    ResultSet rs =
                            stmt.executeQuery()
            ) {

                return rs.next();
            }

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }


    // =========================================================
    // GET ALL TRAINER APPLICATIONS
    // =========================================================

    public List<TrainerApplication> getAllApplications() {

        List<TrainerApplication> applications =
                new ArrayList<>();

        String sql =
                "SELECT " +
                        "ta.application_id, " +
                        "tp.trainer_id, " +
                        "u.user_id, " +
                        "tp.full_name, " +
                        "u.username, " +
                        "u.email, " +
                        "u.phone, " +
                        "tp.qualification, " +
                        "tp.specialization, " +
                        "tp.experience_years, " +
                        "tp.certification, " +
                        "tp.date_of_birth, " +
                        "tp.gender, " +
                        "tp.address, " +
                        "ta.status, " +
                        "ta.application_date, " +
                        "ta.reviewed_date, " +
                        "ta.rejection_reason " +
                        "FROM trainer_applications ta " +
                        "JOIN trainer_profiles tp " +
                        "ON ta.trainer_id = tp.trainer_id " +
                        "JOIN users u " +
                        "ON tp.user_id = u.user_id " +
                        "ORDER BY ta.application_date DESC";

        try (
                Connection conn =
                        DatabaseConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql);

                ResultSet rs =
                        stmt.executeQuery()
        ) {

            while (rs.next()) {

                TrainerApplication application =
                        new TrainerApplication();

                application.setApplicationId(
                        rs.getInt("application_id")
                );

                application.setTrainerId(
                        rs.getInt("trainer_id")
                );

                application.setUserId(
                        rs.getInt("user_id")
                );

                application.setFullName(
                        rs.getString("full_name")
                );

                application.setUsername(
                        rs.getString("username")
                );

                application.setEmail(
                        rs.getString("email")
                );

                application.setPhone(
                        rs.getString("phone")
                );

                application.setQualification(
                        rs.getString("qualification")
                );

                application.setSpecialization(
                        rs.getString("specialization")
                );

                application.setExperienceYears(
                        rs.getInt("experience_years")
                );

                application.setCertification(
                        rs.getString("certification")
                );

                application.setDateOfBirth(
                        rs.getDate("date_of_birth")
                );

                application.setGender(
                        rs.getString("gender")
                );

                application.setAddress(
                        rs.getString("address")
                );

                application.setStatus(
                        rs.getString("status")
                );

                application.setApplicationDate(
                        rs.getTimestamp(
                                "application_date"
                        )
                );

                application.setReviewedDate(
                        rs.getTimestamp(
                                "reviewed_date"
                        )
                );

                application.setRejectionReason(
                        rs.getString(
                                "rejection_reason"
                        )
                );

                applications.add(
                        application
                );
            }

        } catch (SQLException e) {

            System.err.println(
                    "ERROR LOADING TRAINER APPLICATIONS"
            );

            e.printStackTrace();
        }

        return applications;
    }


    // =========================================================
    // APPROVE TRAINER
    // =========================================================

    public boolean approveTrainer(
            int applicationId
    ) {

        Connection conn = null;

        try {

            conn =
                    DatabaseConnection.getConnection();

            conn.setAutoCommit(false);

            // -------------------------------------------------
            // FIND USER
            // -------------------------------------------------

            String findSQL =
                    "SELECT tp.user_id " +
                            "FROM trainer_applications ta " +
                            "JOIN trainer_profiles tp " +
                            "ON ta.trainer_id = tp.trainer_id " +
                            "WHERE ta.application_id = ? " +
                            "AND ta.status = 'PENDING'";

            int userId;

            try (
                    PreparedStatement stmt =
                            conn.prepareStatement(findSQL)
            ) {

                stmt.setInt(
                        1,
                        applicationId
                );

                try (
                        ResultSet rs =
                                stmt.executeQuery()
                ) {

                    if (!rs.next()) {

                        conn.rollback();

                        return false;
                    }

                    userId =
                            rs.getInt("user_id");
                }
            }

            // -------------------------------------------------
            // ACTIVATE USER
            // -------------------------------------------------

            String userSQL =
                    "UPDATE users " +
                            "SET status = 'ACTIVE' " +
                            "WHERE user_id = ? " +
                            "AND role = 'TRAINER'";

            try (
                    PreparedStatement stmt =
                            conn.prepareStatement(userSQL)
            ) {

                stmt.setInt(
                        1,
                        userId
                );

                stmt.executeUpdate();
            }

            // -------------------------------------------------
            // ACCEPT APPLICATION
            // -------------------------------------------------

            String applicationSQL =
                    "UPDATE trainer_applications " +
                            "SET status = 'ACCEPTED', " +
                            "reviewed_date = NOW(), " +
                            "rejection_reason = NULL " +
                            "WHERE application_id = ? " +
                            "AND status = 'PENDING'";

            int rows;

            try (
                    PreparedStatement stmt =
                            conn.prepareStatement(
                                    applicationSQL
                            )
            ) {

                stmt.setInt(
                        1,
                        applicationId
                );

                rows =
                        stmt.executeUpdate();
            }

            if (rows == 0) {

                conn.rollback();

                return false;
            }

            conn.commit();

            return true;

        } catch (SQLException e) {

            if (conn != null) {

                try {
                    conn.rollback();
                } catch (SQLException rollbackError) {
                    rollbackError.printStackTrace();
                }
            }

            System.err.println(
                    "ERROR APPROVING TRAINER"
            );

            e.printStackTrace();

            return false;

        } finally {

            closeConnection(conn);
        }
    }


    // =========================================================
    // REJECT TRAINER
    // =========================================================

    public boolean rejectTrainer(
            int applicationId,
            String rejectionReason
    ) {

        Connection conn = null;

        try {

            conn =
                    DatabaseConnection.getConnection();

            conn.setAutoCommit(false);

            // -------------------------------------------------
            // FIND USER
            // -------------------------------------------------

            String findSQL =
                    "SELECT tp.user_id " +
                            "FROM trainer_applications ta " +
                            "JOIN trainer_profiles tp " +
                            "ON ta.trainer_id = tp.trainer_id " +
                            "WHERE ta.application_id = ? " +
                            "AND ta.status = 'PENDING'";

            int userId;

            try (
                    PreparedStatement stmt =
                            conn.prepareStatement(findSQL)
            ) {

                stmt.setInt(
                        1,
                        applicationId
                );

                try (
                        ResultSet rs =
                                stmt.executeQuery()
                ) {

                    if (!rs.next()) {

                        conn.rollback();

                        return false;
                    }

                    userId =
                            rs.getInt("user_id");
                }
            }

            // -------------------------------------------------
            // REJECT USER
            // -------------------------------------------------

            String userSQL =
                    "UPDATE users " +
                            "SET status = 'REJECTED' " +
                            "WHERE user_id = ? " +
                            "AND role = 'TRAINER'";

            try (
                    PreparedStatement stmt =
                            conn.prepareStatement(userSQL)
            ) {

                stmt.setInt(
                        1,
                        userId
                );

                stmt.executeUpdate();
            }

            // -------------------------------------------------
            // REJECT APPLICATION
            // -------------------------------------------------

            String applicationSQL =
                    "UPDATE trainer_applications " +
                            "SET status = 'REJECTED', " +
                            "reviewed_date = NOW(), " +
                            "rejection_reason = ? " +
                            "WHERE application_id = ? " +
                            "AND status = 'PENDING'";

            int rows;

            try (
                    PreparedStatement stmt =
                            conn.prepareStatement(
                                    applicationSQL
                            )
            ) {

                stmt.setString(
                        1,
                        rejectionReason
                );

                stmt.setInt(
                        2,
                        applicationId
                );

                rows =
                        stmt.executeUpdate();
            }

            if (rows == 0) {

                conn.rollback();

                return false;
            }

            conn.commit();

            return true;

        } catch (SQLException e) {

            if (conn != null) {

                try {
                    conn.rollback();
                } catch (SQLException rollbackError) {
                    rollbackError.printStackTrace();
                }
            }

            System.err.println(
                    "ERROR REJECTING TRAINER"
            );

            e.printStackTrace();

            return false;

        } finally {

            closeConnection(conn);
        }
    }


    // =========================================================
    // CLOSE CONNECTION
    // =========================================================

    private void closeConnection(
            Connection conn
    ) {

        if (conn != null) {

            try {

                conn.setAutoCommit(true);

                conn.close();

            } catch (SQLException e) {

                e.printStackTrace();
            }
        }
    }


    // =========================================================
    // TRAINER APPLICATION MODEL
    // =========================================================

    public static class TrainerApplication {

        private int applicationId;
        private int trainerId;
        private int userId;

        private String fullName;
        private String username;
        private String email;
        private String phone;

        private String qualification;
        private String specialization;

        private int experienceYears;

        private String certification;

        private java.sql.Date dateOfBirth;

        private String gender;
        private String address;

        private String status;

        private java.sql.Timestamp applicationDate;
        private java.sql.Timestamp reviewedDate;

        private String rejectionReason;


        public TrainerApplication() {
        }


        public int getApplicationId() {
            return applicationId;
        }

        public void setApplicationId(
                int applicationId
        ) {
            this.applicationId =
                    applicationId;
        }


        public int getTrainerId() {
            return trainerId;
        }

        public void setTrainerId(
                int trainerId
        ) {
            this.trainerId =
                    trainerId;
        }


        public int getUserId() {
            return userId;
        }

        public void setUserId(
                int userId
        ) {
            this.userId =
                    userId;
        }


        public String getFullName() {
            return fullName;
        }

        public void setFullName(
                String fullName
        ) {
            this.fullName =
                    fullName;
        }


        public String getUsername() {
            return username;
        }

        public void setUsername(
                String username
        ) {
            this.username =
                    username;
        }


        public String getEmail() {
            return email;
        }

        public void setEmail(
                String email
        ) {
            this.email =
                    email;
        }


        public String getPhone() {
            return phone;
        }

        public void setPhone(
                String phone
        ) {
            this.phone =
                    phone;
        }


        public String getQualification() {
            return qualification;
        }

        public void setQualification(
                String qualification
        ) {
            this.qualification =
                    qualification;
        }


        public String getSpecialization() {
            return specialization;
        }

        public void setSpecialization(
                String specialization
        ) {
            this.specialization =
                    specialization;
        }


        public int getExperienceYears() {
            return experienceYears;
        }

        public void setExperienceYears(
                int experienceYears
        ) {
            this.experienceYears =
                    experienceYears;
        }


        public String getCertification() {
            return certification;
        }

        public void setCertification(
                String certification
        ) {
            this.certification =
                    certification;
        }


        public java.sql.Date getDateOfBirth() {
            return dateOfBirth;
        }

        public void setDateOfBirth(
                java.sql.Date dateOfBirth
        ) {
            this.dateOfBirth =
                    dateOfBirth;
        }


        public String getGender() {
            return gender;
        }

        public void setGender(
                String gender
        ) {
            this.gender =
                    gender;
        }


        public String getAddress() {
            return address;
        }

        public void setAddress(
                String address
        ) {
            this.address =
                    address;
        }


        public String getStatus() {
            return status;
        }

        public void setStatus(
                String status
        ) {
            this.status =
                    status;
        }


        public java.sql.Timestamp getApplicationDate() {
            return applicationDate;
        }

        public void setApplicationDate(
                java.sql.Timestamp applicationDate
        ) {
            this.applicationDate =
                    applicationDate;
        }


        public java.sql.Timestamp getReviewedDate() {
            return reviewedDate;
        }

        public void setReviewedDate(
                java.sql.Timestamp reviewedDate
        ) {
            this.reviewedDate =
                    reviewedDate;
        }


        public String getRejectionReason() {
            return rejectionReason;
        }

        public void setRejectionReason(
                String rejectionReason
        ) {
            this.rejectionReason =
                    rejectionReason;
        }
    }
}