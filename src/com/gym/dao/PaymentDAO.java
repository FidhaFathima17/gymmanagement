package com.gym.dao;

import com.gym.config.DatabaseConnection;
import com.gym.model.Payment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {

    // ============================================================
    // GET ALL PAYMENTS
    // ============================================================

    public List<Payment> getAllPayments() {

        List<Payment> payments = new ArrayList<>();

        String sql =
                "SELECT p.payment_id, " +
                        "p.subscription_id, " +
                        "p.member_id, " +
                        "mp.full_name AS member_name, " +
                        "gp.package_name, " +
                        "p.amount, " +
                        "p.transaction_id, " +
                        "p.payment_method, " +
                        "p.payment_status, " +
                        "p.payment_date " +
                        "FROM payments p " +
                        "INNER JOIN member_profiles mp " +
                        "ON p.member_id = mp.member_id " +
                        "INNER JOIN subscriptions s " +
                        "ON p.subscription_id = s.subscription_id " +
                        "INNER JOIN gym_packages gp " +
                        "ON s.package_id = gp.package_id " +
                        "ORDER BY p.payment_id DESC";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {

            while (rs.next()) {

                payments.add(mapPayment(rs));
            }

        } catch (SQLException e) {

            System.err.println("Error loading payments:");
            e.printStackTrace();
        }

        return payments;
    }

    // ============================================================
    // SEARCH PAYMENTS
    // ============================================================

    public List<Payment> searchPayments(String keyword) {

        List<Payment> payments = new ArrayList<>();

        String sql =
                "SELECT p.payment_id, " +
                        "p.subscription_id, " +
                        "p.member_id, " +
                        "mp.full_name AS member_name, " +
                        "gp.package_name, " +
                        "p.amount, " +
                        "p.transaction_id, " +
                        "p.payment_method, " +
                        "p.payment_status, " +
                        "p.payment_date " +
                        "FROM payments p " +
                        "INNER JOIN member_profiles mp " +
                        "ON p.member_id = mp.member_id " +
                        "INNER JOIN subscriptions s " +
                        "ON p.subscription_id = s.subscription_id " +
                        "INNER JOIN gym_packages gp " +
                        "ON s.package_id = gp.package_id " +
                        "WHERE mp.full_name LIKE ? " +
                        "OR gp.package_name LIKE ? " +
                        "OR p.transaction_id LIKE ? " +
                        "OR p.payment_method LIKE ? " +
                        "OR p.payment_status LIKE ? " +
                        "ORDER BY p.payment_id DESC";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            String search = "%" + keyword + "%";

            stmt.setString(1, search);
            stmt.setString(2, search);
            stmt.setString(3, search);
            stmt.setString(4, search);
            stmt.setString(5, search);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {

                    payments.add(mapPayment(rs));
                }
            }

        } catch (SQLException e) {

            System.err.println("Error searching payments:");
            e.printStackTrace();
        }

        return payments;
    }

    // ============================================================
    // ADD PAYMENT + ACTIVATE SUBSCRIPTION
    // ============================================================

    public boolean addSuccessfulPayment(
            int subscriptionId,
            int memberId,
            double amount,
            String transactionId,
            String paymentMethod
    ) {

        Connection conn = null;

        String paymentSql =
                "INSERT INTO payments " +
                        "(subscription_id, member_id, amount, " +
                        "transaction_id, payment_method, payment_status) " +
                        "VALUES (?, ?, ?, ?, ?, 'SUCCESS')";

        String subscriptionSql =
                "UPDATE subscriptions " +
                        "SET status = 'ACTIVE' " +
                        "WHERE subscription_id = ? " +
                        "AND status = 'PENDING'";

        try {

            conn = DatabaseConnection.getConnection();

            conn.setAutoCommit(false);

            // Insert payment
            try (
                    PreparedStatement paymentStmt =
                            conn.prepareStatement(paymentSql)
            ) {

                paymentStmt.setInt(1, subscriptionId);
                paymentStmt.setInt(2, memberId);
                paymentStmt.setDouble(3, amount);

                if (transactionId == null ||
                        transactionId.trim().isEmpty()) {

                    paymentStmt.setNull(
                            4,
                            Types.VARCHAR
                    );

                } else {

                    paymentStmt.setString(
                            4,
                            transactionId.trim()
                    );
                }

                paymentStmt.setString(
                        5,
                        paymentMethod
                );

                paymentStmt.executeUpdate();
            }

            // Activate subscription
            try (
                    PreparedStatement subscriptionStmt =
                            conn.prepareStatement(
                                    subscriptionSql
                            )
            ) {

                subscriptionStmt.setInt(
                        1,
                        subscriptionId
                );

                int updated =
                        subscriptionStmt.executeUpdate();

                if (updated == 0) {

                    conn.rollback();

                    return false;
                }
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

            System.err.println(
                    "Error processing payment:"
            );

            e.printStackTrace();

            return false;

        } finally {

            if (conn != null) {

                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException ignored) {
                }
            }
        }
    }

    // ============================================================
    // UPDATE PAYMENT STATUS
    // ============================================================

    public boolean updatePaymentStatus(
            int paymentId,
            String status
    ) {

        String sql =
                "UPDATE payments " +
                        "SET payment_status = ? " +
                        "WHERE payment_id = ?";

        try (
                Connection conn =
                        DatabaseConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setString(1, status);
            stmt.setInt(2, paymentId);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Error updating payment:"
            );

            e.printStackTrace();

            return false;
        }
    }

    // ============================================================
    // MAP RESULT
    // ============================================================

    private Payment mapPayment(ResultSet rs)
            throws SQLException {

        Payment payment = new Payment();

        payment.setPaymentId(
                rs.getInt("payment_id")
        );

        payment.setSubscriptionId(
                rs.getInt("subscription_id")
        );

        payment.setMemberId(
                rs.getInt("member_id")
        );

        payment.setMemberName(
                rs.getString("member_name")
        );

        payment.setPackageName(
                rs.getString("package_name")
        );

        payment.setAmount(
                rs.getDouble("amount")
        );

        payment.setTransactionId(
                rs.getString("transaction_id")
        );

        payment.setPaymentMethod(
                rs.getString("payment_method")
        );

        payment.setPaymentStatus(
                rs.getString("payment_status")
        );

        Timestamp timestamp =
                rs.getTimestamp("payment_date");

        if (timestamp != null) {

            payment.setPaymentDate(
                    timestamp.toLocalDateTime()
            );
        }

        return payment;
    }
}