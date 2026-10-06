package com.gym.view;

import com.gym.config.DatabaseConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberManagementPanel extends JPanel {

    // =========================
    // COLORS
    // =========================
    private final Color BACKGROUND = new Color(15, 17, 23);
    private final Color CARD = new Color(26, 29, 39);
    private final Color PRIMARY = new Color(56, 189, 248);
    private final Color TEXT = new Color(241, 245, 249);
    private final Color SECONDARY = new Color(148, 163, 184);
    private final Color BORDER = new Color(51, 65, 85);
    private final Color SUCCESS = new Color(34, 197, 94);
    private final Color DANGER = new Color(239, 68, 68);
    private final Color WARNING = new Color(251, 191, 36);

    private JTable memberTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;

    public MemberManagementPanel() {

        setLayout(new BorderLayout());
        setBackground(BACKGROUND);
        setBorder(new EmptyBorder(25, 30, 25, 30));

        add(createHeader(), BorderLayout.NORTH);
        add(createMainContent(), BorderLayout.CENTER);

        loadMembers();
    }

    // =========================
    // HEADER
    // =========================
    private JPanel createHeader() {

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(0, 0, 20, 0));

        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setLayout(
                new BoxLayout(titlePanel, BoxLayout.Y_AXIS)
        );

        JLabel title = new JLabel("Member Management");
        title.setForeground(TEXT);
        title.setFont(
                new Font("SansSerif", Font.BOLD, 28)
        );

        JLabel subtitle = new JLabel(
                "Manage registered gym members and their profiles"
        );

        subtitle.setForeground(SECONDARY);
        subtitle.setFont(
                new Font("SansSerif", Font.PLAIN, 13)
        );

        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(5));
        titlePanel.add(subtitle);

        JButton addButton = new JButton("+ Add Member");

        stylePrimaryButton(addButton);

        addButton.addActionListener(
                e -> showAddMemberDialog()
        );

        header.add(
                titlePanel,
                BorderLayout.WEST
        );

        header.add(
                addButton,
                BorderLayout.EAST
        );

        return header;
    }

    // =========================
    // MAIN CONTENT
    // =========================
    private JPanel createMainContent() {

        JPanel main = new JPanel(new BorderLayout(0, 15));
        main.setOpaque(false);

        main.add(
                createToolbar(),
                BorderLayout.NORTH
        );

        main.add(
                createTableCard(),
                BorderLayout.CENTER
        );

        return main;
    }

    // =========================
    // TOOLBAR
    // =========================
    private JPanel createToolbar() {

        JPanel toolbar = new JPanel(new BorderLayout());
        toolbar.setBackground(CARD);

        toolbar.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER),
                        new EmptyBorder(12, 15, 12, 15)
                )
        );

        JPanel searchPanel = new JPanel(
                new BorderLayout(10, 0)
        );

        searchPanel.setOpaque(false);

        JLabel searchLabel = new JLabel("Search");
        searchLabel.setForeground(SECONDARY);
        searchLabel.setFont(
                new Font("SansSerif", Font.BOLD, 12)
        );

        searchField = new JTextField();
        searchField.setPreferredSize(
                new Dimension(300, 36)
        );

        searchField.setBackground(
                new Color(15, 17, 23)
        );

        searchField.setForeground(TEXT);
        searchField.setCaretColor(PRIMARY);

        searchField.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER),
                        new EmptyBorder(5, 10, 5, 10)
                )
        );

        searchField.setToolTipText(
                "Search by name or phone"
        );

        searchField.addActionListener(
                e -> searchMembers()
        );

        searchPanel.add(
                searchLabel,
                BorderLayout.WEST
        );

        searchPanel.add(
                searchField,
                BorderLayout.CENTER
        );

        JPanel buttonPanel = new JPanel(
                new FlowLayout(
                        FlowLayout.RIGHT,
                        8,
                        0
                )
        );

        buttonPanel.setOpaque(false);

        JButton searchButton =
                new JButton("Search");

        JButton refreshButton =
                new JButton("Refresh");

        JButton viewButton =
                new JButton("View Details");

        JButton deleteButton =
                new JButton("Delete");

        styleSecondaryButton(searchButton);
        styleSecondaryButton(refreshButton);
        styleSecondaryButton(viewButton);

        deleteButton.setFocusPainted(false);
        deleteButton.setFont(
                new Font("SansSerif", Font.BOLD, 12)
        );
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setBackground(DANGER);
        deleteButton.setBorder(
                new EmptyBorder(9, 15, 9, 15)
        );
        deleteButton.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        searchButton.addActionListener(
                e -> searchMembers()
        );

        refreshButton.addActionListener(
                e -> {
                    searchField.setText("");
                    loadMembers();
                }
        );

        viewButton.addActionListener(
                e -> viewSelectedMember()
        );

        deleteButton.addActionListener(
                e -> deleteSelectedMember()
        );

        buttonPanel.add(searchButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(deleteButton);

        toolbar.add(
                searchPanel,
                BorderLayout.WEST
        );

        toolbar.add(
                buttonPanel,
                BorderLayout.EAST
        );

        return toolbar;
    }

    // =========================
    // TABLE CARD
    // =========================
    private JPanel createTableCard() {

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER),
                        new EmptyBorder(15, 15, 15, 15)
                )
        );

        String[] columns = {
                "Member ID",
                "Full Name",
                "Phone",
                "Date of Birth",
                "Gender",
                "Address",
                "Emergency Contact"
        };

        tableModel = new DefaultTableModel(
                columns,
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

        memberTable = new JTable(tableModel);

        memberTable.setRowHeight(40);

        memberTable.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        13
                )
        );

        memberTable.setBackground(CARD);
        memberTable.setForeground(TEXT);

        memberTable.setSelectionBackground(
                new Color(51, 65, 85)
        );

        memberTable.setSelectionForeground(TEXT);

        memberTable.setShowGrid(false);
        memberTable.setFillsViewportHeight(true);

        memberTable.getTableHeader().setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        12
                )
        );

        memberTable.getTableHeader().setBackground(
                new Color(30, 34, 46)
        );

        memberTable.getTableHeader().setForeground(
                SECONDARY
        );

        memberTable.getTableHeader().setPreferredSize(
                new Dimension(0, 40)
        );

        memberTable.setAutoCreateRowSorter(true);

        // Double-click
        memberTable.getSelectionModel()
                .addListSelectionListener(e -> {
                    // Selection is handled by buttons.
                });

        JScrollPane scrollPane =
                new JScrollPane(memberTable);

        scrollPane.setBorder(
                BorderFactory.createEmptyBorder()
        );

        scrollPane.getViewport().setBackground(
                CARD
        );

        card.add(
                scrollPane,
                BorderLayout.CENTER
        );

        return card;
    }

    // =========================
    // LOAD MEMBERS
    // =========================
    private void loadMembers() {

        tableModel.setRowCount(0);

        String sql =
                "SELECT " +
                        "mp.member_id, " +
                        "mp.full_name, " +
                        "u.phone, " +
                        "mp.date_of_birth, " +
                        "mp.gender, " +
                        "mp.address, " +
                        "mp.emergency_contact " +
                        "FROM member_profiles mp " +
                        "JOIN users u ON mp.user_id = u.user_id " +
                        "ORDER BY mp.member_id DESC";

        try (
                Connection conn =
                        DatabaseConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql);

                ResultSet rs =
                        stmt.executeQuery()
        ) {

            while (rs.next()) {

                tableModel.addRow(
                        new Object[]{
                                rs.getInt("member_id"),
                                rs.getString("full_name"),
                                rs.getString("phone"),
                                rs.getDate("date_of_birth"),
                                rs.getString("gender"),
                                rs.getString("address"),
                                rs.getString("emergency_contact")
                        }
                );
            }

        } catch (SQLException e) {

            showError(
                    "Unable to load members.\n\n"
                            + e.getMessage()
            );
        }
    }

    // =========================
    // SEARCH
    // =========================
    private void searchMembers() {

        String keyword =
                searchField.getText().trim();

        if (keyword.isEmpty()) {
            loadMembers();
            return;
        }

        tableModel.setRowCount(0);

        String sql =
                "SELECT " +
                        "mp.member_id, " +
                        "mp.full_name, " +
                        "u.phone, " +
                        "mp.date_of_birth, " +
                        "mp.gender, " +
                        "mp.address, " +
                        "mp.emergency_contact " +
                        "FROM member_profiles mp " +
                        "JOIN users u ON mp.user_id = u.user_id " +
                        "WHERE mp.full_name LIKE ? " +
                        "OR u.phone LIKE ? " +
                        "ORDER BY mp.member_id DESC";

        try (
                Connection conn =
                        DatabaseConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            String search =
                    "%" + keyword + "%";

            stmt.setString(1, search);
            stmt.setString(2, search);

            try (ResultSet rs =
                         stmt.executeQuery()) {

                while (rs.next()) {

                    tableModel.addRow(
                            new Object[]{
                                    rs.getInt("member_id"),
                                    rs.getString("full_name"),
                                    rs.getString("phone"),
                                    rs.getDate("date_of_birth"),
                                    rs.getString("gender"),
                                    rs.getString("address"),
                                    rs.getString("emergency_contact")
                            }
                    );
                }
            }

        } catch (SQLException e) {

            showError(
                    "Search failed.\n\n"
                            + e.getMessage()
            );
        }
    }

    // =========================
    // ADD MEMBER
    // =========================
    private void showAddMemberDialog() {

        JTextField usernameField =
                new JTextField();

        JPasswordField passwordField =
                new JPasswordField();

        JTextField emailField =
                new JTextField();

        JTextField phoneField =
                new JTextField();

        JTextField nameField =
                new JTextField();

        JTextField dobField =
                new JTextField();

        JComboBox<String> genderBox =
                new JComboBox<>(
                        new String[]{
                                "Select Gender",
                                "Male",
                                "Female",
                                "Other"
                        }
                );

        JTextField addressField =
                new JTextField();

        JTextField emergencyField =
                new JTextField();

        JPanel panel = new JPanel(
                new GridLayout(0, 2, 10, 10)
        );

        panel.setBorder(
                new EmptyBorder(10, 10, 10, 10)
        );

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        panel.add(new JLabel("Email:"));
        panel.add(emailField);

        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);

        panel.add(new JLabel("Full Name:"));
        panel.add(nameField);

        panel.add(
                new JLabel("Date of Birth (YYYY-MM-DD):")
        );

        panel.add(dobField);

        panel.add(new JLabel("Gender:"));
        panel.add(genderBox);

        panel.add(new JLabel("Address:"));
        panel.add(addressField);

        panel.add(
                new JLabel("Emergency Contact:")
        );

        panel.add(emergencyField);

        int result =
                JOptionPane.showConfirmDialog(
                        SwingUtilities.getWindowAncestor(this),
                        panel,
                        "Add New Member",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        String username =
                usernameField.getText().trim();

        String password =
                new String(
                        passwordField.getPassword()
                );

        String email =
                emailField.getText().trim();

        String phone =
                phoneField.getText().trim();

        String fullName =
                nameField.getText().trim();

        String dob =
                dobField.getText().trim();

        String gender =
                (String) genderBox.getSelectedItem();

        String address =
                addressField.getText().trim();

        String emergency =
                emergencyField.getText().trim();

        // =========================
        // VALIDATION
        // =========================

        if (username.isEmpty()
                || password.isEmpty()
                || email.isEmpty()
                || phone.isEmpty()
                || fullName.isEmpty()
                || dob.isEmpty()
                || address.isEmpty()
                || emergency.isEmpty()) {

            showWarning(
                    "Please fill in all required fields."
            );

            return;
        }

        if (username.length() < 4) {

            showWarning(
                    "Username must contain at least 4 characters."
            );

            return;
        }

        if (password.length() < 8) {

            showWarning(
                    "Password must contain at least 8 characters."
            );

            return;
        }

        if (!phone.matches("\\d{10}")) {

            showWarning(
                    "Phone number must contain exactly 10 digits."
            );

            return;
        }

        if (!email.matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
        )) {

            showWarning(
                    "Please enter a valid email address."
            );

            return;
        }

        if ("Select Gender".equals(gender)) {

            showWarning(
                    "Please select a gender."
            );

            return;
        }

        Date dateOfBirth;

        try {

            dateOfBirth =
                    Date.valueOf(dob);

        } catch (IllegalArgumentException e) {

            showWarning(
                    "Date of birth must use YYYY-MM-DD format."
            );

            return;
        }

        // =========================
        // CREATE USER + MEMBER
        // =========================

        String userSql =
                "INSERT INTO users " +
                        "(username, password, email, phone, role, status) " +
                        "VALUES (?, ?, ?, ?, 'MEMBER', 'ACTIVE')";

        String memberSql =
                "INSERT INTO member_profiles " +
                        "(user_id, full_name, date_of_birth, gender, address, emergency_contact) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";

        Connection conn = null;

        try {

            conn =
                    DatabaseConnection.getConnection();

            conn.setAutoCommit(false);

            int userId;

            try (
                    PreparedStatement userStmt =
                            conn.prepareStatement(
                                    userSql,
                                    Statement.RETURN_GENERATED_KEYS
                            )
            ) {

                userStmt.setString(1, username);
                userStmt.setString(2, password);
                userStmt.setString(3, email);
                userStmt.setString(4, phone);

                userStmt.executeUpdate();

                try (ResultSet keys =
                             userStmt.getGeneratedKeys()) {

                    if (!keys.next()) {

                        throw new SQLException(
                                "Unable to create user account."
                        );
                    }

                    userId =
                            keys.getInt(1);
                }
            }

            try (
                    PreparedStatement memberStmt =
                            conn.prepareStatement(memberSql)
            ) {

                memberStmt.setInt(1, userId);
                memberStmt.setString(2, fullName);
                memberStmt.setDate(3, dateOfBirth);
                memberStmt.setString(4, gender);
                memberStmt.setString(5, address);
                memberStmt.setString(6, emergency);

                memberStmt.executeUpdate();
            }

            conn.commit();

            JOptionPane.showMessageDialog(
                    SwingUtilities.getWindowAncestor(this),
                    "Member registered successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadMembers();

        } catch (SQLException e) {

            if (conn != null) {

                try {
                    conn.rollback();
                } catch (SQLException ignored) {
                }
            }

            showError(
                    "Unable to register member.\n\n"
                            + e.getMessage()
            );

        } finally {

            if (conn != null) {

                try {
                    conn.close();
                } catch (SQLException ignored) {
                }
            }
        }
    }

    // =========================
    // VIEW MEMBER
    // =========================
    private void viewSelectedMember() {

        int row =
                memberTable.getSelectedRow();

        if (row == -1) {

            showWarning(
                    "Please select a member first."
            );

            return;
        }

        int modelRow =
                memberTable.convertRowIndexToModel(row);

        String details =
                "Member ID: "
                        + tableModel.getValueAt(
                        modelRow, 0
                )
                        + "\n\nFull Name: "
                        + tableModel.getValueAt(
                        modelRow, 1
                )
                        + "\nPhone: "
                        + tableModel.getValueAt(
                        modelRow, 2
                )
                        + "\nDate of Birth: "
                        + tableModel.getValueAt(
                        modelRow, 3
                )
                        + "\nGender: "
                        + tableModel.getValueAt(
                        modelRow, 4
                )
                        + "\nAddress: "
                        + tableModel.getValueAt(
                        modelRow, 5
                )
                        + "\nEmergency Contact: "
                        + tableModel.getValueAt(
                        modelRow, 6
                );

        JOptionPane.showMessageDialog(
                SwingUtilities.getWindowAncestor(this),
                details,
                "Member Details",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // =========================
    // DELETE MEMBER
    // =========================
    private void deleteSelectedMember() {

        int row =
                memberTable.getSelectedRow();

        if (row == -1) {

            showWarning(
                    "Please select a member first."
            );

            return;
        }

        int modelRow =
                memberTable.convertRowIndexToModel(row);

        int memberId =
                Integer.parseInt(
                        tableModel.getValueAt(
                                modelRow,
                                0
                        ).toString()
                );

        String memberName =
                tableModel.getValueAt(
                        modelRow,
                        1
                ).toString();

        int confirm =
                JOptionPane.showConfirmDialog(
                        SwingUtilities.getWindowAncestor(this),
                        "Delete member \"" +
                                memberName +
                                "\"?\n\n"
                                + "This will also remove the member's "
                                + "related records according to the "
                                + "database foreign-key rules.",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        String sql =
                "DELETE FROM member_profiles " +
                        "WHERE member_id = ?";

        try (
                Connection conn =
                        DatabaseConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, memberId);

            int affected =
                    stmt.executeUpdate();

            if (affected > 0) {

                JOptionPane.showMessageDialog(
                        SwingUtilities.getWindowAncestor(this),
                        "Member deleted successfully.",
                        "Deleted",
                        JOptionPane.INFORMATION_MESSAGE
                );

                loadMembers();

            } else {

                showWarning(
                        "Member could not be deleted."
                );
            }

        } catch (SQLException e) {

            showError(
                    "Unable to delete member.\n\n"
                            + e.getMessage()
            );
        }
    }

    // =========================
    // BUTTON STYLES
    // =========================
    private void stylePrimaryButton(
            JButton button
    ) {

        button.setFocusPainted(false);
        button.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        13
                )
        );

        button.setBackground(PRIMARY);
        button.setForeground(Color.BLACK);

        button.setBorder(
                new EmptyBorder(
                        10,
                        18,
                        10,
                        18
                )
        );

        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );
    }

    private void styleSecondaryButton(
            JButton button
    ) {

        button.setFocusPainted(false);
        button.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        12
                )
        );

        button.setBackground(
                new Color(30, 41, 59)
        );

        button.setForeground(TEXT);

        button.setBorder(
                BorderFactory.createLineBorder(BORDER)
        );

        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );
    }

    // =========================
    // MESSAGES
    // =========================
    private void showWarning(
            String message
    ) {

        JOptionPane.showMessageDialog(
                SwingUtilities.getWindowAncestor(this),
                message,
                "Warning",
                JOptionPane.WARNING_MESSAGE
        );
    }

    private void showError(
            String message
    ) {

        JOptionPane.showMessageDialog(
                SwingUtilities.getWindowAncestor(this),
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}