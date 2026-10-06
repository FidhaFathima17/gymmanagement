package com.gym.view;

import com.gym.config.DatabaseConnection;
import com.gym.dao.UserDAO;
import com.gym.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    private final UserDAO userDAO;


    // =====================================================
    // COLORS
    // Same palette as your reference dashboard
    // =====================================================

    private final Color BACKGROUND =
            new Color(15, 17, 23);

    private final Color CARD =
            new Color(26, 29, 39);

    private final Color FIELD =
            new Color(30, 35, 47);

    private final Color PRIMARY =
            new Color(56, 189, 248);

    private final Color PRIMARY_HOVER =
            new Color(34, 211, 238);

    private final Color PURPLE =
            new Color(124, 58, 237);

    private final Color PURPLE_HOVER =
            new Color(139, 92, 246);

    private final Color TEXT =
            new Color(241, 245, 249);

    private final Color SECONDARY =
            new Color(148, 163, 184);

    private final Color BORDER =
            new Color(51, 65, 85);

    private final Color MENU_HOVER_BG =
            new Color(30, 41, 59);

    private final Color DANGER =
            new Color(239, 68, 68);


    // =====================================================
    // CONSTRUCTOR
    // =====================================================

    public LoginFrame() {

        userDAO = new UserDAO();

        setTitle(
                "Gym Management System"
        );


        // =================================================
        // FULL SCREEN / MAXIMIZED
        // =================================================

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setResizable(true);

        setExtendedState(
                JFrame.MAXIMIZED_BOTH
        );

        setLocationRelativeTo(null);


        // =================================================
        // MAIN PANEL
        // =================================================

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
                createHeader();


        mainPanel.add(
                header,
                BorderLayout.NORTH
        );


        // =================================================
        // LOGIN CONTENT
        // =================================================

        JPanel content =
                createLoginContent();


        mainPanel.add(
                content,
                BorderLayout.CENTER
        );


        setContentPane(
                mainPanel
        );
    }


    // =====================================================
    // HEADER
    // =====================================================

    private JPanel createHeader() {

        JPanel header =
                new JPanel(
                        new BorderLayout()
                );

        header.setBackground(
                CARD
        );

        header.setBorder(
                new EmptyBorder(
                        24,
                        40,
                        24,
                        40
                )
        );


        // =================================================
        // LOGO
        // =================================================

        JPanel logoPanel =
                new JPanel();

        logoPanel.setOpaque(false);

        logoPanel.setLayout(
                new BoxLayout(
                        logoPanel,
                        BoxLayout.Y_AXIS
                )
        );


        JLabel logo =
                new JLabel(
                        "GYM MANAGEMENT SYSTEM"
                );

        logo.setForeground(
                TEXT
        );

        logo.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        22
                )
        );


        JLabel subtitle =
                new JLabel(
                        "FITNESS • MEMBERSHIP • MANAGEMENT"
                );

        subtitle.setForeground(
                SECONDARY
        );

        subtitle.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        10
                )
        );


        logoPanel.add(
                logo
        );

        logoPanel.add(
                Box.createVerticalStrut(5)
        );

        logoPanel.add(
                subtitle
        );


        header.add(
                logoPanel,
                BorderLayout.WEST
        );


        // =================================================
        // ACCENT LINE
        // =================================================

        JPanel accent =
                new JPanel();

        accent.setBackground(
                PRIMARY
        );

        accent.setPreferredSize(
                new Dimension(
                        6,
                        48
                )
        );


        header.add(
                accent,
                BorderLayout.EAST
        );


        return header;
    }


    // =====================================================
    // LOGIN CONTENT
    // =====================================================

    private JPanel createLoginContent() {

        JPanel outerPanel =
                new JPanel(
                        new GridBagLayout()
                );

        outerPanel.setBackground(
                BACKGROUND
        );

        outerPanel.setBorder(
                new EmptyBorder(
                        40,
                        40,
                        40,
                        40
                )
        );


        // =================================================
        // LOGIN CARD
        // =================================================

        JPanel card =
                new JPanel();

        card.setBackground(
                CARD
        );

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                38,
                                45,
                                38,
                                45
                        )
                )
        );

        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
        );


        // =================================================
        // LOGIN TITLE
        // =================================================

        JLabel title =
                new JLabel(
                        "Welcome Back"
                );

        title.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        title.setForeground(
                TEXT
        );

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        30
                )
        );


        card.add(
                title
        );


        card.add(
                Box.createVerticalStrut(8)
        );


        JLabel subtitle =
                new JLabel(
                        "Sign in to access your gym dashboard"
                );

        subtitle.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        subtitle.setForeground(
                SECONDARY
        );

        subtitle.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );


        card.add(
                subtitle
        );


        card.add(
                Box.createVerticalStrut(32)
        );


        // =================================================
        // USERNAME
        // =================================================

        JLabel usernameLabel =
                createFieldLabel(
                        "USERNAME"
                );

        usernameLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );


        card.add(
                usernameLabel
        );


        card.add(
                Box.createVerticalStrut(8)
        );


        usernameField =
                createTextField();


        card.add(
                usernameField
        );


        card.add(
                Box.createVerticalStrut(20)
        );


        // =================================================
        // PASSWORD
        // =================================================

        JLabel passwordLabel =
                createFieldLabel(
                        "PASSWORD"
                );

        passwordLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );


        card.add(
                passwordLabel
        );


        card.add(
                Box.createVerticalStrut(8)
        );


        passwordField =
                createPasswordField();


        card.add(
                passwordField
        );


        card.add(
                Box.createVerticalStrut(28)
        );


        // =================================================
        // LOGIN BUTTON
        // =================================================

        loginButton =
                new JButton(
                        "LOGIN"
                );

        stylePrimaryButton(
                loginButton
        );


        loginButton.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );


        loginButton.addActionListener(
                e -> performLogin()
        );


        card.add(
                loginButton
        );


        card.add(
                Box.createVerticalStrut(14)
        );


        // =================================================
        // BACK TO HOME BUTTON
        // =================================================

        JButton backButton =
                new JButton(
                        "←  Back to Home"
                );


        styleSecondaryButton(
                backButton
        );


        backButton.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );


        backButton.addActionListener(
                e -> goBackToHome()
        );


        card.add(
                backButton
        );


        card.add(
                Box.createVerticalStrut(22)
        );


        // =================================================
        // DIVIDER
        // =================================================

        JPanel divider =
                new JPanel(
                        new BorderLayout()
                );

        divider.setBackground(
                BORDER
        );

        divider.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        1
                )
        );

        divider.setPreferredSize(
                new Dimension(
                        400,
                        1
                )
        );


        card.add(
                divider
        );


        card.add(
                Box.createVerticalStrut(18)
        );


        // =================================================
        // TRAINER REGISTRATION
        // =================================================

        JLabel trainerText =
                new JLabel(
                        "New Trainer?"
                );

        trainerText.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        trainerText.setForeground(
                SECONDARY
        );

        trainerText.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        12
                )
        );


        card.add(
                trainerText
        );


        card.add(
                Box.createVerticalStrut(5)
        );


        JButton trainerRegisterButton =
                new JButton(
                        "Register Here"
                );


        trainerRegisterButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        trainerRegisterButton.setFocusPainted(
                false
        );

        trainerRegisterButton.setBorderPainted(
                false
        );

        trainerRegisterButton.setContentAreaFilled(
                false
        );

        trainerRegisterButton.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        trainerRegisterButton.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        12
                )
        );

        trainerRegisterButton.setForeground(
                PRIMARY
        );


        trainerRegisterButton.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            MouseEvent e
                    ) {

                        trainerRegisterButton.setForeground(
                                PRIMARY_HOVER
                        );
                    }


                    @Override
                    public void mouseExited(
                            MouseEvent e
                    ) {

                        trainerRegisterButton.setForeground(
                                PRIMARY
                        );
                    }
                }
        );


        trainerRegisterButton.addActionListener(
                e -> {

                    TrainerRegistrationFrame registrationFrame =
                            new TrainerRegistrationFrame();

                    registrationFrame.setVisible(true);
                }
        );


        card.add(
                trainerRegisterButton
        );


        // =================================================
        // ADD CARD TO CENTER
        // =================================================

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        gbc.anchor =
                GridBagConstraints.CENTER;

        gbc.fill =
                GridBagConstraints.NONE;


        outerPanel.add(
                card,
                gbc
        );


        return outerPanel;
    }


    // =====================================================
    // FIELD LABEL
    // =====================================================

    private JLabel createFieldLabel(
            String text
    ) {

        JLabel label =
                new JLabel(text);

        label.setForeground(
                SECONDARY
        );

        label.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        11
                )
        );


        return label;
    }


    // =====================================================
    // TEXT FIELD
    // =====================================================

    private JTextField createTextField() {

        JTextField field =
                new JTextField();


        field.setPreferredSize(
                new Dimension(
                        430,
                        46
                )
        );


        field.setMaximumSize(
                new Dimension(
                        430,
                        46
                )
        );


        field.setMinimumSize(
                new Dimension(
                        430,
                        46
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
                FIELD
        );


        field.setCaretColor(
                PRIMARY
        );


        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                8,
                                12,
                                8,
                                12
                        )
                )
        );


        return field;
    }


    // =====================================================
    // PASSWORD FIELD
    // =====================================================

    private JPasswordField createPasswordField() {

        JPasswordField field =
                new JPasswordField();


        field.setPreferredSize(
                new Dimension(
                        430,
                        46
                )
        );


        field.setMaximumSize(
                new Dimension(
                        430,
                        46
                )
        );


        field.setMinimumSize(
                new Dimension(
                        430,
                        46
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
                FIELD
        );


        field.setCaretColor(
                PRIMARY
        );


        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                8,
                                12,
                                8,
                                12
                        )
                )
        );


        // Press Enter to Login
        field.addActionListener(
                e -> performLogin()
        );


        return field;
    }


    // =====================================================
    // PRIMARY BUTTON
    // =====================================================

    private void stylePrimaryButton(
            JButton button
    ) {

        button.setPreferredSize(
                new Dimension(
                        430,
                        46
                )
        );


        button.setMaximumSize(
                new Dimension(
                        430,
                        46
                )
        );


        button.setMinimumSize(
                new Dimension(
                        430,
                        46
                )
        );


        button.setBackground(
                PRIMARY
        );


        button.setForeground(
                Color.BLACK
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


        button.setBorderPainted(
                false
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
                                    PRIMARY
                            );
                        }
                    }
                }
        );
    }


    // =====================================================
    // SECONDARY BUTTON
    // =====================================================

    private void styleSecondaryButton(
            JButton button
    ) {

        button.setPreferredSize(
                new Dimension(
                        430,
                        44
                )
        );


        button.setMaximumSize(
                new Dimension(
                        430,
                        44
                )
        );


        button.setMinimumSize(
                new Dimension(
                        430,
                        44
                )
        );


        button.setBackground(
                CARD
        );


        button.setForeground(
                TEXT
        );


        button.setFont(
                new Font(
                        "SansSerif",
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
                                8,
                                12,
                                8,
                                12
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
                                CARD
                        );

                        button.setForeground(
                                TEXT
                        );
                    }
                }
        );
    }


    // =====================================================
    // BACK TO HOME
    // =====================================================

    private void goBackToHome() {

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Return to the home page?",
                        "Back to Home",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );


        if (choice != JOptionPane.YES_OPTION) {

            return;
        }


        // =================================================
        // OPEN HOME PAGE
        // =================================================

        PublicHomeFrame homeFrame =
                new PublicHomeFrame();


        homeFrame.setVisible(true);


        // Close only this Login window
        dispose();
    }


    // =====================================================
    // LOGIN PROCESS
    // =====================================================

    private void performLogin() {

        String username =
                usernameField.getText().trim();


        String password =
                new String(
                        passwordField.getPassword()
                ).trim();


        // =================================================
        // INPUT VALIDATION
        // =================================================

        if (username.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter your username.",
                    "Input Error",
                    JOptionPane.WARNING_MESSAGE
            );


            usernameField.requestFocus();

            return;
        }


        if (password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter your password.",
                    "Input Error",
                    JOptionPane.WARNING_MESSAGE
            );


            passwordField.requestFocus();

            return;
        }


        // =================================================
        // DISABLE BUTTON WHILE PROCESSING
        // =================================================

        loginButton.setEnabled(false);


        try {

            // =================================================
            // AUTHENTICATE USER
            // =================================================

            User user =
                    userDAO.authenticateUser(
                            username,
                            password
                    );


            // =================================================
            // LOGIN SUCCESS
            // =================================================

            if (user != null) {


                // =================================================
                // ADMIN LOGIN
                // =================================================

                if (
                        user.getRole()
                                .equalsIgnoreCase("ADMIN")
                ) {


                    JOptionPane.showMessageDialog(
                            this,
                            "Welcome "
                                    + user.getUsername()
                                    + " (ADMIN)",
                            "Login Successful",
                            JOptionPane.INFORMATION_MESSAGE
                    );


                    AdminDashboardFrame adminDashboard =
                            new AdminDashboardFrame(
                                    user.getUsername()
                            );


                    adminDashboard.setVisible(true);


                    dispose();
                }


                // =================================================
                // MEMBER LOGIN
                // =================================================

                else if (
                        user.getRole()
                                .equalsIgnoreCase("MEMBER")
                ) {


                    // -----------------------------------------
                    // Find member_id using user_id
                    // -----------------------------------------

                    int memberId =
                            getMemberId(
                                    user.getUserId()
                            );


                    if (memberId == -1) {

                        JOptionPane.showMessageDialog(
                                this,
                                "Member profile not found.\n\n"
                                        + "Please contact the gym administrator.",
                                "Member Login Error",
                                JOptionPane.ERROR_MESSAGE
                        );


                        return;
                    }


                    // -----------------------------------------
                    // Login successful
                    // -----------------------------------------

                    JOptionPane.showMessageDialog(
                            this,
                            "Welcome "
                                    + user.getUsername()
                                    + " (MEMBER)",
                            "Login Successful",
                            JOptionPane.INFORMATION_MESSAGE
                    );


                    try {

                        MemberDashboardFrame memberDashboard =
                                new MemberDashboardFrame(
                                        user.getUserId(),
                                        memberId
                                );


                        memberDashboard.setVisible(true);


                        dispose();

                    } catch (Exception dashboardError) {

                        JOptionPane.showMessageDialog(
                                this,
                                "Member login was successful, "
                                        + "but the dashboard could not be opened.\n\n"
                                        + "Error: "
                                        + dashboardError.getMessage(),
                                "Dashboard Error",
                                JOptionPane.ERROR_MESSAGE
                        );


                        dashboardError.printStackTrace();
                    }
                }


                // =================================================
                // TRAINER LOGIN
                // =================================================

                else if (
                        user.getRole()
                                .equalsIgnoreCase("TRAINER")
                ) {


                    // -----------------------------------------
                    // Find trainer_id using user_id
                    // -----------------------------------------

                    int trainerId =
                            getTrainerId(
                                    user.getUserId()
                            );


                    if (trainerId == -1) {

                        JOptionPane.showMessageDialog(
                                this,
                                "Trainer profile not found.\n\n"
                                        + "Please contact the gym administrator.",
                                "Trainer Login Error",
                                JOptionPane.ERROR_MESSAGE
                        );


                        return;
                    }


                    // -----------------------------------------
                    // Get trainer name
                    // -----------------------------------------

                    String trainerName =
                            getTrainerName(
                                    trainerId
                            );


                    // -----------------------------------------
                    // Login successful
                    // -----------------------------------------

                    JOptionPane.showMessageDialog(
                            this,
                            "Welcome "
                                    + trainerName
                                    + " (TRAINER)",
                            "Login Successful",
                            JOptionPane.INFORMATION_MESSAGE
                    );


                    try {

                        TrainerDashboardFrame trainerDashboard =
                                new TrainerDashboardFrame(
                                        trainerId,
                                        trainerName
                                );


                        trainerDashboard.setVisible(true);


                        dispose();

                    } catch (Exception dashboardError) {

                        JOptionPane.showMessageDialog(
                                this,
                                "Trainer login was successful, "
                                        + "but the dashboard could not be opened.\n\n"
                                        + "Error: "
                                        + dashboardError.getMessage(),
                                "Dashboard Error",
                                JOptionPane.ERROR_MESSAGE
                        );


                        dashboardError.printStackTrace();
                    }
                }


                // =================================================
                // UNKNOWN ROLE
                // =================================================

                else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Unknown user role: "
                                    + user.getRole(),
                            "Login Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }

            }


            // =================================================
            // LOGIN FAILED
            // =================================================

            else {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid username or password.\n\n"
                                + "If you are a trainer, your account "
                                + "must be approved by the administrator "
                                + "before you can login.",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE
                );


                passwordField.setText("");


                passwordField.requestFocus();
            }

        }


        // =====================================================
        // DATABASE / OTHER ERROR
        // =====================================================

        catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to connect to the database.\n\n"
                            + "Please check:\n"
                            + "1. MySQL is running\n"
                            + "2. Database 'gym_db' exists\n"
                            + "3. MySQL username is correct\n"
                            + "4. MySQL password is correct\n"
                            + "5. MySQL Connector/J is added\n\n"
                            + "Error: "
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );


            System.err.println(
                    "================================="
            );


            System.err.println(
                    "LOGIN ERROR"
            );


            System.err.println(
                    "================================="
            );


            e.printStackTrace();
        }


        finally {

            loginButton.setEnabled(true);
        }
    }


    // =========================================================
    // GET MEMBER ID
    // =========================================================

    private int getMemberId(
            int userId
    ) {

        String sql =
                "SELECT member_id " +
                        "FROM member_profiles " +
                        "WHERE user_id = ?";


        try (
                Connection conn =
                        DatabaseConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {


            stmt.setInt(
                    1,
                    userId
            );


            try (
                    ResultSet rs =
                            stmt.executeQuery()
            ) {


                if (rs.next()) {

                    return rs.getInt(
                            "member_id"
                    );
                }
            }

        } catch (SQLException e) {

            System.err.println(
                    "================================="
            );


            System.err.println(
                    "MEMBER PROFILE ERROR"
            );


            System.err.println(
                    "================================="
            );


            e.printStackTrace();


            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load member profile.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }


        return -1;
    }


    // =========================================================
    // GET TRAINER ID
    // =========================================================

    private int getTrainerId(
            int userId
    ) {

        String sql =
                "SELECT trainer_id " +
                        "FROM trainer_profiles " +
                        "WHERE user_id = ?";


        try (
                Connection conn =
                        DatabaseConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {


            stmt.setInt(
                    1,
                    userId
            );


            try (
                    ResultSet rs =
                            stmt.executeQuery()
            ) {


                if (rs.next()) {

                    return rs.getInt(
                            "trainer_id"
                    );
                }
            }

        } catch (SQLException e) {

            System.err.println(
                    "================================="
            );


            System.err.println(
                    "TRAINER PROFILE ERROR"
            );


            System.err.println(
                    "================================="
            );


            e.printStackTrace();


            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load trainer profile.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }


        return -1;
    }


    // =========================================================
    // GET TRAINER NAME
    // =========================================================

    private String getTrainerName(
            int trainerId
    ) {

        String sql =
                "SELECT full_name " +
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

                    return rs.getString(
                            "full_name"
                    );
                }
            }

        } catch (SQLException e) {

            System.err.println(
                    "================================="
            );


            System.err.println(
                    "TRAINER NAME ERROR"
            );


            System.err.println(
                    "================================="
            );


            e.printStackTrace();
        }


        return "Trainer";
    }
}