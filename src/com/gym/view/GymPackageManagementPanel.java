package com.gym.view;

import com.gym.dao.GymPackageDAO;
import com.gym.model.GymPackage;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class GymPackageManagementPanel extends JPanel {

    // =========================
    // COLORS
    // =========================
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

    // =========================
    // DAO
    // =========================
    private final GymPackageDAO packageDAO =
            new GymPackageDAO();

    // =========================
    // COMPONENTS
    // =========================
    private JTable packageTable;

    private DefaultTableModel tableModel;

    private JTextField searchField;

    private JLabel totalLabel;

    private JLabel activeLabel;

    private JLabel inactiveLabel;

    // =========================
    // CONSTRUCTOR
    // =========================
    public GymPackageManagementPanel() {

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

        buildUI();

        loadPackages();
    }

    // =========================
    // BUILD UI
    // =========================
    private void buildUI() {

        add(
                createHeader(),
                BorderLayout.NORTH
        );

        add(
                createCenter(),
                BorderLayout.CENTER
        );
    }

    // =========================
    // HEADER
    // =========================
    private JPanel createHeader() {

        JPanel header =
                new JPanel(
                        new BorderLayout()
                );

        header.setOpaque(false);

        // =========================
        // TITLE
        // =========================

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
                new JLabel(
                        "Gym Packages"
                );

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
                        "Create and manage membership packages"
                );

        subtitle.setForeground(
                SECONDARY
        );

        subtitle.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        13
                )
        );

        titlePanel.add(title);

        titlePanel.add(
                Box.createVerticalStrut(5)
        );

        titlePanel.add(subtitle);

        header.add(
                titlePanel,
                BorderLayout.WEST
        );

        // =========================
        // ADD BUTTON
        // =========================

        JButton addButton =
                createPrimaryButton(
                        "+  Add Package"
                );

        addButton.addActionListener(
                e -> showPackageDialog(null)
        );

        header.add(
                addButton,
                BorderLayout.EAST
        );

        return header;
    }

    // =========================
    // CENTER
    // =========================
    private JPanel createCenter() {

        JPanel center =
                new JPanel(
                        new BorderLayout(
                                0,
                                18
                        )
                );

        center.setOpaque(false);

        center.setBorder(
                new EmptyBorder(
                        25,
                        0,
                        0,
                        0
                )
        );

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

    // =========================
    // STATS
    // =========================
    private JPanel createStats() {

        JPanel stats =
                new JPanel(
                        new GridLayout(
                                1,
                                3,
                                15,
                                0
                        )
                );

        stats.setOpaque(false);

        JPanel totalCard =
                createStatCard(
                        "TOTAL PACKAGES",
                        "0",
                        "All packages",
                        PRIMARY
                );

        JPanel activeCard =
                createStatCard(
                        "ACTIVE",
                        "0",
                        "Available for members",
                        SUCCESS
                );

        JPanel inactiveCard =
                createStatCard(
                        "INACTIVE",
                        "0",
                        "Currently disabled",
                        WARNING
                );

        totalLabel =
                (JLabel) totalCard.getClientProperty(
                        "valueLabel"
                );

        activeLabel =
                (JLabel) activeCard.getClientProperty(
                        "valueLabel"
                );

        inactiveLabel =
                (JLabel) inactiveCard.getClientProperty(
                        "valueLabel"
                );

        stats.add(totalCard);

        stats.add(activeCard);

        stats.add(inactiveCard);

        return stats;
    }

    // =========================
    // STAT CARD
    // =========================
    private JPanel createStatCard(
            String title,
            String value,
            String description,
            Color accent
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

        JLabel icon =
                new JLabel("●");

        icon.setForeground(
                accent
        );

        icon.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        18
                )
        );

        JPanel top =
                new JPanel(
                        new BorderLayout()
                );

        top.setOpaque(false);

        top.add(
                titleLabel,
                BorderLayout.WEST
        );

        top.add(
                icon,
                BorderLayout.EAST
        );

        JLabel valueLabel =
                new JLabel(value);

        valueLabel.setForeground(TEXT);

        valueLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        27
                )
        );

        JLabel descriptionLabel =
                new JLabel(description);

        descriptionLabel.setForeground(
                SECONDARY
        );

        descriptionLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        12
                )
        );

        JPanel bottom =
                new JPanel();

        bottom.setOpaque(false);

        bottom.setLayout(
                new BoxLayout(
                        bottom,
                        BoxLayout.Y_AXIS
                )
        );

        bottom.add(
                Box.createVerticalStrut(10)
        );

        bottom.add(valueLabel);

        bottom.add(
                Box.createVerticalStrut(3)
        );

        bottom.add(descriptionLabel);

        card.add(
                top,
                BorderLayout.NORTH
        );

        card.add(
                bottom,
                BorderLayout.CENTER
        );

        card.putClientProperty(
                "valueLabel",
                valueLabel
        );

        return card;
    }

    // =========================
    // TABLE CARD
    // =========================
    private JPanel createTableCard() {

        JPanel card =
                new JPanel(
                        new BorderLayout(
                                0,
                                15
                        )
                );

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

        // =========================
        // TOOLBAR
        // =========================

        JPanel toolbar =
                new JPanel(
                        new BorderLayout(
                                10,
                                0
                        )
                );

        toolbar.setOpaque(false);

        JPanel searchPanel =
                new JPanel(
                        new BorderLayout()
                );

        searchPanel.setOpaque(false);

        searchField =
                new JTextField();

        searchField.setPreferredSize(
                new Dimension(
                        350,
                        38
                )
        );

        searchField.setBackground(
                new Color(
                        30,
                        34,
                        46
                )
        );

        searchField.setForeground(TEXT);

        searchField.setCaretColor(
                PRIMARY
        );

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

        searchField.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        13
                )
        );

        searchField.setToolTipText(
                "Search by package name, description or features"
        );

        searchField.addActionListener(
                e -> searchPackages()
        );

        searchPanel.add(
                searchField,
                BorderLayout.WEST
        );

        toolbar.add(
                searchPanel,
                BorderLayout.WEST
        );

        JPanel buttons =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                8,
                                0
                        )
                );

        buttons.setOpaque(false);

        JButton searchButton =
                createSecondaryButton(
                        "Search"
                );

        searchButton.addActionListener(
                e -> searchPackages()
        );

        JButton refreshButton =
                createSecondaryButton(
                        "Refresh"
                );

        refreshButton.addActionListener(
                e -> {

                    searchField.setText("");

                    loadPackages();
                }
        );

        JButton editButton =
                createSecondaryButton(
                        "Edit"
                );

        editButton.addActionListener(
                e -> editSelectedPackage()
        );

        JButton statusButton =
                createSecondaryButton(
                        "Activate / Deactivate"
                );

        statusButton.addActionListener(
                e -> toggleSelectedPackage()
        );

        JButton deleteButton =
                createDangerButton(
                        "Delete"
                );

        deleteButton.addActionListener(
                e -> deleteSelectedPackage()
        );

        buttons.add(searchButton);

        buttons.add(refreshButton);

        buttons.add(editButton);

        buttons.add(statusButton);

        buttons.add(deleteButton);

        toolbar.add(
                buttons,
                BorderLayout.EAST
        );

        card.add(
                toolbar,
                BorderLayout.NORTH
        );

        // =========================
        // TABLE
        // =========================

        String[] columns =
                {
                        "ID",
                        "Package Name",
                        "Duration",
                        "Price",
                        "Features",
                        "Status",
                        "Created Date"
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

        packageTable =
                new JTable(tableModel);

        packageTable.setRowHeight(42);

        packageTable.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        13
                )
        );

        packageTable.setBackground(CARD);

        packageTable.setForeground(TEXT);

        packageTable.setSelectionBackground(
                new Color(
                        51,
                        65,
                        85
                )
        );

        packageTable.setSelectionForeground(
                TEXT
        );

        packageTable.setShowGrid(false);

        packageTable.setFillsViewportHeight(true);

        packageTable.setIntercellSpacing(
                new Dimension(
                        0,
                        0
                )
        );

        packageTable.setAutoCreateRowSorter(
                true
        );

        packageTable.getTableHeader()
                .setFont(
                        new Font(
                                "SansSerif",
                                Font.BOLD,
                                12
                        )
                );

        packageTable.getTableHeader()
                .setBackground(
                        new Color(
                                30,
                                34,
                                46
                        )
                );

        packageTable.getTableHeader()
                .setForeground(
                        SECONDARY
                );

        packageTable.getTableHeader()
                .setPreferredSize(
                        new Dimension(
                                0,
                                40
                        )
                );

        packageTable.getColumnModel()
                .getColumn(0)
                .setPreferredWidth(50);

        packageTable.getColumnModel()
                .getColumn(1)
                .setPreferredWidth(170);

        packageTable.getColumnModel()
                .getColumn(2)
                .setPreferredWidth(100);

        packageTable.getColumnModel()
                .getColumn(3)
                .setPreferredWidth(100);

        packageTable.getColumnModel()
                .getColumn(4)
                .setPreferredWidth(300);

        packageTable.getColumnModel()
                .getColumn(5)
                .setPreferredWidth(100);

        packageTable.getColumnModel()
                .getColumn(6)
                .setPreferredWidth(160);

        // =========================
        // STATUS RENDERER
        // =========================

        packageTable.getColumnModel()
                .getColumn(5)
                .setCellRenderer(
                        new StatusRenderer()
                );

        // =========================
        // DOUBLE CLICK
        // =========================

        packageTable.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            MouseEvent e
                    ) {

                        if (e.getClickCount() == 2) {

                            editSelectedPackage();
                        }
                    }
                }
        );

        JScrollPane scrollPane =
                new JScrollPane(
                        packageTable
                );

        scrollPane.setBorder(
                BorderFactory.createEmptyBorder()
        );

        scrollPane.getViewport()
                .setBackground(CARD);

        card.add(
                scrollPane,
                BorderLayout.CENTER
        );

        return card;
    }

    // =========================
    // LOAD PACKAGES
    // =========================
    private void loadPackages() {

        List<GymPackage> packages =
                packageDAO.getAllPackages();

        tableModel.setRowCount(0);

        int active = 0;

        int inactive = 0;

        for (
                GymPackage gymPackage :
                packages
        ) {

            if (
                    "ACTIVE".equalsIgnoreCase(
                            gymPackage.getStatus()
                    )
            ) {

                active++;

            } else {

                inactive++;
            }

            tableModel.addRow(
                    new Object[]
                            {
                                    gymPackage.getPackageId(),

                                    gymPackage.getPackageName(),

                                    gymPackage.getDuration()
                                            + " "
                                            + gymPackage.getDurationUnit(),

                                    "₹ "
                                            + String.format(
                                            "%.2f",
                                            gymPackage.getPrice()
                                    ),

                                    gymPackage.getFeatures(),

                                    gymPackage.getStatus(),

                                    gymPackage.getCreatedAt()
                            }
            );
        }

        if (totalLabel != null) {

            totalLabel.setText(
                    String.valueOf(
                            packages.size()
                    )
            );
        }

        if (activeLabel != null) {

            activeLabel.setText(
                    String.valueOf(active)
            );
        }

        if (inactiveLabel != null) {

            inactiveLabel.setText(
                    String.valueOf(inactive)
            );
        }
    }

    // =========================
    // SEARCH
    // =========================
    private void searchPackages() {

        String keyword =
                searchField.getText()
                        .trim();

        if (keyword.isEmpty()) {

            loadPackages();

            return;
        }

        List<GymPackage> packages =
                packageDAO.searchPackages(
                        keyword
                );

        tableModel.setRowCount(0);

        for (
                GymPackage gymPackage :
                packages
        ) {

            tableModel.addRow(
                    new Object[]
                            {
                                    gymPackage.getPackageId(),

                                    gymPackage.getPackageName(),

                                    gymPackage.getDuration()
                                            + " "
                                            + gymPackage.getDurationUnit(),

                                    "₹ "
                                            + String.format(
                                            "%.2f",
                                            gymPackage.getPrice()
                                    ),

                                    gymPackage.getFeatures(),

                                    gymPackage.getStatus(),

                                    gymPackage.getCreatedAt()
                            }
            );
        }
    }

    // =========================
    // ADD / EDIT DIALOG
    // =========================
    private void showPackageDialog(
            GymPackage existingPackage
    ) {

        boolean editing =
                existingPackage != null;

        JDialog dialog =
                new JDialog(
                        SwingUtilities.getWindowAncestor(this),
                        editing
                                ? "Edit Gym Package"
                                : "Add Gym Package",
                        Dialog.ModalityType.APPLICATION_MODAL
                );

        dialog.setSize(
                560,
                650
        );

        dialog.setResizable(false);

        dialog.setLocationRelativeTo(this);

        JPanel main =
                new JPanel(
                        new BorderLayout()
                );

        main.setBackground(BACKGROUND);

        main.setBorder(
                new EmptyBorder(
                        25,
                        25,
                        25,
                        25
                )
        );

        // =========================
        // TITLE
        // =========================

        JPanel header =
                new JPanel();

        header.setOpaque(false);

        header.setLayout(
                new BoxLayout(
                        header,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel title =
                new JLabel(
                        editing
                                ? "Edit Gym Package"
                                : "Create New Gym Package"
                );

        title.setForeground(TEXT);

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        22
                )
        );

        JLabel subtitle =
                new JLabel(
                        editing
                                ? "Update package information"
                                : "Add a membership package for your gym"
                );

        subtitle.setForeground(
                SECONDARY
        );

        subtitle.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        12
                )
        );

        header.add(title);

        header.add(
                Box.createVerticalStrut(5)
        );

        header.add(subtitle);

        main.add(
                header,
                BorderLayout.NORTH
        );

        // =========================
        // FORM
        // =========================

        JPanel form =
                new JPanel(
                        new GridBagLayout()
                );

        form.setOpaque(false);

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(
                        7,
                        0,
                        7,
                        0
                );

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.weightx = 1;

        gbc.gridx = 0;

        gbc.gridy = 0;

        // Package name
        JLabel nameLabel =
                createFormLabel(
                        "Package Name *"
                );

        JTextField nameField =
                createTextField();

        if (editing) {

            nameField.setText(
                    existingPackage.getPackageName()
            );
        }

        addFormRow(
                form,
                gbc,
                nameLabel,
                nameField
        );

        // Description
        JLabel descriptionLabel =
                createFormLabel(
                        "Description"
                );

        JTextArea descriptionArea =
                new JTextArea(
                        3,
                        20
                );

        styleTextArea(
                descriptionArea
        );

        if (editing) {

            descriptionArea.setText(
                    existingPackage.getDescription()
                            == null
                            ? ""
                            : existingPackage.getDescription()
            );
        }

        JScrollPane descriptionScroll =
                new JScrollPane(
                        descriptionArea
                );

        descriptionScroll.setBorder(
                BorderFactory.createLineBorder(
                        BORDER
                )
        );

        addFormRow(
                form,
                gbc,
                descriptionLabel,
                descriptionScroll
        );

        // Duration
        JLabel durationLabel =
                createFormLabel(
                        "Duration *"
                );

        JPanel durationPanel =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                10,
                                0
                        )
                );

        durationPanel.setOpaque(false);

        JTextField durationField =
                createTextField();

        JComboBox<String> durationUnit =
                new JComboBox<>(
                        new String[]
                                {
                                        "DAY",
                                        "MONTH",
                                        "YEAR"
                                }
                );

        styleComboBox(
                durationUnit
        );

        if (editing) {

            durationField.setText(
                    String.valueOf(
                            existingPackage.getDuration()
                    )
            );

            durationUnit.setSelectedItem(
                    existingPackage.getDurationUnit()
            );
        }

        durationPanel.add(
                durationField
        );

        durationPanel.add(
                durationUnit
        );

        addFormRow(
                form,
                gbc,
                durationLabel,
                durationPanel
        );

        // Price
        JLabel priceLabel =
                createFormLabel(
                        "Price (₹) *"
                );

        JTextField priceField =
                createTextField();

        if (editing) {

            priceField.setText(
                    String.valueOf(
                            existingPackage.getPrice()
                    )
            );
        }

        addFormRow(
                form,
                gbc,
                priceLabel,
                priceField
        );

        // Features
        JLabel featuresLabel =
                createFormLabel(
                        "Features"
                );

        JTextArea featuresArea =
                new JTextArea(
                        4,
                        20
                );

        styleTextArea(
                featuresArea
        );

        if (editing) {

            featuresArea.setText(
                    existingPackage.getFeatures()
                            == null
                            ? ""
                            : existingPackage.getFeatures()
            );
        }

        JScrollPane featuresScroll =
                new JScrollPane(
                        featuresArea
                );

        featuresScroll.setBorder(
                BorderFactory.createLineBorder(
                        BORDER
                )
        );

        addFormRow(
                form,
                gbc,
                featuresLabel,
                featuresScroll
        );

        // Status
        JLabel statusLabel =
                createFormLabel(
                        "Status *"
                );

        JComboBox<String> statusCombo =
                new JComboBox<>(
                        new String[]
                                {
                                        "ACTIVE",
                                        "INACTIVE"
                                }
                );

        styleComboBox(
                statusCombo
        );

        if (editing) {

            statusCombo.setSelectedItem(
                    existingPackage.getStatus()
            );
        }

        addFormRow(
                form,
                gbc,
                statusLabel,
                statusCombo
        );

        main.add(
                form,
                BorderLayout.CENTER
        );

        // =========================
        // BUTTONS
        // =========================

        JPanel buttons =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                10,
                                0
                        )
                );

        buttons.setOpaque(false);

        JButton cancelButton =
                createSecondaryButton(
                        "Cancel"
                );

        cancelButton.addActionListener(
                e -> dialog.dispose()
        );

        JButton saveButton =
                createPrimaryButton(
                        editing
                                ? "Update Package"
                                : "Save Package"
                );

        saveButton.addActionListener(
                e -> {

                    String name =
                            nameField.getText()
                                    .trim();

                    String description =
                            descriptionArea
                                    .getText()
                                    .trim();

                    String durationText =
                            durationField.getText()
                                    .trim();

                    String unit =
                            String.valueOf(
                                    durationUnit
                                            .getSelectedItem()
                            );

                    String priceText =
                            priceField.getText()
                                    .trim();

                    String features =
                            featuresArea
                                    .getText()
                                    .trim();

                    String status =
                            String.valueOf(
                                    statusCombo
                                            .getSelectedItem()
                            );

                    // =========================
                    // VALIDATION
                    // =========================

                    if (name.isEmpty()) {

                        showError(
                                dialog,
                                "Package name is required."
                        );

                        return;
                    }

                    if (name.length() > 100) {

                        showError(
                                dialog,
                                "Package name must be 100 characters or less."
                        );

                        return;
                    }

                    if (durationText.isEmpty()) {

                        showError(
                                dialog,
                                "Duration is required."
                        );

                        return;
                    }

                    int duration;

                    try {

                        duration =
                                Integer.parseInt(
                                        durationText
                                );

                    } catch (NumberFormatException ex) {

                        showError(
                                dialog,
                                "Duration must be a valid number."
                        );

                        return;
                    }

                    if (duration <= 0) {

                        showError(
                                dialog,
                                "Duration must be greater than zero."
                        );

                        return;
                    }

                    double price;

                    try {

                        price =
                                Double.parseDouble(
                                        priceText
                                );

                    } catch (NumberFormatException ex) {

                        showError(
                                dialog,
                                "Price must be a valid number."
                        );

                        return;
                    }

                    if (price <= 0) {

                        showError(
                                dialog,
                                "Price must be greater than zero."
                        );

                        return;
                    }

                    if (description.length() > 65535) {

                        showError(
                                dialog,
                                "Description is too long."
                        );

                        return;
                    }

                    if (features.length() > 65535) {

                        showError(
                                dialog,
                                "Features text is too long."
                        );

                        return;
                    }

                    // =========================
                    // CREATE / UPDATE
                    // =========================

                    if (editing) {

                        existingPackage.setPackageName(
                                name
                        );

                        existingPackage.setDescription(
                                description
                        );

                        existingPackage.setDuration(
                                duration
                        );

                        existingPackage.setDurationUnit(
                                unit
                        );

                        existingPackage.setPrice(
                                price
                        );

                        existingPackage.setFeatures(
                                features
                        );

                        existingPackage.setStatus(
                                status
                        );

                        boolean success =
                                packageDAO.updatePackage(
                                        existingPackage
                                );

                        if (success) {

                            JOptionPane.showMessageDialog(
                                    dialog,
                                    "Package updated successfully.",
                                    "Success",
                                    JOptionPane.INFORMATION_MESSAGE
                            );

                            dialog.dispose();

                            loadPackages();

                        } else {

                            showError(
                                    dialog,
                                    "Unable to update package."
                            );
                        }

                    } else {

                        GymPackage newPackage =
                                new GymPackage(
                                        name,
                                        description,
                                        duration,
                                        unit,
                                        price,
                                        features,
                                        status
                                );

                        boolean success =
                                packageDAO.addPackage(
                                        newPackage
                                );

                        if (success) {

                            JOptionPane.showMessageDialog(
                                    dialog,
                                    "Package added successfully.",
                                    "Success",
                                    JOptionPane.INFORMATION_MESSAGE
                            );

                            dialog.dispose();

                            loadPackages();

                        } else {

                            showError(
                                    dialog,
                                    "Unable to add package."
                            );
                        }
                    }
                }
        );

        buttons.add(cancelButton);

        buttons.add(saveButton);

        main.add(
                buttons,
                BorderLayout.SOUTH
        );

        dialog.setContentPane(main);

        dialog.setVisible(true);
    }

    // =========================
    // EDIT SELECTED
    // =========================
    private void editSelectedPackage() {

        int selectedRow =
                packageTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a package first.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int modelRow =
                packageTable.convertRowIndexToModel(
                        selectedRow
                );

        int packageId =
                Integer.parseInt(
                        tableModel.getValueAt(
                                modelRow,
                                0
                        ).toString()
                );

        GymPackage selectedPackage =
                findPackageById(packageId);

        if (selectedPackage == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Package could not be found.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        showPackageDialog(
                selectedPackage
        );
    }

    // =========================
    // FIND PACKAGE
    // =========================
    private GymPackage findPackageById(
            int packageId
    ) {

        List<GymPackage> packages =
                packageDAO.getAllPackages();

        for (
                GymPackage gymPackage :
                packages
        ) {

            if (
                    gymPackage.getPackageId()
                            == packageId
            ) {

                return gymPackage;
            }
        }

        return null;
    }

    // =========================
    // TOGGLE STATUS
    // =========================
    private void toggleSelectedPackage() {

        int selectedRow =
                packageTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a package first.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int modelRow =
                packageTable.convertRowIndexToModel(
                        selectedRow
                );

        int packageId =
                Integer.parseInt(
                        tableModel.getValueAt(
                                modelRow,
                                0
                        ).toString()
                );

        String currentStatus =
                tableModel.getValueAt(
                        modelRow,
                        5
                ).toString();

        String newStatus;

        if (
                "ACTIVE".equalsIgnoreCase(
                        currentStatus
                )
        ) {

            newStatus = "INACTIVE";

        } else {

            newStatus = "ACTIVE";
        }

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Change package status to "
                                + newStatus
                                + "?",
                        "Confirm Status Change",
                        JOptionPane.YES_NO_OPTION
                );

        if (
                choice
                        != JOptionPane.YES_OPTION
        ) {

            return;
        }

        boolean success =
                packageDAO.updateStatus(
                        packageId,
                        newStatus
                );

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Package status updated successfully.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadPackages();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to update package status.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================
    // DELETE
    // =========================
    private void deleteSelectedPackage() {

        int selectedRow =
                packageTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a package first.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int modelRow =
                packageTable.convertRowIndexToModel(
                        selectedRow
                );

        int packageId =
                Integer.parseInt(
                        tableModel.getValueAt(
                                modelRow,
                                0
                        ).toString()
                );

        String packageName =
                tableModel.getValueAt(
                        modelRow,
                        1
                ).toString();

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete\n"
                                + "\""
                                + packageName
                                + "\"?\n\n"
                                + "If this package is already used\n"
                                + "by a subscription, the database\n"
                                + "will prevent deletion.",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

        if (
                choice
                        != JOptionPane.YES_OPTION
        ) {

            return;
        }

        boolean success =
                packageDAO.deletePackage(
                        packageId
                );

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Package deleted successfully.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadPackages();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Package could not be deleted.\n\n"
                            + "It may already be used by an existing subscription.\n"
                            + "Try setting it to INACTIVE instead.",
                    "Delete Failed",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    // =========================
    // FORM ROW
    // =========================
    private void addFormRow(
            JPanel panel,
            GridBagConstraints gbc,
            JLabel label,
            Component component
    ) {

        gbc.gridy++;

        panel.add(
                label,
                gbc
        );

        gbc.gridy++;

        panel.add(
                component,
                gbc
        );
    }

    // =========================
    // FORM LABEL
    // =========================
    private JLabel createFormLabel(
            String text
    ) {

        JLabel label =
                new JLabel(text);

        label.setForeground(TEXT);

        label.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        12
                )
        );

        return label;
    }

    // =========================
    // TEXT FIELD
    // =========================
    private JTextField createTextField() {

        JTextField field =
                new JTextField();

        field.setPreferredSize(
                new Dimension(
                        100,
                        38
                )
        );

        field.setBackground(
                new Color(
                        30,
                        34,
                        46
                )
        );

        field.setForeground(TEXT);

        field.setCaretColor(
                PRIMARY
        );

        field.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        13
                )
        );

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                0,
                                10,
                                0,
                                10
                        )
                )
        );

        return field;
    }

    // =========================
    // TEXT AREA
    // =========================
    private void styleTextArea(
            JTextArea area
    ) {

        area.setBackground(
                new Color(
                        30,
                        34,
                        46
                )
        );

        area.setForeground(TEXT);

        area.setCaretColor(
                PRIMARY
        );

        area.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        13
                )
        );

        area.setLineWrap(true);

        area.setWrapStyleWord(true);

        area.setBorder(
                new EmptyBorder(
                        8,
                        10,
                        8,
                        10
                )
        );
    }

    // =========================
    // COMBO BOX
    // =========================
    private void styleComboBox(
            JComboBox<String> combo
    ) {

        combo.setPreferredSize(
                new Dimension(
                        100,
                        38
                )
        );

        combo.setBackground(
                new Color(
                        30,
                        34,
                        46
                )
        );

        combo.setForeground(TEXT);

        combo.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        13
                )
        );
    }

    // =========================
    // PRIMARY BUTTON
    // =========================
    private JButton createPrimaryButton(
            String text
    ) {

        JButton button =
                new JButton(text);

        button.setFocusPainted(false);

        button.setBorderPainted(false);

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
                        12
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setBorder(
                new EmptyBorder(
                        10,
                        18,
                        10,
                        18
                )
        );

        button.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            MouseEvent e
                    ) {

                        button.setBackground(
                                PRIMARY_HOVER
                        );
                    }

                    @Override
                    public void mouseExited(
                            MouseEvent e
                    ) {

                        button.setBackground(
                                PRIMARY
                        );
                    }
                }
        );

        return button;
    }

    // =========================
    // SECONDARY BUTTON
    // =========================
    private JButton createSecondaryButton(
            String text
    ) {

        JButton button =
                new JButton(text);

        button.setFocusPainted(false);

        button.setBackground(CARD);

        button.setForeground(TEXT);

        button.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        12
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
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

        return button;
    }

    // =========================
    // DANGER BUTTON
    // =========================
    private JButton createDangerButton(
            String text
    ) {

        JButton button =
                createSecondaryButton(text);

        button.setForeground(
                DANGER
        );

        button.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                DANGER
                        ),
                        new EmptyBorder(
                                8,
                                12,
                                8,
                                12
                        )
                )
        );

        return button;
    }

    // =========================
    // ERROR MESSAGE
    // =========================
    private void showError(
            Component parent,
            String message
    ) {

        JOptionPane.showMessageDialog(
                parent,
                message,
                "Validation Error",
                JOptionPane.WARNING_MESSAGE
        );
    }

    // =========================
    // STATUS RENDERER
    // =========================
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

            JLabel label =
                    (JLabel)
                            super.getTableCellRendererComponent(
                                    table,
                                    value,
                                    isSelected,
                                    hasFocus,
                                    row,
                                    column
                            );

            label.setHorizontalAlignment(
                    SwingConstants.CENTER
            );

            label.setFont(
                    new Font(
                            "SansSerif",
                            Font.BOLD,
                            11
                    )
            );

            if (!isSelected) {

                if (
                        "ACTIVE".equalsIgnoreCase(
                                String.valueOf(value)
                        )
                ) {

                    label.setForeground(
                            SUCCESS
                    );

                } else {

                    label.setForeground(
                            WARNING
                    );
                }
            }

            return label;
        }
    }
}