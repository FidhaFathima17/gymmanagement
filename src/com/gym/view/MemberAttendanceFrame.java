package com.gym.view;

import com.gym.config.DatabaseConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MemberAttendanceFrame extends JFrame {

    private final int memberId;

    private JLabel todayStatusLabel;
    private JLabel checkInLabel;
    private JLabel checkOutLabel;

    private JButton checkInButton;
    private JButton checkOutButton;

    private JTable attendanceTable;
    private DefaultTableModel tableModel;

    private final Color BACKGROUND = new Color(15, 18, 25);
    private final Color CARD = new Color(25, 29, 38);
    private final Color CARD_LIGHT = new Color(32, 37, 48);
    private final Color PRIMARY = new Color(0, 200, 255);
    private final Color SUCCESS = new Color(46, 204, 113);
    private final Color WARNING = new Color(255, 193, 7);
    private final Color DANGER = new Color(231, 76, 60);
    private final Color TEXT = new Color(235, 238, 245);
    private final Color MUTED = new Color(160, 170, 185);

    public MemberAttendanceFrame(int memberId) {

        this.memberId = memberId;

        setTitle("Member Attendance");
        setSize(1100, 700);
        setMinimumSize(new Dimension(950, 600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        buildUI();
        loadTodayAttendance();
        loadAttendanceHistory();
    }

    private void buildUI() {

        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(BACKGROUND);
        mainPanel.setBorder(new EmptyBorder(25, 25, 25, 25));

        // =========================================================
        // HEADER
        // =========================================================

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BACKGROUND);

        JLabel titleLabel = new JLabel("📅  My Attendance");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(TEXT);

        JLabel subtitleLabel = new JLabel(
                "Track your daily gym attendance and check-in activity"
        );
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(MUTED);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(BACKGROUND);

        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(5));
        titlePanel.add(subtitleLabel);

        JButton refreshButton = createButton(
                "⟳  Refresh",
                PRIMARY
        );

        refreshButton.addActionListener(e -> {
            loadTodayAttendance();
            loadAttendanceHistory();
        });

        headerPanel.add(titlePanel, BorderLayout.WEST);
        headerPanel.add(refreshButton, BorderLayout.EAST);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // =========================================================
        // TOP CARDS
        // =========================================================

        JPanel cardsPanel = new JPanel(new GridLayout(1, 3, 15, 15));
        cardsPanel.setBackground(BACKGROUND);

        JPanel statusCard = createCard();

        JLabel statusTitle = createCardTitle("TODAY'S STATUS");
        todayStatusLabel = createCardValue("Not Checked In");

        statusCard.add(statusTitle);
        statusCard.add(Box.createVerticalStrut(10));
        statusCard.add(todayStatusLabel);

        JPanel checkInCard = createCard();

        JLabel checkInTitle = createCardTitle("CHECK-IN TIME");
        checkInLabel = createCardValue("--");

        checkInCard.add(checkInTitle);
        checkInCard.add(Box.createVerticalStrut(10));
        checkInCard.add(checkInLabel);

        JPanel checkOutCard = createCard();

        JLabel checkOutTitle = createCardTitle("CHECK-OUT TIME");
        checkOutLabel = createCardValue("--");

        checkOutCard.add(checkOutTitle);
        checkOutCard.add(Box.createVerticalStrut(10));
        checkOutCard.add(checkOutLabel);

        cardsPanel.add(statusCard);
        cardsPanel.add(checkInCard);
        cardsPanel.add(checkOutCard);

        // =========================================================
        // ACTION BUTTONS
        // =========================================================

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        actionPanel.setBackground(BACKGROUND);

        checkInButton = createButton(
                "✓  Check In",
                SUCCESS
        );

        checkOutButton = createButton(
                "↪  Check Out",
                DANGER
        );

        checkInButton.addActionListener(e -> checkIn());
        checkOutButton.addActionListener(e -> checkOut());

        actionPanel.add(checkInButton);
        actionPanel.add(checkOutButton);

        JPanel upperPanel = new JPanel(new BorderLayout());
        upperPanel.setBackground(BACKGROUND);

        upperPanel.add(cardsPanel, BorderLayout.CENTER);
        upperPanel.add(actionPanel, BorderLayout.SOUTH);

        // =========================================================
        // ATTENDANCE HISTORY
        // =========================================================

        JPanel historyPanel = createCard();

        JLabel historyTitle = new JLabel("📋  Attendance History");
        historyTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        historyTitle.setForeground(TEXT);

        historyPanel.add(historyTitle);
        historyPanel.add(Box.createVerticalStrut(15));

        String[] columns = {
                "Date",
                "Check In",
                "Check Out",
                "Method",
                "Status"
        };

        tableModel = new DefaultTableModel(columns, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        attendanceTable = new JTable(tableModel);

        attendanceTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        attendanceTable.setRowHeight(32);
        attendanceTable.setBackground(CARD_LIGHT);
        attendanceTable.setForeground(TEXT);
        attendanceTable.setGridColor(new Color(55, 60, 70));
        attendanceTable.setSelectionBackground(new Color(0, 100, 140));
        attendanceTable.setSelectionForeground(Color.WHITE);

        attendanceTable.getTableHeader().setFont(
                new Font("Segoe UI", Font.BOLD, 14)
        );

        attendanceTable.getTableHeader().setBackground(
                new Color(35, 40, 50)
        );

        attendanceTable.getTableHeader().setForeground(TEXT);
        attendanceTable.getTableHeader().setPreferredSize(
                new Dimension(0, 38)
        );

        DefaultTableCellRenderer centerRenderer =
                new DefaultTableCellRenderer();

        centerRenderer.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        for (int i = 0; i < attendanceTable.getColumnCount(); i++) {
            attendanceTable
                    .getColumnModel()
                    .getColumn(i)
                    .setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(attendanceTable);

        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        new Color(55, 60, 70)
                )
        );

        scrollPane.getViewport().setBackground(CARD_LIGHT);

        historyPanel.add(scrollPane);

        // =========================================================
        // CENTER
        // =========================================================

        JPanel centerPanel = new JPanel(new BorderLayout(0, 15));
        centerPanel.setBackground(BACKGROUND);

        centerPanel.add(upperPanel, BorderLayout.NORTH);
        centerPanel.add(historyPanel, BorderLayout.CENTER);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);
    }

    // =============================================================
    // CHECK IN
    // =============================================================

    private void checkIn() {

        String checkQuery =
                "SELECT attendance_id, check_out_time " +
                        "FROM attendance " +
                        "WHERE member_id = ? " +
                        "AND attendance_date = CURDATE()";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement checkStmt =
                        conn.prepareStatement(checkQuery)
        ) {

            checkStmt.setInt(1, memberId);

            try (ResultSet rs = checkStmt.executeQuery()) {

                if (rs.next()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "You have already checked in today.",
                            "Already Checked In",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    return;
                }
            }

            String insertQuery =
                    "INSERT INTO attendance " +
                            "(member_id, attendance_date, check_in_time, method, status) " +
                            "VALUES (?, CURDATE(), NOW(), 'MANUAL', 'PRESENT')";

            try (PreparedStatement stmt =
                         conn.prepareStatement(insertQuery)) {

                stmt.setInt(1, memberId);

                int rows = stmt.executeUpdate();

                if (rows > 0) {

                    JOptionPane.showMessageDialog(
                            this,
                            "✓ Check-in successful!",
                            "Attendance",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    loadTodayAttendance();
                    loadAttendanceHistory();
                }
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to check in.\n\n" + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =============================================================
    // CHECK OUT
    // =============================================================

    private void checkOut() {

        String query =
                "UPDATE attendance " +
                        "SET check_out_time = NOW() " +
                        "WHERE member_id = ? " +
                        "AND attendance_date = CURDATE() " +
                        "AND check_out_time IS NULL";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt =
                        conn.prepareStatement(query)
        ) {

            stmt.setInt(1, memberId);

            int rows = stmt.executeUpdate();

            if (rows == 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "You have not checked in today " +
                                "or you have already checked out.",
                        "Check Out",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            JOptionPane.showMessageDialog(
                    this,
                    "✓ Check-out successful!",
                    "Attendance",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadTodayAttendance();
            loadAttendanceHistory();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to check out.\n\n" + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =============================================================
    // LOAD TODAY'S ATTENDANCE
    // =============================================================

    private void loadTodayAttendance() {

        String query =
                "SELECT check_in_time, check_out_time, status " +
                        "FROM attendance " +
                        "WHERE member_id = ? " +
                        "AND attendance_date = CURDATE()";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt =
                        conn.prepareStatement(query)
        ) {

            stmt.setInt(1, memberId);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    Timestamp checkIn =
                            rs.getTimestamp("check_in_time");

                    Timestamp checkOut =
                            rs.getTimestamp("check_out_time");

                    String status =
                            rs.getString("status");

                    todayStatusLabel.setText(
                            checkOut == null
                                    ? "Checked In"
                                    : "Completed"
                    );

                    todayStatusLabel.setForeground(
                            checkOut == null
                                    ? SUCCESS
                                    : PRIMARY
                    );

                    if (checkIn != null) {

                        checkInLabel.setText(
                                formatTime(checkIn)
                        );
                    }

                    if (checkOut != null) {

                        checkOutLabel.setText(
                                formatTime(checkOut)
                        );
                    } else {

                        checkOutLabel.setText("--");
                    }

                    checkInButton.setEnabled(false);

                    checkOutButton.setEnabled(
                            checkOut == null
                    );

                } else {

                    todayStatusLabel.setText(
                            "Not Checked In"
                    );

                    todayStatusLabel.setForeground(
                            WARNING
                    );

                    checkInLabel.setText("--");
                    checkOutLabel.setText("--");

                    checkInButton.setEnabled(true);
                    checkOutButton.setEnabled(false);
                }
            }

        } catch (SQLException e) {

            todayStatusLabel.setText(
                    "Database Error"
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load today's attendance.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =============================================================
    // LOAD ATTENDANCE HISTORY
    // =============================================================

    private void loadAttendanceHistory() {

        tableModel.setRowCount(0);

        String query =
                "SELECT attendance_date, " +
                        "check_in_time, " +
                        "check_out_time, " +
                        "method, " +
                        "status " +
                        "FROM attendance " +
                        "WHERE member_id = ? " +
                        "ORDER BY attendance_date DESC";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt =
                        conn.prepareStatement(query)
        ) {

            stmt.setInt(1, memberId);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {

                    Date attendanceDate =
                            rs.getDate("attendance_date");

                    Timestamp checkIn =
                            rs.getTimestamp("check_in_time");

                    Timestamp checkOut =
                            rs.getTimestamp("check_out_time");

                    String method =
                            rs.getString("method");

                    String status =
                            rs.getString("status");

                    tableModel.addRow(
                            new Object[]{
                                    formatDate(attendanceDate),
                                    formatTime(checkIn),
                                    formatTime(checkOut),
                                    method,
                                    status
                            }
                    );
                }
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load attendance history.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =============================================================
    // UI HELPERS
    // =============================================================

    private JPanel createCard() {

        JPanel panel = new JPanel();

        panel.setLayout(
                new BoxLayout(
                        panel,
                        BoxLayout.Y_AXIS
                )
        );

        panel.setBackground(CARD);

        panel.setBorder(
                new EmptyBorder(
                        18,
                        20,
                        18,
                        20
                )
        );

        return panel;
    }

    private JLabel createCardTitle(String text) {

        JLabel label = new JLabel(text);

        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        label.setForeground(MUTED);

        return label;
    }

    private JLabel createCardValue(String text) {

        JLabel label = new JLabel(text);

        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        22
                )
        );

        label.setForeground(TEXT);

        return label;
    }

    private JButton createButton(
            String text,
            Color color
    ) {

        JButton button = new JButton(text);

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        button.setForeground(Color.WHITE);
        button.setBackground(color);

        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        button.setPreferredSize(
                new Dimension(150, 42)
        );

        return button;
    }

    private String formatTime(Timestamp timestamp) {

        if (timestamp == null) {
            return "--";
        }

        return new SimpleDateFormat(
                "hh:mm a"
        ).format(timestamp);
    }

    private String formatDate(Date date) {

        if (date == null) {
            return "--";
        }

        return new SimpleDateFormat(
                "dd MMM yyyy"
        ).format(date);
    }
}