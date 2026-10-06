package com.gym.view;

import com.gym.config.DatabaseConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDashboardFrame extends JFrame {

    // =====================================================
    // COLORS
    // =====================================================

    private final Color BACKGROUND =
            new Color(15, 17, 23);

    private final Color CARD =
            new Color(26, 29, 39);

    private final Color SIDEBAR =
            new Color(18, 20, 28);

    private final Color PRIMARY =
            new Color(56, 189, 248);

    private final Color PRIMARY_HOVER =
            new Color(34, 211, 238);

    private final Color TEXT =
            new Color(241, 245, 249);

    private final Color SECONDARY =
            new Color(148, 163, 184);

    private final Color BORDER =
            new Color(51, 65, 85);

    private final Color SUCCESS =
            new Color(34, 197, 94);

    private final Color WARNING =
            new Color(251, 191, 36);

    private final Color DANGER =
            new Color(239, 68, 68);

    private final Color MENU_TEXT =
            new Color(203, 213, 225);

    private final Color MENU_HOVER_BG =
            new Color(30, 41, 59);


    // =====================================================
    // DASHBOARD COUNT LABELS
    // =====================================================

    private JLabel totalMembersValue;
    private JLabel activeTrainersValue;
    private JLabel monthlyRevenueValue;
    private JLabel todayAttendanceValue;


    // =====================================================
    // CONSTRUCTOR
    // =====================================================

    public AdminDashboardFrame(String username) {

        setTitle(
                "Gym Management System - Admin Dashboard"
        );

        setSize(
                1350,
                800
        );

        setMinimumSize(
                new Dimension(
                        1100,
                        700
                )
        );

        // IMPORTANT:
        // Do not use EXIT_ON_CLOSE here.
        // Logout should close only this window.
        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLocationRelativeTo(null);


        JPanel mainPanel =
                new JPanel(
                        new BorderLayout()
                );

        mainPanel.setBackground(
                BACKGROUND
        );


        mainPanel.add(
                createSidebar(),
                BorderLayout.WEST
        );


        mainPanel.add(
                createContent(username),
                BorderLayout.CENTER
        );


        setContentPane(mainPanel);


        // =================================================
        // LOAD REAL DASHBOARD DATA
        // =================================================

        loadDashboardCounts();
    }


    // =====================================================
    // SIDEBAR
    // =====================================================

    private JPanel createSidebar() {

        JPanel sidebar =
                new JPanel(
                        new BorderLayout()
                );

        sidebar.setPreferredSize(
                new Dimension(
                        250,
                        0
                )
        );

        sidebar.setBackground(
                SIDEBAR
        );


        // =================================================
        // LOGO
        // =================================================

        JPanel logoPanel =
                new JPanel();

        logoPanel.setOpaque(false);

        logoPanel.setBorder(
                new EmptyBorder(
                        28,
                        22,
                        28,
                        22
                )
        );

        logoPanel.setLayout(
                new BoxLayout(
                        logoPanel,
                        BoxLayout.Y_AXIS
                )
        );


        JLabel logo =
                new JLabel("GYM");

        logo.setForeground(
                PRIMARY
        );

        logo.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        32
                )
        );


        JLabel subtitle =
                new JLabel(
                        "MANAGEMENT SYSTEM"
                );

        subtitle.setForeground(
                SECONDARY
        );

        subtitle.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        11
                )
        );


        logoPanel.add(logo);

        logoPanel.add(
                Box.createVerticalStrut(4)
        );

        logoPanel.add(subtitle);


        sidebar.add(
                logoPanel,
                BorderLayout.NORTH
        );


        // =================================================
        // MENU
        // =================================================

        JPanel menu =
                new JPanel();

        menu.setOpaque(false);

        menu.setBorder(
                new EmptyBorder(
                        8,
                        14,
                        10,
                        14
                )
        );

        menu.setLayout(
                new BoxLayout(
                        menu,
                        BoxLayout.Y_AXIS
                )
        );


        // =================================================
        // WORKING MENU BUTTONS
        // =================================================

        addMenuButton(
                menu,
                "Dashboard",
                true
        );


        addMenuButton(
                menu,
                "Members",
                false
        );


        addMenuButton(
                menu,
                "Trainers",
                false
        );


        addMenuButton(
                menu,
                "Trainer Assignment",
                false
        );


        addMenuButton(
                menu,
                "Gym Packages",
                false
        );


        addMenuButton(
                menu,
                "Payments",
                false
        );


        sidebar.add(
                menu,
                BorderLayout.CENTER
        );


        // =================================================
        // LOGOUT AREA
        // =================================================

        JPanel bottomPanel =
                new JPanel();

        bottomPanel.setOpaque(false);

        bottomPanel.setLayout(
                new BoxLayout(
                        bottomPanel,
                        BoxLayout.Y_AXIS
                )
        );

        bottomPanel.setBorder(
                new EmptyBorder(
                        10,
                        14,
                        18,
                        14
                )
        );


        // =================================================
        // LOGOUT BUTTON
        // =================================================

        JButton logoutButton =
                new JButton(
                        "🚪  Logout"
                );

        logoutButton.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        44
                )
        );

        logoutButton.setPreferredSize(
                new Dimension(
                        220,
                        44
                )
        );

        logoutButton.setHorizontalAlignment(
                SwingConstants.LEFT
        );

        logoutButton.setBorder(
                new EmptyBorder(
                        0,
                        18,
                        0,
                        12
                )
        );

        logoutButton.setFocusPainted(false);

        logoutButton.setBorderPainted(false);

        logoutButton.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        13
                )
        );

        logoutButton.setForeground(
                MENU_TEXT
        );

        logoutButton.setBackground(
                SIDEBAR
        );

        logoutButton.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );


        // =================================================
        // LOGOUT ACTION
        // =================================================

        logoutButton.addActionListener(
                e -> logout()
        );


        // =================================================
        // LOGOUT HOVER
        // =================================================

        logoutButton.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            MouseEvent e
                    ) {

                        logoutButton.setBackground(
                                MENU_HOVER_BG
                        );

                        logoutButton.setForeground(
                                DANGER
                        );
                    }


                    @Override
                    public void mouseExited(
                            MouseEvent e
                    ) {

                        logoutButton.setBackground(
                                SIDEBAR
                        );

                        logoutButton.setForeground(
                                MENU_TEXT
                        );
                    }
                }
        );


        bottomPanel.add(
                logoutButton
        );


        // =================================================
        // VERSION
        // =================================================

        JLabel version =
                new JLabel(
                        "  v1.0  •  High-Tech Gym OS"
                );

        version.setForeground(
                new Color(
                        100,
                        116,
                        139
                )
        );

        version.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        11
                )
        );

        version.setBorder(
                new EmptyBorder(
                        15,
                        2,
                        0,
                        0
                )
        );


        bottomPanel.add(
                version
        );


        sidebar.add(
                bottomPanel,
                BorderLayout.SOUTH
        );


        return sidebar;
    }


    // =====================================================
    // LOGOUT
    // =====================================================

    private void logout() {

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to logout?",
                        "Confirm Logout",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );


        if (choice != JOptionPane.YES_OPTION) {
            return;
        }


        // Open public home page
        PublicHomeFrame homeFrame =
                new PublicHomeFrame();

        homeFrame.setVisible(true);


        // Close only Admin Dashboard
        dispose();
    }


    // =====================================================
    // MENU BUTTON
    // =====================================================

    private void addMenuButton(
            JPanel menu,
            String text,
            boolean selected
    ) {

        JButton button =
                new JButton(text);


        button.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        44
                )
        );


        button.setPreferredSize(
                new Dimension(
                        220,
                        44
                )
        );


        button.setHorizontalAlignment(
                SwingConstants.LEFT
        );


        button.setBorder(
                new EmptyBorder(
                        0,
                        18,
                        0,
                        12
                )
        );


        button.setFocusPainted(false);


        button.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        13
                )
        );


        button.setBorderPainted(false);


        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );


        // =================================================
        // COLORS
        // =================================================

        if (selected) {

            button.setBackground(
                    PRIMARY
            );

            button.setForeground(
                    Color.BLACK
            );

        } else {

            button.setBackground(
                    SIDEBAR
            );

            button.setForeground(
                    MENU_TEXT
            );
        }


        // =================================================
        // BUTTON ACTIONS
        // =================================================

        if (text.equals("Members")) {

            button.addActionListener(
                    e -> openMemberManagement()
            );
        }


        if (text.equals("Trainers")) {

            button.addActionListener(
                    e -> openTrainerManagement()
            );
        }


        if (text.equals("Trainer Assignment")) {

            button.addActionListener(
                    e -> openTrainerAssignment()
            );
        }


        if (text.equals("Gym Packages")) {

            button.addActionListener(
                    e -> openGymPackageManagement()
            );
        }


        if (text.equals("Payments")) {

            button.addActionListener(
                    e -> openPaymentManagement()
            );
        }


        // =================================================
        // HOVER
        // =================================================

        if (!selected) {

            button.addMouseListener(
                    new MouseAdapter() {

                        @Override
                        public void mouseEntered(
                                MouseEvent e
                        ) {

                            button.setBackground(
                                    MENU_HOVER_BG
                            );

                            button.setForeground(
                                    PRIMARY
                            );
                        }


                        @Override
                        public void mouseExited(
                                MouseEvent e
                        ) {

                            button.setBackground(
                                    SIDEBAR
                            );

                            button.setForeground(
                                    MENU_TEXT
                            );
                        }
                    }
            );

        } else {

            button.addMouseListener(
                    new MouseAdapter() {

                        @Override
                        public void mouseEntered(
                                MouseEvent e
                        ) {

                            button.setBackground(
                                    PRIMARY_HOVER
                            );
                        }


                        @Override
                        public void mouseExited(
                                MouseEvent e
                        ) {

                            button.setBackground(
                                    PRIMARY
                            );
                        }
                    }
            );
        }


        menu.add(button);


        menu.add(
                Box.createVerticalStrut(4)
        );
    }


    // =====================================================
    // MAIN CONTENT
    // =====================================================

    private JPanel createContent(
            String username
    ) {

        JPanel content =
                new JPanel(
                        new BorderLayout()
                );


        content.setBackground(
                BACKGROUND
        );


        content.setBorder(
                new EmptyBorder(
                        28,
                        32,
                        28,
                        32
                )
        );


        // =================================================
        // TOP
        // =================================================

        JPanel top =
                new JPanel(
                        new BorderLayout()
                );


        top.setOpaque(false);


        JPanel welcomePanel =
                new JPanel();


        welcomePanel.setOpaque(false);


        welcomePanel.setLayout(
                new BoxLayout(
                        welcomePanel,
                        BoxLayout.Y_AXIS
                )
        );


        JLabel title =
                new JLabel(
                        "Good day, "
                                + username
                                + " 👋"
                );


        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        26
                )
        );


        title.setForeground(
                TEXT
        );


        JLabel description =
                new JLabel(
                        "Here's what's happening in your gym today."
                );


        description.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );


        description.setForeground(
                SECONDARY
        );


        welcomePanel.add(title);


        welcomePanel.add(
                Box.createVerticalStrut(6)
        );


        welcomePanel.add(description);


        // =================================================
        // NO NOTIFICATION BUTTON
        // =================================================

        top.add(
                welcomePanel,
                BorderLayout.WEST
        );


        content.add(
                top,
                BorderLayout.NORTH
        );


        // =================================================
        // DASHBOARD
        // =================================================

        JPanel dashboard =
                new JPanel();


        dashboard.setOpaque(false);


        dashboard.setBorder(
                new EmptyBorder(
                        28,
                        0,
                        0,
                        0
                )
        );


        dashboard.setLayout(
                new BorderLayout(
                        0,
                        22
                )
        );


        // =================================================
        // STATISTICS
        // =================================================

        JPanel stats =
                new JPanel(
                        new GridLayout(
                                1,
                                4,
                                16,
                                0
                        )
                );


        stats.setOpaque(false);


        // TOTAL MEMBERS

        JPanel totalMembersCard =
                createStatCard(
                        "TOTAL MEMBERS",
                        "0",
                        "Registered members",
                        PRIMARY
                );


        totalMembersValue =
                findValueLabel(
                        totalMembersCard
                );


        stats.add(
                totalMembersCard
        );


        // ACTIVE TRAINERS

        JPanel activeTrainersCard =
                createStatCard(
                        "ACTIVE TRAINERS",
                        "0",
                        "Currently active",
                        SUCCESS
                );


        activeTrainersValue =
                findValueLabel(
                        activeTrainersCard
                );


        stats.add(
                activeTrainersCard
        );


        // MONTHLY REVENUE

        JPanel monthlyRevenueCard =
                createStatCard(
                        "MONTHLY REVENUE",
                        "₹0.00",
                        "This month",
                        WARNING
                );


        monthlyRevenueValue =
                findValueLabel(
                        monthlyRevenueCard
                );


        stats.add(
                monthlyRevenueCard
        );


        // TODAY ATTENDANCE

        JPanel todayAttendanceCard =
                createStatCard(
                        "TODAY'S ATTENDANCE",
                        "0",
                        "Check-ins today",
                        DANGER
                );


        todayAttendanceValue =
                findValueLabel(
                        todayAttendanceCard
                );


        stats.add(
                todayAttendanceCard
        );


        dashboard.add(
                stats,
                BorderLayout.NORTH
        );


        // =================================================
        // MIDDLE
        // =================================================

        JPanel middle =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                20,
                                0
                        )
                );


        middle.setOpaque(false);


        middle.add(
                createInfoCard(
                        "Quick Access",
                        "Use the sidebar to manage members, trainers, packages and payments."
                )
        );


        middle.add(
                createInfoCard(
                        "Trainer Assignment",
                        "Assign approved trainers to members from the Trainer Assignment menu."
                )
        );


        dashboard.add(
                middle,
                BorderLayout.CENTER
        );


        // =================================================
        // BOTTOM
        // =================================================

        JPanel bottom =
                new JPanel(
                        new BorderLayout()
                );


        bottom.setBackground(
                CARD
        );


        bottom.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                20,
                                22,
                                20,
                                22
                        )
                )
        );


        JLabel bottomTitle =
                new JLabel(
                        "GYM MANAGEMENT SYSTEM"
                );


        bottomTitle.setForeground(
                PRIMARY
        );


        bottomTitle.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        16
                )
        );


        JLabel bottomText =
                new JLabel(
                        "Membership • Trainers • Payments • Attendance • Fitness"
                );


        bottomText.setForeground(
                SECONDARY
        );


        bottom.add(
                bottomTitle,
                BorderLayout.NORTH
        );


        bottom.add(
                bottomText,
                BorderLayout.CENTER
        );


        dashboard.add(
                bottom,
                BorderLayout.SOUTH
        );


        content.add(
                dashboard,
                BorderLayout.CENTER
        );


        return content;
    }


    // =====================================================
    // STAT CARD
    // =====================================================

    private JPanel createStatCard(
            String title,
            String value,
            String description,
            Color accent
    ) {

        JPanel card =
                new JPanel(
                        new BorderLayout()
                );


        card.setBackground(
                CARD
        );


        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                20,
                                22,
                                20,
                                22
                        )
                )
        );


        JLabel titleLabel =
                new JLabel(title);


        titleLabel.setForeground(
                SECONDARY
        );


        titleLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        11
                )
        );


        JLabel valueLabel =
                new JLabel(value);


        valueLabel.setForeground(
                TEXT
        );


        valueLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        28
                )
        );


        JLabel descriptionLabel =
                new JLabel(description);


        descriptionLabel.setForeground(
                SECONDARY
        );


        descriptionLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        12
                )
        );


        JPanel center =
                new JPanel();


        center.setOpaque(false);


        center.setLayout(
                new BoxLayout(
                        center,
                        BoxLayout.Y_AXIS
                )
        );


        center.add(titleLabel);


        center.add(
                Box.createVerticalStrut(12)
        );


        center.add(valueLabel);


        center.add(
                Box.createVerticalStrut(4)
        );


        center.add(descriptionLabel);


        card.add(
                center,
                BorderLayout.CENTER
        );


        JPanel accentPanel =
                new JPanel();


        accentPanel.setBackground(
                accent
        );


        accentPanel.setPreferredSize(
                new Dimension(
                        5,
                        0
                )
        );


        card.add(
                accentPanel,
                BorderLayout.WEST
        );


        return card;
    }


    // =====================================================
    // FIND VALUE LABEL FROM STAT CARD
    // =====================================================

    private JLabel findValueLabel(
            JPanel card
    ) {

        Component[] components =
                card.getComponents();


        for (Component component : components) {

            if (component instanceof JPanel) {

                JPanel panel =
                        (JPanel) component;


                Component[] children =
                        panel.getComponents();


                for (Component child : children) {

                    if (child instanceof JLabel) {

                        JLabel label =
                                (JLabel) child;


                        // Value label uses font size 28
                        if (label.getFont().getSize() == 28) {

                            return label;
                        }
                    }
                }
            }
        }


        return new JLabel("0");
    }


    // =====================================================
    // LOAD DASHBOARD COUNTS
    // =====================================================

    private void loadDashboardCounts() {

        String membersSql =
                "SELECT COUNT(*) AS total_members " +
                        "FROM member_profiles";


        String trainersSql =
                "SELECT COUNT(*) AS active_trainers " +
                        "FROM trainer_profiles tp " +
                        "JOIN users u ON tp.user_id = u.user_id " +
                        "WHERE u.role = 'TRAINER' " +
                        "AND u.status = 'ACTIVE'";


        String revenueSql =
                "SELECT COALESCE(SUM(amount), 0) AS monthly_revenue " +
                        "FROM payments " +
                        "WHERE payment_status = 'SUCCESS' " +
                        "AND YEAR(payment_date) = YEAR(CURDATE()) " +
                        "AND MONTH(payment_date) = MONTH(CURDATE())";


        String attendanceSql =
                "SELECT COUNT(*) AS today_attendance " +
                        "FROM attendance " +
                        "WHERE attendance_date = CURDATE()";


        try (
                Connection conn =
                        DatabaseConnection.getConnection();

                PreparedStatement membersStmt =
                        conn.prepareStatement(
                                membersSql
                        );

                PreparedStatement trainersStmt =
                        conn.prepareStatement(
                                trainersSql
                        );

                PreparedStatement revenueStmt =
                        conn.prepareStatement(
                                revenueSql
                        );

                PreparedStatement attendanceStmt =
                        conn.prepareStatement(
                                attendanceSql
                        )
        ) {


            // =================================================
            // TOTAL MEMBERS
            // =================================================

            try (
                    ResultSet rs =
                            membersStmt.executeQuery()
            ) {

                if (rs.next()) {

                    int count =
                            rs.getInt(
                                    "total_members"
                            );

                    totalMembersValue.setText(
                            String.valueOf(count)
                    );
                }
            }


            // =================================================
            // ACTIVE TRAINERS
            // =================================================

            try (
                    ResultSet rs =
                            trainersStmt.executeQuery()
            ) {

                if (rs.next()) {

                    int count =
                            rs.getInt(
                                    "active_trainers"
                            );

                    activeTrainersValue.setText(
                            String.valueOf(count)
                    );
                }
            }


            // =================================================
            // MONTHLY REVENUE
            // =================================================

            try (
                    ResultSet rs =
                            revenueStmt.executeQuery()
            ) {

                if (rs.next()) {

                    double revenue =
                            rs.getDouble(
                                    "monthly_revenue"
                            );

                    monthlyRevenueValue.setText(
                            String.format(
                                    "₹%,.2f",
                                    revenue
                            )
                    );
                }
            }


            // =================================================
            // TODAY'S ATTENDANCE
            // =================================================

            try (
                    ResultSet rs =
                            attendanceStmt.executeQuery()
            ) {

                if (rs.next()) {

                    int count =
                            rs.getInt(
                                    "today_attendance"
                            );

                    todayAttendanceValue.setText(
                            String.valueOf(count)
                    );
                }
            }


        } catch (SQLException e) {

            e.printStackTrace();


            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load dashboard statistics.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =====================================================
    // INFO CARD
    // =====================================================

    private JPanel createInfoCard(
            String title,
            String description
    ) {

        JPanel card =
                new JPanel(
                        new BorderLayout()
                );


        card.setBackground(
                CARD
        );


        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                25,
                                25,
                                25,
                                25
                        )
                )
        );


        JLabel titleLabel =
                new JLabel(title);


        titleLabel.setForeground(
                PRIMARY
        );


        titleLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        18
                )
        );


        JLabel descriptionLabel =
                new JLabel(
                        "<html>"
                                + description
                                + "</html>"
                );


        descriptionLabel.setForeground(
                SECONDARY
        );


        descriptionLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );


        card.add(
                titleLabel,
                BorderLayout.NORTH
        );


        card.add(
                descriptionLabel,
                BorderLayout.CENTER
        );


        return card;
    }


    // =====================================================
    // ADMIN → TRAINER ASSIGNMENT
    // =====================================================

    private void openTrainerAssignment() {

        JFrame frame =
                new JFrame(
                        "Trainer Assignment"
                );


        frame.setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );


        frame.setSize(
                1000,
                650
        );


        frame.setMinimumSize(
                new Dimension(
                        850,
                        550
                )
        );


        frame.setLocationRelativeTo(
                this
        );


        TrainerAssignmentPanel panel =
                new TrainerAssignmentPanel();


        frame.setContentPane(
                panel
        );


        frame.setVisible(true);
    }


    // =====================================================
    // MEMBER MANAGEMENT
    // =====================================================

    private void openMemberManagement() {

        JFrame frame =
                new JFrame(
                        "Member Management"
                );


        frame.setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );


        frame.setSize(
                1250,
                750
        );


        frame.setMinimumSize(
                new Dimension(
                        1000,
                        650
                )
        );


        frame.setLocationRelativeTo(
                this
        );


        MemberManagementPanel panel =
                new MemberManagementPanel();


        frame.setContentPane(
                panel
        );


        frame.setVisible(true);
    }


    // =====================================================
    // TRAINER MANAGEMENT
    // =====================================================

    private void openTrainerManagement() {

        JFrame frame =
                new JFrame(
                        "Trainer Management"
                );


        frame.setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );


        frame.setSize(
                1250,
                750
        );


        frame.setMinimumSize(
                new Dimension(
                        1000,
                        650
                )
        );


        frame.setLocationRelativeTo(
                this
        );


        TrainerManagementPanel panel =
                new TrainerManagementPanel();


        frame.setContentPane(
                panel
        );


        frame.setVisible(true);
    }


    // =====================================================
    // PACKAGE MANAGEMENT
    // =====================================================

    private void openGymPackageManagement() {

        JFrame frame =
                new JFrame(
                        "Gym Package Management"
                );


        frame.setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );


        frame.setSize(
                1250,
                750
        );


        frame.setMinimumSize(
                new Dimension(
                        1000,
                        650
                )
        );


        frame.setLocationRelativeTo(
                this
        );


        GymPackageManagementPanel panel =
                new GymPackageManagementPanel();


        frame.setContentPane(
                panel
        );


        frame.setVisible(true);
    }


    // =====================================================
    // PAYMENT MANAGEMENT
    // =====================================================

    private void openPaymentManagement() {

        JFrame frame =
                new JFrame(
                        "Payment Management"
                );


        frame.setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );


        frame.setSize(
                1200,
                700
        );


        frame.setMinimumSize(
                new Dimension(
                        950,
                        600
                )
        );


        frame.setLocationRelativeTo(
                this
        );


        PaymentManagementPanel panel =
                new PaymentManagementPanel();


        frame.setContentPane(
                panel
        );


        frame.setVisible(true);
    }
}