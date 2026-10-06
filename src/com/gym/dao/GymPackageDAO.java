package com.gym.dao;

import com.gym.config.DatabaseConnection;
import com.gym.model.GymPackage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GymPackageDAO {

    // =====================================================
    // ADD PACKAGE
    // =====================================================

    public boolean addPackage(GymPackage gymPackage) {

        String sql =
                "INSERT INTO gym_packages " +
                        "(package_name, description, duration, duration_unit, price, features, status) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (
                Connection conn =
                        DatabaseConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setString(
                    1,
                    gymPackage.getPackageName()
            );

            stmt.setString(
                    2,
                    gymPackage.getDescription()
            );

            stmt.setInt(
                    3,
                    gymPackage.getDuration()
            );

            stmt.setString(
                    4,
                    gymPackage.getDurationUnit()
            );

            stmt.setDouble(
                    5,
                    gymPackage.getPrice()
            );

            stmt.setString(
                    6,
                    gymPackage.getFeatures()
            );

            stmt.setString(
                    7,
                    gymPackage.getStatus()
            );

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println("ADD PACKAGE ERROR");
            e.printStackTrace();

            return false;
        }
    }


    // =====================================================
    // GET ALL PACKAGES
    // =====================================================

    public List<GymPackage> getAllPackages() {

        List<GymPackage> packages =
                new ArrayList<>();

        String sql =
                "SELECT package_id, package_name, description, " +
                        "duration, duration_unit, price, features, status, created_at " +
                        "FROM gym_packages " +
                        "ORDER BY package_id DESC";

        try (
                Connection conn =
                        DatabaseConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql);

                ResultSet rs =
                        stmt.executeQuery()
        ) {

            while (rs.next()) {

                packages.add(
                        mapPackage(rs)
                );
            }

        } catch (SQLException e) {

            System.err.println(
                    "GET PACKAGES ERROR"
            );

            e.printStackTrace();
        }

        return packages;
    }


    // =====================================================
    // GET ACTIVE PACKAGES
    // Used by PublicHomeFrame and MemberRegistrationFrame
    // =====================================================

    public List<GymPackage> getActivePackages() {

        List<GymPackage> packages =
                new ArrayList<>();

        String sql =
                "SELECT package_id, package_name, description, " +
                        "duration, duration_unit, price, features, status, created_at " +
                        "FROM gym_packages " +
                        "WHERE status = 'ACTIVE' " +
                        "ORDER BY price ASC";

        try (
                Connection conn =
                        DatabaseConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql);

                ResultSet rs =
                        stmt.executeQuery()
        ) {

            while (rs.next()) {

                packages.add(
                        mapPackage(rs)
                );
            }

        } catch (SQLException e) {

            System.err.println(
                    "GET ACTIVE PACKAGES ERROR"
            );

            e.printStackTrace();
        }

        return packages;
    }


    // =====================================================
    // GET PACKAGE BY ID
    // Used by SubscriptionPaymentFrame
    // =====================================================

    public GymPackage getPackageById(
            int packageId
    ) {

        String sql =
                "SELECT package_id, package_name, description, " +
                        "duration, duration_unit, price, features, status, created_at " +
                        "FROM gym_packages " +
                        "WHERE package_id = ?";

        try (
                Connection conn =
                        DatabaseConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setInt(
                    1,
                    packageId
            );

            try (
                    ResultSet rs =
                            stmt.executeQuery()
            ) {

                if (rs.next()) {

                    return mapPackage(rs);
                }
            }

        } catch (SQLException e) {

            System.err.println(
                    "GET PACKAGE BY ID ERROR"
            );

            e.printStackTrace();
        }

        return null;
    }


    // =====================================================
    // SEARCH PACKAGES
    // =====================================================

    public List<GymPackage> searchPackages(
            String keyword
    ) {

        List<GymPackage> packages =
                new ArrayList<>();

        String sql =
                "SELECT package_id, package_name, description, " +
                        "duration, duration_unit, price, features, status, created_at " +
                        "FROM gym_packages " +
                        "WHERE package_name LIKE ? " +
                        "OR description LIKE ? " +
                        "OR features LIKE ? " +
                        "ORDER BY package_id DESC";

        try (
                Connection conn =
                        DatabaseConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            String search =
                    "%" + keyword + "%";

            stmt.setString(
                    1,
                    search
            );

            stmt.setString(
                    2,
                    search
            );

            stmt.setString(
                    3,
                    search
            );

            try (
                    ResultSet rs =
                            stmt.executeQuery()
            ) {

                while (rs.next()) {

                    packages.add(
                            mapPackage(rs)
                    );
                }
            }

        } catch (SQLException e) {

            System.err.println(
                    "SEARCH PACKAGES ERROR"
            );

            e.printStackTrace();
        }

        return packages;
    }


    // =====================================================
    // UPDATE PACKAGE
    // =====================================================

    public boolean updatePackage(
            GymPackage gymPackage
    ) {

        String sql =
                "UPDATE gym_packages SET " +
                        "package_name = ?, " +
                        "description = ?, " +
                        "duration = ?, " +
                        "duration_unit = ?, " +
                        "price = ?, " +
                        "features = ?, " +
                        "status = ? " +
                        "WHERE package_id = ?";

        try (
                Connection conn =
                        DatabaseConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setString(
                    1,
                    gymPackage.getPackageName()
            );

            stmt.setString(
                    2,
                    gymPackage.getDescription()
            );

            stmt.setInt(
                    3,
                    gymPackage.getDuration()
            );

            stmt.setString(
                    4,
                    gymPackage.getDurationUnit()
            );

            stmt.setDouble(
                    5,
                    gymPackage.getPrice()
            );

            stmt.setString(
                    6,
                    gymPackage.getFeatures()
            );

            stmt.setString(
                    7,
                    gymPackage.getStatus()
            );

            stmt.setInt(
                    8,
                    gymPackage.getPackageId()
            );

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println(
                    "UPDATE PACKAGE ERROR"
            );

            e.printStackTrace();

            return false;
        }
    }


    // =====================================================
    // CHANGE STATUS
    // =====================================================

    public boolean updateStatus(
            int packageId,
            String status
    ) {

        String sql =
                "UPDATE gym_packages " +
                        "SET status = ? " +
                        "WHERE package_id = ?";

        try (
                Connection conn =
                        DatabaseConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setString(
                    1,
                    status
            );

            stmt.setInt(
                    2,
                    packageId
            );

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println(
                    "UPDATE PACKAGE STATUS ERROR"
            );

            e.printStackTrace();

            return false;
        }
    }


    // =====================================================
    // DELETE PACKAGE
    // =====================================================

    public boolean deletePackage(
            int packageId
    ) {

        String sql =
                "DELETE FROM gym_packages " +
                        "WHERE package_id = ?";

        try (
                Connection conn =
                        DatabaseConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setInt(
                    1,
                    packageId
            );

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println(
                    "DELETE PACKAGE ERROR"
            );

            e.printStackTrace();

            return false;
        }
    }


    // =====================================================
    // COUNT ACTIVE PACKAGES
    // =====================================================

    public int getActivePackageCount() {

        String sql =
                "SELECT COUNT(*) " +
                        "FROM gym_packages " +
                        "WHERE status = 'ACTIVE'";

        try (
                Connection conn =
                        DatabaseConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql);

                ResultSet rs =
                        stmt.executeQuery()
        ) {

            if (rs.next()) {

                return rs.getInt(1);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return 0;
    }


    // =====================================================
    // COUNT TOTAL PACKAGES
    // =====================================================

    public int getTotalPackageCount() {

        String sql =
                "SELECT COUNT(*) " +
                        "FROM gym_packages";

        try (
                Connection conn =
                        DatabaseConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql);

                ResultSet rs =
                        stmt.executeQuery()
        ) {

            if (rs.next()) {

                return rs.getInt(1);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return 0;
    }


    // =====================================================
    // MAP RESULT
    // =====================================================

    private GymPackage mapPackage(
            ResultSet rs
    ) throws SQLException {

        Timestamp created =
                rs.getTimestamp(
                        "created_at"
                );

        return new GymPackage(

                rs.getInt(
                        "package_id"
                ),

                rs.getString(
                        "package_name"
                ),

                rs.getString(
                        "description"
                ),

                rs.getInt(
                        "duration"
                ),

                rs.getString(
                        "duration_unit"
                ),

                rs.getDouble(
                        "price"
                ),

                rs.getString(
                        "features"
                ),

                rs.getString(
                        "status"
                ),

                created != null
                        ? created.toString()
                        : ""
        );
    }
}