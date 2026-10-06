package com.gym.view;

import com.gym.config.DatabaseConnection;
import com.gym.dao.GymPackageDAO;
import com.gym.model.GymPackage;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class MemberRegistrationFrame extends JFrame {

    private JComboBox<GymPackage> packageComboBox;

    private JTextField fullNameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField usernameField;

    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    private JTextField dobField;

    private JComboBox<String> genderComboBox;

    private JTextField addressField;
    private JTextField emergencyContactField;

    private int selectedPackageId = -1;

    // =====================================================
    // THEME
    // =====================================================

    private final Color BACKGROUND =
            new Color(10, 13, 22);

    private final Color BACKGROUND_2 =
            new Color(15, 19, 31);

    private final Color PANEL =
            new Color(23, 28, 42);

    private final Color PANEL_LIGHT =
            new Color(29, 35, 52);

    private final Color FIELD =
            new Color(31, 38, 56);

    private final Color FIELD_HOVER =
            new Color(38, 46, 66);

    private final Color PRIMARY =
            new Color(0, 210, 255);

    private final Color PRIMARY_HOVER =
            new Color(50, 225, 255);

    private final Color SECONDARY =
            new Color(124, 80, 255);

    private final Color SUCCESS =
            new Color(45, 205, 120);

    private final Color SUCCESS_HOVER =
            new Color(65, 225, 140);

    private final Color DANGER =
            new Color(239, 80, 90);

    private final Color WHITE =
            new Color(245, 247, 250);

    private final Color TEXT =
            new Color(225, 231, 240);

    private final Color MUTED =
            new Color(150, 162, 182);

    private final Color BORDER =
            new Color(52, 63, 84);

    private final Color BORDER_ACTIVE =
            new Color(0, 180, 225);


    // =====================================================
    // CONSTRUCTOR
    // =====================================================

    public MemberRegistrationFrame() {
        this(-1);
    }


    public MemberRegistrationFrame(int packageId) {

        this.selectedPackageId = packageId;

        setTitle("Gym Management System - Member Registration");

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        /*
         * Large initial window.
         * User can maximize it normally.
         */
        setSize(
                1200,
                800
        );

        setMinimumSize(
                new Dimension(
                        950,
                        700
                )
        );

        setLocationRelativeTo(null);

        buildUI();

        loadPackages();
    }


    // =====================================================
    // BUILD UI
    // =====================================================

    private void buildUI() {

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout()
                );

        mainPanel.setBackground(
                BACKGROUND
        );


        // =================================================
        // HEADER
        // =================================================

        JPanel header =
                new JPanel(
                        new BorderLayout()
                );

        header.setBackground(
                PANEL
        );

        header.setBorder(
                new EmptyBorder(
                        20,
                        35,
                        20,
                        35
                )
        );


        // =================================================
        // HEADER LEFT
        // =================================================

        JPanel heading =
                new JPanel();

        heading.setOpaque(false);

        heading.setLayout(
                new BoxLayout(
                        heading,
                        BoxLayout.Y_AXIS
                )
        );


        JLabel title =
                new JLabel(
                        "CREATE YOUR MEMBERSHIP"
                );

        title.setForeground(
                WHITE
        );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        26
                )
        );


        JLabel subtitle =
                new JLabel(
                        "Join the gym and start your fitness journey"
                );

        subtitle.setForeground(
                MUTED
        );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );


        heading.add(
                title
        );

        heading.add(
                Box.createVerticalStrut(5)
        );

        heading.add(
                subtitle
        );


        // =================================================
        // HEADER RIGHT
        // =================================================

        JButton backButton =
                createBackButton();


        backButton.addActionListener(
                e -> goBack()
        );


        header.add(
                heading,
                BorderLayout.WEST
        );

        header.add(
                backButton,
                BorderLayout.EAST
        );


        // =================================================
        // ACCENT LINE
        // =================================================

        JPanel accentLine =
                new JPanel();

        accentLine.setBackground(
                PRIMARY
        );

        accentLine.setPreferredSize(
                new Dimension(
                        0,
                        3
                )
        );


        // =================================================
        // FORM
        // =================================================

        JPanel formPanel =
                new JPanel(
                        new GridBagLayout()
                );

        formPanel.setBackground(
                BACKGROUND
        );

        formPanel.setBorder(
                new EmptyBorder(
                        25,
                        45,
                        35,
                        45
                )
        );


        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(
                        8,
                        10,
                        8,
                        10
                );

        gbc.fill =
                GridBagConstraints.HORIZONTAL;


        // =================================================
        // ACCOUNT SECTION
        // =================================================

        addSectionTitle(
                formPanel,
                "01",
                "MEMBERSHIP & ACCOUNT",
                "Choose your membership and create your login account.",
                gbc,
                0
        );


        // =================================================
        // PACKAGE
        // =================================================

        addLabel(
                formPanel,
                "Membership Package *",
                gbc,
                0,
                1
        );


        packageComboBox =
                new JComboBox<>();

        styleComboBox(
                packageComboBox
        );


        packageComboBox.setRenderer(
                new DefaultListCellRenderer() {

                    @Override
                    public Component getListCellRendererComponent(
                            JList<?> list,
                            Object value,
                            int index,
                            boolean isSelected,
                            boolean cellHasFocus
                    ) {

                        JLabel label =
                                (JLabel) super
                                        .getListCellRendererComponent(
                                                list,
                                                value,
                                                index,
                                                isSelected,
                                                cellHasFocus
                                        );


                        if (value instanceof GymPackage) {

                            GymPackage gymPackage =
                                    (GymPackage) value;


                            label.setText(
                                    gymPackage.getPackageName()
                                            + "   •   ₹"
                                            + String.format(
                                            "%.2f",
                                            gymPackage.getPrice()
                                    )
                                            + "   •   "
                                            + gymPackage.getDuration()
                                            + " "
                                            + gymPackage.getDurationUnit()
                            );
                        }


                        return label;
                    }
                }
        );


        addComponent(
                formPanel,
                packageComboBox,
                gbc,
                1,
                1
        );


        // =================================================
        // USERNAME
        // =================================================

        addLabel(
                formPanel,
                "Username *",
                gbc,
                0,
                2
        );


        usernameField =
                createTextField();


        addComponent(
                formPanel,
                usernameField,
                gbc,
                1,
                2
        );


        // =================================================
        // PASSWORD
        // =================================================

        addLabel(
                formPanel,
                "Password *",
                gbc,
                0,
                3
        );


        passwordField =
                new JPasswordField();

        stylePasswordField(
                passwordField
        );


        addComponent(
                formPanel,
                passwordField,
                gbc,
                1,
                3
        );


        // =================================================
        // CONFIRM PASSWORD
        // =================================================

        addLabel(
                formPanel,
                "Confirm Password *",
                gbc,
                0,
                4
        );


        confirmPasswordField =
                new JPasswordField();

        stylePasswordField(
                confirmPasswordField
        );


        addComponent(
                formPanel,
                confirmPasswordField,
                gbc,
                1,
                4
        );


        // =================================================
        // PERSONAL INFORMATION
        // =================================================

        addSectionTitle(
                formPanel,
                "02",
                "PERSONAL INFORMATION",
                "Tell us a little about yourself.",
                gbc,
                5
        );


        // =================================================
        // FULL NAME
        // =================================================

        addLabel(
                formPanel,
                "Full Name *",
                gbc,
                0,
                6
        );


        fullNameField =
                createTextField();


        addComponent(
                formPanel,
                fullNameField,
                gbc,
                1,
                6
        );


        // =================================================
        // EMAIL
        // =================================================

        addLabel(
                formPanel,
                "Email *",
                gbc,
                0,
                7
        );


        emailField =
                createTextField();


        addComponent(
                formPanel,
                emailField,
                gbc,
                1,
                7
        );


        // =================================================
        // PHONE
        // =================================================

        addLabel(
                formPanel,
                "Phone *",
                gbc,
                0,
                8
        );


        phoneField =
                createTextField();


        addComponent(
                formPanel,
                phoneField,
                gbc,
                1,
                8
        );


        // =================================================
        // DATE OF BIRTH
        // =================================================

        addLabel(
                formPanel,
                "Date of Birth",
                gbc,
                0,
                9
        );


        dobField =
                createTextField();

        dobField.setToolTipText(
                "Format: YYYY-MM-DD"
        );


        addComponent(
                formPanel,
                dobField,
                gbc,
                1,
                9
        );


        // =================================================
        // GENDER
        // =================================================

        addLabel(
                formPanel,
                "Gender",
                gbc,
                0,
                10
        );


        genderComboBox =
                new JComboBox<>(
                        new String[]{
                                "Select Gender",
                                "Male",
                                "Female",
                                "Other"
                        }
                );


        styleComboBox(
                genderComboBox
        );


        addComponent(
                formPanel,
                genderComboBox,
                gbc,
                1,
                10
        );


        // =================================================
        // CONTACT INFORMATION
        // =================================================

        addSectionTitle(
                formPanel,
                "03",
                "CONTACT INFORMATION",
                "Provide your address and emergency contact details.",
                gbc,
                11
        );


        // =================================================
        // ADDRESS
        // =================================================

        addLabel(
                formPanel,
                "Address",
                gbc,
                0,
                12
        );


        addressField =
                createTextField();


        addComponent(
                formPanel,
                addressField,
                gbc,
                1,
                12
        );


        // =================================================
        // EMERGENCY CONTACT
        // =================================================

        addLabel(
                formPanel,
                "Emergency Contact",
                gbc,
                0,
                13
        );


        emergencyContactField =
                createTextField();


        addComponent(
                formPanel,
                emergencyContactField,
                gbc,
                1,
                13
        );


        // =================================================
        // INFORMATION NOTE
        // =================================================

        gbc.gridx = 0;
        gbc.gridy = 14;
        gbc.gridwidth = 2;
        gbc.weightx = 1;

        JPanel notePanel =
                createInfoPanel(
                        "🔐  Your account will be created securely. "
                                + "After registration, you will continue to the membership payment."
                );


        formPanel.add(
                notePanel,
                gbc
        );


        // =================================================
        // BUTTONS
        // =================================================

        JButton cancelButton =
                createBackButton();


        cancelButton.setText(
                "←  BACK TO HOME"
        );


        JButton registerButton =
                createButton(
                        "CONTINUE TO PAYMENT  →",
                        SUCCESS
                );


        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                12,
                                5
                        )
                );

        buttonPanel.setOpaque(false);


        buttonPanel.add(
                cancelButton
        );

        buttonPanel.add(
                registerButton
        );


        gbc.gridx = 0;
        gbc.gridy = 15;
        gbc.gridwidth = 2;
        gbc.weightx = 1;

        formPanel.add(
                buttonPanel,
                gbc
        );


        // =================================================
        // BUTTON ACTIONS
        // =================================================

        cancelButton.addActionListener(
                e -> goBack()
        );


        registerButton.addActionListener(
                e -> registerMember()
        );


        // =================================================
        // SCROLL PANE
        // =================================================

        JScrollPane scrollPane =
                new JScrollPane(
                        formPanel
                );


        scrollPane.setBorder(
                BorderFactory.createEmptyBorder()
        );


        scrollPane.setBackground(
                BACKGROUND
        );


        scrollPane.getViewport()
                .setBackground(
                        BACKGROUND
                );


        scrollPane.getVerticalScrollBar()
                .setUnitIncrement(
                        16
                );


        // =================================================
        // MAIN LAYOUT
        // =================================================

        mainPanel.add(
                header,
                BorderLayout.NORTH
        );

        mainPanel.add(
                accentLine,
                BorderLayout.CENTER
        );


        JPanel contentWrapper =
                new JPanel(
                        new BorderLayout()
                );

        contentWrapper.setBackground(
                BACKGROUND
        );

        contentWrapper.add(
                scrollPane,
                BorderLayout.CENTER
        );


        mainPanel.add(
                contentWrapper,
                BorderLayout.CENTER
        );


        setContentPane(
                mainPanel
        );
    }


    // =====================================================
    // SECTION TITLE
    // =====================================================

    private void addSectionTitle(
            JPanel panel,
            String number,
            String title,
            String description,
            GridBagConstraints gbc,
            int row
    ) {

        JPanel section =
                new JPanel(
                        new BorderLayout(
                                12,
                                0
                        )
                );

        section.setBackground(
                PANEL
        );

        section.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                13,
                                15,
                                13,
                                15
                        )
                )
        );


        JLabel numberLabel =
                new JLabel(
                        number
                );

        numberLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        numberLabel.setForeground(
                Color.BLACK
        );

        numberLabel.setBackground(
                PRIMARY
        );

        numberLabel.setOpaque(true);

        numberLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        numberLabel.setPreferredSize(
                new Dimension(
                        38,
                        38
                )
        );


        JPanel textPanel =
                new JPanel();

        textPanel.setOpaque(false);

        textPanel.setLayout(
                new BoxLayout(
                        textPanel,
                        BoxLayout.Y_AXIS
                )
        );


        JLabel titleLabel =
                new JLabel(
                        title
                );

        titleLabel.setForeground(
                WHITE
        );

        titleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );


        JLabel descriptionLabel =
                new JLabel(
                        description
                );

        descriptionLabel.setForeground(
                MUTED
        );

        descriptionLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );


        textPanel.add(
                titleLabel
        );

        textPanel.add(
                Box.createVerticalStrut(3)
        );

        textPanel.add(
                descriptionLabel
        );


        section.add(
                numberLabel,
                BorderLayout.WEST
        );

        section.add(
                textPanel,
                BorderLayout.CENTER
        );


        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.weightx = 1;

        gbc.insets =
                new Insets(
                        18,
                        10,
                        10,
                        10
                );


        panel.add(
                section,
                gbc
        );


        gbc.insets =
                new Insets(
                        8,
                        10,
                        8,
                        10
                );
    }


    // =====================================================
    // INFO PANEL
    // =====================================================

    private JPanel createInfoPanel(
            String message
    ) {

        JPanel panel =
                new JPanel(
                        new BorderLayout()
                );

        panel.setBackground(
                new Color(
                        18,
                        45,
                        60
                )
        );

        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        0,
                                        150,
                                        190
                                )
                        ),
                        new EmptyBorder(
                                12,
                                15,
                                12,
                                15
                        )
                )
        );


        JLabel label =
                new JLabel(
                        message
                );

        label.setForeground(
                new Color(
                        175,
                        225,
                        240
                )
        );

        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );


        panel.add(
                label,
                BorderLayout.CENTER
        );


        return panel;
    }


    // =====================================================
    // LOAD PACKAGES
    // =====================================================

    private void loadPackages() {

        GymPackageDAO dao =
                new GymPackageDAO();

        List<GymPackage> packages =
                dao.getActivePackages();


        packageComboBox.removeAllItems();


        if (
                packages == null
                        || packages.isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "No active membership packages are available.\n\n"
                            + "Please contact the gym administrator.",
                    "No Packages Available",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        for (
                GymPackage gymPackage :
                packages
        ) {

            packageComboBox.addItem(
                    gymPackage
            );
        }


        if (selectedPackageId != -1) {

            for (
                    int i = 0;
                    i < packageComboBox.getItemCount();
                    i++
            ) {

                GymPackage gymPackage =
                        packageComboBox.getItemAt(i);


                if (
                        gymPackage.getPackageId()
                                == selectedPackageId
                ) {

                    packageComboBox.setSelectedIndex(
                            i
                    );

                    break;
                }
            }
        }
    }


    // =====================================================
    // REGISTER MEMBER
    // =====================================================

    private void registerMember() {

        String fullName =
                fullNameField
                        .getText()
                        .trim();


        String email =
                emailField
                        .getText()
                        .trim();


        String phone =
                phoneField
                        .getText()
                        .trim();


        String username =
                usernameField
                        .getText()
                        .trim();


        String password =
                new String(
                        passwordField
                                .getPassword()
                );


        String confirmPassword =
                new String(
                        confirmPasswordField
                                .getPassword()
                );


        String dob =
                dobField
                        .getText()
                        .trim();


        String address =
                addressField
                        .getText()
                        .trim();


        String emergency =
                emergencyContactField
                        .getText()
                        .trim();


        // =================================================
        // PACKAGE
        // =================================================

        if (
                packageComboBox.getSelectedItem()
                        == null
        ) {

            showError(
                    "Please select a membership package."
            );

            return;
        }


        GymPackage selectedPackage =
                (GymPackage)
                        packageComboBox
                                .getSelectedItem();


        // =================================================
        // NAME
        // =================================================

        if (fullName.isEmpty()) {

            showError(
                    "Please enter your full name."
            );

            fullNameField.requestFocus();

            return;
        }


        if (
                fullName.length() < 3
                        || fullName.length() > 100
        ) {

            showError(
                    "Full name must contain between 3 and 100 characters."
            );

            fullNameField.requestFocus();

            return;
        }


        if (
                !fullName.matches(
                        "[A-Za-z .'-]+"
                )
        ) {

            showError(
                    "Full name can contain only letters, spaces, apostrophes and hyphens."
            );

            fullNameField.requestFocus();

            return;
        }


        // =================================================
        // EMAIL
        // =================================================

        if (email.isEmpty()) {

            showError(
                    "Please enter your email."
            );

            emailField.requestFocus();

            return;
        }


        if (email.length() > 100) {

            showError(
                    "Email cannot exceed 100 characters."
            );

            emailField.requestFocus();

            return;
        }


        if (
                !email.matches(
                        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
                )
        ) {

            showError(
                    "Please enter a valid email address."
            );

            emailField.requestFocus();

            return;
        }


        // =================================================
        // PHONE
        // =================================================

        if (phone.isEmpty()) {

            showError(
                    "Please enter your phone number."
            );

            phoneField.requestFocus();

            return;
        }


        if (
                !phone.matches(
                        "[6-9][0-9]{9}"
                )
        ) {

            showError(
                    "Phone number must contain exactly 10 digits and start with 6-9."
            );

            phoneField.requestFocus();

            return;
        }


        // =================================================
        // USERNAME
        // =================================================

        if (username.isEmpty()) {

            showError(
                    "Please enter a username."
            );

            usernameField.requestFocus();

            return;
        }


        if (
                username.length() < 4
                        || username.length() > 50
        ) {

            showError(
                    "Username must contain between 4 and 50 characters."
            );

            usernameField.requestFocus();

            return;
        }


        if (
                !username.matches(
                        "[A-Za-z0-9_]+"
                )
        ) {

            showError(
                    "Username can contain only letters, numbers and underscore."
            );

            usernameField.requestFocus();

            return;
        }


        // =================================================
        // PASSWORD
        // =================================================

        if (password.length() < 8) {

            showError(
                    "Password must contain at least 8 characters."
            );

            passwordField.requestFocus();

            return;
        }


        if (password.contains(" ")) {

            showError(
                    "Password cannot contain spaces."
            );

            passwordField.requestFocus();

            return;
        }


        if (!password.matches(".*[A-Z].*")) {

            showError(
                    "Password must contain at least one uppercase letter."
            );

            passwordField.requestFocus();

            return;
        }


        if (!password.matches(".*[a-z].*")) {

            showError(
                    "Password must contain at least one lowercase letter."
            );

            passwordField.requestFocus();

            return;
        }


        if (!password.matches(".*[0-9].*")) {

            showError(
                    "Password must contain at least one number."
            );

            passwordField.requestFocus();

            return;
        }


        if (!password.matches(".*[^A-Za-z0-9].*")) {

            showError(
                    "Password must contain at least one special character."
            );

            passwordField.requestFocus();

            return;
        }


        // =================================================
        // CONFIRM PASSWORD
        // =================================================

        if (
                !password.equals(
                        confirmPassword
                )
        ) {

            showError(
                    "Passwords do not match."
            );

            confirmPasswordField.requestFocus();

            return;
        }


        // =================================================
        // DATE OF BIRTH
        // =================================================

        LocalDate dateOfBirth =
                null;


        if (!dob.isEmpty()) {

            try {

                dateOfBirth =
                        LocalDate.parse(dob);

            } catch (Exception e) {

                showError(
                        "Invalid date of birth.\n"
                                + "Use format: YYYY-MM-DD"
                );

                dobField.requestFocus();

                return;
            }


            if (
                    dateOfBirth.isAfter(
                            LocalDate.now()
                    )
            ) {

                showError(
                        "Date of birth cannot be in the future."
                );

                dobField.requestFocus();

                return;
            }


            int age =
                    Period.between(
                            dateOfBirth,
                            LocalDate.now()
                    ).getYears();


            if (age < 18) {

                showError(
                        "Member must be at least 18 years old."
                );

                dobField.requestFocus();

                return;
            }


            if (age > 100) {

                showError(
                        "Please enter a valid date of birth."
                );

                dobField.requestFocus();

                return;
            }
        }


        // =================================================
        // GENDER
        // =================================================

        if (
                genderComboBox.getSelectedIndex()
                        == 0
        ) {

            showError(
                    "Please select your gender."
            );

            genderComboBox.requestFocus();

            return;
        }


        // =================================================
        // ADDRESS
        // =================================================

        if (address.length() > 255) {

            showError(
                    "Address cannot exceed 255 characters."
            );

            addressField.requestFocus();

            return;
        }


        // =================================================
        // EMERGENCY CONTACT
        // =================================================

        if (!emergency.isEmpty()) {

            if (
                    !emergency.matches(
                            "[6-9][0-9]{9}"
                    )
            ) {

                showError(
                        "Emergency contact must contain exactly 10 digits and start with 6-9."
                );

                emergencyContactField.requestFocus();

                return;
            }
        }


        // =================================================
        // CONFIRM
        // =================================================

        int confirmation =
                JOptionPane.showConfirmDialog(
                        this,
                        "Create your gym account with:\n\n"
                                + "Name: "
                                + fullName
                                + "\n"
                                + "Package: "
                                + selectedPackage.getPackageName()
                                + "\n"
                                + "Price: ₹"
                                + String.format(
                                "%.2f",
                                selectedPackage.getPrice()
                        )
                                + "\n\n"
                                + "Continue to payment?",
                        "Confirm Registration",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );


        if (
                confirmation
                        != JOptionPane.YES_OPTION
        ) {

            return;
        }


        // =================================================
        // SQL
        // =================================================

        String checkUserSql =
                "SELECT user_id "
                        + "FROM users "
                        + "WHERE username = ? "
                        + "OR email = ?";


        String insertUserSql =
                "INSERT INTO users "
                        + "(username, password, email, phone, role, status) "
                        + "VALUES (?, ?, ?, ?, 'MEMBER', 'ACTIVE')";


        String insertProfileSql =
                "INSERT INTO member_profiles "
                        + "(user_id, full_name, date_of_birth, gender, address, emergency_contact) "
                        + "VALUES (?, ?, ?, ?, ?, ?)";


        String getMemberIdSql =
                "SELECT member_id "
                        + "FROM member_profiles "
                        + "WHERE user_id = ?";


        // =================================================
        // DATABASE
        // =================================================

        try (
                Connection conn =
                        DatabaseConnection.getConnection()
        ) {

            conn.setAutoCommit(false);


            // =================================================
            // CHECK DUPLICATE
            // =================================================

            try (
                    PreparedStatement checkStmt =
                            conn.prepareStatement(
                                    checkUserSql
                            )
            ) {

                checkStmt.setString(
                        1,
                        username
                );

                checkStmt.setString(
                        2,
                        email
                );


                try (
                        ResultSet rs =
                                checkStmt.executeQuery()
                ) {

                    if (rs.next()) {

                        conn.rollback();

                        showError(
                                "Username or email already exists."
                        );

                        return;
                    }
                }
            }


            // =================================================
            // CREATE USER
            // =================================================

            int userId;


            try (
                    PreparedStatement userStmt =
                            conn.prepareStatement(
                                    insertUserSql,
                                    Statement.RETURN_GENERATED_KEYS
                            )
            ) {

                userStmt.setString(
                        1,
                        username
                );

                userStmt.setString(
                        2,
                        password
                );

                userStmt.setString(
                        3,
                        email
                );

                userStmt.setString(
                        4,
                        phone
                );


                int affectedRows =
                        userStmt.executeUpdate();


                if (affectedRows == 0) {

                    conn.rollback();

                    showError(
                            "Could not create user account."
                    );

                    return;
                }


                try (
                        ResultSet keys =
                                userStmt.getGeneratedKeys()
                ) {

                    if (!keys.next()) {

                        conn.rollback();

                        showError(
                                "Could not retrieve the new user ID."
                        );

                        return;
                    }


                    userId =
                            keys.getInt(1);
                }
            }


            // =================================================
            // CREATE MEMBER PROFILE
            // =================================================

            try (
                    PreparedStatement profileStmt =
                            conn.prepareStatement(
                                    insertProfileSql
                            )
            ) {

                profileStmt.setInt(
                        1,
                        userId
                );

                profileStmt.setString(
                        2,
                        fullName
                );


                if (dateOfBirth == null) {

                    profileStmt.setNull(
                            3,
                            java.sql.Types.DATE
                    );

                } else {

                    profileStmt.setDate(
                            3,
                            Date.valueOf(
                                    dateOfBirth
                            )
                    );
                }


                profileStmt.setString(
                        4,
                        genderComboBox
                                .getSelectedItem()
                                .toString()
                );

                profileStmt.setString(
                        5,
                        address
                );

                profileStmt.setString(
                        6,
                        emergency
                );


                int affectedRows =
                        profileStmt.executeUpdate();


                if (affectedRows == 0) {

                    conn.rollback();

                    showError(
                            "Could not create member profile."
                    );

                    return;
                }
            }


            // =================================================
            // GET MEMBER ID
            // =================================================

            int memberId = -1;


            try (
                    PreparedStatement memberStmt =
                            conn.prepareStatement(
                                    getMemberIdSql
                            )
            ) {

                memberStmt.setInt(
                        1,
                        userId
                );


                try (
                        ResultSet rs =
                                memberStmt.executeQuery()
                ) {

                    if (rs.next()) {

                        memberId =
                                rs.getInt(
                                        "member_id"
                                );
                    }
                }
            }


            if (memberId == -1) {

                conn.rollback();

                showError(
                        "Member profile was created, but member ID could not be found."
                );

                return;
            }


            // =================================================
            // COMMIT
            // =================================================

            conn.commit();


            // =================================================
            // SUCCESS
            // =================================================

            JOptionPane.showMessageDialog(
                    this,
                    "Registration successful!\n\n"
                            + "Welcome, "
                            + fullName
                            + "!\n\n"
                            + "Selected Package: "
                            + selectedPackage.getPackageName()
                            + "\n"
                            + "Amount: ₹"
                            + String.format(
                            "%.2f",
                            selectedPackage.getPrice()
                    )
                            + "\n\n"
                            + "Continue to payment.",
                    "Registration Successful",
                    JOptionPane.INFORMATION_MESSAGE
            );


            // =================================================
            // OPEN PAYMENT
            // =================================================

            SubscriptionPaymentFrame paymentFrame =
                    new SubscriptionPaymentFrame(
                            userId,
                            memberId,
                            selectedPackage.getPackageId()
                    );


            paymentFrame.setVisible(
                    true
            );


            dispose();


        } catch (
                java.sql.SQLIntegrityConstraintViolationException e
        ) {

            e.printStackTrace();

            showError(
                    "Username or email already exists."
            );


        } catch (Exception e) {

            e.printStackTrace();

            showError(
                    "Registration failed.\n\n"
                            + e.getMessage()
            );
        }
    }


    // =====================================================
    // ADD LABEL
    // =====================================================

    private void addLabel(
            JPanel panel,
            String text,
            GridBagConstraints gbc,
            int x,
            int y
    ) {

        JLabel label =
                new JLabel(
                        text
                );

        label.setForeground(
                TEXT
        );

        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );


        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        gbc.weightx = 0.30;


        panel.add(
                label,
                gbc
        );
    }


    // =====================================================
    // ADD COMPONENT
    // =====================================================

    private void addComponent(
            JPanel panel,
            Component component,
            GridBagConstraints gbc,
            int x,
            int y
    ) {

        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        gbc.weightx = 0.70;


        panel.add(
                component,
                gbc
        );
    }


    // =====================================================
    // TEXT FIELD
    // =====================================================

    private JTextField createTextField() {

        JTextField field =
                new JTextField();


        field.setPreferredSize(
                new Dimension(
                        500,
                        44
                )
        );


        field.setBackground(
                FIELD
        );

        field.setForeground(
                WHITE
        );

        field.setCaretColor(
                PRIMARY
        );


        field.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );


        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                6,
                                12,
                                6,
                                12
                        )
                )
        );


        addFieldHoverEffect(
                field
        );


        return field;
    }


    // =====================================================
    // PASSWORD FIELD
    // =====================================================

    private void stylePasswordField(
            JPasswordField field
    ) {

        field.setPreferredSize(
                new Dimension(
                        500,
                        44
                )
        );


        field.setBackground(
                FIELD
        );

        field.setForeground(
                WHITE
        );

        field.setCaretColor(
                PRIMARY
        );


        field.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );


        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                6,
                                12,
                                6,
                                12
                        )
                )
        );


        addFieldHoverEffect(
                field
        );
    }


    // =====================================================
    // FIELD HOVER
    // =====================================================

    private void addFieldHoverEffect(
            JComponent field
    ) {

        field.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            MouseEvent e
                    ) {

                        field.setBackground(
                                FIELD_HOVER
                        );
                    }


                    @Override
                    public void mouseExited(
                            MouseEvent e
                    ) {

                        field.setBackground(
                                FIELD
                        );
                    }
                }
        );
    }


    // =====================================================
    // COMBO BOX
    // =====================================================

    private void styleComboBox(
            JComboBox<?> comboBox
    ) {

        comboBox.setPreferredSize(
                new Dimension(
                        500,
                        44
                )
        );


        comboBox.setBackground(
                FIELD
        );

        comboBox.setForeground(
                WHITE
        );


        comboBox.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );


        comboBox.setBorder(
                BorderFactory.createLineBorder(
                        BORDER
                )
        );
    }


    // =====================================================
    // BUTTON
    // =====================================================

    private JButton createButton(
            String text,
            Color color
    ) {

        JButton button =
                new JButton(
                        text
                );


        button.setForeground(
                Color.WHITE
        );

        button.setBackground(
                color
        );


        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
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
                        230,
                        46
                )
        );


        button.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            MouseEvent e
                    ) {

                        button.setBackground(
                                color == SUCCESS
                                        ? SUCCESS_HOVER
                                        : PRIMARY_HOVER
                        );
                    }


                    @Override
                    public void mouseExited(
                            MouseEvent e
                    ) {

                        button.setBackground(
                                color
                        );
                    }
                }
        );


        return button;
    }


    // =====================================================
    // BACK BUTTON
    // =====================================================

    private JButton createBackButton() {

        JButton button =
                new JButton(
                        "←  BACK TO HOME"
                );


        button.setForeground(
                WHITE
        );

        button.setBackground(
                PANEL_LIGHT
        );


        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );


        button.setFocusPainted(
                false
        );

        button.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                10,
                                18,
                                10,
                                18
                        )
                )
        );


        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );


        button.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            MouseEvent e
                    ) {

                        button.setBackground(
                                new Color(
                                        45,
                                        53,
                                        72
                                )
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
                                PANEL_LIGHT
                        );

                        button.setForeground(
                                WHITE
                        );
                    }
                }
        );


        return button;
    }


    // =====================================================
    // ERROR
    // =====================================================

    private void showError(
            String message
    ) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Validation Error",
                JOptionPane.WARNING_MESSAGE
        );
    }


    // =====================================================
    // BACK TO HOME
    // =====================================================

    private void goBack() {

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to return to the home page?\n\n"
                                + "Any information entered in this form will be lost.",
                        "Back to Home",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );


        if (
                choice
                        != JOptionPane.YES_OPTION
        ) {

            return;
        }


        PublicHomeFrame home =
                new PublicHomeFrame();


        home.setVisible(
                true
        );


        dispose();
    }
}