package com.gym.view;

import com.gym.dao.ExerciseCompletionDAO;
import com.gym.dao.ExerciseCompletionDAO.ExerciseItem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class DailyProductivityPanel extends JPanel {

    private final int memberId;

    private JPanel exerciseListPanel;

    private JLabel completedLabel;
    private JLabel productivityLabel;

    private JProgressBar progressBar;

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

    public DailyProductivityPanel(
            int memberId
    ) {

        this.memberId = memberId;

        setLayout(
                new BorderLayout(
                        20,
                        20
                )
        );

        setBackground(
                BACKGROUND
        );

        setBorder(
                new EmptyBorder(
                        25,
                        25,
                        25,
                        25
                )
        );

        buildUI();

        loadExercises();
    }

    // =========================================================
    // BUILD UI
    // =========================================================

    private void buildUI() {

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
                        "📊  Daily Productivity"
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
                        "Complete today's assigned exercises"
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
                e -> loadExercises()
        );

        header.add(
                titlePanel,
                BorderLayout.WEST
        );

        header.add(
                refreshButton,
                BorderLayout.EAST
        );

        add(
                header,
                BorderLayout.NORTH
        );

        // =====================================================
        // PRODUCTIVITY CARD
        // =====================================================

        JPanel productivityCard =
                new JPanel();

        productivityCard.setLayout(
                new BoxLayout(
                        productivityCard,
                        BoxLayout.Y_AXIS
                )
        );

        productivityCard.setBackground(
                CARD
        );

        productivityCard.setBorder(
                new EmptyBorder(
                        20,
                        25,
                        20,
                        25
                )
        );

        JLabel productivityTitle =
                new JLabel(
                        "TODAY'S PRODUCTIVITY"
                );

        productivityTitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        productivityTitle.setForeground(
                MUTED
        );

        completedLabel =
                new JLabel(
                        "Exercises Completed: 0 / 0"
                );

        completedLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        18
                )
        );

        completedLabel.setForeground(
                TEXT
        );

        productivityLabel =
                new JLabel(
                        "0%"
                );

        productivityLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        30
                )
        );

        productivityLabel.setForeground(
                SUCCESS
        );

        progressBar =
                new JProgressBar(
                        0,
                        100
                );

        progressBar.setValue(
                0
        );

        progressBar.setStringPainted(
                true
        );

        progressBar.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        progressBar.setForeground(
                SUCCESS
        );

        progressBar.setBackground(
                CARD_LIGHT
        );

        productivityCard.add(
                productivityTitle
        );

        productivityCard.add(
                Box.createVerticalStrut(10)
        );

        productivityCard.add(
                completedLabel
        );

        productivityCard.add(
                Box.createVerticalStrut(8)
        );

        productivityCard.add(
                productivityLabel
        );

        productivityCard.add(
                Box.createVerticalStrut(8)
        );

        productivityCard.add(
                progressBar
        );

        // =====================================================
        // EXERCISE LIST
        // =====================================================

        exerciseListPanel =
                new JPanel();

        exerciseListPanel.setLayout(
                new BoxLayout(
                        exerciseListPanel,
                        BoxLayout.Y_AXIS
                )
        );

        exerciseListPanel.setBackground(
                BACKGROUND
        );

        JScrollPane scrollPane =
                new JScrollPane(
                        exerciseListPanel
                );

        scrollPane.setBorder(
                BorderFactory.createEmptyBorder()
        );

        scrollPane.getVerticalScrollBar()
                .setUnitIncrement(16);

        // =====================================================
        // CENTER
        // =====================================================

        JPanel center =
                new JPanel(
                        new BorderLayout(
                                0,
                                15
                        )
                );

        center.setBackground(
                BACKGROUND
        );

        center.add(
                productivityCard,
                BorderLayout.NORTH
        );

        center.add(
                scrollPane,
                BorderLayout.CENTER
        );

        add(
                center,
                BorderLayout.CENTER
        );
    }

    // =========================================================
    // LOAD EXERCISES
    // =========================================================

    private void loadExercises() {

        exerciseListPanel.removeAll();

        ExerciseCompletionDAO dao =
                new ExerciseCompletionDAO();

        List<ExerciseItem> exercises =
                dao.getTodayExercises(
                        memberId
                );

        if (exercises.isEmpty()) {

            JLabel emptyLabel =
                    new JLabel(
                            "No active workout exercises assigned for today."
                    );

            emptyLabel.setFont(
                    new Font(
                            "Segoe UI",
                            Font.PLAIN,
                            16
                    )
            );

            emptyLabel.setForeground(
                    MUTED
            );

            emptyLabel.setBorder(
                    new EmptyBorder(
                            30,
                            10,
                            30,
                            10
                    )
            );

            exerciseListPanel.add(
                    emptyLabel
            );

            updateProductivity(
                    0,
                    0
            );

        } else {

            int completed = 0;

            for (
                    ExerciseItem exercise :
                    exercises
            ) {

                if (exercise.isCompleted()) {
                    completed++;
                }

                exerciseListPanel.add(
                        createExerciseCard(
                                exercise
                        )
                );

                exerciseListPanel.add(
                        Box.createVerticalStrut(10)
                );
            }

            updateProductivity(
                    completed,
                    exercises.size()
            );
        }

        exerciseListPanel.revalidate();
        exerciseListPanel.repaint();
    }

    // =========================================================
    // CREATE EXERCISE CARD
    // =========================================================

    private JPanel createExerciseCard(
            ExerciseItem exercise
    ) {

        JPanel card =
                new JPanel(
                        new BorderLayout(
                                15,
                                5
                        )
                );

        card.setBackground(
                CARD
        );

        card.setBorder(
                new EmptyBorder(
                        15,
                        18,
                        15,
                        18
                )
        );

        // =====================================================
        // LEFT
        // =====================================================

        JPanel details =
                new JPanel();

        details.setLayout(
                new BoxLayout(
                        details,
                        BoxLayout.Y_AXIS
                )
        );

        details.setBackground(
                CARD
        );

        JLabel name =
                new JLabel(
                        exercise.getExerciseName()
                );

        name.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        17
                )
        );

        name.setForeground(
                TEXT
        );

        String workoutInfo =
                buildWorkoutInfo(
                        exercise
                );

        JLabel info =
                new JLabel(
                        workoutInfo
                );

        info.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        info.setForeground(
                MUTED
        );

        details.add(
                name
        );

        details.add(
                Box.createVerticalStrut(6)
        );

        details.add(
                info
        );

        // =====================================================
        // CHECKBOX
        // =====================================================

        JCheckBox completedBox =
                new JCheckBox(
                        "Completed"
                );

        completedBox.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        completedBox.setForeground(
                SUCCESS
        );

        completedBox.setBackground(
                CARD
        );

        completedBox.setFocusPainted(
                false
        );

        completedBox.setSelected(
                exercise.isCompleted()
        );

        completedBox.addActionListener(
                e -> {

                    ExerciseCompletionDAO dao =
                            new ExerciseCompletionDAO();

                    boolean success;

                    if (completedBox.isSelected()) {

                        success =
                                dao.markCompleted(
                                        memberId,
                                        exercise.getExerciseId()
                                );

                    } else {

                        success =
                                dao.markNotCompleted(
                                        memberId,
                                        exercise.getExerciseId()
                                );
                    }

                    if (!success) {

                        JOptionPane.showMessageDialog(
                                this,
                                "Unable to update exercise status.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        );

                        completedBox.setSelected(
                                exercise.isCompleted()
                        );

                        return;
                    }

                    exercise.setCompleted(
                            completedBox.isSelected()
                    );

                    updateProductivityFromCards();
                }
        );

        card.add(
                details,
                BorderLayout.CENTER
        );

        card.add(
                completedBox,
                BorderLayout.EAST
        );

        return card;
    }

    // =========================================================
    // WORKOUT INFO
    // =========================================================

    private String buildWorkoutInfo(
            ExerciseItem exercise
    ) {

        StringBuilder text =
                new StringBuilder();

        if (exercise.getSets() != null) {

            text.append(
                    "Sets: "
            ).append(
                    exercise.getSets()
            );
        }

        if (exercise.getRepetitions() != null) {

            if (text.length() > 0) {
                text.append("   •   ");
            }

            text.append(
                    "Reps: "
            ).append(
                    exercise.getRepetitions()
            );
        }

        if (exercise.getDurationMinutes() != null) {

            if (text.length() > 0) {
                text.append("   •   ");
            }

            text.append(
                    "Duration: "
            ).append(
                    exercise.getDurationMinutes()
            ).append(
                    " min"
            );
        }

        if (text.length() == 0) {
            text.append("Workout exercise");
        }

        return text.toString();
    }

    // =========================================================
    // UPDATE PRODUCTIVITY
    // =========================================================

    private void updateProductivity(
            int completed,
            int total
    ) {

        int percentage = 0;

        if (total > 0) {

            percentage =
                    (completed * 100) / total;
        }

        completedLabel.setText(
                "Exercises Completed: "
                        + completed
                        + " / "
                        + total
        );

        productivityLabel.setText(
                percentage + "%"
        );

        progressBar.setValue(
                percentage
        );

        progressBar.setString(
                percentage + "%"
        );
    }

    // =========================================================
    // UPDATE AFTER CHECKBOX
    // =========================================================

    private void updateProductivityFromCards() {

        int total =
                exerciseListPanel
                        .getComponentCount();

        int completed = 0;

        int actualExercises = 0;

        for (
                Component component :
                exerciseListPanel.getComponents()
        ) {

            if (!(component instanceof JPanel)) {
                continue;
            }

            JPanel card =
                    (JPanel) component;

            JCheckBox checkBox =
                    findCheckBox(card);

            if (checkBox != null) {

                actualExercises++;

                if (checkBox.isSelected()) {
                    completed++;
                }
            }
        }

        updateProductivity(
                completed,
                actualExercises
        );
    }

    // =========================================================
    // FIND CHECKBOX
    // =========================================================

    private JCheckBox findCheckBox(
            Container container
    ) {

        for (
                Component component :
                container.getComponents()
        ) {

            if (component instanceof JCheckBox) {

                return (JCheckBox) component;
            }

            if (component instanceof Container) {

                JCheckBox result =
                        findCheckBox(
                                (Container) component
                        );

                if (result != null) {
                    return result;
                }
            }
        }

        return null;
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