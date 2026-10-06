package com.gym.view;

import com.gym.config.DatabaseConnection;
import com.gym.dao.GymPackageDAO;
import com.gym.model.GymPackage;
import com.gym.payment.RazorpayService;
import com.razorpay.PaymentLink;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URI;
import java.sql.*;
import java.time.LocalDate;

public class SubscriptionPaymentFrame extends JFrame {

    private final int userId;
    private final int memberId;
    private final int packageId;

    private GymPackage selectedPackage;

    private JComboBox<String> paymentMethodComboBox;

    private JLabel packageNameLabel;
    private JLabel durationLabel;
    private JLabel priceLabel;
    private JLabel startDateLabel;
    private JLabel endDateLabel;

    private final Color BACKGROUND = new Color(12, 15, 25);
    private final Color PANEL = new Color(25, 29, 42);
    private final Color FIELD = new Color(35, 40, 55);
    private final Color PRIMARY = new Color(0, 210, 255);
    private final Color SUCCESS = new Color(45, 190, 110);
    private final Color WHITE = new Color(245, 247, 250);
    private final Color MUTED = new Color(170, 178, 195);

    // =====================================================
    // CONSTRUCTOR
    // =====================================================

    public SubscriptionPaymentFrame(
            int userId,
            int memberId,
            int packageId
    ) {

        this.userId = userId;
        this.memberId = memberId;
        this.packageId = packageId;

        setTitle("Membership Subscription & Payment");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 650);
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null);

        loadPackage();

        if (selectedPackage != null) {
            buildUI();
        }
    }

    // =====================================================
    // LOAD PACKAGE
    // =====================================================

    private void loadPackage() {

        GymPackageDAO dao = new GymPackageDAO();

        selectedPackage = dao.getPackageById(packageId);

        if (selectedPackage == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "The selected membership package is no longer available.",
                    "Package Error",
                    JOptionPane.ERROR_MESSAGE
            );

            dispose();
        }
    }

    // =====================================================
    // BUILD UI
    // =====================================================

    private void buildUI() {

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND);

        // =================================================
        // HEADER
        // =================================================

        JPanel header = new JPanel(new BorderLayout());

        header.setBackground(
                new Color(18, 22, 35)
        );

        header.setBorder(
                new EmptyBorder(
                        20,
                        30,
                        20,
                        30
                )
        );

        JLabel title = new JLabel(
                "Complete Your Membership"
        );

        title.setForeground(WHITE);

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        26
                )
        );

        JLabel subtitle = new JLabel(
                "Review your package and complete secure payment"
        );

        subtitle.setForeground(MUTED);

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        JPanel heading = new JPanel();

        heading.setLayout(
                new BoxLayout(
                        heading,
                        BoxLayout.Y_AXIS
                )
        );

        heading.setOpaque(false);

        heading.add(title);

        heading.add(
                Box.createVerticalStrut(5)
        );

        heading.add(subtitle);

        header.add(
                heading,
                BorderLayout.WEST
        );

        // =================================================
        // CENTER CONTENT
        // =================================================

        JPanel content = new JPanel(
                new GridBagLayout()
        );

        content.setBackground(BACKGROUND);

        content.setBorder(
                new EmptyBorder(
                        30,
                        40,
                        30,
                        40
                )
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(
                        10,
                        10,
                        10,
                        10
                );

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        // =================================================
        // PACKAGE CARD
        // =================================================

        JPanel packageCard = new JPanel();

        packageCard.setLayout(
                new BoxLayout(
                        packageCard,
                        BoxLayout.Y_AXIS
                )
        );

        packageCard.setBackground(PANEL);

        packageCard.setBorder(
                BorderFactory.createCompoundBorder(

                        BorderFactory.createLineBorder(
                                new Color(55, 62, 82)
                        ),

                        new EmptyBorder(
                                25,
                                30,
                                25,
                                30
                        )
                )
        );

        packageNameLabel =
                createValueLabel(
                        selectedPackage.getPackageName(),
                        24
                );

        durationLabel =
                createValueLabel(
                        selectedPackage.getDuration()
                                + " "
                                + selectedPackage.getDurationUnit(),
                        16
                );

        durationLabel.setForeground(PRIMARY);

        priceLabel =
                createValueLabel(
                        "₹ "
                                + String.format(
                                "%.2f",
                                selectedPackage.getPrice()
                        ),
                        28
                );

        priceLabel.setForeground(SUCCESS);

        packageCard.add(
                packageNameLabel
        );

        packageCard.add(
                Box.createVerticalStrut(10)
        );

        packageCard.add(
                durationLabel
        );

        packageCard.add(
                Box.createVerticalStrut(10)
        );

        packageCard.add(
                priceLabel
        );

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1;

        content.add(
                packageCard,
                gbc
        );

        // =================================================
        // START DATE
        // =================================================

        JLabel startTitle =
                createLabel(
                        "Subscription Start"
                );

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;

        content.add(
                startTitle,
                gbc
        );

        startDateLabel =
                createValueLabel(
                        LocalDate.now().toString(),
                        15
                );

        gbc.gridx = 1;

        content.add(
                startDateLabel,
                gbc
        );

        // =================================================
        // END DATE
        // =================================================

        JLabel endTitle =
                createLabel(
                        "Subscription End"
                );

        gbc.gridx = 0;
        gbc.gridy = 2;

        content.add(
                endTitle,
                gbc
        );

        LocalDate endDate =
                calculateEndDate(
                        LocalDate.now()
                );

        endDateLabel =
                createValueLabel(
                        endDate.toString(),
                        15
                );

        gbc.gridx = 1;

        content.add(
                endDateLabel,
                gbc
        );

        // =================================================
        // PAYMENT METHOD
        // =================================================

        JLabel paymentTitle =
                createLabel(
                        "Payment Method"
                );

        gbc.gridx = 0;
        gbc.gridy = 3;

        content.add(
                paymentTitle,
                gbc
        );

        paymentMethodComboBox =
                new JComboBox<>(
                        new String[]{
                                "RAZORPAY ONLINE"
                        }
                );

        paymentMethodComboBox.setBackground(FIELD);

        paymentMethodComboBox.setForeground(WHITE);

        paymentMethodComboBox.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        gbc.gridx = 1;

        content.add(
                paymentMethodComboBox,
                gbc
        );

        // =================================================
        // INFORMATION
        // =================================================

        JLabel info =
                new JLabel(
                        "🔒 Secure payment powered by Razorpay"
                );

        info.setForeground(MUTED);

        info.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;

        content.add(
                info,
                gbc
        );

        // =================================================
        // BUTTONS
        // =================================================

        JButton backButton =
                createButton(
                        "BACK",
                        new Color(70, 75, 90)
                );

        JButton payButton =
                createButton(
                        "PAY ₹ "
                                + String.format(
                                "%.2f",
                                selectedPackage.getPrice()
                        ),
                        SUCCESS
                );

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                10,
                                0
                        )
                );

        buttonPanel.setOpaque(false);

        buttonPanel.add(backButton);
        buttonPanel.add(payButton);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;

        content.add(
                buttonPanel,
                gbc
        );

        // =================================================
        // BUTTON ACTIONS
        // =================================================

        backButton.addActionListener(
                e -> goBack()
        );

        payButton.addActionListener(
                e -> processPayment()
        );

        // =================================================
        // ADD TO FRAME
        // =================================================

        mainPanel.add(
                header,
                BorderLayout.NORTH
        );

        mainPanel.add(
                content,
                BorderLayout.CENTER
        );

        setContentPane(mainPanel);
    }
    // =====================================================
    // CALCULATE END DATE
    // =====================================================

    private LocalDate calculateEndDate(LocalDate startDate) {

        int duration =
                selectedPackage.getDuration();

        String unit =
                selectedPackage.getDurationUnit();

        if (unit == null) {
            return startDate;
        }

        switch (unit.toUpperCase()) {

            case "DAY":
                return startDate.plusDays(duration);

            case "MONTH":
                return startDate.plusMonths(duration);

            case "YEAR":
                return startDate.plusYears(duration);

            default:
                return startDate;
        }
    }

    // =====================================================
    // CREATE LABEL
    // =====================================================

    private JLabel createLabel(String text) {

        JLabel label =
                new JLabel(text);

        label.setForeground(MUTED);

        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        return label;
    }

    // =====================================================
    // CREATE VALUE LABEL
    // =====================================================

    private JLabel createValueLabel(
            String text,
            int fontSize
    ) {

        JLabel label =
                new JLabel(text);

        label.setForeground(WHITE);

        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        fontSize
                )
        );

        return label;
    }

    // =====================================================
    // CREATE BUTTON
    // =====================================================

    private JButton createButton(
            String text,
            Color background
    ) {

        JButton button =
                new JButton(text);

        button.setBackground(background);

        button.setForeground(Color.WHITE);

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        button.setFocusPainted(false);

        button.setBorder(
                BorderFactory.createEmptyBorder(
                        12,
                        22,
                        12,
                        22
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        return button;
    }

    // =====================================================
    // GO BACK
    // =====================================================

    private void goBack() {

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to go back?\n"
                                + "Your membership payment has not been completed.",
                        "Go Back",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

        if (result == JOptionPane.YES_OPTION) {

            dispose();

            new PublicHomeFrame().setVisible(true);
        }
    }

    // =====================================================
    // PROCESS PAYMENT
    // =====================================================

    private void processPayment() {

        if (selectedPackage == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Membership package information is missing.",
                    "Payment Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        try {

            // -------------------------------------------------
            // GET MEMBER DETAILS
            // -------------------------------------------------

            MemberDetails memberDetails =
                    getMemberDetails();

            if (memberDetails == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Unable to find your member details.",
                        "Member Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            // -------------------------------------------------
            // CREATE RAZORPAY SERVICE
            // -------------------------------------------------

            RazorpayService razorpayService =
                    new RazorpayService();

            // -------------------------------------------------
            // CREATE PAYMENT LINK
            // -------------------------------------------------

            PaymentLink paymentLink =
                    razorpayService.createPaymentLink(

                            selectedPackage.getPrice(),

                            selectedPackage.getPackageName(),

                            memberDetails.name,

                            memberDetails.email,

                            memberDetails.phone
                    );

            String paymentLinkId =
                    razorpayService.getPaymentLinkId(
                            paymentLink
                    );

            String paymentUrl =
                    razorpayService.getPaymentUrl(
                            paymentLink
                    );

            if (paymentLinkId == null
                    || paymentUrl == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Razorpay did not return a valid payment link.",
                        "Payment Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            // -------------------------------------------------
            // OPEN RAZORPAY PAYMENT PAGE
            // -------------------------------------------------

            Desktop.getDesktop().browse(
                    new URI(paymentUrl)
            );

            // -------------------------------------------------
            // INFORM USER
            // -------------------------------------------------

            JOptionPane.showMessageDialog(
                    this,
                    "Razorpay payment page has been opened in your browser.\n\n"
                            + "1. Complete the payment.\n"
                            + "2. Return to this application.\n"
                            + "3. Click OK to verify the payment.",
                    "Complete Payment",
                    JOptionPane.INFORMATION_MESSAGE
            );

            // -------------------------------------------------
            // CHECK PAYMENT STATUS
            // -------------------------------------------------

            boolean paymentSuccessful = false;

            for (int attempt = 1; attempt <= 5; attempt++) {

                try {

                    paymentSuccessful =
                            razorpayService.isPaymentSuccessful(
                                    paymentLinkId
                            );

                    if (paymentSuccessful) {
                        break;
                    }

                    Thread.sleep(2000);

                } catch (InterruptedException e) {

                    Thread.currentThread().interrupt();

                    break;
                }
            }

            // -------------------------------------------------
            // PAYMENT NOT CONFIRMED
            // -------------------------------------------------

            if (!paymentSuccessful) {

                JOptionPane.showMessageDialog(
                        this,
                        "Payment could not be confirmed yet.\n\n"
                                + "Your membership has NOT been activated.\n"
                                + "You can return to the dashboard and try again.",
                        "Payment Not Confirmed",
                        JOptionPane.WARNING_MESSAGE
                );

                openMemberDashboard();

                return;
            }

            // -------------------------------------------------
            // GET RAZORPAY PAYMENT ID
            // -------------------------------------------------

            String transactionId =
                    razorpayService.getPaymentId(
                            paymentLink
                    );

            if (transactionId == null
                    || transactionId.trim().isEmpty()) {

                transactionId =
                        paymentLinkId;
            }

            // -------------------------------------------------
            // CALCULATE SUBSCRIPTION DATES
            // -------------------------------------------------

            LocalDate startDate =
                    LocalDate.now();

            LocalDate endDate =
                    calculateEndDate(
                            startDate
                    );

            // -------------------------------------------------
            // SAVE SUBSCRIPTION + PAYMENT
            // -------------------------------------------------

            boolean saved =
                    saveSubscriptionAndPayment(
                            startDate,
                            endDate,
                            transactionId
                    );

            if (!saved) {

                JOptionPane.showMessageDialog(
                        this,
                        "Payment was successful, but the membership "
                                + "could not be saved.\n\n"
                                + "Please contact the gym administrator.",
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            // -------------------------------------------------
            // SUCCESS MESSAGE
            // -------------------------------------------------

            JOptionPane.showMessageDialog(
                    this,
                    "🎉 Payment Successful!\n\n"
                            + "Membership: "
                            + selectedPackage.getPackageName()
                            + "\n"
                            + "Amount: ₹ "
                            + String.format(
                            "%.2f",
                            selectedPackage.getPrice()
                    )
                            + "\n"
                            + "Valid Until: "
                            + endDate,
                    "Payment Successful",
                    JOptionPane.INFORMATION_MESSAGE
            );

            // -------------------------------------------------
            // OPEN MEMBER DASHBOARD
            // -------------------------------------------------

            openMemberDashboard();

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Payment processing failed.\n\n"
                            + e.getMessage(),
                    "Payment Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =====================================================
    // GET MEMBER DETAILS
    // =====================================================

    private MemberDetails getMemberDetails()
            throws SQLException {

        String sql =
                "SELECT mp.full_name, "
                        + "u.email, "
                        + "u.phone "
                        + "FROM member_profiles mp "
                        + "JOIN users u "
                        + "ON mp.user_id = u.user_id "
                        + "WHERE mp.member_id = ?";

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

                    return new MemberDetails(
                            rs.getString("full_name"),
                            rs.getString("email"),
                            rs.getString("phone")
                    );
                }
            }
        }

        return null;
    }

    // =====================================================
    // SAVE SUBSCRIPTION + PAYMENT
    // =====================================================

    private boolean saveSubscriptionAndPayment(
            LocalDate startDate,
            LocalDate endDate,
            String transactionId
    ) {

        String subscriptionSQL =
                "INSERT INTO subscriptions "
                        + "(member_id, package_id, start_date, "
                        + "end_date, amount, status) "
                        + "VALUES (?, ?, ?, ?, ?, 'ACTIVE')";

        String paymentSQL =
                "INSERT INTO payments "
                        + "(subscription_id, member_id, amount, "
                        + "transaction_id, payment_method, "
                        + "payment_status) "
                        + "VALUES (?, ?, ?, ?, 'ONLINE', 'SUCCESS')";

        Connection conn = null;

        try {

            conn =
                    DatabaseConnection.getConnection();

            conn.setAutoCommit(false);

            int subscriptionId;

            // -------------------------------------------------
            // INSERT SUBSCRIPTION
            // -------------------------------------------------

            try (
                    PreparedStatement stmt =
                            conn.prepareStatement(
                                    subscriptionSQL,
                                    Statement.RETURN_GENERATED_KEYS
                            )
            ) {

                stmt.setInt(
                        1,
                        memberId
                );

                stmt.setInt(
                        2,
                        packageId
                );

                stmt.setDate(
                        3,
                        Date.valueOf(startDate)
                );

                stmt.setDate(
                        4,
                        Date.valueOf(endDate)
                );

                stmt.setDouble(
                        5,
                        selectedPackage.getPrice()
                );

                stmt.executeUpdate();

                try (
                        ResultSet keys =
                                stmt.getGeneratedKeys()
                ) {

                    if (!keys.next()) {

                        conn.rollback();

                        return false;
                    }

                    subscriptionId =
                            keys.getInt(1);
                }
            }

            // -------------------------------------------------
            // INSERT PAYMENT
            // -------------------------------------------------

            try (
                    PreparedStatement stmt =
                            conn.prepareStatement(
                                    paymentSQL
                            )
            ) {

                stmt.setInt(
                        1,
                        subscriptionId
                );

                stmt.setInt(
                        2,
                        memberId
                );

                stmt.setDouble(
                        3,
                        selectedPackage.getPrice()
                );

                stmt.setString(
                        4,
                        transactionId
                );

                stmt.executeUpdate();
            }

            // -------------------------------------------------
            // COMMIT
            // -------------------------------------------------

            conn.commit();

            return true;

        } catch (SQLException e) {

            e.printStackTrace();

            if (conn != null) {

                try {
                    conn.rollback();
                } catch (SQLException rollbackError) {
                    rollbackError.printStackTrace();
                }
            }

            return false;

        } finally {

            if (conn != null) {

                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // =====================================================
    // OPEN MEMBER DASHBOARD
    // =====================================================

    private void openMemberDashboard() {

        try {

            MemberDashboardFrame dashboard =
                    new MemberDashboardFrame(
                            userId,
                            memberId
                    );

            dashboard.setVisible(true);

            dispose();

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Payment was successful, but the "
                            + "Member Dashboard could not be opened.\n\n"
                            + e.getMessage(),
                    "Dashboard Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =====================================================
    // MEMBER DETAILS CLASS
    // =====================================================

    private static class MemberDetails {

        private final String name;
        private final String email;
        private final String phone;

        public MemberDetails(
                String name,
                String email,
                String phone
        ) {

            this.name = name;
            this.email = email;
            this.phone = phone;
        }
    }
}