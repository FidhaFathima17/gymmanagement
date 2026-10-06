package com.gym.view;

import com.gym.config.DatabaseConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class MemberWorkoutPlanFrame extends JFrame {

    private final int memberId;

    private JComboBox<PlanItem> planCombo;

    private JLabel planNameLabel;
    private JLabel trainerLabel;
    private JLabel dateLabel;
    private JLabel statusLabel;
    private JLabel descriptionLabel;

    private JTable exerciseTable;
    private DefaultTableModel exerciseTableModel;

    private final Color BACKGROUND =
            new Color(15, 18, 25);

    private final Color CARD =
            new Color(25, 29, 38);

    private final Color CARD_LIGHT =
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
    // PLAN ITEM
    // =========================================================

    private static class PlanItem {

        private final int planId;
        private final String planName;
        private final String status;

        public PlanItem(
                int planId,
                String planName,
                String status
        ) {
            this.planId = planId;
            this.planName = planName;
            this.status = status;
        }

        public int getPlanId() {
            return planId;
        }

        @Override
        public String toString() {
            return planName + "  [" + status + "]";
        }
    }

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public MemberWorkoutPlanFrame(int memberId) {

        this.memberId = memberId;

        setTitle("My Workout Plans");

        setSize(
                1200,
                750
        );

        setMinimumSize(
                new Dimension(
                        1000,
                        650
                )
        );

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        buildUI();

        loadPlans();
    }

    // =========================================================
    // BUILD UI
    // =========================================================

    private void buildUI() {

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout(
                                20,
                                20
                        )
                );

        mainPanel.setBackground(
                BACKGROUND
        );

        mainPanel.setBorder(
                new EmptyBorder(
                        25,
                        25,
                        25,
                        25
                )
        );

        // =====================================================
        // HEADER
        // =====================================================

        JPanel header =
                new JPanel(
                        new BorderLayout()
                );

        header.setBackground(
                BACKGROUND
        );

        JPanel titlePanel =
                new JPanel();

        titlePanel.setLayout(
                new BoxLayout(
                        titlePanel,
                        BoxLayout.Y_AXIS
                )
        );

        titlePanel.setBackground(
                BACKGROUND
        );

        JLabel title =
                new JLabel(
                        "🏋️  My Workout Plan"
                );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        28
                )
        );

        title.setForeground(
                TEXT
        );

        JLabel subtitle =
                new JLabel(
                        "View the workout plans assigned by your trainer"
                );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        subtitle.setForeground(
                MUTED
        );

        titlePanel.add(title);

        titlePanel.add(
                Box.createVerticalStrut(5)
        );

        titlePanel.add(subtitle);

        JButton refreshButton =
                createButton(
                        "⟳  Refresh",
                        PRIMARY
                );

        refreshButton.addActionListener(
                e -> loadPlans()
        );

        header.add(
                titlePanel,
                BorderLayout.WEST
        );

        header.add(
                refreshButton,
                BorderLayout.EAST
        );

        mainPanel.add(
                header,
                BorderLayout.NORTH
        );

        // =====================================================
        // PLAN SELECTOR
        // =====================================================

        JPanel selectorCard =
                createCard();

        JLabel selectorTitle =
                createLabel(
                        "SELECT WORKOUT PLAN",
                        12,
                        Font.BOLD,
                        MUTED
                );

        selectorCard.add(
                selectorTitle
        );

        selectorCard.add(
                Box.createVerticalStrut(10)
        );

        planCombo =
                new JComboBox<>();

        planCombo.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        planCombo.setBackground(
                CARD_LIGHT
        );

        planCombo.setForeground(
                TEXT
        );

        planCombo.setPreferredSize(
                new Dimension(
                        400,
                        40
                )
        );

        planCombo.addActionListener(
                e -> loadSelectedPlan()
        );

        selectorCard.add(
                planCombo
        );

        // =====================================================
        // PLAN DETAILS
        // =====================================================

        JPanel detailsCard =
                createCard();

        JLabel detailsTitle =
                createLabel(
                        "📋  Workout Details",
                        18,
                        Font.BOLD,
                        TEXT
                );

        detailsCard.add(
                detailsTitle
        );

        detailsCard.add(
                Box.createVerticalStrut(15)
        );

        planNameLabel =
                createInfoLabel(
                        "Plan: -"
                );

        trainerLabel =
                createInfoLabel(
                        "Trainer: -"
                );

        dateLabel =
                createInfoLabel(
                        "Created: -"
                );

        statusLabel =
                createInfoLabel(
                        "Status: -"
                );

        descriptionLabel =
                createInfoLabel(
                        "Description: -"
                );

        detailsCard.add(
                planNameLabel
        );

        detailsCard.add(
                Box.createVerticalStrut(6)
        );

        detailsCard.add(
                trainerLabel
        );

        detailsCard.add(
                Box.createVerticalStrut(6)
        );

        detailsCard.add(
                dateLabel
        );

        detailsCard.add(
                Box.createVerticalStrut(6)
        );

        detailsCard.add(
                statusLabel
        );

        detailsCard.add(
                Box.createVerticalStrut(6)
        );

        detailsCard.add(
                descriptionLabel
        );

        // =====================================================
        // TOP SECTION
        // =====================================================

        JPanel topSection =
                new JPanel(
                        new BorderLayout(
                                15,
                                0
                        )
                );

        topSection.setBackground(
                BACKGROUND
        );

        topSection.add(
                selectorCard,
                BorderLayout.WEST
        );

        topSection.add(
                detailsCard,
                BorderLayout.CENTER
        );

        // =====================================================
        // EXERCISES CARD
        // =====================================================

        JPanel exerciseCard =
                createCard();

        JLabel exerciseTitle =
                createLabel(
                        "💪  Exercises",
                        18,
                        Font.BOLD,
                        TEXT
                );

        exerciseCard.add(
                exerciseTitle
        );

        exerciseCard.add(
                Box.createVerticalStrut(15)
        );

        String[] columns = {

                "Exercise",
                "Sets",
                "Repetitions",
                "Duration",
                "Instructions"
        };

        exerciseTableModel =
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

        exerciseTable =
                new JTable(
                        exerciseTableModel
                );

        styleTable(
                exerciseTable
        );

        JScrollPane scrollPane =
                new JScrollPane(
                        exerciseTable
                );

        exerciseCard.add(
                scrollPane
        );

        // =====================================================
        // CENTER
        // =====================================================

        JPanel content =
                new JPanel(
                        new BorderLayout(
                                0,
                                15
                        )
                );

        content.setBackground(
                BACKGROUND
        );

        content.add(
                topSection,
                BorderLayout.NORTH
        );

        content.add(
                exerciseCard,
                BorderLayout.CENTER
        );

        mainPanel.add(
                content,
                BorderLayout.CENTER
        );

        setContentPane(
                mainPanel
        );
    }

    // =========================================================
    // LOAD PLANS
    // =========================================================

    private void loadPlans() {

        planCombo.removeAllItems();

        String sql =
                "SELECT " +
                        "plan_id, " +
                        "plan_name, " +
                        "status " +
                        "FROM workout_plans " +
                        "WHERE member_id = ? " +
                        "ORDER BY created_date DESC, plan_id DESC";

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

                while (rs.next()) {

                    planCombo.addItem(
                            new PlanItem(
                                    rs.getInt(
                                            "plan_id"
                                    ),
                                    rs.getString(
                                            "plan_name"
                                    ),
                                    rs.getString(
                                            "status"
                                    )
                            )
                    );
                }
            }

            if (planCombo.getItemCount() == 0) {

                clearPlanDetails();

                JOptionPane.showMessageDialog(
                        this,
                        "No workout plans have been assigned to you yet.",
                        "Workout Plans",
                        JOptionPane.INFORMATION_MESSAGE
                );

                return;
            }

            planCombo.setSelectedIndex(0);

            loadSelectedPlan();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load workout plans.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // LOAD SELECTED PLAN
    // =========================================================

    private void loadSelectedPlan() {

        PlanItem selected =
                (PlanItem) planCombo.getSelectedItem();

        if (selected == null) {
            return;
        }

        loadPlanDetails(
                selected.getPlanId()
        );

        loadExercises(
                selected.getPlanId()
        );
    }

    // =========================================================
    // LOAD PLAN DETAILS
    // =========================================================

    private void loadPlanDetails(
            int planId
    ) {

        String sql =
                "SELECT " +
                        "wp.plan_name, " +
                        "wp.description, " +
                        "wp.created_date, " +
                        "wp.status, " +
                        "tp.full_name AS trainer_name " +
                        "FROM workout_plans wp " +
                        "JOIN trainer_profiles tp " +
                        "ON wp.trainer_id = tp.trainer_id " +
                        "WHERE wp.plan_id = ? " +
                        "AND wp.member_id = ?";

        try (
                Connection conn =
                        DatabaseConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setInt(
                    1,
                    planId
            );

            stmt.setInt(
                    2,
                    memberId
            );

            try (
                    ResultSet rs =
                            stmt.executeQuery()
            ) {

                if (rs.next()) {

                    String planName =
                            rs.getString(
                                    "plan_name"
                            );

                    String description =
                            rs.getString(
                                    "description"
                            );

                    Date createdDate =
                            rs.getDate(
                                    "created_date"
                            );

                    String status =
                            rs.getString(
                                    "status"
                            );

                    String trainerName =
                            rs.getString(
                                    "trainer_name"
                            );

                    planNameLabel.setText(
                            "Plan: " + planName
                    );

                    trainerLabel.setText(
                            "Trainer: " + trainerName
                    );

                    dateLabel.setText(
                            "Created: " + createdDate
                    );

                    statusLabel.setText(
                            "Status: " + status
                    );

                    descriptionLabel.setText(
                            "Description: "
                                    + (
                                    description == null
                                            || description.trim().isEmpty()
                                            ? "No description"
                                            : description
                            )
                    );
                }

            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load plan details.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // LOAD EXERCISES
    // =========================================================

    private void loadExercises(
            int planId
    ) {

        exerciseTableModel.setRowCount(
                0
        );

        String sql =
                "SELECT " +
                        "exercise_name, " +
                        "sets, " +
                        "repetitions, " +
                        "duration_minutes, " +
                        "instructions " +
                        "FROM workout_exercises " +
                        "WHERE plan_id = ? " +
                        "ORDER BY exercise_id";

        try (
                Connection conn =
                        DatabaseConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setInt(
                    1,
                    planId
            );

            try (
                    ResultSet rs =
                            stmt.executeQuery()
            ) {

                while (rs.next()) {

                    Object sets =
                            rs.getObject(
                                    "sets"
                            );

                    Object repetitions =
                            rs.getObject(
                                    "repetitions"
                            );

                    Object duration =
                            rs.getObject(
                                    "duration_minutes"
                            );

                    String instructions =
                            rs.getString(
                                    "instructions"
                            );

                    exerciseTableModel.addRow(
                            new Object[]{

                                    rs.getString(
                                            "exercise_name"
                                    ),

                                    sets == null
                                            ? "-"
                                            : sets,

                                    repetitions == null
                                            ? "-"
                                            : repetitions,

                                    duration == null
                                            ? "-"
                                            : duration + " min",

                                    instructions == null
                                            || instructions.trim().isEmpty()
                                            ? "-"
                                            : instructions
                            }
                    );
                }
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load exercises.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // CLEAR DETAILS
    // =========================================================

    private void clearPlanDetails() {

        planNameLabel.setText(
                "Plan: -"
        );

        trainerLabel.setText(
                "Trainer: -"
        );

        dateLabel.setText(
                "Created: -"
        );

        statusLabel.setText(
                "Status: -"
        );

        descriptionLabel.setText(
                "Description: -"
        );

        exerciseTableModel.setRowCount(
                0
        );
    }

    // =========================================================
    // CREATE CARD
    // =========================================================

    private JPanel createCard() {

        JPanel panel =
                new JPanel();

        panel.setLayout(
                new BoxLayout(
                        panel,
                        BoxLayout.Y_AXIS
                )
        );

        panel.setBackground(
                CARD
        );

        panel.setBorder(
                new EmptyBorder(
                        18,
                        20,
                        18,
                        20
                )
        );

        return panel;
    }

    // =========================================================
    // CREATE LABEL
    // =========================================================

    private JLabel createLabel(
            String text,
            int size,
            int style,
            Color color
    ) {

        JLabel label =
                new JLabel(
                        text
                );

        label.setFont(
                new Font(
                        "Segoe UI",
                        style,
                        size
                )
        );

        label.setForeground(
                color
        );

        return label;
    }

    // =========================================================
    // INFO LABEL
    // =========================================================

    private JLabel createInfoLabel(
            String text
    ) {

        JLabel label =
                new JLabel(
                        text
                );

        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        label.setForeground(
                TEXT
        );

        return label;
    }

    // =========================================================
    // TABLE STYLE
    // =========================================================

    private void styleTable(
            JTable table
    ) {

        table.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        table.setRowHeight(
                32
        );

        table.setBackground(
                CARD_LIGHT
        );

        table.setForeground(
                TEXT
        );

        table.setGridColor(
                new Color(
                        55,
                        60,
                        70
                )
        );

        table.setSelectionBackground(
                new Color(
                        0,
                        100,
                        140
                )
        );

        table.setSelectionForeground(
                Color.WHITE
        );

        table.getTableHeader()
                .setFont(
                        new Font(
                                "Segoe UI",
                                Font.BOLD,
                                13
                        )
                );

        table.getTableHeader()
                .setBackground(
                        new Color(
                                35,
                                40,
                                50
                        )
                );

        table.getTableHeader()
                .setForeground(
                        TEXT
                );

        table.getTableHeader()
                .setPreferredSize(
                        new Dimension(
                                0,
                                35
                        )
                );

        DefaultTableCellRenderer renderer =
                new DefaultTableCellRenderer();

        renderer.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        for (
                int i = 0;
                i < table.getColumnCount();
                i++
        ) {

            table.getColumnModel()
                    .getColumn(i)
                    .setCellRenderer(
                            renderer
                    );
        }
    }

    // =========================================================
    // BUTTON
    // =========================================================

    private JButton createButton(
            String text,
            Color color
    ) {

        JButton button =
                new JButton(
                        text
                );

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        button.setForeground(
                Color.WHITE
        );

        button.setBackground(
                color
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
                        130,
                        40
                )
        );

        return button;
    }
}