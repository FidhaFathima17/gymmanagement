package com.gym.view;

import com.gym.config.DatabaseConnection;
import com.gym.dao.PaymentDAO;
import com.gym.model.Payment;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentManagementPanel extends JPanel {

    // ============================================================
    // COLORS
    // ============================================================

    private final Color BACKGROUND =
            new Color(15, 17, 23);

    private final Color CARD =
            new Color(26, 29, 39);

    private final Color PRIMARY =
            new Color(56, 189, 248);

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

    // ============================================================
    // COMPONENTS
    // ============================================================

    private JTable table;

    private DefaultTableModel tableModel;

    private JTextField searchField;

    private JLabel totalLabel;
    private JLabel successLabel;
    private JLabel pendingLabel;
    private JLabel failedLabel;

    private final PaymentDAO paymentDAO =
            new PaymentDAO();

    // ============================================================
    // CONSTRUCTOR
    // ============================================================

    public PaymentManagementPanel() {

        setLayout(new BorderLayout());

        setBackground(BACKGROUND);

        setBorder(
                new EmptyBorder(
                        25,
                        28,
                        25,
                        28
                )
        );

        buildUI();

        loadPayments();
    }

    // ============================================================
    // BUILD UI
    // ============================================================

    private void buildUI() {

        JPanel main =
                new JPanel(
                        new BorderLayout(0, 20)
                );

        main.setOpaque(false);

        main.add(
                createHeader(),
                BorderLayout.NORTH
        );

        main.add(
                createCenter(),
                BorderLayout.CENTER
        );

        add(main);
    }

    // ============================================================
    // HEADER
    // ============================================================

    private JPanel createHeader() {

        JPanel header =
                new JPanel(new BorderLayout());

        header.setOpaque(false);

        JPanel titlePanel =
                new JPanel();

        titlePanel.setOpaque(false);

        titlePanel.setLayout(
                new BoxLayout(
                        titlePanel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel title =
                new JLabel("Payment Management");

        title.setForeground(TEXT);

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        27
                )
        );

        JLabel subtitle =
                new JLabel(
                        "Track membership payments and payment status"
                );

        subtitle.setForeground(SECONDARY);

        subtitle.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        13
                )
        );

        titlePanel.add(title);

        titlePanel.add(
                Box.createVerticalStrut(6)
        );

        titlePanel.add(subtitle);

        JButton addPaymentButton =
                createButton(
                        "+ Record Payment",
                        PRIMARY
                );

        addPaymentButton.setForeground(
                Color.BLACK
        );

        addPaymentButton.addActionListener(
                e -> showRecordPaymentDialog()
        );

        header.add(
                titlePanel,
                BorderLayout.WEST
        );

        header.add(
                addPaymentButton,
                BorderLayout.EAST
        );

        return header;
    }

    // ============================================================
    // CENTER
    // ============================================================

    private JPanel createCenter() {

        JPanel center =
                new JPanel(
                        new BorderLayout(0, 15)
                );

        center.setOpaque(false);

        center.add(
                createStats(),
                BorderLayout.NORTH
        );

        center.add(
                createTableCard(),
                BorderLayout.CENTER
        );

        return center;
    }

    // ============================================================
    // STATS
    // ============================================================

    private JPanel createStats() {

        JPanel stats =
                new JPanel(
                        new GridLayout(
                                1,
                                4,
                                15,
                                0
                        )
                );

        stats.setOpaque(false);

        totalLabel = new JLabel("0");
        successLabel = new JLabel("0");
        pendingLabel = new JLabel("0");
        failedLabel = new JLabel("0");

        stats.add(
                createStatCard(
                        "TOTAL PAYMENTS",
                        totalLabel,
                        PRIMARY
                )
        );

        stats.add(
                createStatCard(
                        "SUCCESS",
                        successLabel,
                        SUCCESS
                )
        );

        stats.add(
                createStatCard(
                        "PENDING",
                        pendingLabel,
                        WARNING
                )
        );

        stats.add(
                createStatCard(
                        "FAILED",
                        failedLabel,
                        DANGER
                )
        );

        return stats;
    }

    private JPanel createStatCard(
            String title,
            JLabel value,
            Color color
    ) {

        JPanel card =
                new JPanel(
                        new BorderLayout()
                );

        card.setBackground(CARD);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                18,
                                20,
                                18,
                                20
                        )
                )
        );

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setForeground(SECONDARY);

        titleLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        11
                )
        );

        value.setForeground(color);

        value.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        27
                )
        );

        card.add(
                titleLabel,
                BorderLayout.NORTH
        );

        card.add(
                value,
                BorderLayout.CENTER
        );

        return card;
    }

    // ============================================================
    // TABLE
    // ============================================================

    private JPanel createTableCard() {

        JPanel card =
                new JPanel(new BorderLayout(0, 15));

        card.setBackground(CARD);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                20,
                                20,
                                20,
                                20
                        )
                )
        );

        JPanel searchPanel =
                new JPanel(
                        new BorderLayout()
                );

        searchPanel.setOpaque(false);

        JPanel left =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                8,
                                0
                        )
                );

        left.setOpaque(false);

        searchField =
                new JTextField();

        searchField.setPreferredSize(
                new Dimension(300, 38)
        );

        searchField.setBackground(
                new Color(30, 34, 46)
        );

        searchField.setForeground(TEXT);

        searchField.setCaretColor(PRIMARY);

        searchField.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                0,
                                12,
                                0,
                                12
                        )
                )
        );

        JButton searchButton =
                createButton(
                        "Search",
                        PRIMARY
                );

        searchButton.setForeground(
                Color.BLACK
        );

        JButton clearButton =
                createButton(
                        "Clear",
                        CARD
                );

        searchButton.addActionListener(
                e -> searchPayments()
        );

        clearButton.addActionListener(e -> {

            searchField.setText("");

            loadPayments();
        });

        left.add(searchField);
        left.add(searchButton);
        left.add(clearButton);

        JButton refreshButton =
                createButton(
                        "⟳ Refresh",
                        CARD
                );

        refreshButton.addActionListener(
                e -> loadPayments()
        );

        JPanel right =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                8,
                                0
                        )
                );

        right.setOpaque(false);

        right.add(refreshButton);

        searchPanel.add(
                left,
                BorderLayout.WEST
        );

        searchPanel.add(
                right,
                BorderLayout.EAST
        );

        String[] columns = {

                "Payment ID",
                "Member",
                "Package",
                "Amount",
                "Method",
                "Transaction ID",
                "Status",
                "Payment Date"

        };

        tableModel =
                new DefaultTableModel(
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

        table =
                new JTable(tableModel);

        table.setRowHeight(42);

        table.setBackground(CARD);

        table.setForeground(TEXT);

        table.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        13
                )
        );

        table.setSelectionBackground(
                new Color(51, 65, 85)
        );

        table.setSelectionForeground(TEXT);

        table.setShowGrid(false);

        table.setFillsViewportHeight(true);

        table.setIntercellSpacing(
                new Dimension(0, 0)
        );

        table.getTableHeader().setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        12
                )
        );

        table.getTableHeader().setBackground(
                new Color(30, 34, 46)
        );

        table.getTableHeader().setForeground(
                SECONDARY
        );

        table.getTableHeader().setPreferredSize(
                new Dimension(0, 40)
        );

        table.getColumnModel()
                .getColumn(6)
                .setCellRenderer(
                        new PaymentStatusRenderer()
                );

        table.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            MouseEvent e
                    ) {

                        if (e.getClickCount() == 2) {

                            showPaymentDetails();
                        }
                    }
                }
        );

        JScrollPane scrollPane =
                new JScrollPane(table);

        scrollPane.setBorder(
                BorderFactory.createEmptyBorder()
        );

        scrollPane.getViewport()
                .setBackground(CARD);

        card.add(
                searchPanel,
                BorderLayout.NORTH
        );

        card.add(
                scrollPane,
                BorderLayout.CENTER
        );

        return card;
    }

    // ============================================================
    // LOAD PAYMENTS
    // ============================================================

    private void loadPayments() {

        List<Payment> payments =
                paymentDAO.getAllPayments();

        populateTable(payments);
    }

    // ============================================================
    // SEARCH
    // ============================================================

    private void searchPayments() {

        String keyword =
                searchField.getText().trim();

        if (keyword.isEmpty()) {

            loadPayments();

            return;
        }

        List<Payment> payments =
                paymentDAO.searchPayments(keyword);

        populateTable(payments);
    }

    // ============================================================
    // POPULATE TABLE
    // ============================================================

    private void populateTable(
            List<Payment> payments
    ) {

        tableModel.setRowCount(0);

        int success = 0;
        int pending = 0;
        int failed = 0;

        for (Payment payment : payments) {

            String status =
                    payment.getPaymentStatus();

            if ("SUCCESS".equals(status)) {
                success++;
            }

            if ("PENDING".equals(status)) {
                pending++;
            }

            if ("FAILED".equals(status)) {
                failed++;
            }

            tableModel.addRow(
                    new Object[]{

                            payment.getPaymentId(),

                            payment.getMemberName(),

                            payment.getPackageName(),

                            String.format(
                                    "₹ %.2f",
                                    payment.getAmount()
                            ),

                            payment.getPaymentMethod(),

                            payment.getTransactionId() == null
                                    ? "-"
                                    : payment.getTransactionId(),

                            status,

                            payment.getPaymentDate()
                    }
            );
        }

        totalLabel.setText(
                String.valueOf(payments.size())
        );

        successLabel.setText(
                String.valueOf(success)
        );

        pendingLabel.setText(
                String.valueOf(pending)
        );

        failedLabel.setText(
                String.valueOf(failed)
        );
    }

    // ============================================================
    // RECORD PAYMENT DIALOG
    // ============================================================

    private void showRecordPaymentDialog() {

        List<SubscriptionItem> subscriptions =
                getPendingSubscriptions();

        if (subscriptions.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "There are no pending subscriptions.\n\n" +
                            "Create a subscription first.",
                    "No Pending Subscriptions",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        JPanel panel =
                new JPanel(
                        new GridLayout(
                                0,
                                2,
                                10,
                                12
                        )
                );

        panel.setBorder(
                new EmptyBorder(
                        10,
                        10,
                        10,
                        10
                )
        );

        JComboBox<SubscriptionItem>
                subscriptionCombo =
                new JComboBox<>();

        for (
                SubscriptionItem subscription :
                subscriptions
        ) {

            subscriptionCombo.addItem(
                    subscription
            );
        }

        JTextField amountField =
                new JTextField();

        amountField.setEditable(false);

        JComboBox<String> methodCombo =
                new JComboBox<>(
                        new String[]{
                                "CASH",
                                "UPI",
                                "CARD",
                                "ONLINE"
                        }
                );

        JTextField transactionField =
                new JTextField();

        panel.add(
                new JLabel("Subscription:")
        );

        panel.add(
                subscriptionCombo
        );

        panel.add(
                new JLabel("Amount:")
        );

        panel.add(
                amountField
        );

        panel.add(
                new JLabel("Payment Method:")
        );

        panel.add(
                methodCombo
        );

        panel.add(
                new JLabel("Transaction ID:")
        );

        panel.add(
                transactionField
        );

        Runnable updateAmount =
                () -> {

                    SubscriptionItem selected =
                            (SubscriptionItem)
                                    subscriptionCombo
                                            .getSelectedItem();

                    if (selected != null) {

                        amountField.setText(
                                String.format(
                                        "₹ %.2f",
                                        selected.amount
                                )
                        );
                    }
                };

        subscriptionCombo.addActionListener(
                e -> updateAmount.run()
        );

        updateAmount.run();

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Record Payment",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

        if (
                result !=
                        JOptionPane.OK_OPTION
        ) {
            return;
        }

        SubscriptionItem selected =
                (SubscriptionItem)
                        subscriptionCombo
                                .getSelectedItem();

        if (selected == null) {
            return;
        }

        String transactionId =
                transactionField
                        .getText()
                        .trim();

        String method =
                methodCombo
                        .getSelectedItem()
                        .toString();

        if (
                ("UPI".equals(method)
                        || "CARD".equals(method)
                        || "ONLINE".equals(method))
                        &&
                        transactionId.isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Transaction ID is required for "
                            + method
                            + " payments.",
                    "Missing Transaction ID",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        boolean success =
                paymentDAO.addSuccessfulPayment(
                        selected.subscriptionId,
                        selected.memberId,
                        selected.amount,
                        transactionId,
                        method
                );

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Payment recorded successfully!\n\n" +
                            "Payment Status: SUCCESS\n" +
                            "Subscription Status: ACTIVE",
                    "Payment Successful",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadPayments();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Payment could not be recorded.",
                    "Payment Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ============================================================
    // GET PENDING SUBSCRIPTIONS
    // ============================================================

    private List<SubscriptionItem>
    getPendingSubscriptions() {

        List<SubscriptionItem> subscriptions =
                new ArrayList<>();

        String sql =
                "SELECT s.subscription_id, " +
                        "s.member_id, " +
                        "mp.full_name, " +
                        "gp.package_name, " +
                        "s.amount " +
                        "FROM subscriptions s " +
                        "INNER JOIN member_profiles mp " +
                        "ON s.member_id = mp.member_id " +
                        "INNER JOIN gym_packages gp " +
                        "ON s.package_id = gp.package_id " +
                        "WHERE s.status = 'PENDING' " +
                        "ORDER BY s.subscription_id DESC";

        try (
                Connection conn =
                        DatabaseConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql);

                ResultSet rs =
                        stmt.executeQuery()
        ) {

            while (rs.next()) {

                subscriptions.add(
                        new SubscriptionItem(
                                rs.getInt(
                                        "subscription_id"
                                ),
                                rs.getInt(
                                        "member_id"
                                ),
                                rs.getString(
                                        "full_name"
                                ),
                                rs.getString(
                                        "package_name"
                                ),
                                rs.getDouble(
                                        "amount"
                                )
                        )
                );
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return subscriptions;
    }

    // ============================================================
    // DETAILS
    // ============================================================

    private void showPaymentDetails() {

        int row =
                table.getSelectedRow();

        if (row < 0) {
            return;
        }

        String details =
                "Payment ID: "
                        + tableModel.getValueAt(row, 0)
                        + "\n\nMember: "
                        + tableModel.getValueAt(row, 1)
                        + "\n\nPackage: "
                        + tableModel.getValueAt(row, 2)
                        + "\n\nAmount: "
                        + tableModel.getValueAt(row, 3)
                        + "\n\nPayment Method: "
                        + tableModel.getValueAt(row, 4)
                        + "\n\nTransaction ID: "
                        + tableModel.getValueAt(row, 5)
                        + "\n\nStatus: "
                        + tableModel.getValueAt(row, 6)
                        + "\n\nPayment Date: "
                        + tableModel.getValueAt(row, 7);

        JOptionPane.showMessageDialog(
                this,
                details,
                "Payment Details",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // ============================================================
    // BUTTON
    // ============================================================

    private JButton createButton(
            String text,
            Color background
    ) {

        JButton button =
                new JButton(text);

        button.setFocusPainted(false);

        button.setBorderPainted(false);

        button.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        12
                )
        );

        button.setBackground(background);

        button.setForeground(TEXT);

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setBorder(
                new EmptyBorder(
                        10,
                        16,
                        10,
                        16
                )
        );

        return button;
    }

    // ============================================================
    // STATUS RENDERER
    // ============================================================

    private class PaymentStatusRenderer
            extends DefaultTableCellRenderer {

        @Override
        public Component
        getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column
        ) {

            Component component =
                    super.getTableCellRendererComponent(
                            table,
                            value,
                            isSelected,
                            hasFocus,
                            row,
                            column
                    );

            String status =
                    value == null
                            ? ""
                            : value.toString();

            if ("SUCCESS".equals(status)) {

                component.setForeground(
                        SUCCESS
                );

            } else if ("PENDING".equals(status)) {

                component.setForeground(
                        WARNING
                );

            } else if ("FAILED".equals(status)) {

                component.setForeground(
                        DANGER
                );

            } else {

                component.setForeground(TEXT);
            }

            setHorizontalAlignment(
                    SwingConstants.CENTER
            );

            return component;
        }
    }

    // ============================================================
    // SUBSCRIPTION ITEM
    // ============================================================

    private static class SubscriptionItem {

        int subscriptionId;

        int memberId;

        String memberName;

        String packageName;

        double amount;

        SubscriptionItem(
                int subscriptionId,
                int memberId,
                String memberName,
                String packageName,
                double amount
        ) {

            this.subscriptionId =
                    subscriptionId;

            this.memberId =
                    memberId;

            this.memberName =
                    memberName;

            this.packageName =
                    packageName;

            this.amount =
                    amount;
        }

        @Override
        public String toString() {

            return memberName
                    + " | "
                    + packageName
                    + " | ₹"
                    + String.format(
                    "%.2f",
                    amount
            )
                    + " | ID: "
                    + subscriptionId;
        }
    }
}