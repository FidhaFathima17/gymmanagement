package com.gym.view;

import com.gym.dao.TrainerDAO;
import com.gym.dao.TrainerDAO.TrainerApplication;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class TrainerManagementPanel extends JPanel {

    // =========================================================
    // COLORS
    // =========================================================

    private static final Color BACKGROUND =
            new Color(15, 17, 23);

    private static final Color CARD =
            new Color(26, 29, 39);

    private static final Color INPUT =
            new Color(35, 39, 51);

    private static final Color PRIMARY =
            new Color(56, 189, 248);

    private static final Color PRIMARY_HOVER =
            new Color(34, 211, 238);

    private static final Color TEXT =
            new Color(241, 245, 249);

    private static final Color SECONDARY =
            new Color(148, 163, 184);

    private static final Color BORDER =
            new Color(51, 65, 85);

    private static final Color SUCCESS =
            new Color(34, 197, 94);

    private static final Color DANGER =
            new Color(239, 68, 68);

    private static final Color WARNING =
            new Color(251, 191, 36);

    // =========================================================
    // COMPONENTS
    // =========================================================

    private JTable trainerTable;

    private DefaultTableModel tableModel;

    private JTextField searchField;

    private JButton refreshButton;

    private JButton viewButton;

    private JButton approveButton;

    private JButton rejectButton;

    private JLabel totalLabel;

    private JLabel pendingLabel;

    private JLabel approvedLabel;

    private JLabel rejectedLabel;

    private final TrainerDAO trainerDAO;

    private List<TrainerApplication> applications;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public TrainerManagementPanel() {

        trainerDAO = new TrainerDAO();

        setLayout(
                new BorderLayout()
        );

        setBackground(
                BACKGROUND
        );

        setBorder(
                new EmptyBorder(
                        25,
                        30,
                        25,
                        30
                )
        );

        createUI();

        loadApplications();
    }

    // =========================================================
    // CREATE UI
    // =========================================================

    private void createUI() {

        // =====================================================
        // HEADER
        // =====================================================

        JPanel headerPanel =
                new JPanel(
                        new BorderLayout()
                );

        headerPanel.setOpaque(false);

        JLabel titleLabel =
                new JLabel(
                        "Trainer Management"
                );

        titleLabel.setForeground(
                TEXT
        );

        titleLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        28
                )
        );

        JLabel subtitleLabel =
                new JLabel(
                        "Review, approve and manage trainer applications"
                );

        subtitleLabel.setForeground(
                SECONDARY
        );

        subtitleLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

        JPanel titleContainer =
                new JPanel();

        titleContainer.setOpaque(false);

        titleContainer.setLayout(
                new BoxLayout(
                        titleContainer,
                        BoxLayout.Y_AXIS
                )
        );

        titleContainer.add(
                titleLabel
        );

        titleContainer.add(
                Box.createVerticalStrut(6)
        );

        titleContainer.add(
                subtitleLabel
        );

        headerPanel.add(
                titleContainer,
                BorderLayout.WEST
        );

        // =====================================================
        // REFRESH
        // =====================================================

        refreshButton =
                new JButton(
                        "↻  Refresh"
                );

        styleButton(
                refreshButton,
                PRIMARY
        );

        refreshButton.addActionListener(
                e -> loadApplications()
        );

        headerPanel.add(
                refreshButton,
                BorderLayout.EAST
        );

        add(
                headerPanel,
                BorderLayout.NORTH
        );

        // =====================================================
        // CENTER
        // =====================================================

        JPanel centerPanel =
                new JPanel(
                        new BorderLayout(
                                0,
                                15
                        )
                );

        centerPanel.setOpaque(false);

        // =====================================================
        // STAT CARDS
        // =====================================================

        JPanel statsPanel =
                new JPanel(
                        new GridLayout(
                                1,
                                4,
                                15,
                                0
                        )
                );

        statsPanel.setOpaque(false);

        totalLabel =
                createStatValue();

        pendingLabel =
                createStatValue();

        approvedLabel =
                createStatValue();

        rejectedLabel =
                createStatValue();

        statsPanel.add(
                createStatCard(
                        "TOTAL APPLICATIONS",
                        totalLabel,
                        PRIMARY
                )
        );

        statsPanel.add(
                createStatCard(
                        "PENDING",
                        pendingLabel,
                        WARNING
                )
        );

        statsPanel.add(
                createStatCard(
                        "APPROVED",
                        approvedLabel,
                        SUCCESS
                )
        );

        statsPanel.add(
                createStatCard(
                        "REJECTED",
                        rejectedLabel,
                        DANGER
                )
        );

        centerPanel.add(
                statsPanel,
                BorderLayout.NORTH
        );

        // =====================================================
        // TABLE CARD
        // =====================================================

        JPanel tableCard =
                new JPanel(
                        new BorderLayout(
                                0,
                                15
                        )
                );

        tableCard.setBackground(
                CARD
        );

        tableCard.setBorder(
                new EmptyBorder(
                        20,
                        20,
                        20,
                        20
                )
        );

        // =====================================================
        // SEARCH
        // =====================================================

        JPanel searchPanel =
                new JPanel(
                        new BorderLayout(
                                10,
                                0
                        )
                );

        searchPanel.setOpaque(false);

        JLabel searchLabel =
                new JLabel(
                        "Search:"
                );

        searchLabel.setForeground(
                TEXT
        );

        searchLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        13
                )
        );

        searchField =
                new JTextField();

        styleTextField(
                searchField
        );

        searchField.setToolTipText(
                "Search by name, username, email or status"
        );

        searchField.getDocument()
                .addDocumentListener(
                        new javax.swing.event.DocumentListener() {

                            @Override
                            public void insertUpdate(
                                    javax.swing.event.DocumentEvent e
                            ) {
                                filterTable();
                            }

                            @Override
                            public void removeUpdate(
                                    javax.swing.event.DocumentEvent e
                            ) {
                                filterTable();
                            }

                            @Override
                            public void changedUpdate(
                                    javax.swing.event.DocumentEvent e
                            ) {
                                filterTable();
                            }
                        }
                );

        searchPanel.add(
                searchLabel,
                BorderLayout.WEST
        );

        searchPanel.add(
                searchField,
                BorderLayout.CENTER
        );

        tableCard.add(
                searchPanel,
                BorderLayout.NORTH
        );

        // =====================================================
        // TABLE
        // =====================================================

        String[] columns = {
                "ID",
                "Trainer Name",
                "Username",
                "Email",
                "Phone",
                "Qualification",
                "Specialization",
                "Experience",
                "Status",
                "Applied Date"
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

        trainerTable =
                new JTable(
                        tableModel
                );

        trainerTable.setBackground(
                INPUT
        );

        trainerTable.setForeground(
                TEXT
        );

        trainerTable.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        13
                )
        );

        trainerTable.setRowHeight(
                42
        );

        trainerTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        trainerTable.setGridColor(
                BORDER
        );

        trainerTable.setShowGrid(
                true
        );

        trainerTable.setAutoResizeMode(
                JTable.AUTO_RESIZE_OFF
        );

        // =====================================================
        // HEADER
        // =====================================================

        JTableHeader tableHeader =
                trainerTable.getTableHeader();

        tableHeader.setBackground(
                new Color(
                        20,
                        24,
                        32
                )
        );

        tableHeader.setForeground(
                TEXT
        );

        tableHeader.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        12
                )
        );

        tableHeader.setPreferredSize(
                new Dimension(
                        0,
                        42
                )
        );

        // =====================================================
        // COLUMN WIDTHS
        // =====================================================

        int[] widths = {
                60,
                160,
                130,
                220,
                120,
                180,
                180,
                100,
                110,
                160
        };

        for (
                int i = 0;
                i < widths.length;
                i++
        ) {

            trainerTable
                    .getColumnModel()
                    .getColumn(i)
                    .setPreferredWidth(
                            widths[i]
                    );
        }

        // =====================================================
        // CENTER ALIGN ID / EXPERIENCE
        // =====================================================

        DefaultTableCellRenderer centerRenderer =
                new DefaultTableCellRenderer();

        centerRenderer.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        trainerTable
                .getColumnModel()
                .getColumn(0)
                .setCellRenderer(
                        centerRenderer
                );

        trainerTable
                .getColumnModel()
                .getColumn(7)
                .setCellRenderer(
                        centerRenderer
                );

        // =====================================================
        // STATUS RENDERER
        // =====================================================

        trainerTable
                .getColumnModel()
                .getColumn(8)
                .setCellRenderer(
                        new StatusRenderer()
                );

        // =====================================================
        // DOUBLE CLICK
        // =====================================================

        trainerTable.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            java.awt.event.MouseEvent e
                    ) {

                        if (
                                e.getClickCount() == 2 &&
                                        trainerTable.getSelectedRow() >= 0
                        ) {

                            viewSelectedTrainer();
                        }
                    }
                }
        );

        JScrollPane scrollPane =
                new JScrollPane(
                        trainerTable
                );

        scrollPane.setBorder(
                new LineBorder(
                        BORDER
                )
        );

        scrollPane.getViewport()
                .setBackground(
                        INPUT
                );

        tableCard.add(
                scrollPane,
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

        buttonPanel.setOpaque(false);

        viewButton =
                new JButton(
                        "View Details"
                );

        approveButton =
                new JButton(
                        "✓  Approve"
                );

        rejectButton =
                new JButton(
                        "✕  Reject"
                );

        styleButton(
                viewButton,
                new Color(
                        71,
                        85,
                        105
                )
        );

        styleButton(
                approveButton,
                SUCCESS
        );

        styleButton(
                rejectButton,
                DANGER
        );

        viewButton.addActionListener(
                e -> viewSelectedTrainer()
        );

        approveButton.addActionListener(
                e -> approveSelectedTrainer()
        );

        rejectButton.addActionListener(
                e -> rejectSelectedTrainer()
        );

        buttonPanel.add(
                viewButton
        );

        buttonPanel.add(
                approveButton
        );

        buttonPanel.add(
                rejectButton
        );

        tableCard.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        centerPanel.add(
                tableCard,
                BorderLayout.CENTER
        );

        add(
                centerPanel,
                BorderLayout.CENTER
        );
    }

    // =========================================================
    // LOAD APPLICATIONS
    // =========================================================

    private void loadApplications() {

        applications =
                trainerDAO.getAllApplications();

        tableModel.setRowCount(0);

        if (applications == null) {

            applications =
                    new java.util.ArrayList<>();
        }

        for (
                TrainerApplication application :
                applications
        ) {

            String appliedDate =
                    application.getApplicationDate()
                            != null
                            ? application
                            .getApplicationDate()
                            .toString()
                            : "-";

            tableModel.addRow(
                    new Object[]{
                            application.getApplicationId(),
                            application.getFullName(),
                            application.getUsername(),
                            application.getEmail(),
                            application.getPhone(),
                            application.getQualification(),
                            application.getSpecialization(),
                            application.getExperienceYears()
                                    + " years",
                            application.getStatus(),
                            appliedDate
                    }
            );
        }

        updateStatistics();
    }

    // =========================================================
    // UPDATE STATISTICS
    // =========================================================

    private void updateStatistics() {

        int total = 0;

        int pending = 0;

        int approved = 0;

        int rejected = 0;

        for (
                TrainerApplication application :
                applications
        ) {

            total++;

            String status =
                    application.getStatus();

            if (
                    status != null &&
                            status.equalsIgnoreCase(
                                    "PENDING"
                            )
            ) {

                pending++;

            } else if (
                    status != null &&
                            status.equalsIgnoreCase(
                                    "ACCEPTED"
                            )
            ) {

                approved++;

            } else if (
                    status != null &&
                            status.equalsIgnoreCase(
                                    "REJECTED"
                            )
            ) {

                rejected++;
            }
        }

        totalLabel.setText(
                String.valueOf(total)
        );

        pendingLabel.setText(
                String.valueOf(pending)
        );

        approvedLabel.setText(
                String.valueOf(approved)
        );

        rejectedLabel.setText(
                String.valueOf(rejected)
        );
    }

    // =========================================================
    // FILTER TABLE
    // =========================================================

    private void filterTable() {

        String search =
                searchField
                        .getText()
                        .trim()
                        .toLowerCase();

        tableModel.setRowCount(0);

        for (
                TrainerApplication application :
                applications
        ) {

            String fullName =
                    safe(application.getFullName());

            String username =
                    safe(application.getUsername());

            String email =
                    safe(application.getEmail());

            String status =
                    safe(application.getStatus());

            if (
                    search.isEmpty() ||
                            fullName.contains(search) ||
                            username.contains(search) ||
                            email.contains(search) ||
                            status.contains(search)
            ) {

                String appliedDate =
                        application.getApplicationDate()
                                != null
                                ? application
                                .getApplicationDate()
                                .toString()
                                : "-";

                tableModel.addRow(
                        new Object[]{
                                application.getApplicationId(),
                                application.getFullName(),
                                application.getUsername(),
                                application.getEmail(),
                                application.getPhone(),
                                application.getQualification(),
                                application.getSpecialization(),
                                application.getExperienceYears()
                                        + " years",
                                application.getStatus(),
                                appliedDate
                        }
                );
            }
        }
    }

    // =========================================================
    // GET SELECTED APPLICATION
    // =========================================================

    private TrainerApplication getSelectedApplication() {

        int selectedRow =
                trainerTable.getSelectedRow();

        if (selectedRow < 0) {

            return null;
        }

        int applicationId =
                (Integer)
                        trainerTable
                                .getValueAt(
                                        selectedRow,
                                        0
                                );

        for (
                TrainerApplication application :
                applications
        ) {

            if (
                    application.getApplicationId()
                            == applicationId
            ) {

                return application;
            }
        }

        return null;
    }

    // =========================================================
    // VIEW DETAILS
    // =========================================================

    private void viewSelectedTrainer() {

        TrainerApplication application =
                getSelectedApplication();

        if (application == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a trainer first.",
                    "No Trainer Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String dateOfBirth =
                application.getDateOfBirth()
                        != null
                        ? application
                        .getDateOfBirth()
                        .toString()
                        : "Not provided";

        String applicationDate =
                application.getApplicationDate()
                        != null
                        ? application
                        .getApplicationDate()
                        .toString()
                        : "Not available";

        String details =
                "<html>" +
                        "<div style='width:500px;'>" +

                        "<h2>Trainer Details</h2>" +

                        "<b>Full Name:</b> "
                        + safe(application.getFullName())
                        + "<br><br>" +

                        "<b>Username:</b> "
                        + safe(application.getUsername())
                        + "<br>" +

                        "<b>Email:</b> "
                        + safe(application.getEmail())
                        + "<br>" +

                        "<b>Phone:</b> "
                        + safe(application.getPhone())
                        + "<br><br>" +

                        "<b>Date of Birth:</b> "
                        + dateOfBirth
                        + "<br>" +

                        "<b>Gender:</b> "
                        + safe(application.getGender())
                        + "<br><br>" +

                        "<b>Address:</b> "
                        + safe(application.getAddress())
                        + "<br><br>" +

                        "<b>Qualification:</b> "
                        + safe(application.getQualification())
                        + "<br>" +

                        "<b>Specialization:</b> "
                        + safe(application.getSpecialization())
                        + "<br>" +

                        "<b>Experience:</b> "
                        + application.getExperienceYears()
                        + " years"
                        + "<br>" +

                        "<b>Certification:</b> "
                        + safe(application.getCertification())
                        + "<br><br>" +

                        "<b>Application Date:</b> "
                        + applicationDate
                        + "<br>" +

                        "<b>Status:</b> "
                        + safe(application.getStatus())
                        + "<br>" +

                        "</div>" +
                        "</html>";

        JOptionPane.showMessageDialog(
                this,
                new JLabel(details),
                "Trainer Details",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // =========================================================
    // APPROVE
    // =========================================================

    private void approveSelectedTrainer() {

        TrainerApplication application =
                getSelectedApplication();

        if (application == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a trainer application.",
                    "No Trainer Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (
                !application.getStatus()
                        .equalsIgnoreCase(
                                "PENDING"
                        )
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Only pending applications can be approved.",
                    "Action Not Allowed",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        "Approve trainer application for\n\n"
                                + application.getFullName()
                                + "?\n\n"
                                + "The trainer will be able to log in after approval.",
                        "Confirm Approval",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

        if (
                result !=
                        JOptionPane.YES_OPTION
        ) {

            return;
        }

        boolean success =
                trainerDAO.approveTrainer(
                        application.getApplicationId()
                );

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Trainer application approved successfully.\n\n"
                            + application.getFullName()
                            + " can now log in.",
                    "Trainer Approved",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadApplications();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to approve this application.\n\n"
                            + "It may have already been reviewed.",
                    "Approval Failed",
                    JOptionPane.ERROR_MESSAGE
            );

            loadApplications();
        }
    }

    // =========================================================
    // REJECT
    // =========================================================

    private void rejectSelectedTrainer() {

        TrainerApplication application =
                getSelectedApplication();

        if (application == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a trainer application.",
                    "No Trainer Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (
                !application.getStatus()
                        .equalsIgnoreCase(
                                "PENDING"
                        )
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Only pending applications can be rejected.",
                    "Action Not Allowed",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        JTextArea reasonArea =
                new JTextArea(
                        5,
                        35
                );

        reasonArea.setLineWrap(true);

        reasonArea.setWrapStyleWord(true);

        reasonArea.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

        reasonArea.setBackground(
                INPUT
        );

        reasonArea.setForeground(
                TEXT
        );

        reasonArea.setCaretColor(
                TEXT
        );

        reasonArea.setBorder(
                new LineBorder(
                        BORDER
                )
        );

        JPanel panel =
                new JPanel(
                        new BorderLayout(
                                0,
                                10
                        )
                );

        panel.setBackground(
                CARD
        );

        JLabel label =
                new JLabel(
                        "<html>Enter the reason for rejecting " +
                                "<b>"
                                + application.getFullName()
                                + "</b>:</html>"
                );

        label.setForeground(
                TEXT
        );

        panel.add(
                label,
                BorderLayout.NORTH
        );

        panel.add(
                new JScrollPane(
                        reasonArea
                ),
                BorderLayout.CENTER
        );

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Reject Trainer Application",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

        if (
                result != JOptionPane.OK_OPTION
        ) {

            return;
        }

        String reason =
                reasonArea
                        .getText()
                        .trim();

        if (reason.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a rejection reason.",
                    "Reason Required",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (reason.length() > 255) {

            JOptionPane.showMessageDialog(
                    this,
                    "Rejection reason cannot exceed 255 characters.",
                    "Invalid Reason",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        boolean success =
                trainerDAO.rejectTrainer(
                        application.getApplicationId(),
                        reason
                );

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Trainer application rejected successfully.",
                    "Trainer Rejected",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadApplications();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to reject this application.\n\n"
                            + "It may have already been reviewed.",
                    "Rejection Failed",
                    JOptionPane.ERROR_MESSAGE
            );

            loadApplications();
        }
    }

    // =========================================================
    // STAT CARD
    // =========================================================

    private JPanel createStatCard(
            String title,
            JLabel value,
            Color accent
    ) {

        JPanel card =
                new JPanel(
                        new BorderLayout()
                );

        card.setBackground(
                CARD
        );

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                15,
                                18,
                                15,
                                18
                        )
                )
        );

        JLabel titleLabel =
                new JLabel(
                        title
                );

        titleLabel.setForeground(
                SECONDARY
        );

        titleLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        11
                )
        );

        value.setForeground(
                accent
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

    // =========================================================
    // STAT VALUE
    // =========================================================

    private JLabel createStatValue() {

        JLabel label =
                new JLabel(
                        "0"
                );

        label.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        25
                )
        );

        label.setBorder(
                new EmptyBorder(
                        7,
                        0,
                        0,
                        0
                )
        );

        return label;
    }

    // =========================================================
    // TEXT FIELD STYLE
    // =========================================================

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
                                BORDER
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
                        12
                )
        );

        button.setFocusPainted(
                false
        );

        button.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        16,
                        10,
                        16
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                ));

        button.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            java.awt.event.MouseEvent e
                    ) {

                        if (button.isEnabled()) {

                            button.setBackground(
                                    PRIMARY_HOVER
                            );
                        }
                    }

                    @Override
                    public void mouseExited(
                            java.awt.event.MouseEvent e
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
    // SAFE STRING
    // =========================================================

    private String safe(
            String value
    ) {

        if (
                value == null ||
                        value.trim().isEmpty()
        ) {

            return "-";
        }

        return value;
    }

    // =========================================================
    // STATUS RENDERER
    // =========================================================

    private static class StatusRenderer
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

            setHorizontalAlignment(
                    SwingConstants.CENTER
            );

            if (isSelected) {

                setBackground(
                        new Color(
                                30,
                                64,
                                82
                        )
                );

                setForeground(
                        TEXT
                );

                return component;
            }

            setBackground(
                    INPUT
            );

            String status =
                    value != null
                            ? value.toString()
                            : "";

            if (
                    status.equalsIgnoreCase(
                            "PENDING"
                    )
            ) {

                setForeground(
                        WARNING
                );

            } else if (
                    status.equalsIgnoreCase(
                            "ACCEPTED"
                    )
            ) {

                setForeground(
                        SUCCESS
                );

            } else if (
                    status.equalsIgnoreCase(
                            "REJECTED"
                    )
            ) {

                setForeground(
                        DANGER
                );

            } else {

                setForeground(
                        TEXT
                );
            }

            return component;
        }
    }
}