
package com.gym.view;

import com.gym.config.DatabaseConnection;
import com.gym.dao.MemberTrainerDAO;
import com.gym.model.MemberTrainer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class MemberDashboardFrame extends JFrame {

    private final int userId;
    private final int memberId;

    private JLabel welcomeLabel;
    private JLabel planLabel;
    private JLabel membershipStatusLabel;
    private JLabel expiryLabel;
    private JLabel daysLabel;
    private JLabel paymentLabel;

    // Separate label for the MY TRAINER dashboard card
    private JLabel trainerCardLabel;

    private JLabel trainerNameLabel;
    private JLabel trainerSpecializationLabel;
    private JLabel trainerStatusLabel;

    public MemberDashboardFrame(
            int userId,
            int memberId
    ) {

        this.userId = userId;
        this.memberId = memberId;

        setTitle(
                "Gym Management System - Member Dashboard"
        );

        setSize(1300, 820);

        setMinimumSize(
                new Dimension(1100, 700)
        );

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        buildUI();

        loadMemberData();

        loadTrainerData();
    }

    // =========================================================
    // BUILD UI
    // =========================================================

    private void buildUI() {

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout()
                );

        mainPanel.setBackground(
                new Color(15, 23, 42)
        );

        // =====================================================
        // HEADER
        // =====================================================

        JPanel header =
                new JPanel(
                        new BorderLayout()
                );

        header.setBackground(
                new Color(17, 24, 39)
        );

        header.setBorder(
                new EmptyBorder(
                        20,
                        30,
                        20,
                        30
                )
        );

        JLabel title =
                new JLabel(
                        "GYM MANAGEMENT SYSTEM"
                );

        title.setForeground(
                Color.WHITE
        );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        24
                )
        );

        welcomeLabel =
                new JLabel(
                        "Welcome"
                );

        welcomeLabel.setForeground(
                new Color(96, 165, 250)
        );

        welcomeLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        18
                )
        );

        header.add(
                title,
                BorderLayout.WEST
        );

        header.add(
                welcomeLabel,
                BorderLayout.EAST
        );

        mainPanel.add(
                header,
                BorderLayout.NORTH
        );

        // =====================================================
        // SIDEBAR
        // =====================================================

        JPanel sidebar =
                new JPanel();

        sidebar.setPreferredSize(
                new Dimension(230, 0)
        );

        sidebar.setBackground(
                new Color(17, 24, 39)
        );

        sidebar.setLayout(
                new BoxLayout(
                        sidebar,
                        BoxLayout.Y_AXIS
                )
        );

        sidebar.setBorder(
                new EmptyBorder(
                        30,
                        15,
                        20,
                        15
                )
        );

        JLabel menuTitle =
                new JLabel(
                        "MEMBER MENU"
                );

        menuTitle.setForeground(
                new Color(148, 163, 184)
        );

        menuTitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        menuTitle.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        sidebar.add(menuTitle);

        sidebar.add(
                Box.createVerticalStrut(20)
        );

        addMenuButton(
                sidebar,
                "🏠  Dashboard",
                null
        );

        addMenuButton(
                sidebar,
                "👤  My Profile",
                e -> openMemberProfile()
        );

        addMenuButton(
                sidebar,
                "💳  Membership",
                e -> showMembershipDetails()
        );

        addMenuButton(
                sidebar,
                "👨‍🏫  My Trainer",
                e -> showTrainerDetails()
        );

        addMenuButton(
                sidebar,
                "📅  Attendance",
                e -> openAttendance()
        );

        addMenuButton(
                sidebar,
                "🏋️  My Workout Plan",
                e -> openWorkoutPlans()
        );

        addMenuButton(
                sidebar,
                "📊  Daily Productivity",
                e -> openDailyProductivity()
        );

        addMenuButton(
                sidebar,
                "📈  Progress",
                e -> openProgress()
        );

        sidebar.add(
                Box.createVerticalGlue()
        );

        JButton logoutButton =
                new JButton(
                        "🚪  Logout"
                );

        styleMenuButton(
                logoutButton
        );

        logoutButton.setBackground(
                new Color(127, 29, 29)
        );

        logoutButton.addActionListener(
                e -> logout()
        );

        sidebar.add(logoutButton);

        mainPanel.add(
                sidebar,
                BorderLayout.WEST
        );

        // =====================================================
        // CONTENT
        // =====================================================

        JPanel content =
                new JPanel(
                        new BorderLayout()
                );

        content.setBackground(
                new Color(15, 23, 42)
        );

        content.setBorder(
                new EmptyBorder(
                        30,
                        30,
                        30,
                        30
                )
        );

        // =====================================================
        // TITLE
        // =====================================================

        JLabel dashboardTitle =
                new JLabel(
                        "Member Dashboard"
                );

        dashboardTitle.setForeground(
                Color.WHITE
        );

        dashboardTitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        30
                )
        );

        JLabel subtitle =
                new JLabel(
                        "Manage your fitness journey from one place."
                );

        subtitle.setForeground(
                new Color(148, 163, 184)
        );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        15
                )
        );

        JPanel titlePanel =
                new JPanel();

        titlePanel.setBackground(
                new Color(15, 23, 42)
        );

        titlePanel.setLayout(
                new BoxLayout(
                        titlePanel,
                        BoxLayout.Y_AXIS
                )
        );

        titlePanel.add(
                dashboardTitle
        );

        titlePanel.add(
                Box.createVerticalStrut(5)
        );

        titlePanel.add(
                subtitle
        );

        content.add(
                titlePanel,
                BorderLayout.NORTH
        );

        // =====================================================
        // MAIN CENTER
        // =====================================================

        JPanel centerPanel =
                new JPanel(
                        new BorderLayout()
                );

        centerPanel.setBackground(
                new Color(15, 23, 42)
        );

        // =====================================================
        // MEMBERSHIP CARDS
        // =====================================================

        JPanel cardsPanel =
                new JPanel(
                        new GridLayout(
                                2,
                                3,
                                20,
                                20
                        )
                );

        cardsPanel.setBackground(
                new Color(15, 23, 42)
        );

        cardsPanel.setBorder(
                new EmptyBorder(
                        30,
                        0,
                        20,
                        0
                )
        );

        JPanel planCard =
                createCard(
                        "MEMBERSHIP PLAN",
                        "Loading...",
                        new Color(37, 99, 235)
                );

        JPanel statusCard =
                createCard(
                        "MEMBERSHIP STATUS",
                        "Loading...",
                        new Color(16, 185, 129)
                );

        JPanel expiryCard =
                createCard(
                        "EXPIRY DATE",
                        "Loading...",
                        new Color(124, 58, 237)
                );

        JPanel daysCard =
                createCard(
                        "DAYS REMAINING",
                        "Loading...",
                        new Color(5, 150, 105)
                );

        JPanel paymentCard =
                createCard(
                        "PAYMENT STATUS",
                        "Loading...",
                        new Color(234, 88, 12)
                );

        JPanel trainerCard =
                createCard(
                        "MY TRAINER",
                        "Not Assigned",
                        new Color(219, 39, 119)
                );

        planLabel =
                getValueLabel(planCard);

        membershipStatusLabel =
                getValueLabel(statusCard);

        expiryLabel =
                getValueLabel(expiryCard);

        daysLabel =
                getValueLabel(daysCard);

        paymentLabel =
                getValueLabel(paymentCard);

        trainerCardLabel =
                getValueLabel(trainerCard);

        cardsPanel.add(planCard);
        cardsPanel.add(statusCard);
        cardsPanel.add(expiryCard);
        cardsPanel.add(daysCard);
        cardsPanel.add(paymentCard);
        cardsPanel.add(trainerCard);

        centerPanel.add(
                cardsPanel,
                BorderLayout.CENTER
        );

        // =====================================================
        // TRAINER DETAILS PANEL
        // =====================================================

        JPanel trainerPanel =
                new JPanel();

        trainerPanel.setBackground(
                new Color(30, 41, 59)
        );

        trainerPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(51, 65, 85)
                        ),
                        new EmptyBorder(
                                15,
                                20,
                                15,
                                20
                        )
                )
        );

        trainerPanel.setLayout(
                new BoxLayout(
                        trainerPanel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel trainerTitle =
                new JLabel(
                        "👨‍🏫 Assigned Trainer"
                );

        trainerTitle.setForeground(
                Color.WHITE
        );

        trainerTitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        18
                )
        );

        trainerNameLabel =
                new JLabel(
                        "Trainer: Not Assigned"
                );

        trainerNameLabel.setForeground(
                new Color(96, 165, 250)
        );

        trainerNameLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        15
                )
        );

        trainerSpecializationLabel =
                new JLabel(
                        "Specialization: -"
                );

        trainerSpecializationLabel.setForeground(
                new Color(203, 213, 225)
        );

        trainerStatusLabel =
                new JLabel(
                        "Status: -"
                );

        trainerStatusLabel.setForeground(
                new Color(203, 213, 225)
        );

        trainerPanel.add(
                trainerTitle
        );

        trainerPanel.add(
                Box.createVerticalStrut(10)
        );

        trainerPanel.add(
                trainerNameLabel
        );

        trainerPanel.add(
                Box.createVerticalStrut(5)
        );

        trainerPanel.add(
                trainerSpecializationLabel
        );

        trainerPanel.add(
                Box.createVerticalStrut(5)
        );

        trainerPanel.add(
                trainerStatusLabel
        );

        centerPanel.add(
                trainerPanel,
                BorderLayout.SOUTH
        );

        content.add(
                centerPanel,
                BorderLayout.CENTER
        );

        // =====================================================
        // QUICK ACTIONS
        // =====================================================

        JPanel bottom =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                15,
                                10
                        )
                );

        bottom.setBackground(
                new Color(15, 23, 42)
        );

        JButton attendanceButton =
                new JButton("📱 Mark Attendance");

        JButton workoutButton =
                new JButton("🏋 View Workout Plan");

        JButton progressButton =
                new JButton("📈 View Progress");

// =====================================================
// BUTTON ACTIONS
// =====================================================

        attendanceButton.addActionListener(e -> {

            MemberAttendanceFrame frame =
                    new MemberAttendanceFrame(memberId);

            frame.setVisible(true);
        });


        workoutButton.addActionListener(e -> {

            MemberWorkoutPlanFrame frame =
                    new MemberWorkoutPlanFrame(memberId);

            frame.setVisible(true);
        });


        progressButton.addActionListener(e -> {

            MemberProgressFrame frame =
                    new MemberProgressFrame(memberId);

            frame.setVisible(true);
        });
        styleActionButton(
                attendanceButton
        );

        styleActionButton(
                workoutButton
        );

        styleActionButton(
                progressButton
        );

        attendanceButton.addActionListener(
                e -> showMessage(
                        "QR Attendance module will open here."
                )
        );

        workoutButton.addActionListener(
                e -> showMessage(
                        "Workout Plan module will open here."
                )
        );

        progressButton.addActionListener(
                e -> showMessage(
                        "Progress module will open here."
                )
        );

        bottom.add(attendanceButton);
        bottom.add(workoutButton);
        bottom.add(progressButton);

        content.add(
                bottom,
                BorderLayout.SOUTH
        );

        mainPanel.add(
                content,
                BorderLayout.CENTER
        );

        add(mainPanel);
    }

    // =========================================================
    // LOAD MEMBER DATA
    // =========================================================

    private void loadMemberData() {

        String sql =
                "SELECT mp.full_name, " +
                        "gp.package_name, " +
                        "s.start_date, " +
                        "s.end_date, " +
                        "s.status, " +
                        "COALESCE(p.payment_status, 'PENDING') AS payment_status " +
                        "FROM member_profiles mp " +
                        "LEFT JOIN subscriptions s " +
                        "ON mp.member_id = s.member_id " +
                        "LEFT JOIN gym_packages gp " +
                        "ON s.package_id = gp.package_id " +
                        "LEFT JOIN payments p " +
                        "ON s.subscription_id = p.subscription_id " +
                        "WHERE mp.member_id = ? " +
                        "ORDER BY s.subscription_id DESC " +
                        "LIMIT 1";

        try (
                Connection conn =
                        DatabaseConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setInt(
                    1,
                    memberId
            );

            try (
                    ResultSet rs =
                            stmt.executeQuery()
            ) {

                if (rs.next()) {

                    String name =
                            rs.getString(
                                    "full_name"
                            );

                    String packageName =
                            rs.getString(
                                    "package_name"
                            );

                    Date startDate =
                            rs.getDate(
                                    "start_date"
                            );

                    Date expiry =
                            rs.getDate(
                                    "end_date"
                            );

                    String subscriptionStatus =
                            rs.getString(
                                    "status"
                            );

                    String paymentStatus =
                            rs.getString(
                                    "payment_status"
                            );

                    welcomeLabel.setText(
                            "Welcome, "
                                    + name
                                    + " 👋"
                    );

                    // =========================================
                    // PACKAGE
                    // =========================================

                    planLabel.setText(
                            packageName != null
                                    ? packageName
                                    : "No Membership"
                    );

                    // =========================================
                    // MEMBERSHIP STATUS
                    // =========================================

                    if (
                            "ACTIVE".equalsIgnoreCase(
                                    subscriptionStatus
                            )
                    ) {

                        membershipStatusLabel.setText(
                                "✓ ACTIVE"
                        );

                        membershipStatusLabel.setForeground(
                                new Color(
                                        52,
                                        211,
                                        153
                                )
                        );

                    } else if (
                            "PENDING".equalsIgnoreCase(
                                    subscriptionStatus
                            )
                    ) {

                        membershipStatusLabel.setText(
                                "PENDING"
                        );

                    } else if (
                            "EXPIRED".equalsIgnoreCase(
                                    subscriptionStatus
                            )
                    ) {

                        membershipStatusLabel.setText(
                                "EXPIRED"
                        );

                    } else {

                        membershipStatusLabel.setText(
                                subscriptionStatus != null
                                        ? subscriptionStatus
                                        : "NO ACTIVE PLAN"
                        );
                    }

                    // =========================================
                    // EXPIRY
                    // =========================================

                    expiryLabel.setText(
                            expiry != null
                                    ? expiry.toString()
                                    : "-"
                    );

                    // =========================================
                    // DAYS REMAINING
                    // =========================================

                    if (expiry != null) {

                        LocalDate expiryDate =
                                expiry.toLocalDate();

                        long days =
                                ChronoUnit.DAYS.between(
                                        LocalDate.now(),
                                        expiryDate
                                );

                        if (days < 0) {
                            days = 0;
                        }

                        daysLabel.setText(
                                days + " Days"
                        );

                    } else {

                        daysLabel.setText(
                                "-"
                        );
                    }

                    // =========================================
                    // PAYMENT
                    // =========================================

                    if (
                            "SUCCESS".equalsIgnoreCase(
                                    paymentStatus
                            )
                    ) {

                        paymentLabel.setText(
                                "✓ PAID"
                        );

                        paymentLabel.setForeground(
                                new Color(
                                        52,
                                        211,
                                        153
                                )
                        );

                    } else {

                        paymentLabel.setText(
                                paymentStatus != null
                                        ? paymentStatus
                                        : "PENDING"
                        );
                    }

                } else {

                    welcomeLabel.setText(
                            "Welcome 👋"
                    );

                    planLabel.setText(
                            "No Membership"
                    );

                    membershipStatusLabel.setText(
                            "NOT ACTIVE"
                    );

                    expiryLabel.setText("-");
                    daysLabel.setText("-");
                    paymentLabel.setText(
                            "PENDING"
                    );
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load member information.\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // LOAD TRAINER
    // =========================================================

    private void loadTrainerData() {

        MemberTrainerDAO dao =
                new MemberTrainerDAO();

        MemberTrainer trainer =
                dao.getActiveTrainerForMember(
                        memberId
                );

        if (trainer == null) {

            trainerCardLabel.setText(
                    "Not Assigned"
            );

            trainerNameLabel.setText(
                    "Trainer: Not Assigned"
            );

            trainerSpecializationLabel.setText(
                    "Specialization: -"
            );

            trainerStatusLabel.setText(
                    "Status: Waiting for admin assignment"
            );

            return;
        }

        trainerCardLabel.setText(
                trainer.getTrainerName()
        );

        trainerNameLabel.setText(
                "Trainer: "
                        + trainer.getTrainerName()
        );

        trainerSpecializationLabel.setText(
                "Specialization: "
                        + (
                        trainer.getSpecialization() != null
                                ? trainer.getSpecialization()
                                : "General Fitness"
                )
        );

        trainerStatusLabel.setText(
                "Status: "
                        + trainer.getStatus()
        );
    }

    // =========================================================
    // MEMBERSHIP DETAILS
    // =========================================================

    private void showMembershipDetails() {

        JOptionPane.showMessageDialog(
                this,
                "Your membership information is shown "
                        + "on the dashboard.\n\n"
                        + "Package: "
                        + planLabel.getText()
                        + "\n"
                        + "Status: "
                        + membershipStatusLabel.getText()
                        + "\n"
                        + "Expiry: "
                        + expiryLabel.getText()
                        + "\n"
                        + "Payment: "
                        + paymentLabel.getText(),
                "Membership Details",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // =========================================================
    // TRAINER DETAILS
    // =========================================================

    private void showTrainerDetails() {

        JOptionPane.showMessageDialog(
                this,
                trainerNameLabel.getText()
                        + "\n"
                        + trainerSpecializationLabel.getText()
                        + "\n"
                        + trainerStatusLabel.getText(),
                "My Trainer",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // =========================================================
    // CARD
    // =========================================================

    private JPanel createCard(
            String title,
            String value,
            Color accent
    ) {

        JPanel card =
                new JPanel(
                        new BorderLayout()
                );

        card.setBackground(
                new Color(30, 41, 59)
        );

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        51,
                                        65,
                                        85
                                )
                        ),
                        new EmptyBorder(
                                20,
                                20,
                                20,
                                20
                        )
                )
        );

        JPanel accentBar =
                new JPanel();

        accentBar.setBackground(
                accent
        );

        accentBar.setPreferredSize(
                new Dimension(
                        6,
                        0
                )
        );

        JPanel textPanel =
                new JPanel();

        textPanel.setBackground(
                new Color(30, 41, 59)
        );

        textPanel.setLayout(
                new BoxLayout(
                        textPanel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setForeground(
                new Color(
                        148,
                        163,
                        184
                )
        );

        titleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        JLabel valueLabel =
                new JLabel(value);

        valueLabel.setForeground(
                Color.WHITE
        );

        valueLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        22
                )
        );

        textPanel.add(
                titleLabel
        );

        textPanel.add(
                Box.createVerticalStrut(12)
        );

        textPanel.add(
                valueLabel
        );

        card.add(
                accentBar,
                BorderLayout.WEST
        );

        card.add(
                textPanel,
                BorderLayout.CENTER
        );

        return card;
    }

    private JLabel getValueLabel(
            JPanel card
    ) {

        JPanel textPanel =
                (JPanel) card.getComponent(1);

        return (JLabel)
                textPanel.getComponent(2);
    }

    // =========================================================
    // MENU BUTTON
    // =========================================================

    private void addMenuButton(
            JPanel sidebar,
            String text,
            java.awt.event.ActionListener listener
    ) {

        JButton button =
                new JButton(text);

        styleMenuButton(button);

        if (listener != null) {

            button.addActionListener(
                    listener
            );
        }

        sidebar.add(button);

        sidebar.add(
                Box.createVerticalStrut(8)
        );
    }

    private void styleMenuButton(
            JButton button
    ) {

        button.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        48
                )
        );

        button.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        button.setHorizontalAlignment(
                SwingConstants.LEFT
        );

        button.setForeground(
                Color.WHITE
        );

        button.setBackground(
                new Color(
                        30,
                        41,
                        59
                )
        );

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );
    }

    // =========================================================
    // ACTION BUTTON
    // =========================================================

    private void styleActionButton(
            JButton button
    ) {

        button.setForeground(
                Color.WHITE
        );

        button.setBackground(
                new Color(
                        37,
                        99,
                        235
                )
        );

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.setPreferredSize(
                new Dimension(
                        190,
                        45
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );
    }

    // =========================================================
    // LOGOUT
    // =========================================================
    private void openGymAttendanceQR() {

        GymAttendanceQRFrame frame =
                new GymAttendanceQRFrame();

        frame.setVisible(true);
    }
    private void openWorkoutPlans() {

        MemberWorkoutPlanFrame frame =
                new MemberWorkoutPlanFrame(memberId);

        frame.setVisible(true);
    }
    private void openAttendance() {

        MemberAttendanceFrame attendanceFrame =
                new MemberAttendanceFrame(memberId);

        attendanceFrame.setVisible(true);
    }
    private void openDailyProductivity() {

        JFrame frame =
                new JFrame(
                        "Daily Productivity"
                );

        frame.setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        frame.setSize(
                1100,
                750
        );

        frame.setMinimumSize(
                new Dimension(
                        900,
                        600
                )
        );

        frame.setLocationRelativeTo(
                this
        );

        DailyProductivityPanel panel =
                new DailyProductivityPanel(
                        memberId
                );

        frame.setContentPane(
                panel
        );

        frame.setVisible(
                true
        );
    }
    private void openProgress() {

        MemberProgressFrame frame =
                new MemberProgressFrame(
                        memberId
                );

        frame.setVisible(true);
    }
    private void logout() {

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to logout?",
                        "Logout",
                        JOptionPane.YES_NO_OPTION
                );

        if (
                result ==
                        JOptionPane.YES_OPTION
        ) {

            LoginFrame loginFrame =
                    new LoginFrame();

            loginFrame.setVisible(true);

            dispose();
        }
    }

    // =========================================================
    // MESSAGE
    // =========================================================
    private void openMemberProfile() {

        MemberProfileFrame frame =
                new MemberProfileFrame(
                        userId,
                        memberId
                );

        frame.setVisible(true);
    }
    private void showMessage(
            String message
    ) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Gym Management System",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}
