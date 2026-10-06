package com.gym.view;

import com.gym.config.DatabaseConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;

public class MemberProfileFrame extends JFrame {

    private final int userId;
    private final int memberId;

    private JTextField fullNameField;
    private JTextField usernameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField dobField;
    private JTextField genderField;
    private JTextArea addressArea;
    private JTextField emergencyContactField;

    private JButton editButton;
    private JButton saveButton;

    // =========================================================
    // COLORS
    // =========================================================

    private final Color BACKGROUND =
            new Color(15, 18, 25);

    private final Color CARD =
            new Color(25, 29, 38);

    private final Color FIELD =
            new Color(32, 37, 48);

    private final Color PRIMARY =
            new Color(0, 200, 255);

    private final Color SUCCESS =
            new Color(46, 204, 113);

    private final Color TEXT =
            new Color(235, 238, 245);

    private final Color MUTED =
            new Color(160, 170, 185);

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public MemberProfileFrame(int userId, int memberId) {

        this.userId = userId;
        this.memberId = memberId;

        setTitle("My Profile");

        setSize(1000, 750);

        setMinimumSize(
                new Dimension(850, 650)
        );

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        buildUI();

        loadProfile();
    }

    // =========================================================
    // BUILD UI
    // =========================================================

    private void buildUI() {

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout(20, 20)
                );

        mainPanel.setBackground(BACKGROUND);

        mainPanel.setBorder(
                new EmptyBorder(
                        25,
                        30,
                        25,
                        30
                )
        );

        setContentPane(mainPanel);

        // =====================================================
        // HEADER
        // =====================================================

        JPanel header =
                new JPanel(
                        new BorderLayout()
                );

        header.setBackground(BACKGROUND);

        JPanel titlePanel =
                new JPanel();

        titlePanel.setLayout(
                new BoxLayout(
                        titlePanel,
                        BoxLayout.Y_AXIS
                )
        );

        titlePanel.setBackground(BACKGROUND);

        JLabel title =
                new JLabel("👤  My Profile");

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        30
                )
        );

        title.setForeground(TEXT);

        JLabel subtitle =
                new JLabel(
                        "View and manage your personal information"
                );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        subtitle.setForeground(MUTED);

        titlePanel.add(title);

        titlePanel.add(
                Box.createVerticalStrut(5)
        );

        titlePanel.add(subtitle);

        header.add(
                titlePanel,
                BorderLayout.WEST
        );

        mainPanel.add(
                header,
                BorderLayout.NORTH
        );

        // =====================================================
        // PROFILE CARD
        // =====================================================

        JPanel profileCard =
                new JPanel(
                        new GridBagLayout()
                );

        profileCard.setBackground(CARD);

        profileCard.setBorder(
                new EmptyBorder(
                        25,
                        30,
                        25,
                        30
                )
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(8, 8, 8, 8);

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.anchor =
                GridBagConstraints.WEST;

        // =====================================================
        // FULL NAME
        // =====================================================

        fullNameField =
                createTextField();

        addField(
                profileCard,
                gbc,
                0,
                0,
                "Full Name",
                fullNameField
        );

        // =====================================================
        // USERNAME
        // =====================================================

        usernameField =
                createTextField();

        usernameField.setEditable(false);

        addField(
                profileCard,
                gbc,
                0,
                1,
                "Username",
                usernameField
        );

        // =====================================================
        // EMAIL
        // =====================================================

        emailField =
                createTextField();

        addField(
                profileCard,
                gbc,
                0,
                2,
                "Email",
                emailField
        );

        // =====================================================
        // PHONE
        // =====================================================

        phoneField =
                createTextField();

        addField(
                profileCard,
                gbc,
                1,
                0,
                "Phone",
                phoneField
        );

        // =====================================================
        // DATE OF BIRTH
        // =====================================================

        dobField =
                createTextField();

        addField(
                profileCard,
                gbc,
                1,
                1,
                "Date of Birth",
                dobField
        );

        // =====================================================
        // GENDER
        // =====================================================

        genderField =
                createTextField();

        addField(
                profileCard,
                gbc,
                1,
                2,
                "Gender",
                genderField
        );

        // =====================================================
        // ADDRESS
        // =====================================================

        JLabel addressLabel =
                createLabel("Address");

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.weightx = 0.3;

        profileCard.add(
                addressLabel,
                gbc
        );

        addressArea =
                new JTextArea(4, 20);

        addressArea.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        addressArea.setForeground(TEXT);

        addressArea.setBackground(FIELD);

        addressArea.setCaretColor(TEXT);

        addressArea.setLineWrap(true);

        addressArea.setWrapStyleWord(true);

        addressArea.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(55, 62, 75)
                        ),
                        new EmptyBorder(
                                8,
                                10,
                                8,
                                10
                        )
                )
        );

        JScrollPane addressScroll =
                new JScrollPane(addressArea);

        addressScroll.setBorder(
                BorderFactory.createEmptyBorder()
        );

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.weightx = 0.7;
        gbc.fill = GridBagConstraints.BOTH;

        profileCard.add(
                addressScroll,
                gbc
        );

        // =====================================================
        // EMERGENCY CONTACT
        // =====================================================

        emergencyContactField =
                createTextField();

        addField(
                profileCard,
                gbc,
                0,
                4,
                "Emergency Contact",
                emergencyContactField
        );

        // =====================================================
        // PROFILE CARD
        // =====================================================

        mainPanel.add(
                profileCard,
                BorderLayout.CENTER
        );

        // =====================================================
        // BUTTON PANEL
        // =====================================================

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                10,
                                0
                        )
                );

        buttonPanel.setBackground(BACKGROUND);

        editButton =
                createButton(
                        "✏ Edit Profile",
                        PRIMARY
                );

        saveButton =
                createButton(
                        "✓ Save Changes",
                        SUCCESS
                );

        saveButton.setVisible(false);

        editButton.addActionListener(
                e -> enableEditMode()
        );

        saveButton.addActionListener(
                e -> saveProfile()
        );

        buttonPanel.add(editButton);

        buttonPanel.add(saveButton);

        mainPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        setFieldsEditable(false);
    }

    // =========================================================
    // ADD FIELD
    // =========================================================

    private void addField(
            JPanel panel,
            GridBagConstraints gbc,
            int column,
            int row,
            String labelText,
            JTextField field
    ) {

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.gridx = column;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.weightx = 0.3;

        panel.add(
                createLabel(labelText),
                gbc
        );

        gbc.gridx = column + 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.7;

        panel.add(
                field,
                gbc
        );
    }

    // =========================================================
    // LABEL
    // =========================================================

    private JLabel createLabel(String text) {

        JLabel label =
                new JLabel(text);

        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        label.setForeground(MUTED);

        return label;
    }

    // =========================================================
    // TEXT FIELD
    // =========================================================

    private JTextField createTextField() {

        JTextField field =
                new JTextField();

        field.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        field.setForeground(TEXT);

        field.setBackground(FIELD);

        field.setCaretColor(TEXT);

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(55, 62, 75)
                        ),
                        new EmptyBorder(
                                8,
                                10,
                                8,
                                10
                        )
                )
        );

        field.setPreferredSize(
                new Dimension(250, 40)
        );

        return field;
    }

    // =========================================================
    // BUTTON
    // =========================================================

    private JButton createButton(
            String text,
            Color color
    ) {

        JButton button =
                new JButton(text);

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
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setPreferredSize(
                new Dimension(160, 42)
        );

        return button;
    }

    // =========================================================
    // LOAD PROFILE
    // =========================================================

    private void loadProfile() {

        String sql =
                "SELECT " +
                        "u.username, " +
                        "u.email, " +
                        "u.phone, " +
                        "mp.full_name, " +
                        "mp.date_of_birth, " +
                        "mp.gender, " +
                        "mp.address, " +
                        "mp.emergency_contact " +
                        "FROM users u " +
                        "JOIN member_profiles mp " +
                        "ON u.user_id = mp.user_id " +
                        "WHERE u.user_id = ? " +
                        "AND mp.member_id = ?";

        try (
                Connection conn =
                        DatabaseConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, userId);

            stmt.setInt(2, memberId);

            try (
                    ResultSet rs =
                            stmt.executeQuery()
            ) {

                if (rs.next()) {

                    usernameField.setText(
                            safeString(
                                    rs.getString("username")
                            )
                    );

                    emailField.setText(
                            safeString(
                                    rs.getString("email")
                            )
                    );

                    phoneField.setText(
                            safeString(
                                    rs.getString("phone")
                            )
                    );

                    fullNameField.setText(
                            safeString(
                                    rs.getString("full_name")
                            )
                    );

                    Date dob =
                            rs.getDate("date_of_birth");

                    if (dob != null) {

                        dobField.setText(
                                dob.toString()
                        );

                    } else {

                        dobField.setText("");
                    }

                    genderField.setText(
                            safeString(
                                    rs.getString("gender")
                            )
                    );

                    addressArea.setText(
                            safeString(
                                    rs.getString("address")
                            )
                    );

                    emergencyContactField.setText(
                            safeString(
                                    rs.getString(
                                            "emergency_contact"
                                    )
                            )
                    );
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load profile.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // ENABLE EDIT MODE
    // =========================================================

    private void enableEditMode() {

        setFieldsEditable(true);

        editButton.setVisible(false);

        saveButton.setVisible(true);
    }

    // =========================================================
    // SET EDITABLE
    // =========================================================

    private void setFieldsEditable(
            boolean editable
    ) {

        fullNameField.setEditable(editable);

        emailField.setEditable(editable);

        phoneField.setEditable(editable);

        dobField.setEditable(editable);

        genderField.setEditable(editable);

        addressArea.setEditable(editable);

        emergencyContactField.setEditable(editable);

        // Username cannot be changed.
        usernameField.setEditable(false);
    }

    // =========================================================
    // SAVE PROFILE
    // =========================================================

    private void saveProfile() {

        String fullName =
                fullNameField.getText().trim();

        String email =
                emailField.getText().trim();

        String phone =
                phoneField.getText().trim();

        String dob =
                dobField.getText().trim();

        String gender =
                genderField.getText().trim();

        String address =
                addressArea.getText().trim();

        String emergencyContact =
                emergencyContactField
                        .getText()
                        .trim();

        // =====================================================
        // VALIDATION
        // =====================================================

        if (fullName.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Full name is required.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (email.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Email is required.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (!email.matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
        )) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid email address.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (!phone.isEmpty()
                && !phone.matches("\\d{10}")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Phone number must contain exactly 10 digits.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (!emergencyContact.isEmpty()
                && !emergencyContact.matches("\\d{10}")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Emergency contact must contain exactly 10 digits.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (!dob.isEmpty()) {

            try {

                Date.valueOf(dob);

            } catch (IllegalArgumentException e) {

                JOptionPane.showMessageDialog(
                        this,
                        "Date of Birth must use YYYY-MM-DD format.\n\n"
                                + "Example: 2002-05-18",
                        "Invalid Date",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }
        }

        // =====================================================
        // SQL
        // =====================================================

        String userSql =
                "UPDATE users " +
                        "SET email = ?, phone = ? " +
                        "WHERE user_id = ?";

        String memberSql =
                "UPDATE member_profiles " +
                        "SET full_name = ?, " +
                        "date_of_birth = ?, " +
                        "gender = ?, " +
                        "address = ?, " +
                        "emergency_contact = ? " +
                        "WHERE member_id = ? " +
                        "AND user_id = ?";

        try (
                Connection conn =
                        DatabaseConnection.getConnection()
        ) {

            conn.setAutoCommit(false);

            try (
                    PreparedStatement userStmt =
                            conn.prepareStatement(userSql);

                    PreparedStatement memberStmt =
                            conn.prepareStatement(memberSql)
            ) {

                // =================================================
                // UPDATE USERS
                // =================================================

                userStmt.setString(
                        1,
                        email
                );

                if (phone.isEmpty()) {

                    userStmt.setNull(
                            2,
                            Types.VARCHAR
                    );

                } else {

                    userStmt.setString(
                            2,
                            phone
                    );
                }

                userStmt.setInt(
                        3,
                        userId
                );

                userStmt.executeUpdate();

                // =================================================
                // UPDATE MEMBER PROFILE
                // =================================================

                memberStmt.setString(
                        1,
                        fullName
                );

                if (dob.isEmpty()) {

                    memberStmt.setNull(
                            2,
                            Types.DATE
                    );

                } else {

                    memberStmt.setDate(
                            2,
                            Date.valueOf(dob)
                    );
                }

                if (gender.isEmpty()) {

                    memberStmt.setNull(
                            3,
                            Types.VARCHAR
                    );

                } else {

                    memberStmt.setString(
                            3,
                            gender
                    );
                }

                if (address.isEmpty()) {

                    memberStmt.setNull(
                            4,
                            Types.VARCHAR
                    );

                } else {

                    memberStmt.setString(
                            4,
                            address
                    );
                }

                if (emergencyContact.isEmpty()) {

                    memberStmt.setNull(
                            5,
                            Types.VARCHAR
                    );

                } else {

                    memberStmt.setString(
                            5,
                            emergencyContact
                    );
                }

                memberStmt.setInt(
                        6,
                        memberId
                );

                memberStmt.setInt(
                        7,
                        userId
                );

                memberStmt.executeUpdate();

                // =================================================
                // COMMIT
                // =================================================

                conn.commit();

                JOptionPane.showMessageDialog(
                        this,
                        "Profile updated successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                setFieldsEditable(false);

                saveButton.setVisible(false);

                editButton.setVisible(true);
            }

        } catch (SQLException e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to update profile.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // SAFE STRING
    // =========================================================

    private String safeString(String value) {

        return value == null
                ? ""
                : value;
    }
}