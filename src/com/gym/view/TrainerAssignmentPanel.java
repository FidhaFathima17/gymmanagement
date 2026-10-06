package com.gym.view;

import com.gym.config.DatabaseConnection;
import com.gym.dao.MemberTrainerDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;

public class TrainerAssignmentPanel extends JPanel {

    private JComboBox<MemberItem> memberCombo;
    private JComboBox<TrainerItem> trainerCombo;

    private JButton assignButton;
    private JButton refreshButton;

    public TrainerAssignmentPanel() {

        setLayout(new BorderLayout());

        setBackground(
                new Color(15, 23, 42)
        );

        buildUI();

        loadMembers();

        loadTrainers();
    }

    // =========================================================
    // UI
    // =========================================================

    private void buildUI() {

        JPanel header =
                new JPanel(
                        new BorderLayout()
                );

        header.setBackground(
                new Color(17, 24, 39)
        );

        header.setBorder(
                new EmptyBorder(
                        20,
                        25,
                        20,
                        25
                )
        );

        JLabel title =
                new JLabel(
                        "👨‍🏫 Trainer Assignment"
                );

        title.setForeground(
                Color.WHITE
        );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        24
                )
        );

        JLabel subtitle =
                new JLabel(
                        "Assign an approved trainer to a gym member"
                );

        subtitle.setForeground(
                new Color(
                        148,
                        163,
                        184
                )
        );

        JPanel heading =
                new JPanel();

        heading.setOpaque(false);

        heading.setLayout(
                new BoxLayout(
                        heading,
                        BoxLayout.Y_AXIS
                )
        );

        heading.add(title);

        heading.add(
                Box.createVerticalStrut(5)
        );

        heading.add(subtitle);

        header.add(
                heading,
                BorderLayout.WEST
        );

        add(
                header,
                BorderLayout.NORTH
        );

        // =====================================================
        // FORM
        // =====================================================

        JPanel form =
                new JPanel(
                        new GridBagLayout()
                );

        form.setBackground(
                new Color(15, 23, 42)
        );

        form.setBorder(
                new EmptyBorder(
                        50,
                        100,
                        50,
                        100
                )
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(
                        12,
                        12,
                        12,
                        12
                );

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.weightx = 1;

        JLabel memberLabel =
                createLabel(
                        "Select Member"
                );

        gbc.gridx = 0;
        gbc.gridy = 0;

        form.add(
                memberLabel,
                gbc
        );

        memberCombo =
                new JComboBox<>();

        styleComboBox(
                memberCombo
        );

        gbc.gridx = 1;

        form.add(
                memberCombo,
                gbc
        );

        JLabel trainerLabel =
                createLabel(
                        "Select Trainer"
                );

        gbc.gridx = 0;
        gbc.gridy = 1;

        form.add(
                trainerLabel,
                gbc
        );

        trainerCombo =
                new JComboBox<>();

        styleComboBox(
                trainerCombo
        );

        gbc.gridx = 1;

        form.add(
                trainerCombo,
                gbc
        );

        // =====================================================
        // BUTTONS
        // =====================================================

        assignButton =
                new JButton(
                        "✓ ASSIGN TRAINER"
                );

        refreshButton =
                new JButton(
                        "↻ REFRESH"
                );

        styleButton(
                assignButton,
                new Color(
                        16,
                        185,
                        129
                )
        );

        styleButton(
                refreshButton,
                new Color(
                        37,
                        99,
                        235
                )
        );

        JPanel buttons =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                15,
                                10
                        )
                );

        buttons.setOpaque(false);

        buttons.add(
                assignButton
        );

        buttons.add(
                refreshButton
        );

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;

        form.add(
                buttons,
                gbc
        );

        add(
                form,
                BorderLayout.CENTER
        );

        // =====================================================
        // EVENTS
        // =====================================================

        assignButton.addActionListener(
                e -> assignTrainer()
        );

        refreshButton.addActionListener(
                e -> {

                    loadMembers();
                    loadTrainers();
                }
        );
    }

    // =========================================================
    // LOAD MEMBERS
    // =========================================================

    private void loadMembers() {

        memberCombo.removeAllItems();

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

                memberCombo.addItem(
                        new MemberItem(
                                rs.getInt(
                                        "member_id"
                                ),
                                rs.getString(
                                        "full_name"
                                )
                        )
                );
            }

        } catch (SQLException e) {

            e.printStackTrace();

            showError(
                    "Unable to load members.\n"
                            + e.getMessage()
            );
        }
    }

    // =========================================================
    // LOAD APPROVED TRAINERS
    // =========================================================

    private void loadTrainers() {

        trainerCombo.removeAllItems();

        String sql =
                "SELECT tp.trainer_id, " +
                        "tp.full_name, " +
                        "tp.specialization " +
                        "FROM trainer_profiles tp " +
                        "JOIN users u " +
                        "ON tp.user_id = u.user_id " +
                        "WHERE u.role = 'TRAINER' " +
                        "AND u.status = 'ACTIVE' " +
                        "ORDER BY tp.full_name";

        try (
                Connection conn =
                        DatabaseConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql);

                ResultSet rs =
                        stmt.executeQuery()
        ) {

            while (rs.next()) {

                trainerCombo.addItem(
                        new TrainerItem(
                                rs.getInt(
                                        "trainer_id"
                                ),
                                rs.getString(
                                        "full_name"
                                ),
                                rs.getString(
                                        "specialization"
                                )
                        )
                );
            }

        } catch (SQLException e) {

            e.printStackTrace();

            showError(
                    "Unable to load trainers.\n"
                            + e.getMessage()
            );
        }
    }

    // =========================================================
    // ASSIGN
    // =========================================================

    private void assignTrainer() {

        MemberItem member =
                (MemberItem)
                        memberCombo.getSelectedItem();

        TrainerItem trainer =
                (TrainerItem)
                        trainerCombo.getSelectedItem();

        if (member == null) {

            showError(
                    "Please select a member."
            );

            return;
        }

        if (trainer == null) {

            showError(
                    "Please select an approved trainer."
            );

            return;
        }

        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Assign trainer?\n\n"
                                + "Member: "
                                + member.name
                                + "\n"
                                + "Trainer: "
                                + trainer.name,
                        "Confirm Assignment",
                        JOptionPane.YES_NO_OPTION
                );

        if (
                confirm !=
                        JOptionPane.YES_OPTION
        ) {
            return;
        }

        MemberTrainerDAO dao =
                new MemberTrainerDAO();

        boolean success =
                dao.assignTrainer(
                        member.id,
                        trainer.id
                );

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Trainer assigned successfully!\n\n"
                            + "Member: "
                            + member.name
                            + "\n"
                            + "Trainer: "
                            + trainer.name,
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } else {

            showError(
                    "Unable to assign trainer."
            );
        }
    }

    // =========================================================
    // MEMBER ITEM
    // =========================================================

    private static class MemberItem {

        private final int id;
        private final String name;

        public MemberItem(
                int id,
                String name
        ) {

            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {

            return name
                    + " (ID: "
                    + id
                    + ")";
        }
    }

    // =========================================================
    // TRAINER ITEM
    // =========================================================

    private static class TrainerItem {

        private final int id;
        private final String name;
        private final String specialization;

        public TrainerItem(
                int id,
                String name,
                String specialization
        ) {

            this.id = id;
            this.name = name;
            this.specialization =
                    specialization;
        }

        @Override
        public String toString() {

            if (
                    specialization == null ||
                            specialization.trim().isEmpty()
            ) {

                return name;
            }

            return name
                    + " - "
                    + specialization;
        }
    }

    // =========================================================
    // STYLING
    // =========================================================

    private JLabel createLabel(
            String text
    ) {

        JLabel label =
                new JLabel(text);

        label.setForeground(
                Color.WHITE
        );

        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        15
                )
        );

        return label;
    }

    private void styleComboBox(
            JComboBox<?> combo
    ) {

        combo.setBackground(
                new Color(
                        30,
                        41,
                        59
                )
        );

        combo.setForeground(
                Color.WHITE
        );

        combo.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        combo.setPreferredSize(
                new Dimension(
                        350,
                        45
                )
        );
    }

    private void styleButton(
            JButton button,
            Color color
    ) {

        button.setBackground(color);

        button.setForeground(
                Color.WHITE
        );

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        button.setFocusPainted(false);

        button.setBorderPainted(false);

        button.setPreferredSize(
                new Dimension(
                        190,
                        45
                )
        );
    }

    private void showError(
            String message
    ) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}