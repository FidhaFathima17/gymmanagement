package com.gym.dao;

import com.gym.config.DatabaseConnection;
import com.gym.model.Subscription;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionDAO {

    // ============================================================
    // GET ALL SUBSCRIPTIONS
    // ============================================================

    public List<Subscription> getAllSubscriptions() {

        List<Subscription> subscriptions = new ArrayList<>();

        String sql =
                "SELECT s.subscription_id, " +
                        "s.member_id, " +
                        "s.package_id, " +
                        "mp.full_name AS member_name, " +
                        "gp.package_name, " +
                        "s.start_date, " +
                        "s.end_date, " +
                        "s.amount, " +
                        "s.status, " +
                        "s.created_at " +
                        "FROM subscriptions s " +
                        "INNER JOIN member_profiles mp " +
                        "ON s.member_id = mp.member_id " +
                        "INNER JOIN gym_packages gp " +
                        "ON s.package_id = gp.package_id " +
                        "ORDER BY s.subscription_id DESC";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {

            while (rs.next()) {

                Subscription subscription = new Subscription();

                subscription.setSubscriptionId(
                        rs.getInt("subscription_id")
                );

                subscription.setMemberId(
                        rs.getInt("member_id")
                );

                subscription.setPackageId(
                        rs.getInt("package_id")
                );

                subscription.setMemberName(
                        rs.getString("member_name")
                );

                subscription.setPackageName(
                        rs.getString("package_name")
                );

                Date startDate = rs.getDate("start_date");

                if (startDate != null) {
                    subscription.setStartDate(
                            startDate.toLocalDate()
                    );
                }

                Date endDate = rs.getDate("end_date");

                if (endDate != null) {
                    subscription.setEndDate(
                            endDate.toLocalDate()
                    );
                }

                subscription.setAmount(
                        rs.getDouble("amount")
                );

                subscription.setStatus(
                        rs.getString("status")
                );

                Timestamp createdAt =
                        rs.getTimestamp("created_at");

                if (createdAt != null) {
                    subscription.setCreatedAt(
                            createdAt.toString()
                    );
                }

                subscriptions.add(subscription);
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error loading subscriptions:"
            );

            e.printStackTrace();
        }

        return subscriptions;
    }

    // ============================================================
    // SEARCH SUBSCRIPTIONS
    // ============================================================

    public List<Subscription> searchSubscriptions(
            String keyword
    ) {

        List<Subscription> subscriptions =
                new ArrayList<>();

        String sql =
                "SELECT s.subscription_id, " +
                        "s.member_id, " +
                        "s.package_id, " +
                        "mp.full_name AS member_name, " +
                        "gp.package_name, " +
                        "s.start_date, " +
                        "s.end_date, " +
                        "s.amount, " +
                        "s.status, " +
                        "s.created_at " +
                        "FROM subscriptions s " +
                        "INNER JOIN member_profiles mp " +
                        "ON s.member_id = mp.member_id " +
                        "INNER JOIN gym_packages gp " +
                        "ON s.package_id = gp.package_id " +
                        "WHERE mp.full_name LIKE ? " +
                        "OR gp.package_name LIKE ? " +
                        "OR s.status LIKE ? " +
                        "ORDER BY s.subscription_id DESC";

        try (
                Connection conn =
                        DatabaseConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            String search = "%" + keyword + "%";

            stmt.setString(1, search);
            stmt.setString(2, search);
            stmt.setString(3, search);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {

                    Subscription subscription =
                            new Subscription();

                    subscription.setSubscriptionId(
                            rs.getInt("subscription_id")
                    );

                    subscription.setMemberId(
                            rs.getInt("member_id")
                    );

                    subscription.setPackageId(
                            rs.getInt("package_id")
                    );

                    subscription.setMemberName(
                            rs.getString("member_name")
                    );

                    subscription.setPackageName(
                            rs.getString("package_name")
                    );

                    Date startDate =
                            rs.getDate("start_date");

                    if (startDate != null) {
                        subscription.setStartDate(
                                startDate.toLocalDate()
                        );
                    }

                    Date endDate =
                            rs.getDate("end_date");

                    if (endDate != null) {
                        subscription.setEndDate(
                                endDate.toLocalDate()
                        );
                    }

                    subscription.setAmount(
                            rs.getDouble("amount")
                    );

                    subscription.setStatus(
                            rs.getString("status")
                    );

                    Timestamp createdAt =
                            rs.getTimestamp("created_at");

                    if (createdAt != null) {
                        subscription.setCreatedAt(
                                createdAt.toString()
                        );
                    }

                    subscriptions.add(subscription);
                }
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error searching subscriptions:"
            );

            e.printStackTrace();
        }

        return subscriptions;
    }

    // ============================================================
    // ADD SUBSCRIPTION
    // ============================================================

    public boolean addSubscription(
            int memberId,
            int packageId,
            LocalDate startDate,
            LocalDate endDate,
            double amount
    ) {

        String sql =
                "INSERT INTO subscriptions " +
                        "(member_id, package_id, start_date, " +
                        "end_date, amount, status) " +
                        "VALUES (?, ?, ?, ?, ?, 'PENDING')";

        try (
                Connection conn =
                        DatabaseConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, memberId);
            stmt.setInt(2, packageId);
            stmt.setDate(
                    3,
                    Date.valueOf(startDate)
            );
            stmt.setDate(
                    4,
                    Date.valueOf(endDate)
            );
            stmt.setDouble(5, amount);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Error adding subscription:"
            );

            e.printStackTrace();

            return false;
        }
    }

    // ============================================================
    // UPDATE STATUS
    // ============================================================

    public boolean updateStatus(
            int subscriptionId,
            String status
    ) {

        String sql =
                "UPDATE subscriptions " +
                        "SET status = ? " +
                        "WHERE subscription_id = ?";

        try (
                Connection conn =
                        DatabaseConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setString(1, status);
            stmt.setInt(2, subscriptionId);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Error updating subscription status:"
            );

            e.printStackTrace();

            return false;
        }
    }

    // ============================================================
    // DELETE SUBSCRIPTION
    // ============================================================

    public boolean deleteSubscription(
            int subscriptionId
    ) {

        String sql =
                "DELETE FROM subscriptions " +
                        "WHERE subscription_id = ?";

        try (
                Connection conn =
                        DatabaseConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, subscriptionId);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Error deleting subscription:"
            );

            e.printStackTrace();

            return false;
        }
    }

    // ============================================================
    // AUTOMATICALLY EXPIRE OLD SUBSCRIPTIONS
    // ============================================================

    public void updateExpiredSubscriptions() {

        String sql =
                "UPDATE subscriptions " +
                        "SET status = 'EXPIRED' " +
                        "WHERE end_date < CURDATE() " +
                        "AND status = 'ACTIVE'";

        try (
                Connection conn =
                        DatabaseConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.executeUpdate();

        } catch (SQLException e) {

            System.err.println(
                    "Error updating expired subscriptions:"
            );

            e.printStackTrace();
        }
    }
}