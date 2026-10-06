package com.gym.view;

import com.gym.config.DatabaseConnection;
import com.gym.dao.SubscriptionDAO;
import com.gym.model.Subscription;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionManagementPanel extends JPanel {

    // ============================================================
    // COLORS
    // ============================================================

    private final Color BACKGROUND =
            new Color(15, 17, 23);

    private final Color CARD =
            new Color(26, 29, 39);

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

    private final Color PURPLE =
            new Color(168, 85, 247);

    // ============================================================
    // COMPONENTS
    // ============================================================

    private JTable table;

    private DefaultTableModel tableModel;

    private JTextField searchField;

    private JLabel totalLabel;

    private JLabel activeLabel;

    private JLabel pendingLabel;

    private JLabel expiredLabel;

    private final SubscriptionDAO subscriptionDAO =
            new SubscriptionDAO();

    // ============================================================
    // CONSTRUCTOR
    // ============================================================

    public SubscriptionManagementPanel() {

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

        loadSubscriptions();
    }

    // ============================================================
    // BUILD UI
    // ============================================================

    private void buildUI() {

        JPanel main =
                new JPanel(new BorderLayout(0, 20));

        main.setOpaque(false);

        main.add(
                createHeader(),
                BorderLayout.NORTH
        );

        main.add(
                createCenter(),
                BorderLayout.CENTER
        );

        add(main, BorderLayout.CENTER);
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
                new JLabel("Subscription Management");

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
                        "Manage member memberships and subscription validity"
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

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                10,
                                0
                        )
                );

        buttonPanel.setOpaque(false);

        JButton refreshButton =
                createButton(
                        "⟳ Refresh",
                        CARD
                );

        JButton addButton =
                createButton(
                        "+ Add Subscription",
                        PRIMARY
                );

        addButton.setForeground(Color.BLACK);

        refreshButton.addActionListener(
                e -> loadSubscriptions()
        );

        addButton.addActionListener(
                e -> showAddSubscriptionDialog()
        );

        buttonPanel.add(refreshButton);

        buttonPanel.add(addButton);

        header.add(
                titlePanel,
                BorderLayout.WEST
        );

        header.add(
                buttonPanel,
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
    // STAT CARDS
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

        totalLabel =
                new JLabel("0");

        activeLabel =
                new JLabel("0");

        pendingLabel =
                new JLabel("0");

        expiredLabel =
                new JLabel("0");

        stats.add(
                createStatCard(
                        "TOTAL",
                        totalLabel,
                        PRIMARY
                )
        );

        stats.add(
                createStatCard(
                        "ACTIVE",
                        activeLabel,
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
                        "EXPIRED",
                        expiredLabel,
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
    // TABLE CARD
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

        // Search area

        JPanel searchPanel =
                new JPanel(
                        new BorderLayout(10, 0)
                );

        searchPanel.setOpaque(false);

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

        searchButton.setForeground(Color.BLACK);

        JButton clearButton =
                createButton(
                        "Clear",
                        CARD
                );

        searchButton.addActionListener(
                e -> searchSubscriptions()
        );

        clearButton.addActionListener(e -> {

            searchField.setText("");

            loadSubscriptions();
        });

        JPanel leftSearch =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                8,
                                0
                        )
                );

        leftSearch.setOpaque(false);

        leftSearch.add(searchField);

        leftSearch.add(searchButton);

        leftSearch.add(clearButton);

        JButton activateButton =
                createButton(
                        "Activate",
                        SUCCESS
                );

        JButton cancelButton =
                createButton(
                        "Cancel",
                        DANGER
                );

        JButton deleteButton =
                createButton(
                        "Delete",
                        CARD
                );

        activateButton.setForeground(Color.BLACK);

        cancelButton.setForeground(Color.WHITE);

        activateButton.addActionListener(
                e -> changeSelectedStatus("ACTIVE")
        );

        cancelButton.addActionListener(
                e -> changeSelectedStatus("CANCELLED")
        );

        deleteButton.addActionListener(
                e -> deleteSelectedSubscription()
        );

        JPanel actions =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                8,
                                0
                        )
                );

        actions.setOpaque(false);

        actions.add(activateButton);

        actions.add(cancelButton);

        actions.add(deleteButton);

        searchPanel.add(
                leftSearch,
                BorderLayout.WEST
        );

        searchPanel.add(
                actions,
                BorderLayout.EAST
        );

        // Table

        String[] columns = {

                "ID",
                "Member",
                "Package",
                "Start Date",
                "End Date",
                "Amount",
                "Status",
                "Created"

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

        table.setGridColor(BORDER);

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

        table.getTableHeader().setBorder(
                BorderFactory.createMatteBorder(
                        0,
                        0,
                        1,
                        0,
                        BORDER
                )
        );

        // Status renderer

        table.getColumnModel()
                .getColumn(6)
                .setCellRenderer(
                        new StatusRenderer()
                );

        // Double click

        table.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            MouseEvent e
                    ) {

                        if (e.getClickCount() == 2) {

                            showSelectedDetails();
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
    // LOAD
    // ============================================================

    private void loadSubscriptions() {

        subscriptionDAO.updateExpiredSubscriptions();

        List<Subscription> subscriptions =
                subscriptionDAO.getAllSubscriptions();

        populateTable(subscriptions);
    }

    // ============================================================
    // SEARCH
    // ============================================================

    private void searchSubscriptions() {

        String keyword =
                searchField.getText().trim();

        if (keyword.isEmpty()) {

            loadSubscriptions();

            return;
        }

        List<Subscription> subscriptions =
                subscriptionDAO.searchSubscriptions(
                        keyword
                );

        populateTable(subscriptions);
    }

    // ============================================================
    // POPULATE TABLE
    // ============================================================

    private void populateTable(
            List<Subscription> subscriptions
    ) {

        tableModel.setRowCount(0);

        int active = 0;
        int pending = 0;
        int expired = 0;

        for (
                Subscription subscription :
                subscriptions
        ) {

            String status =
                    subscription.getStatus();

            if ("ACTIVE".equals(status)) {
                active++;
            }

            if ("PENDING".equals(status)) {
                pending++;
            }

            if ("EXPIRED".equals(status)) {
                expired++;
            }

            tableModel.addRow(
                    new Object[]{

                            subscription
                                    .getSubscriptionId(),

                            subscription
                                    .getMemberName(),

                            subscription
                                    .getPackageName(),

                            subscription
                                    .getStartDate(),

                            subscription
                                    .getEndDate(),

                            String.format(
                                    "₹ %.2f",
                                    subscription
                                            .getAmount()
                            ),

                            status,

                            subscription
                                    .getCreatedAt()
                    }
            );
        }

        totalLabel.setText(
                String.valueOf(
                        subscriptions.size()
                )
        );

        activeLabel.setText(
                String.valueOf(active)
        );

        pendingLabel.setText(
                String.valueOf(pending)
        );

        expiredLabel.setText(
                String.valueOf(expired)
        );
    }

    // ============================================================
    // ADD SUBSCRIPTION DIALOG
    // ============================================================

    private void showAddSubscriptionDialog() {

        List<MemberItem> members =
                getMembers();

        List<PackageItem> packages =
                getActivePackages();

        if (members.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No members are available.\n" +
                            "Please register a member first.",
                    "No Members",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (packages.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No active gym packages are available.\n" +
                            "Please create an active package first.",
                    "No Packages",
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

        JComboBox<MemberItem> memberCombo =
                new JComboBox<>();

        for (MemberItem member : members) {
            memberCombo.addItem(member);
        }

        JComboBox<PackageItem> packageCombo =
                new JComboBox<>();

        for (PackageItem packageItem : packages) {
            packageCombo.addItem(packageItem);
        }

        JTextField startDateField =
                new JTextField(
                        LocalDate.now().toString()
                );

        JTextField endDateField =
                new JTextField();

        JTextField amountField =
                new JTextField();

        endDateField.setEditable(false);

        amountField.setEditable(false);

        memberCombo.setBackground(
                new Color(30, 34, 46)
        );

        packageCombo.setBackground(
                new Color(30, 34, 46)
        );

        panel.add(
                new JLabel("Member:")
        );

        panel.add(memberCombo);

        panel.add(
                new JLabel("Gym Package:")
        );

        panel.add(packageCombo);

        panel.add(
                new JLabel("Start Date (YYYY-MM-DD):")
        );

        panel.add(startDateField);

        panel.add(
                new JLabel("End Date:")
        );

        panel.add(endDateField);

        panel.add(
                new JLabel("Amount:")
        );

        panel.add(amountField);

        Runnable calculate =
                () -> {

                    try {

                        PackageItem selected =
                                (PackageItem)
                                        packageCombo
                                                .getSelectedItem();

                        if (selected == null) {
                            return;
                        }

                        LocalDate start =
                                LocalDate.parse(
                                        startDateField
                                                .getText()
                                                .trim()
                                );

                        LocalDate end =
                                calculateEndDate(
                                        start,
                                        selected.duration,
                                        selected.durationUnit
                                );

                        endDateField.setText(
                                end.toString()
                        );

                        amountField.setText(
                                String.format(
                                        "₹ %.2f",
                                        selected.price
                                )
                        );

                    } catch (Exception ignored) {

                        endDateField.setText("");

                        amountField.setText("");
                    }
                };

        packageCombo.addActionListener(
                e -> calculate.run()
        );

        startDateField.addActionListener(
                e -> calculate.run()
        );

        calculate.run();

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Add Subscription",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

        if (
                result !=
                        JOptionPane.OK_OPTION
        ) {
            return;
        }

        try {

            MemberItem member =
                    (MemberItem)
                            memberCombo
                                    .getSelectedItem();

            PackageItem packageItem =
                    (PackageItem)
                            packageCombo
                                    .getSelectedItem();

            LocalDate start =
                    LocalDate.parse(
                            startDateField
                                    .getText()
                                    .trim()
                    );

            LocalDate end =
                    calculateEndDate(
                            start,
                            packageItem.duration,
                            packageItem.durationUnit
                    );

            if (start.isBefore(LocalDate.now())) {

                JOptionPane.showMessageDialog(
                        this,
                        "Start date cannot be before today.",
                        "Invalid Date",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            boolean success =
                    subscriptionDAO.addSubscription(
                            member.memberId,
                            packageItem.packageId,
                            start,
                            end,
                            packageItem.price
                    );

            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        "Subscription created successfully.\n\n" +
                                "Status: PENDING\n" +
                                "The subscription can be activated after payment.",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                loadSubscriptions();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Unable to create subscription.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid start date.\n\n" +
                            "Format: YYYY-MM-DD",
                    "Invalid Date",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    // ============================================================
    // END DATE CALCULATION
    // ============================================================

    private LocalDate calculateEndDate(
            LocalDate start,
            int duration,
            String unit
    ) {

        if ("DAY".equals(unit)) {

            return start
                    .plusDays(duration)
                    .minusDays(1);
        }

        if ("MONTH".equals(unit)) {

            return start
                    .plusMonths(duration)
                    .minusDays(1);
        }

        if ("YEAR".equals(unit)) {

            return start
                    .plusYears(duration)
                    .minusDays(1);
        }

        return start;
    }

    // ============================================================
    // CHANGE STATUS
    // ============================================================

    private void changeSelectedStatus(
            String status
    ) {

        int row =
                table.getSelectedRow();

        if (row < 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a subscription first.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int subscriptionId =
                Integer.parseInt(
                        tableModel
                                .getValueAt(row, 0)
                                .toString()
                );

        String currentStatus =
                tableModel
                        .getValueAt(row, 6)
                        .toString();

        if (
                "EXPIRED".equals(currentStatus)
                        &&
                        "ACTIVE".equals(status)
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "An expired subscription cannot be activated.",
                    "Invalid Operation",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String action =
                "ACTIVE".equals(status)
                        ? "activate"
                        : "cancel";

        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to "
                                + action
                                + " this subscription?",
                        "Confirm",
                        JOptionPane.YES_NO_OPTION
                );

        if (
                confirm !=
                        JOptionPane.YES_OPTION
        ) {
            return;
        }

        if (
                subscriptionDAO.updateStatus(
                        subscriptionId,
                        status
                )
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Subscription status updated.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadSubscriptions();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to update subscription.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ============================================================
    // DELETE
    // ============================================================

    private void deleteSelectedSubscription() {

        int row =
                table.getSelectedRow();

        if (row < 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a subscription first.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int subscriptionId =
                Integer.parseInt(
                        tableModel
                                .getValueAt(row, 0)
                                .toString()
                );

        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Delete this subscription permanently?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

        if (
                confirm !=
                        JOptionPane.YES_OPTION
        ) {
            return;
        }

        if (
                subscriptionDAO.deleteSubscription(
                        subscriptionId
                )
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Subscription deleted.",
                    "Deleted",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadSubscriptions();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to delete subscription.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ============================================================
    // DETAILS
    // ============================================================

    private void showSelectedDetails() {

        int row =
                table.getSelectedRow();

        if (row < 0) {
            return;
        }

        String details =
                "Subscription ID: "
                        + tableModel
                        .getValueAt(row, 0)
                        + "\n\nMember: "
                        + tableModel
                        .getValueAt(row, 1)
                        + "\n\nPackage: "
                        + tableModel
                        .getValueAt(row, 2)
                        + "\n\nStart Date: "
                        + tableModel
                        .getValueAt(row, 3)
                        + "\n\nEnd Date: "
                        + tableModel
                        .getValueAt(row, 4)
                        + "\n\nAmount: "
                        + tableModel
                        .getValueAt(row, 5)
                        + "\n\nStatus: "
                        + tableModel
                        .getValueAt(row, 6)
                        + "\n\nCreated: "
                        + tableModel
                        .getValueAt(row, 7);

        JOptionPane.showMessageDialog(
                this,
                details,
                "Subscription Details",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // ============================================================
    // GET MEMBERS
    // ============================================================

    private List<MemberItem> getMembers() {

        List<MemberItem> members =
                new ArrayList<>();

        String sql =
                "SELECT member_id, full_name " +
                        "FROM member_profiles " +
                        "ORDER BY full_name";

        try (
                Connection conn =
                        DatabaseConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql);

                ResultSet rs =
                        stmt.executeQuery()
        ) {

            while (rs.next()) {

                members.add(
                        new MemberItem(
                                rs.getInt("member_id"),
                                rs.getString("full_name")
                        )
                );
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return members;
    }

    // ============================================================
    // GET ACTIVE PACKAGES
    // ============================================================

    private List<PackageItem> getActivePackages() {

        List<PackageItem> packages =
                new ArrayList<>();

        String sql =
                "SELECT package_id, package_name, " +
                        "duration, duration_unit, price " +
                        "FROM gym_packages " +
                        "WHERE status = 'ACTIVE' " +
                        "ORDER BY package_name";

        try (
                Connection conn =
                        DatabaseConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql);

                ResultSet rs =
                        stmt.executeQuery()
        ) {

            while (rs.next()) {

                packages.add(
                        new PackageItem(
                                rs.getInt("package_id"),
                                rs.getString("package_name"),
                                rs.getInt("duration"),
                                rs.getString("duration_unit"),
                                rs.getDouble("price")
                        )
                );
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return packages;
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

    private class StatusRenderer
            extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(
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

            if ("ACTIVE".equals(status)) {

                component.setForeground(
                        SUCCESS
                );

            } else if ("PENDING".equals(status)) {

                component.setForeground(
                        WARNING
                );

            } else if ("EXPIRED".equals(status)) {

                component.setForeground(
                        DANGER
                );

            } else if ("CANCELLED".equals(status)) {

                component.setForeground(
                        SECONDARY
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
    // MEMBER ITEM
    // ============================================================

    private static class MemberItem {

        int memberId;

        String name;

        MemberItem(
                int memberId,
                String name
        ) {

            this.memberId = memberId;

            this.name = name;
        }

        @Override
        public String toString() {

            return name
                    + "  [ID: "
                    + memberId
                    + "]";
        }
    }

    // ============================================================
    // PACKAGE ITEM
    // ============================================================

    private static class PackageItem {

        int packageId;

        String name;

        int duration;

        String durationUnit;

        double price;

        PackageItem(
                int packageId,
                String name,
                int duration,
                String durationUnit,
                double price
        ) {

            this.packageId = packageId;

            this.name = name;

            this.duration = duration;

            this.durationUnit =
                    durationUnit;

            this.price = price;
        }

        @Override
        public String toString() {

            return name
                    + "  |  "
                    + duration
                    + " "
                    + durationUnit
                    + "  |  ₹"
                    + String.format(
                    "%.2f",
                    price
            );
        }
    }
}