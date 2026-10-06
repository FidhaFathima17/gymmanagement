package com.gym.view;

import com.gym.dao.WorkoutPlanDAO;
import com.gym.model.WorkoutExercise;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class WorkoutPlanManagementPanel extends JPanel {

    private final int trainerId;

    private JComboBox<WorkoutPlanDAO.AssignedMember> memberCombo;
    private JTextField planNameField;
    private JTextArea descriptionArea;

    private JComboBox<WorkoutPlanDAO.WorkoutPlanInfo> planCombo;

    private JTextField exerciseNameField;
    private JTextField setsField;
    private JTextField repetitionsField;
    private JTextField durationField;
    private JTextArea instructionsArea;

    private JTable exerciseTable;
    private DefaultTableModel exerciseTableModel;

    private JLabel selectedPlanLabel;
    private JLabel statusLabel;

    private final WorkoutPlanDAO dao = new WorkoutPlanDAO();

    private static final Color BACKGROUND = new Color(15, 23, 42);
    private static final Color CARD = new Color(30, 41, 59);
    private static final Color CARD2 = new Color(51, 65, 85);
    private static final Color TEXT = new Color(241, 245, 249);
    private static final Color MUTED = new Color(148, 163, 184);
    private static final Color ACCENT = new Color(99, 102, 241);
    private static final Color GREEN = new Color(34, 197, 94);
    private static final Color RED = new Color(239, 68, 68);

    public WorkoutPlanManagementPanel(int trainerId) {

        this.trainerId = trainerId;

        setLayout(new BorderLayout());
        setBackground(BACKGROUND);

        buildUI();

        loadMembers();
        loadPlans();
    }

    // =========================================================
    // MAIN UI
    // =========================================================

    private void buildUI() {

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BACKGROUND);
        header.setBorder(new EmptyBorder(25, 30, 20, 30));

        JLabel title = new JLabel("🏋️ Workout Plan Management");

        title.setFont(new Font("Segoe UI", Font.BOLD, 27));
        title.setForeground(TEXT);

        JLabel subtitle = new JLabel(
                "Create and manage workout plans for your assigned members"
        );

        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(MUTED);

        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));

        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(5));
        titlePanel.add(subtitle);

        header.add(titlePanel, BorderLayout.WEST);

        JButton refreshButton = createButton(
                "⟳ Refresh",
                ACCENT
        );

        refreshButton.addActionListener(e -> refreshAll());

        header.add(refreshButton, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setOpaque(false);
        mainPanel.setBorder(new EmptyBorder(0, 30, 30, 30));

        mainPanel.add(createTopPanel(), BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(15, 15));
        centerPanel.setOpaque(false);

        centerPanel.add(createExerciseForm(), BorderLayout.NORTH);
        centerPanel.add(createExerciseTablePanel(), BorderLayout.CENTER);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);
    }

    // =========================================================
    // TOP PANEL
    // =========================================================

    private JPanel createTopPanel() {

        JPanel panel = createCard();

        panel.setLayout(new BorderLayout(15, 15));

        JLabel heading = new JLabel("Create / Select Workout Plan");

        heading.setFont(new Font("Segoe UI", Font.BOLD, 18));
        heading.setForeground(TEXT);

        panel.add(heading, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // MEMBER

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;

        form.add(createLabel("Member"), gbc);

        memberCombo = new JComboBox<>();

        styleCombo(memberCombo);

        gbc.gridx = 1;
        gbc.weightx = 1;

        form.add(memberCombo, gbc);

        // PLAN NAME

        gbc.gridx = 2;
        gbc.weightx = 0;

        form.add(createLabel("Plan Name"), gbc);

        planNameField = createTextField();

        gbc.gridx = 3;
        gbc.weightx = 1;

        form.add(planNameField, gbc);

        // DESCRIPTION

        gbc.gridx = 0;
        gbc.gridy = 1;

        form.add(createLabel("Description"), gbc);

        descriptionArea = createTextArea(2);

        gbc.gridx = 1;
        gbc.gridwidth = 3;

        form.add(new JScrollPane(descriptionArea), gbc);

        panel.add(form, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.setOpaque(false);

        JButton createPlanButton =
                createButton("＋ Create Plan", GREEN);

        JButton deletePlanButton =
                createButton("Delete Plan", RED);

        createPlanButton.addActionListener(e -> createPlan());
        deletePlanButton.addActionListener(e -> deletePlan());

        buttons.add(createPlanButton);
        buttons.add(deletePlanButton);

        panel.add(buttons, BorderLayout.SOUTH);

        // PLAN SELECTOR

        JPanel planPanel = new JPanel(new BorderLayout(10, 0));
        planPanel.setOpaque(false);

        JLabel selectLabel = createLabel("Existing Plans:");

        planCombo = new JComboBox<>();

        styleCombo(planCombo);

        planCombo.addActionListener(e -> planSelected());

        planPanel.add(selectLabel, BorderLayout.WEST);
        planPanel.add(planCombo, BorderLayout.CENTER);

        panel.add(planPanel, BorderLayout.EAST);

        return panel;
    }

    // =========================================================
    // EXERCISE FORM
    // =========================================================

    private JPanel createExerciseForm() {

        JPanel panel = createCard();

        panel.setLayout(new BorderLayout(10, 10));

        JLabel heading =
                new JLabel("➕ Add Exercise");

        heading.setFont(new Font("Segoe UI", Font.BOLD, 18));
        heading.setForeground(TEXT);

        panel.add(heading, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(4, 5, 4, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // EXERCISE NAME

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;

        form.add(createLabel("Exercise"), gbc);

        exerciseNameField = createTextField();

        gbc.gridx = 1;
        gbc.weightx = 1;

        form.add(exerciseNameField, gbc);

        // SETS

        gbc.gridx = 2;
        gbc.weightx = 0;

        form.add(createLabel("Sets"), gbc);

        setsField = createTextField();

        gbc.gridx = 3;

        form.add(setsField, gbc);

        // REPS

        gbc.gridx = 4;

        form.add(createLabel("Repetitions"), gbc);

        repetitionsField = createTextField();

        gbc.gridx = 5;
        gbc.weightx = 1;

        form.add(repetitionsField, gbc);

        // DURATION

        gbc.gridx = 6;
        gbc.weightx = 0;

        form.add(createLabel("Duration (min)"), gbc);

        durationField = createTextField();

        gbc.gridx = 7;

        form.add(durationField, gbc);

        // INSTRUCTIONS

        gbc.gridx = 0;
        gbc.gridy = 1;

        form.add(createLabel("Instructions"), gbc);

        instructionsArea = createTextArea(2);

        gbc.gridx = 1;
        gbc.gridwidth = 5;
        gbc.weightx = 1;

        form.add(new JScrollPane(instructionsArea), gbc);

        JButton addButton =
                createButton("＋ Add Exercise", ACCENT);

        addButton.addActionListener(e -> addExercise());

        gbc.gridx = 6;
        gbc.gridwidth = 2;

        form.add(addButton, gbc);

        panel.add(form, BorderLayout.CENTER);

        selectedPlanLabel =
                new JLabel("No workout plan selected");

        selectedPlanLabel.setFont(
                new Font("Segoe UI", Font.BOLD, 13)
        );

        selectedPlanLabel.setForeground(MUTED);

        panel.add(selectedPlanLabel, BorderLayout.SOUTH);

        return panel;
    }

    // =========================================================
    // EXERCISE TABLE
    // =========================================================

    private JPanel createExerciseTablePanel() {

        JPanel panel = createCard();

        panel.setLayout(new BorderLayout(10, 10));

        JPanel top = new JPanel(new BorderLayout());

        top.setOpaque(false);

        JLabel title =
                new JLabel("Workout Exercises");

        title.setFont(
                new Font("Segoe UI", Font.BOLD, 18)
        );

        title.setForeground(TEXT);

        statusLabel =
                new JLabel("Select a workout plan");

        statusLabel.setForeground(MUTED);

        top.add(title, BorderLayout.WEST);
        top.add(statusLabel, BorderLayout.EAST);

        panel.add(top, BorderLayout.NORTH);

        exerciseTableModel =
                new DefaultTableModel(
                        new Object[]{
                                "ID",
                                "Exercise",
                                "Sets",
                                "Repetitions",
                                "Duration",
                                "Instructions"
                        }, 0) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column) {
                        return false;
                    }
                };

        exerciseTable = new JTable(exerciseTableModel);

        styleTable(exerciseTable);

        JScrollPane scrollPane =
                new JScrollPane(exerciseTable);

        scrollPane.setBorder(
                BorderFactory.createEmptyBorder()
        );

        panel.add(scrollPane, BorderLayout.CENTER);

        JButton deleteButton =
                createButton("🗑 Delete Selected Exercise", RED);

        deleteButton.addActionListener(
                e -> deleteSelectedExercise()
        );

        JPanel bottom =
                new JPanel(new FlowLayout(FlowLayout.RIGHT));

        bottom.setOpaque(false);

        bottom.add(deleteButton);

        panel.add(bottom, BorderLayout.SOUTH);

        return panel;
    }

    // =========================================================
    // LOAD MEMBERS
    // =========================================================

    private void loadMembers() {

        memberCombo.removeAllItems();

        List<WorkoutPlanDAO.AssignedMember> members =
                dao.getAssignedMembers(trainerId);

        for (WorkoutPlanDAO.AssignedMember member : members) {

            memberCombo.addItem(member);
        }
    }

    // =========================================================
    // LOAD PLANS
    // =========================================================

    private void loadPlans() {

        planCombo.removeAllItems();

        List<WorkoutPlanDAO.WorkoutPlanInfo> plans =
                dao.getPlansForTrainer(trainerId);

        for (WorkoutPlanDAO.WorkoutPlanInfo plan : plans) {

            planCombo.addItem(plan);
        }

        planCombo.setRenderer(new DefaultListCellRenderer() {

            @Override
            public Component getListCellRendererComponent(
                    JList<?> list,
                    Object value,
                    int index,
                    boolean selected,
                    boolean focused) {

                super.getListCellRendererComponent(
                        list,
                        value,
                        index,
                        selected,
                        focused
                );

                if (value instanceof WorkoutPlanDAO.WorkoutPlanInfo) {

                    WorkoutPlanDAO.WorkoutPlanInfo p =
                            (WorkoutPlanDAO.WorkoutPlanInfo) value;

                    setText(
                            p.getPlanName()
                                    + " - "
                                    + p.getMemberName()
                                    + " ["
                                    + p.getStatus()
                                    + "]"
                    );
                }

                return this;
            }
        });

        if (planCombo.getItemCount() > 0) {

            planCombo.setSelectedIndex(0);

            planSelected();
        }
    }

    // =========================================================
    // CREATE PLAN
    // =========================================================

    private void createPlan() {

        WorkoutPlanDAO.AssignedMember member =
                (WorkoutPlanDAO.AssignedMember)
                        memberCombo.getSelectedItem();

        if (member == null) {

            showError("Please select a member.");

            return;
        }

        String planName =
                planNameField.getText().trim();

        String description =
                descriptionArea.getText().trim();

        if (planName.isEmpty()) {

            showError("Please enter a workout plan name.");

            planNameField.requestFocus();

            return;
        }

        int planId =
                dao.createPlan(
                        trainerId,
                        member.getMemberId(),
                        planName,
                        description
                );

        if (planId > 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Workout plan created successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            planNameField.setText("");
            descriptionArea.setText("");

            loadPlans();

            selectPlanById(planId);

        } else {

            showError(
                    "Unable to create workout plan."
            );
        }
    }

    // =========================================================
    // PLAN SELECTED
    // =========================================================

    private void planSelected() {

        WorkoutPlanDAO.WorkoutPlanInfo plan =
                (WorkoutPlanDAO.WorkoutPlanInfo)
                        planCombo.getSelectedItem();

        exerciseTableModel.setRowCount(0);

        if (plan == null) {

            selectedPlanLabel.setText(
                    "No workout plan selected"
            );

            statusLabel.setText(
                    "Select a workout plan"
            );

            return;
        }

        selectedPlanLabel.setText(
                "Selected: "
                        + plan.getPlanName()
                        + " | Member: "
                        + plan.getMemberName()
                        + " | Status: "
                        + plan.getStatus()
        );

        statusLabel.setText(
                "Plan ID: " + plan.getPlanId()
        );

        loadExercises(plan.getPlanId());

        planNameField.setText(
                plan.getPlanName()
        );

        descriptionArea.setText(
                plan.getDescription() != null
                        ? plan.getDescription()
                        : ""
        );
    }

    // =========================================================
    // LOAD EXERCISES
    // =========================================================

    private void loadExercises(int planId) {

        exerciseTableModel.setRowCount(0);

        List<WorkoutExercise> exercises =
                dao.getExercises(planId);

        for (WorkoutExercise exercise : exercises) {

            exerciseTableModel.addRow(
                    new Object[]{
                            exercise.getExerciseId(),
                            exercise.getExerciseName(),
                            valueOrDash(exercise.getSets()),
                            valueOrDash(exercise.getRepetitions()),
                            exercise.getDurationMinutes() != null
                                    ? exercise.getDurationMinutes() + " min"
                                    : "-",
                            exercise.getInstructions() != null
                                    ? exercise.getInstructions()
                                    : "-"
                    }
            );
        }
    }

    // =========================================================
    // ADD EXERCISE
    // =========================================================

    private void addExercise() {

        WorkoutPlanDAO.WorkoutPlanInfo plan =
                (WorkoutPlanDAO.WorkoutPlanInfo)
                        planCombo.getSelectedItem();

        if (plan == null) {

            showError(
                    "Please create or select a workout plan first."
            );

            return;
        }

        if (!"ACTIVE".equalsIgnoreCase(plan.getStatus())) {

            showError(
                    "Exercises can only be added to an ACTIVE plan."
            );

            return;
        }

        String exerciseName =
                exerciseNameField.getText().trim();

        if (exerciseName.isEmpty()) {

            showError(
                    "Please enter exercise name."
            );

            exerciseNameField.requestFocus();

            return;
        }

        Integer sets =
                parseInteger(setsField.getText().trim());

        Integer repetitions =
                parseInteger(repetitionsField.getText().trim());

        Integer duration =
                parseInteger(durationField.getText().trim());

        String instructions =
                instructionsArea.getText().trim();

        if (setsField.getText().trim().length() > 0
                && sets == null) {

            showError("Sets must be a number.");

            return;
        }

        if (repetitionsField.getText().trim().length() > 0
                && repetitions == null) {

            showError("Repetitions must be a number.");

            return;
        }

        if (durationField.getText().trim().length() > 0
                && duration == null) {

            showError("Duration must be a number.");

            return;
        }

        boolean success =
                dao.addExercise(
                        plan.getPlanId(),
                        exerciseName,
                        sets,
                        repetitions,
                        duration,
                        instructions
                );

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Exercise added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            exerciseNameField.setText("");
            setsField.setText("");
            repetitionsField.setText("");
            durationField.setText("");
            instructionsArea.setText("");

            loadExercises(plan.getPlanId());

        } else {

            showError(
                    "Unable to add exercise."
            );
        }
    }

    // =========================================================
    // DELETE EXERCISE
    // =========================================================

    private void deleteSelectedExercise() {

        int row =
                exerciseTable.getSelectedRow();

        if (row == -1) {

            showError(
                    "Please select an exercise first."
            );

            return;
        }

        int exerciseId =
                Integer.parseInt(
                        exerciseTableModel
                                .getValueAt(row, 0)
                                .toString()
                );

        String exerciseName =
                exerciseTableModel
                        .getValueAt(row, 1)
                        .toString();

        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Delete exercise \"" +
                                exerciseName +
                                "\"?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        if (dao.deleteExercise(exerciseId)) {

            WorkoutPlanDAO.WorkoutPlanInfo plan =
                    (WorkoutPlanDAO.WorkoutPlanInfo)
                            planCombo.getSelectedItem();

            if (plan != null) {
                loadExercises(plan.getPlanId());
            }

        } else {

            showError(
                    "Unable to delete exercise."
            );
        }
    }

    // =========================================================
    // DELETE PLAN
    // =========================================================

    private void deletePlan() {

        WorkoutPlanDAO.WorkoutPlanInfo plan =
                (WorkoutPlanDAO.WorkoutPlanInfo)
                        planCombo.getSelectedItem();

        if (plan == null) {

            showError(
                    "Please select a workout plan."
            );

            return;
        }

        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Delete workout plan:\n\n"
                                + plan.getPlanName()
                                + "\n\nAll exercises inside this plan will also be deleted.",
                        "Delete Workout Plan",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        if (dao.deletePlan(plan.getPlanId())) {

            JOptionPane.showMessageDialog(
                    this,
                    "Workout plan deleted.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadPlans();

        } else {

            showError(
                    "Unable to delete workout plan."
            );
        }
    }

    // =========================================================
    // SELECT PLAN BY ID
    // =========================================================

    private void selectPlanById(int planId) {

        for (int i = 0; i < planCombo.getItemCount(); i++) {

            WorkoutPlanDAO.WorkoutPlanInfo plan =
                    planCombo.getItemAt(i);

            if (plan.getPlanId() == planId) {

                planCombo.setSelectedIndex(i);

                return;
            }
        }
    }

    // =========================================================
    // REFRESH
    // =========================================================

    private void refreshAll() {

        loadMembers();
        loadPlans();

        JOptionPane.showMessageDialog(
                this,
                "Workout data refreshed.",
                "Refresh",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // =========================================================
    // UI HELPERS
    // =========================================================

    private JPanel createCard() {

        JPanel panel = new JPanel();

        panel.setBackground(CARD);

        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(71, 85, 105)
                        ),
                        new EmptyBorder(
                                15, 15, 15, 15
                        )
                )
        );

        return panel;
    }

    private JLabel createLabel(String text) {

        JLabel label = new JLabel(text);

        label.setForeground(MUTED);

        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        return label;
    }

    private JTextField createTextField() {

        JTextField field = new JTextField();

        field.setBackground(CARD2);
        field.setForeground(TEXT);
        field.setCaretColor(TEXT);

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(71, 85, 105)
                        ),
                        new EmptyBorder(
                                7, 9, 7, 9
                        )
                )
        );

        return field;
    }

    private JTextArea createTextArea(int rows) {

        JTextArea area =
                new JTextArea(rows, 20);

        area.setBackground(CARD2);
        area.setForeground(TEXT);
        area.setCaretColor(TEXT);

        area.setLineWrap(true);
        area.setWrapStyleWord(true);

        area.setBorder(
                BorderFactory.createEmptyBorder(
                        7, 9, 7, 9
                )
        );

        return area;
    }

    private void styleCombo(JComboBox<?> combo) {

        combo.setBackground(CARD2);
        combo.setForeground(TEXT);
        combo.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );
    }

    private JButton createButton(
            String text,
            Color color) {

        JButton button =
                new JButton(text);

        button.setBackground(color);
        button.setForeground(Color.WHITE);

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        button.setFocusPainted(false);

        button.setBorder(
                BorderFactory.createEmptyBorder(
                        9, 16, 9, 16
                )
        );

        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        return button;
    }

    private void styleTable(JTable table) {

        table.setBackground(CARD2);
        table.setForeground(TEXT);

        table.setGridColor(
                new Color(71, 85, 105)
        );

        table.setRowHeight(32);

        table.setSelectionBackground(
                new Color(79, 70, 229)
        );

        table.setSelectionForeground(Color.WHITE);

        table.getTableHeader().setBackground(
                new Color(15, 23, 42)
        );

        table.getTableHeader().setForeground(TEXT);

        table.getTableHeader().setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        DefaultTableCellRenderer renderer =
                new DefaultTableCellRenderer();

        renderer.setBackground(CARD2);
        renderer.setForeground(TEXT);

        for (int i = 0;
             i < table.getColumnCount();
             i++) {

            table.getColumnModel()
                    .getColumn(i)
                    .setCellRenderer(renderer);
        }
    }

    private Integer parseInteger(String value) {

        if (value == null || value.isEmpty()) {
            return null;
        }

        try {

            int number =
                    Integer.parseInt(value);

            if (number < 0) {
                return null;
            }

            return number;

        } catch (NumberFormatException e) {

            return null;
        }
    }

    private String valueOrDash(Integer value) {

        return value == null
                ? "-"
                : String.valueOf(value);
    }

    private void showError(String message) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Workout Plan",
                JOptionPane.ERROR_MESSAGE
        );
    }
}