package com.gym.view;

import com.gym.config.DatabaseConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class TrainerDashboardFrame extends JFrame {

    private final int trainerId;
    private final String trainerName;

    private JLabel welcomeLabel;
    private JLabel memberCountLabel;
    private JLabel attendanceCountLabel;

    private JTable memberTable;
    private DefaultTableModel memberTableModel;

    private JTable attendanceTable;
    private DefaultTableModel attendanceTableModel;

    // =========================================================
    // COLORS
    // =========================================================

    private final Color BACKGROUND =
            new Color(15, 18, 25);

    private final Color CARD =
            new Color(25, 29, 38);

    private final Color CARD_LIGHT =
            new Color(32, 37, 48);

    private final Color PRIMARY =
            new Color(0, 200, 255);

    private final Color SUCCESS =
            new Color(46, 204, 113);

    private final Color TEXT =
            new Color(235, 238, 245);

    private final Color MUTED =
            new Color(160, 170, 185);

    private final Color WARNING =
            new Color(255, 180, 50);

    private final Color DANGER =
            new Color(239, 68, 68);

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public TrainerDashboardFrame(
            int trainerId,
            String trainerName
    ) {

        this.trainerId = trainerId;
        this.trainerName = trainerName;

        setTitle("Trainer Dashboard");

        setSize(
                1250,
                800
        );

        setMinimumSize(
                new Dimension(
                        1050,
                        650
                )
        );

        setLocationRelativeTo(null);

        // IMPORTANT:
        // Do not use EXIT_ON_CLOSE here.
        // Logout should return to Home instead of
        // closing the entire Java application.
        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        buildUI();

        loadTrainerDetails();
        loadAssignedMembers();
        loadAttendance();
    }

    // =========================================================
    // BUILD UI
    // =========================================================

    private void buildUI() {

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout(
                                20,
                                20
                        )
                );

        mainPanel.setBackground(
                BACKGROUND
        );

        mainPanel.setBorder(
                new EmptyBorder(
                        25,
                        25,
                        25,
                        25
                )
        );

        // =====================================================
        // HEADER
        // =====================================================

        JPanel headerPanel =
                new JPanel(
                        new BorderLayout()
                );

        headerPanel.setBackground(
                BACKGROUND
        );

        // -----------------------------------------------------
        // TITLE
        // -----------------------------------------------------

        JPanel titlePanel =
                new JPanel();

        titlePanel.setLayout(
                new BoxLayout(
                        titlePanel,
                        BoxLayout.Y_AXIS
                )
        );

        titlePanel.setBackground(
                BACKGROUND
        );

        welcomeLabel =
                new JLabel(
                        "Welcome, Trainer"
                );

        welcomeLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        28
                )
        );

        welcomeLabel.setForeground(
                TEXT
        );

        JLabel subtitle =
                new JLabel(
                        "Manage your assigned members and monitor attendance"
                );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        subtitle.setForeground(
                MUTED
        );

        titlePanel.add(
                welcomeLabel
        );

        titlePanel.add(
                Box.createVerticalStrut(5)
        );

        titlePanel.add(
                subtitle
        );

        // -----------------------------------------------------
        // HEADER BUTTONS
        // -----------------------------------------------------

        JPanel headerButtons =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                10,
                                0
                        )
                );

        headerButtons.setBackground(
                BACKGROUND
        );

        // -----------------------------------------------------
        // WORKOUT BUTTON
        // -----------------------------------------------------

        JButton workoutButton =
                createButton(
                        "🏋️  Workout Plans",
                        SUCCESS
                );

        workoutButton.addActionListener(
                e -> openWorkoutPlans()
        );

        // -----------------------------------------------------
        // REFRESH BUTTON
        // -----------------------------------------------------

        JButton refreshButton =
                createButton(
                        "⟳  Refresh",
                        PRIMARY
                );

        refreshButton.addActionListener(
                e -> refreshDashboard()
        );

        // -----------------------------------------------------
        // LOGOUT BUTTON
        // -----------------------------------------------------

        JButton logoutButton =
                createButton(
                        "🚪  Logout",
                        DANGER
                );

        logoutButton.addActionListener(
                e -> logoutToHome()
        );

        // -----------------------------------------------------
        // ADD BUTTONS
        // -----------------------------------------------------

        headerButtons.add(
                workoutButton
        );

        headerButtons.add(
                refreshButton
        );

        headerButtons.add(
                logoutButton
        );

        headerPanel.add(
                titlePanel,
                BorderLayout.WEST
        );

        headerPanel.add(
                headerButtons,
                BorderLayout.EAST
        );

        mainPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );

        // =====================================================
        // SUMMARY CARDS
        // =====================================================

        JPanel cardsPanel =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                15,
                                15
                        )
                );

        cardsPanel.setBackground(
                BACKGROUND
        );

        // -----------------------------------------------------
        // MEMBERS CARD
        // -----------------------------------------------------

        JPanel membersCard =
                createCard();

        JLabel membersTitle =
                createCardTitle(
                        "ASSIGNED MEMBERS"
                );

        memberCountLabel =
                createCardValue(
                        "0"
                );

        membersCard.add(
                membersTitle
        );

        membersCard.add(
                Box.createVerticalStrut(10)
        );

        membersCard.add(
                memberCountLabel
        );

        // -----------------------------------------------------
        // ATTENDANCE CARD
        // -----------------------------------------------------

        JPanel attendanceCard =
                createCard();

        JLabel attendanceTitle =
                createCardTitle(
                        "TODAY'S ATTENDANCE"
                );

        attendanceCountLabel =
                createCardValue(
                        "0"
                );

        attendanceCard.add(
                attendanceTitle
        );

        attendanceCard.add(
                Box.createVerticalStrut(10)
        );

        attendanceCard.add(
                attendanceCountLabel
        );

        cardsPanel.add(
                membersCard
        );

        cardsPanel.add(
                attendanceCard
        );

        // =====================================================
        // ASSIGNED MEMBERS
        // =====================================================

        JPanel membersPanel =
                createCard();

        JLabel membersHeading =
                new JLabel(
                        "👥  Assigned Members"
                );

        membersHeading.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        18
                )
        );

        membersHeading.setForeground(
                TEXT
        );

        membersPanel.add(
                membersHeading
        );

        membersPanel.add(
                Box.createVerticalStrut(15)
        );

        String[] memberColumns = {

                "Member ID",
                "Member Name",
                "Gender",
                "Phone",
                "Assigned Date",
                "Status"
        };

        memberTableModel =
                new DefaultTableModel(
                        memberColumns,
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {
                        return false;
                    }
                };

        memberTable =
                createTable(
                        memberTableModel
                );

        JScrollPane memberScrollPane =
                new JScrollPane(
                        memberTable
                );

        memberScrollPane.setPreferredSize(
                new Dimension(
                        0,
                        220
                )
        );

        membersPanel.add(
                memberScrollPane
        );

        // =====================================================
        // ATTENDANCE
        // =====================================================

        JPanel attendancePanel =
                createCard();

        JLabel attendanceHeading =
                new JLabel(
                        "📅  Member Attendance"
                );

        attendanceHeading.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        18
                )
        );

        attendanceHeading.setForeground(
                TEXT
        );

        attendancePanel.add(
                attendanceHeading
        );

        attendancePanel.add(
                Box.createVerticalStrut(15)
        );

        String[] attendanceColumns = {

                "Member",
                "Date",
                "Check In",
                "Check Out",
                "Method",
                "Status"
        };

        attendanceTableModel =
                new DefaultTableModel(
                        attendanceColumns,
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {
                        return false;
                    }
                };

        attendanceTable =
                createTable(
                        attendanceTableModel
                );

        JScrollPane attendanceScrollPane =
                new JScrollPane(
                        attendanceTable
                );

        attendancePanel.add(
                attendanceScrollPane
        );

        // =====================================================
        // CONTENT
        // =====================================================

        JPanel contentPanel =
                new JPanel(
                        new BorderLayout(
                                0,
                                15
                        )
                );

        contentPanel.setBackground(
                BACKGROUND
        );

        contentPanel.add(
                cardsPanel,
                BorderLayout.NORTH
        );

        JPanel tablesPanel =
                new JPanel(
                        new GridLayout(
                                2,
                                1,
                                0,
                                15
                        )
                );

        tablesPanel.setBackground(
                BACKGROUND
        );

        tablesPanel.add(
                membersPanel
        );

        tablesPanel.add(
                attendancePanel
        );

        contentPanel.add(
                tablesPanel,
                BorderLayout.CENTER
        );

        mainPanel.add(
                contentPanel,
                BorderLayout.CENTER
        );

        setContentPane(
                mainPanel
        );
    }

    // =========================================================
    // OPEN WORKOUT PLANS
    // =========================================================

    private void openWorkoutPlans() {

        JFrame workoutFrame =
                new JFrame(
                        "Workout Plan Management"
                );

        workoutFrame.setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        workoutFrame.setSize(
                1450,
                850
        );

        workoutFrame.setMinimumSize(
                new Dimension(
                        1100,
                        700
                )
        );

        workoutFrame.setLocationRelativeTo(
                this
        );

        WorkoutPlanManagementPanel panel =
                new WorkoutPlanManagementPanel(
                        trainerId
                );

        workoutFrame.setContentPane(
                panel
        );

        workoutFrame.setVisible(
                true
        );
    }

    // =========================================================
    // LOGOUT TO HOME
    // =========================================================

    private void logoutToHome() {

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to logout?",
                        "Logout",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        // -----------------------------------------------------
        // Open Public Home
        // -----------------------------------------------------

        PublicHomeFrame homeFrame =
                new PublicHomeFrame();

        homeFrame.setVisible(
                true
        );

        // -----------------------------------------------------
        // Close only Trainer Dashboard
        // -----------------------------------------------------

        dispose();
    }

    // =========================================================
    // REFRESH DASHBOARD
    // =========================================================

    private void refreshDashboard() {

        loadTrainerDetails();
        loadAssignedMembers();
        loadAttendance();
    }

    // =========================================================
    // LOAD TRAINER DETAILS
    // =========================================================

    private void loadTrainerDetails() {

        String sql =
                "SELECT full_name, specialization " +
                        "FROM trainer_profiles " +
                        "WHERE trainer_id = ?";

        try (
                Connection conn =
                        DatabaseConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setInt(
                    1,
                    trainerId
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

                    String specialization =
                            rs.getString(
                                    "specialization"
                            );

                    welcomeLabel.setText(
                            "Welcome, " + name
                    );

                    setTitle(
                            "Trainer Dashboard - "
                                    + name
                    );
                }
            }

        } catch (SQLException e) {

            welcomeLabel.setText(
                    "Welcome, " + trainerName
            );
        }
    }

    // =========================================================
    // LOAD ASSIGNED MEMBERS
    // =========================================================

    private void loadAssignedMembers() {

        if (memberTableModel == null) {
            return;
        }

        memberTableModel.setRowCount(
                0
        );

        String sql =
                "SELECT DISTINCT " +
                        "mp.member_id, " +
                        "mp.full_name, " +
                        "mp.gender, " +
                        "u.phone, " +
                        "mt.assigned_date, " +
                        "mt.status " +
                        "FROM member_trainers mt " +
                        "JOIN member_profiles mp " +
                        "ON mt.member_id = mp.member_id " +
                        "JOIN users u " +
                        "ON mp.user_id = u.user_id " +
                        "WHERE mt.trainer_id = ? " +
                        "AND mt.status = 'ACTIVE' " +
                        "ORDER BY mp.full_name";

        int count = 0;

        try (
                Connection conn =
                        DatabaseConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setInt(
                    1,
                    trainerId
            );

            try (
                    ResultSet rs =
                            stmt.executeQuery()
            ) {

                while (rs.next()) {

                    memberTableModel.addRow(
                            new Object[]{

                                    rs.getInt(
                                            "member_id"
                                    ),

                                    rs.getString(
                                            "full_name"
                                    ),

                                    rs.getString(
                                            "gender"
                                    ),

                                    rs.getString(
                                            "phone"
                                    ),

                                    rs.getDate(
                                            "assigned_date"
                                    ),

                                    rs.getString(
                                            "status"
                                    )
                            }
                    );

                    count++;
                }
            }

            // -------------------------------------------------
            // REAL ASSIGNED MEMBER COUNT
            // -------------------------------------------------

            memberCountLabel.setText(
                    String.valueOf(
                            count
                    )
            );

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load assigned members.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // LOAD ATTENDANCE
    // =========================================================

    private void loadAttendance() {

        if (attendanceTableModel == null) {
            return;
        }

        attendanceTableModel.setRowCount(
                0
        );

        String sql =
                "SELECT DISTINCT " +
                        "mp.full_name, " +
                        "a.attendance_date, " +
                        "a.check_in_time, " +
                        "a.check_out_time, " +
                        "a.method, " +
                        "a.status " +
                        "FROM attendance a " +
                        "JOIN member_profiles mp " +
                        "ON a.member_id = mp.member_id " +
                        "WHERE EXISTS (" +
                        "SELECT 1 " +
                        "FROM member_trainers mt " +
                        "WHERE mt.member_id = a.member_id " +
                        "AND mt.trainer_id = ? " +
                        "AND mt.status = 'ACTIVE'" +
                        ") " +
                        "ORDER BY a.attendance_date DESC, " +
                        "a.check_in_time DESC";

        int todayCount = 0;

        try (
                Connection conn =
                        DatabaseConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setInt(
                    1,
                    trainerId
            );

            try (
                    ResultSet rs =
                            stmt.executeQuery()
            ) {

                while (rs.next()) {

                    Date attendanceDate =
                            rs.getDate(
                                    "attendance_date"
                            );

                    Timestamp checkIn =
                            rs.getTimestamp(
                                    "check_in_time"
                            );

                    Timestamp checkOut =
                            rs.getTimestamp(
                                    "check_out_time"
                            );

                    String method =
                            rs.getString(
                                    "method"
                            );

                    String status =
                            rs.getString(
                                    "status"
                            );

                    attendanceTableModel.addRow(
                            new Object[]{

                                    rs.getString(
                                            "full_name"
                                    ),

                                    attendanceDate,

                                    formatTime(
                                            checkIn
                                    ),

                                    formatTime(
                                            checkOut
                                    ),

                                    method,

                                    status
                            }
                    );

                    // -------------------------------------------------
                    // REAL TODAY ATTENDANCE COUNT
                    // -------------------------------------------------

                    if (attendanceDate != null) {

                        Date today =
                                Date.valueOf(
                                        java.time.LocalDate.now()
                                );

                        if (attendanceDate.equals(
                                today
                        )) {

                            todayCount++;
                        }
                    }
                }
            }

            attendanceCountLabel.setText(
                    String.valueOf(
                            todayCount
                    )
            );

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load attendance.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // FORMAT TIME
    // =========================================================

    private String formatTime(
            Timestamp timestamp
    ) {

        if (timestamp == null) {
            return "--";
        }

        return new java.text.SimpleDateFormat(
                "hh:mm a"
        ).format(timestamp);
    }

    // =========================================================
    // CREATE CARD
    // =========================================================

    private JPanel createCard() {

        JPanel panel =
                new JPanel();

        panel.setLayout(
                new BoxLayout(
                        panel,
                        BoxLayout.Y_AXIS
                )
        );

        panel.setBackground(
                CARD
        );

        panel.setBorder(
                new EmptyBorder(
                        15,
                        18,
                        15,
                        18
                )
        );

        return panel;
    }

    // =========================================================
    // CARD TITLE
    // =========================================================

    private JLabel createCardTitle(
            String text
    ) {

        JLabel label =
                new JLabel(
                        text
                );

        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        label.setForeground(
                MUTED
        );

        return label;
    }

    // =========================================================
    // CARD VALUE
    // =========================================================

    private JLabel createCardValue(
            String text
    ) {

        JLabel label =
                new JLabel(
                        text
                );

        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        25
                )
        );

        label.setForeground(
                PRIMARY
        );

        return label;
    }

    // =========================================================
    // TABLE
    // =========================================================

    private JTable createTable(
            DefaultTableModel model
    ) {

        JTable table =
                new JTable(
                        model
                );

        table.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        table.setRowHeight(
                30
        );

        table.setBackground(
                CARD_LIGHT
        );

        table.setForeground(
                TEXT
        );

        table.setGridColor(
                new Color(
                        55,
                        60,
                        70
                )
        );

        table.setSelectionBackground(
                new Color(
                        0,
                        100,
                        140
                )
        );

        table.setSelectionForeground(
                Color.WHITE
        );

        table.getTableHeader()
                .setFont(
                        new Font(
                                "Segoe UI",
                                Font.BOLD,
                                13
                        )
                );

        table.getTableHeader()
                .setBackground(
                        new Color(
                                35,
                                40,
                                50
                        )
                );

        table.getTableHeader()
                .setForeground(
                        TEXT
                );

        table.getTableHeader()
                .setPreferredSize(
                        new Dimension(
                                0,
                                35
                        )
                );

        DefaultTableCellRenderer renderer =
                new DefaultTableCellRenderer();

        renderer.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        for (
                int i = 0;
                i < table.getColumnCount();
                i++
        ) {

            table.getColumnModel()
                    .getColumn(i)
                    .setCellRenderer(
                            renderer
                    );
        }

        return table;
    }

    // =========================================================
    // BUTTON
    // =========================================================

    private JButton createButton(
            String text,
            Color color
    ) {

        JButton button =
                new JButton(
                        text
                );

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        button.setForeground(
                Color.WHITE
        );

        button.setBackground(
                color
        );

        button.setFocusPainted(
                false
        );

        button.setBorderPainted(
                false
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setPreferredSize(
                new Dimension(
                        150,
                        40
                )
        );

        return button;
    }
}