package com.gym.view;

import com.gym.dao.TrainerDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Calendar;
import java.util.regex.Pattern;

public class TrainerRegistrationFrame extends JFrame {

    // =========================
    // COLORS
    // =========================

    private static final Color BACKGROUND = new Color(15, 17, 23);
    private static final Color CARD = new Color(26, 29, 39);
    private static final Color INPUT = new Color(35, 39, 51);
    private static final Color PRIMARY = new Color(56, 189, 248);
    private static final Color PRIMARY_HOVER = new Color(34, 211, 238);
    private static final Color TEXT = new Color(241, 245, 249);
    private static final Color SECONDARY = new Color(148, 163, 184);
    private static final Color BORDER = new Color(71, 85, 105);
    private static final Color ERROR = new Color(248, 113, 113);
    private static final Color SUCCESS = new Color(74, 222, 128);

    // =========================
    // FIELDS
    // =========================

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    private JTextField emailField;
    private JTextField phoneField;
    private JTextField fullNameField;

    private JTextField dobField;
    private JButton calendarButton;

    private JComboBox<String> genderCombo;

    private JTextArea addressArea;

    private JTextField qualificationField;
    private JTextField specializationField;
    private JTextField experienceField;
    private JTextField certificationField;

    private JButton registerButton;
    private JButton clearButton;

    private JCheckBox showPasswordCheckBox;

    private final TrainerDAO trainerDAO;

    private boolean registering = false;

    // =========================
    // DATE FORMAT
    // =========================

    private final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("uuuu-MM-dd")
                    .withResolverStyle(ResolverStyle.STRICT);

    // =========================
    // REGEX
    // =========================

    private static final Pattern USERNAME_PATTERN =
            Pattern.compile("^[A-Za-z0-9_]+$");

    private static final Pattern NAME_PATTERN =
            Pattern.compile("^[A-Za-z][A-Za-z .'-]*$");

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile(
                    "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
            );

    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^[6-9][0-9]{9}$");

    private static final Pattern PASSWORD_UPPER =
            Pattern.compile(".*[A-Z].*");

    private static final Pattern PASSWORD_LOWER =
            Pattern.compile(".*[a-z].*");

    private static final Pattern PASSWORD_NUMBER =
            Pattern.compile(".*[0-9].*");

    private static final Pattern PASSWORD_SPECIAL =
            Pattern.compile(".*[^A-Za-z0-9].*");

    // =========================
    // CONSTRUCTOR
    // =========================

    public TrainerRegistrationFrame() {

        trainerDAO = new TrainerDAO();

        setTitle("Gym Management System - Trainer Registration");

        setSize(850, 850);

        setMinimumSize(new Dimension(750, 700));

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLocationRelativeTo(null);

        setResizable(true);

        createUI();
    }

    // =========================
    // MAIN UI
    // =========================

    private void createUI() {

        JPanel mainPanel = new JPanel(new BorderLayout());

        mainPanel.setBackground(BACKGROUND);

        // =========================
        // HEADER
        // =========================

        JPanel headerPanel = new JPanel(new BorderLayout());

        headerPanel.setBackground(BACKGROUND);

        headerPanel.setBorder(
                new EmptyBorder(
                        25,
                        35,
                        20,
                        35
                )
        );

        JLabel titleLabel =
                new JLabel("Trainer Registration");

        titleLabel.setForeground(TEXT);

        titleLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        28
                )
        );

        JLabel subtitleLabel =
                new JLabel(
                        "Create your trainer account and submit it for admin approval"
                );

        subtitleLabel.setForeground(SECONDARY);

        subtitleLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

        JPanel titleContainer =
                new JPanel();

        titleContainer.setLayout(
                new BoxLayout(
                        titleContainer,
                        BoxLayout.Y_AXIS
                )
        );

        titleContainer.setOpaque(false);

        titleContainer.add(titleLabel);

        titleContainer.add(
                Box.createVerticalStrut(7)
        );

        titleContainer.add(subtitleLabel);

        headerPanel.add(
                titleContainer,
                BorderLayout.WEST
        );

        mainPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );

        // =========================
        // FORM
        // =========================

        JPanel formCard =
                new JPanel(new GridBagLayout());

        formCard.setBackground(CARD);

        formCard.setBorder(
                new EmptyBorder(
                        30,
                        35,
                        30,
                        35
                )
        );

        JPanel scrollContent =
                new JPanel(new BorderLayout());

        scrollContent.setBackground(BACKGROUND);

        scrollContent.setBorder(
                new EmptyBorder(
                        0,
                        30,
                        0,
                        30
                )
        );

        scrollContent.add(
                formCard,
                BorderLayout.NORTH
        );

        JScrollPane scrollPane =
                new JScrollPane(scrollContent);

        scrollPane.setBorder(null);

        scrollPane.getVerticalScrollBar()
                .setUnitIncrement(16);

        scrollPane.setBackground(BACKGROUND);

        mainPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(
                        9,
                        8,
                        9,
                        8
                );

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.weightx = 1;

        int row = 0;

        // =========================
        // ACCOUNT INFORMATION
        // =========================

        addSectionTitle(
                formCard,
                gbc,
                row++,
                "ACCOUNT INFORMATION"
        );

        usernameField =
                new JTextField();

        addField(
                formCard,
                gbc,
                row++,
                "Username *",
                usernameField,
                "4-30 characters, letters/numbers/underscore only"
        );

        passwordField =
                new JPasswordField();

        addField(
                formCard,
                gbc,
                row++,
                "Password *",
                passwordField,
                "8-30 chars, uppercase, lowercase, number & special character"
        );

        confirmPasswordField =
                new JPasswordField();

        addField(
                formCard,
                gbc,
                row++,
                "Confirm Password *",
                confirmPasswordField,
                "Must match your password"
        );

        // =========================
        // PERSONAL INFORMATION
        // =========================

        addSectionTitle(
                formCard,
                gbc,
                row++,
                "PERSONAL INFORMATION"
        );

        fullNameField =
                new JTextField();

        addField(
                formCard,
                gbc,
                row++,
                "Full Name *",
                fullNameField,
                "Letters, spaces, apostrophe and hyphen allowed"
        );

        emailField =
                new JTextField();

        addField(
                formCard,
                gbc,
                row++,
                "Email *",
                emailField,
                "Enter a valid email address"
        );

        phoneField =
                new JTextField();

        addField(
                formCard,
                gbc,
                row++,
                "Phone *",
                phoneField,
                "10-digit Indian mobile number"
        );

        // =========================
        // DOB
        // =========================

        JLabel dobLabel =
                createLabel("Date of Birth *");

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;

        formCard.add(
                dobLabel,
                gbc
        );

        JPanel dobPanel =
                new JPanel(new BorderLayout(5, 0));

        dobPanel.setOpaque(false);

        dobField =
                createTextField();

        dobField.setToolTipText(
                "Format: YYYY-MM-DD"
        );

        calendarButton =
                new JButton("📅");

        calendarButton.setPreferredSize(
                new Dimension(
                        55,
                        38
                )
        );

        styleButton(
                calendarButton,
                PRIMARY
        );

        calendarButton.addActionListener(
                e -> showCalendar()
        );

        dobPanel.add(
                dobField,
                BorderLayout.CENTER
        );

        dobPanel.add(
                calendarButton,
                BorderLayout.EAST
        );

        gbc.gridx = 1;
        gbc.weightx = 1;

        formCard.add(
                dobPanel,
                gbc
        );

        row++;

        // =========================
        // GENDER
        // =========================

        genderCombo =
                new JComboBox<>(
                        new String[]{
                                "Select Gender",
                                "Male",
                                "Female",
                                "Other"
                        }
                );

        styleComboBox(genderCombo);

        addField(
                formCard,
                gbc,
                row++,
                "Gender *",
                genderCombo,
                "Please select your gender"
        );

        // =========================
        // ADDRESS
        // =========================

        JLabel addressLabel =
                createLabel("Address *");

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;

        formCard.add(
                addressLabel,
                gbc
        );

        addressArea =
                new JTextArea(4, 20);

        addressArea.setLineWrap(true);

        addressArea.setWrapStyleWord(true);

        addressArea.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

        addressArea.setForeground(TEXT);

        addressArea.setBackground(INPUT);

        addressArea.setCaretColor(TEXT);

        addressArea.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(
                                BORDER,
                                1
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

        addressScroll.setBorder(null);

        gbc.gridx = 1;
        gbc.weightx = 1;

        formCard.add(
                addressScroll,
                gbc
        );

        row++;

        // =========================
        // PROFESSIONAL INFORMATION
        // =========================

        addSectionTitle(
                formCard,
                gbc,
                row++,
                "PROFESSIONAL INFORMATION"
        );

        qualificationField =
                new JTextField();

        addField(
                formCard,
                gbc,
                row++,
                "Qualification *",
                qualificationField,
                "Example: B.Sc, B.Tech, Certified Trainer"
        );

        specializationField =
                new JTextField();

        addField(
                formCard,
                gbc,
                row++,
                "Specialization *",
                specializationField,
                "Example: Strength Training, Yoga, Fitness"
        );

        experienceField =
                new JTextField();

        addField(
                formCard,
                gbc,
                row++,
                "Experience (Years) *",
                experienceField,
                "Enter a whole number from 0 to 50"
        );

        certificationField =
                new JTextField();

        addField(
                formCard,
                gbc,
                row++,
                "Certification",
                certificationField,
                "Optional - maximum 255 characters"
        );

        // =========================
        // PASSWORD CHECKBOX
        // =========================

        showPasswordCheckBox =
                new JCheckBox(
                        "Show Password"
                );

        showPasswordCheckBox.setForeground(
                SECONDARY
        );

        showPasswordCheckBox.setBackground(
                CARD
        );

        showPasswordCheckBox.setFocusPainted(
                false
        );

        showPasswordCheckBox.addActionListener(
                e -> togglePasswordVisibility()
        );

        gbc.gridx = 1;
        gbc.gridy = row++;

        formCard.add(
                showPasswordCheckBox,
                gbc
        );

        // =========================
        // BUTTONS
        // =========================

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                10,
                                10
                        )
                );

        buttonPanel.setOpaque(false);

        clearButton =
                new JButton("Clear");

        registerButton =
                new JButton("Submit Registration");

        styleButton(
                clearButton,
                new Color(
                        71,
                        85,
                        105
                )
        );

        styleButton(
                registerButton,
                PRIMARY
        );

        clearButton.addActionListener(
                e -> clearForm()
        );

        registerButton.addActionListener(
                e -> registerTrainer()
        );

        buttonPanel.add(
                clearButton
        );

        buttonPanel.add(
                registerButton
        );

        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;

        formCard.add(
                buttonPanel,
                gbc
        );

        // =========================
        // NOTE
        // =========================

        JLabel noteLabel =
                new JLabel(
                        "<html><center>" +
                                "Your account will remain <b>PENDING</b> " +
                                "until an administrator approves your application." +
                                "</center></html>"
                );

        noteLabel.setForeground(
                SECONDARY
        );

        noteLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;

        formCard.add(
                noteLabel,
                gbc
        );

        add(mainPanel);
    }

    // =========================================================
    // ADD SECTION TITLE
    // =========================================================

    private void addSectionTitle(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            String title
    ) {

        JLabel label =
                new JLabel(title);

        label.setForeground(
                PRIMARY
        );

        label.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        13
                )
        );

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.weightx = 1;

        gbc.insets =
                new Insets(
                        18,
                        8,
                        8,
                        8
                );

        panel.add(
                label,
                gbc
        );

        gbc.gridwidth = 1;

        gbc.insets =
                new Insets(
                        9,
                        8,
                        9,
                        8
                );
    }

    // =========================================================
    // ADD FIELD
    // =========================================================

    private void addField(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            String labelText,
            JComponent component,
            String tooltip
    ) {

        JLabel label =
                createLabel(labelText);

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.weightx = 0.3;

        panel.add(
                label,
                gbc
        );

        if (component instanceof JTextField) {

            JTextField field =
                    (JTextField) component;

            styleTextField(field);

        }

        component.setToolTipText(
                tooltip
        );

        gbc.gridx = 1;
        gbc.weightx = 1;

        panel.add(
                component,
                gbc
        );
    }

    // =========================================================
    // LABEL
    // =========================================================

    private JLabel createLabel(
            String text
    ) {

        JLabel label =
                new JLabel(text);

        label.setForeground(
                TEXT
        );

        label.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        13
                )
        );

        return label;
    }

    // =========================================================
    // TEXT FIELD
    // =========================================================

    private JTextField createTextField() {

        JTextField field =
                new JTextField();

        styleTextField(field);

        return field;
    }

    private void styleTextField(
            JTextField field
    ) {

        field.setPreferredSize(
                new Dimension(
                        300,
                        38
                )
        );

        field.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

        field.setForeground(
                TEXT
        );

        field.setBackground(
                INPUT
        );

        field.setCaretColor(
                TEXT
        );

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(
                                BORDER,
                                1
                        ),
                        new EmptyBorder(
                                8,
                                10,
                                8,
                                10
                        )
                )
        );

        field.addFocusListener(
                new FocusAdapter() {

                    @Override
                    public void focusGained(
                            FocusEvent e
                    ) {

                        field.setBorder(
                                BorderFactory.createCompoundBorder(
                                        new LineBorder(
                                                PRIMARY,
                                                1
                                        ),
                                        new EmptyBorder(
                                                8,
                                                10,
                                                8,
                                                10
                                        )
                                )
                        );
                    }

                    @Override
                    public void focusLost(
                            FocusEvent e
                    ) {

                        field.setBorder(
                                BorderFactory.createCompoundBorder(
                                        new LineBorder(
                                                BORDER,
                                                1
                                        ),
                                        new EmptyBorder(
                                                8,
                                                10,
                                                8,
                                                10
                                        )
                                )
                        );
                    }
                }
        );
    }

    // =========================================================
    // COMBO BOX
    // =========================================================

    private void styleComboBox(
            JComboBox<String> combo
    ) {

        combo.setPreferredSize(
                new Dimension(
                        300,
                        38
                )
        );

        combo.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

        combo.setForeground(
                TEXT
        );

        combo.setBackground(
                INPUT
        );

        combo.setBorder(
                new LineBorder(
                        BORDER
                )
        );
    }

    // =========================================================
    // BUTTON STYLE
    // =========================================================

    private void styleButton(
            JButton button,
            Color background
    ) {

        button.setForeground(
                Color.WHITE
        );

        button.setBackground(
                background
        );

        button.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        13
                )
        );

        button.setFocusPainted(
                false
        );

        button.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        18,
                        10,
                        18
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

                        if (button.isEnabled()) {

                            button.setBackground(
                                    PRIMARY_HOVER
                            );
                        }
                    }

                    @Override
                    public void mouseExited(
                            MouseEvent e
                    ) {

                        if (button.isEnabled()) {

                            button.setBackground(
                                    background
                            );
                        }
                    }
                }
        );
    }

    // =========================================================
    // PASSWORD SHOW / HIDE
    // =========================================================

    private void togglePasswordVisibility() {

        char echo;

        if (showPasswordCheckBox.isSelected()) {

            echo = (char) 0;

        } else {

            echo = '•';
        }

        passwordField.setEchoChar(echo);

        confirmPasswordField.setEchoChar(echo);
    }

    // =========================================================
    // REGISTRATION
    // =========================================================

    private void registerTrainer() {

        if (registering) {
            return;
        }

        // =========================
        // GET VALUES
        // =========================

        String username =
                usernameField.getText().trim();

        String password =
                new String(
                        passwordField.getPassword()
                );

        String confirmPassword =
                new String(
                        confirmPasswordField.getPassword()
                );

        String email =
                emailField.getText().trim();

        String phone =
                phoneField.getText().trim();

        String fullName =
                fullNameField.getText().trim();

        String dob =
                dobField.getText().trim();

        String gender =
                (String) genderCombo.getSelectedItem();

        String address =
                addressArea.getText().trim();

        String qualification =
                qualificationField.getText().trim();

        String specialization =
                specializationField.getText().trim();

        String experienceText =
                experienceField.getText().trim();

        String certification =
                certificationField.getText().trim();

        // =========================
        // USERNAME
        // =========================

        if (username.isEmpty()) {

            showError(
                    "Username is required.",
                    usernameField
            );

            return;
        }

        if (username.length() < 4 ||
                username.length() > 30) {

            showError(
                    "Username must contain 4 to 30 characters.",
                    usernameField
            );

            return;
        }

        if (!USERNAME_PATTERN.matcher(username).matches()) {

            showError(
                    "Username can contain only letters, numbers and underscore (_).",
                    usernameField
            );

            return;
        }

        // =========================
        // PASSWORD
        // =========================

        if (password.isEmpty()) {

            showError(
                    "Password is required.",
                    passwordField
            );

            return;
        }

        if (password.length() < 8 ||
                password.length() > 30) {

            showError(
                    "Password must contain 8 to 30 characters.",
                    passwordField
            );

            return;
        }

        if (password.contains(" ")) {

            showError(
                    "Password cannot contain spaces.",
                    passwordField
            );

            return;
        }

        if (!PASSWORD_UPPER.matcher(password).matches()) {

            showError(
                    "Password must contain at least one uppercase letter.",
                    passwordField
            );

            return;
        }

        if (!PASSWORD_LOWER.matcher(password).matches()) {

            showError(
                    "Password must contain at least one lowercase letter.",
                    passwordField
            );

            return;
        }

        if (!PASSWORD_NUMBER.matcher(password).matches()) {

            showError(
                    "Password must contain at least one number.",
                    passwordField
            );

            return;
        }

        if (!PASSWORD_SPECIAL.matcher(password).matches()) {

            showError(
                    "Password must contain at least one special character.",
                    passwordField
            );

            return;
        }

        // =========================
        // CONFIRM PASSWORD
        // =========================

        if (confirmPassword.isEmpty()) {

            showError(
                    "Please confirm your password.",
                    confirmPasswordField
            );

            return;
        }

        if (!password.equals(confirmPassword)) {

            showError(
                    "Passwords do not match.",
                    confirmPasswordField
            );

            return;
        }

        // =========================
        // EMAIL
        // =========================

        if (email.isEmpty()) {

            showError(
                    "Email is required.",
                    emailField
            );

            return;
        }

        if (email.length() > 100) {

            showError(
                    "Email cannot exceed 100 characters.",
                    emailField
            );

            return;
        }

        if (email.contains(" ")) {

            showError(
                    "Email cannot contain spaces.",
                    emailField
            );

            return;
        }

        if (!EMAIL_PATTERN.matcher(email).matches()) {

            showError(
                    "Please enter a valid email address.",
                    emailField
            );

            return;
        }

        // =========================
        // PHONE
        // =========================

        if (phone.isEmpty()) {

            showError(
                    "Phone number is required.",
                    phoneField
            );

            return;
        }

        if (!PHONE_PATTERN.matcher(phone).matches()) {

            showError(
                    "Enter a valid 10-digit Indian mobile number starting with 6, 7, 8 or 9.",
                    phoneField
            );

            return;
        }

        // =========================
        // FULL NAME
        // =========================

        if (fullName.isEmpty()) {

            showError(
                    "Full name is required.",
                    fullNameField
            );

            return;
        }

        if (fullName.length() < 3 ||
                fullName.length() > 100) {

            showError(
                    "Full name must contain 3 to 100 characters.",
                    fullNameField
            );

            return;
        }

        if (!NAME_PATTERN.matcher(fullName).matches()) {

            showError(
                    "Full name can contain only letters, spaces, apostrophe and hyphen.",
                    fullNameField
            );

            return;
        }

        // =========================
        // DATE OF BIRTH
        // =========================

        if (dob.isEmpty()) {

            showError(
                    "Date of birth is required.",
                    dobField
            );

            return;
        }

        LocalDate birthDate;

        try {

            birthDate =
                    LocalDate.parse(
                            dob,
                            DATE_FORMATTER
                    );

        } catch (DateTimeParseException e) {

            showError(
                    "Invalid date. Please select a valid date using the calendar.",
                    dobField
            );

            return;
        }

        LocalDate today =
                LocalDate.now();

        if (birthDate.isAfter(today)) {

            showError(
                    "Date of birth cannot be in the future.",
                    dobField
            );

            return;
        }

        int age =
                Period.between(
                        birthDate,
                        today
                ).getYears();

        if (age < 18) {

            showError(
                    "Trainer must be at least 18 years old.",
                    dobField
            );

            return;
        }

        if (age > 70) {

            showError(
                    "Please enter a valid date of birth. Maximum allowed age is 70.",
                    dobField
            );

            return;
        }

        // =========================
        // GENDER
        // =========================

        if (gender == null ||
                gender.equals("Select Gender")) {

            showError(
                    "Please select your gender.",
                    genderCombo
            );

            return;
        }

        // =========================
        // ADDRESS
        // =========================

        if (address.isEmpty()) {

            showError(
                    "Address is required.",
                    addressArea
            );

            return;
        }

        if (address.length() < 10) {

            showError(
                    "Address must contain at least 10 characters.",
                    addressArea
            );

            return;
        }

        if (address.length() > 255) {

            showError(
                    "Address cannot exceed 255 characters.",
                    addressArea
            );

            return;
        }

        // =========================
        // QUALIFICATION
        // =========================

        if (qualification.isEmpty()) {

            showError(
                    "Qualification is required.",
                    qualificationField
            );

            return;
        }

        if (qualification.length() < 2 ||
                qualification.length() > 150) {

            showError(
                    "Qualification must contain 2 to 150 characters.",
                    qualificationField
            );

            return;
        }

        if (qualification.matches("[0-9]+")) {

            showError(
                    "Qualification cannot contain only numbers.",
                    qualificationField
            );

            return;
        }

        // =========================
        // SPECIALIZATION
        // =========================

        if (specialization.isEmpty()) {

            showError(
                    "Specialization is required.",
                    specializationField
            );

            return;
        }

        if (specialization.length() < 2 ||
                specialization.length() > 150) {

            showError(
                    "Specialization must contain 2 to 150 characters.",
                    specializationField
            );

            return;
        }

        // =========================
        // EXPERIENCE
        // =========================

        if (experienceText.isEmpty()) {

            showError(
                    "Experience is required.",
                    experienceField
            );

            return;
        }

        int experienceYears;

        try {

            experienceYears =
                    Integer.parseInt(
                            experienceText
                    );

        } catch (NumberFormatException e) {

            showError(
                    "Experience must be a whole number.",
                    experienceField
            );

            return;
        }

        if (experienceYears < 0 ||
                experienceYears > 50) {

            showError(
                    "Experience must be between 0 and 50 years.",
                    experienceField
            );

            return;
        }

        // =========================
        // CERTIFICATION
        // =========================

        if (certification.length() > 255) {

            showError(
                    "Certification cannot exceed 255 characters.",
                    certificationField
            );

            return;
        }

        // =========================
        // DATABASE CHECKS
        // =========================

        registerButton.setEnabled(false);

        clearButton.setEnabled(false);

        registering = true;

        try {

            if (trainerDAO.usernameExists(username)) {

                showError(
                        "This username is already registered.",
                        usernameField
                );

                return;
            }

            if (trainerDAO.emailExists(email)) {

                showError(
                        "This email address is already registered.",
                        emailField
                );

                return;
            }

            // =========================
            // REGISTER
            // =========================

            boolean success =
                    trainerDAO.registerTrainer(
                            username,
                            password,
                            email,
                            phone,
                            fullName,
                            dob,
                            gender,
                            address,
                            qualification,
                            specialization,
                            experienceYears,
                            certification
                    );

            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        "Trainer registration submitted successfully!\n\n"
                                + "Username: " + username + "\n"
                                + "Status: PENDING\n\n"
                                + "Your account must be approved by an administrator "
                                + "before you can log in.",
                        "Registration Successful",
                        JOptionPane.INFORMATION_MESSAGE
                );

                dispose();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Registration could not be completed.\n\n"
                                + "Please check the entered information and try again.",
                        "Registration Failed",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } finally {

            registering = false;

            registerButton.setEnabled(true);

            clearButton.setEnabled(true);
        }
    }

    // =========================================================
    // ERROR MESSAGE
    // =========================================================

    private void showError(
            String message,
            JComponent component
    ) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Validation Error",
                JOptionPane.WARNING_MESSAGE
        );

        component.requestFocusInWindow();
    }

    // =========================================================
    // CLEAR FORM
    // =========================================================

    private void clearForm() {

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to clear all entered information?",
                        "Clear Form",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

        if (result != JOptionPane.YES_OPTION) {
            return;
        }

        usernameField.setText("");

        passwordField.setText("");

        confirmPasswordField.setText("");

        emailField.setText("");

        phoneField.setText("");

        fullNameField.setText("");

        dobField.setText("");

        genderCombo.setSelectedIndex(0);

        addressArea.setText("");

        qualificationField.setText("");

        specializationField.setText("");

        experienceField.setText("");

        certificationField.setText("");

        showPasswordCheckBox.setSelected(false);

        togglePasswordVisibility();

        usernameField.requestFocus();
    }

    // =========================================================
    // CALENDAR
    // =========================================================

    private void showCalendar() {

        JDialog dialog =
                new JDialog(
                        this,
                        "Select Date of Birth",
                        true
                );

        dialog.setSize(
                400,
                330
        );

        dialog.setLocationRelativeTo(this);

        dialog.setResizable(false);

        JPanel panel =
                new JPanel(
                        new BorderLayout()
                );

        panel.setBackground(
                CARD
        );

        // =========================
        // HEADER
        // =========================

        JPanel header =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                10,
                                10
                        )
                );

        header.setBackground(
                CARD
        );

        JComboBox<String> monthCombo =
                new JComboBox<>(
                        new String[]{
                                "January",
                                "February",
                                "March",
                                "April",
                                "May",
                                "June",
                                "July",
                                "August",
                                "September",
                                "October",
                                "November",
                                "December"
                        }
                );

        JComboBox<Integer> yearCombo =
                new JComboBox<>();

        int currentYear =
                Calendar.getInstance()
                        .get(Calendar.YEAR);

        for (
                int year = currentYear - 80;
                year <= currentYear - 18;
                year++
        ) {

            yearCombo.addItem(year);
        }

        monthCombo.setSelectedIndex(
                Calendar.getInstance()
                        .get(Calendar.MONTH)
        );

        yearCombo.setSelectedItem(
                currentYear - 25
        );

        header.add(monthCombo);

        header.add(yearCombo);

        panel.add(
                header,
                BorderLayout.NORTH
        );

        // =========================
        // CALENDAR DAYS
        // =========================

        JPanel calendarPanel =
                new JPanel(
                        new GridLayout(
                                7,
                                7,
                                3,
                                3
                        )
                );

        calendarPanel.setBackground(
                CARD
        );

        String[] dayNames = {
                "Sun",
                "Mon",
                "Tue",
                "Wed",
                "Thu",
                "Fri",
                "Sat"
        };

        for (String day : dayNames) {

            JLabel label =
                    new JLabel(
                            day,
                            SwingConstants.CENTER
                    );

            label.setForeground(
                    PRIMARY
            );

            label.setFont(
                    new Font(
                            "SansSerif",
                            Font.BOLD,
                            12
                    )
            );

            calendarPanel.add(label);
        }

        Runnable refreshCalendar =
                () -> {

                    while (
                            calendarPanel.getComponentCount()
                                    > 7
                    ) {

                        calendarPanel.remove(7);
                    }

                    int month =
                            monthCombo.getSelectedIndex();

                    int year =
                            (Integer)
                                    yearCombo.getSelectedItem();

                    Calendar calendar =
                            Calendar.getInstance();

                    calendar.set(
                            year,
                            month,
                            1
                    );

                    int firstDay =
                            calendar.get(
                                    Calendar.DAY_OF_WEEK
                            );

                    int daysInMonth =
                            calendar.getActualMaximum(
                                    Calendar.DAY_OF_MONTH
                            );

                    // Empty cells
                    for (
                            int i = 1;
                            i < firstDay;
                            i++
                    ) {

                        calendarPanel.add(
                                new JLabel()
                        );
                    }

                    for (
                            int day = 1;
                            day <= daysInMonth;
                            day++
                    ) {

                        final int selectedDay =
                                day;

                        JButton dayButton =
                                new JButton(
                                        String.valueOf(day)
                                );

                        dayButton.setFocusPainted(
                                false
                        );

                        dayButton.setForeground(
                                TEXT
                        );

                        dayButton.setBackground(
                                INPUT
                        );

                        dayButton.setBorder(
                                new LineBorder(
                                        BORDER
                                )
                        );

                        dayButton.setCursor(
                                new Cursor(
                                        Cursor.HAND_CURSOR
                                )
                        );

                        dayButton.addActionListener(
                                e -> {

                                    String selectedDate =
                                            String.format(
                                                    "%04d-%02d-%02d",
                                                    year,
                                                    month + 1,
                                                    selectedDay
                                            );

                                    dobField.setText(
                                            selectedDate
                                    );

                                    dialog.dispose();
                                }
                        );

                        calendarPanel.add(
                                dayButton
                        );
                    }

                    calendarPanel.revalidate();

                    calendarPanel.repaint();
                };

        monthCombo.addActionListener(
                e -> refreshCalendar.run()
        );

        yearCombo.addActionListener(
                e -> refreshCalendar.run()
        );

        refreshCalendar.run();

        panel.add(
                calendarPanel,
                BorderLayout.CENTER
        );

        // =========================
        // TODAY / CANCEL
        // =========================

        JPanel bottomPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT
                        )
                );

        bottomPanel.setBackground(
                CARD
        );

        JButton cancelButton =
                new JButton("Cancel");

        styleButton(
                cancelButton,
                new Color(
                        71,
                        85,
                        105
                )
        );

        cancelButton.addActionListener(
                e -> dialog.dispose()
        );

        bottomPanel.add(
                cancelButton
        );

        panel.add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        dialog.add(panel);

        dialog.setVisible(true);
    }

    // =========================================================
    // MAIN - OPTIONAL TEST
    // =========================================================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                () -> {

                    try {

                        UIManager.setLookAndFeel(
                                UIManager.getSystemLookAndFeelClassName()
                        );

                    } catch (Exception ignored) {
                    }

                    new TrainerRegistrationFrame()
                            .setVisible(true);
                }
        );
    }
}