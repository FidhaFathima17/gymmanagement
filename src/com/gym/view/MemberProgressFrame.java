package com.gym.view;

import com.gym.dao.ExerciseCompletionDAO;
import com.gym.dao.ExerciseCompletionDAO.ExerciseItem;
import com.gym.dao.ExerciseCompletionDAO.WeeklyProgress;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class MemberProgressFrame extends JFrame {

    private final int memberId;

    private JPanel exercisePanel;

    private JLabel completedLabel;
    private JLabel percentageLabel;

    private JProgressBar progressBar;

    private final Color BACKGROUND =
            new Color(15, 18, 25);

    private final Color CARD =
            new Color(25, 29, 38);

    private final Color CARD_LIGHT =
            new Color(35, 40, 52);

    private final Color PRIMARY =
            new Color(0, 200, 255);

    private final Color SUCCESS =
            new Color(46, 204, 113);

    private final Color TEXT =
            new Color(235, 238, 245);

    private final Color MUTED =
            new Color(160, 170, 185);

    public MemberProgressFrame(int memberId) {

        this.memberId = memberId;

        setTitle("My Progress");

        setSize(
                1150,
                850
        );

        setMinimumSize(
                new Dimension(
                        950,
                        700
                )
        );

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        buildUI();

        loadProgress();
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

        setContentPane(mainPanel);

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
                        "📈  My Progress"
                );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        30
                )
        );

        title.setForeground(
                TEXT
        );

        JLabel subtitle =
                new JLabel(
                        "Track your workout progress and daily productivity"
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
                e -> loadProgress()
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
        // SCROLLABLE CONTENT
        // =====================================================

        JPanel content =
                new JPanel();

        content.setLayout(
                new BoxLayout(
                        content,
                        BoxLayout.Y_AXIS
                )
        );

        content.setBackground(
                BACKGROUND
        );

        // Daily productivity
        content.add(
                createProductivityCard()
        );

        content.add(
                Box.createVerticalStrut(20)
        );

        // Weekly progress
        content.add(
                createWeeklyProgressSection()
        );

        content.add(
                Box.createVerticalStrut(20)
        );

        // Today's exercises title
        JLabel exerciseTitle =
                new JLabel(
                        "📊  Today's Workout Progress"
                );

        exerciseTitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        20
                )
        );

        exerciseTitle.setForeground(
                TEXT
        );

        exerciseTitle.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        content.add(
                exerciseTitle
        );

        content.add(
                Box.createVerticalStrut(12)
        );

        exercisePanel =
                new JPanel();

        exercisePanel.setLayout(
                new BoxLayout(
                        exercisePanel,
                        BoxLayout.Y_AXIS
                )
        );

        exercisePanel.setBackground(
                BACKGROUND
        );

        exercisePanel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        content.add(
                exercisePanel
        );

        JScrollPane scrollPane =
                new JScrollPane(
                        content
                );

        scrollPane.setBorder(
                BorderFactory.createEmptyBorder()
        );

        scrollPane.getVerticalScrollBar()
                .setUnitIncrement(16);

        mainPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );
    }

    // =========================================================
    // DAILY PRODUCTIVITY CARD
    // =========================================================

    private JPanel createProductivityCard() {

        JPanel card =
                new JPanel(
                        new BorderLayout(
                                20,
                                10
                        )
                );

        card.setBackground(
                CARD
        );

        card.setBorder(
                new EmptyBorder(
                        20,
                        25,
                        20,
                        25
                )
        );

        card.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        140
                )
        );

        // LEFT

        JPanel left =
                new JPanel();

        left.setLayout(
                new BoxLayout(
                        left,
                        BoxLayout.Y_AXIS
                )
        );

        left.setBackground(
                CARD
        );

        JLabel title =
                new JLabel(
                        "📊  DAILY PRODUCTIVITY"
                );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        title.setForeground(
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
                        19
                )
        );

        completedLabel.setForeground(
                TEXT
        );

        JLabel description =
                new JLabel(
                        "Complete your assigned exercises to improve your daily progress."
                );

        description.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        description.setForeground(
                MUTED
        );

        left.add(title);

        left.add(
                Box.createVerticalStrut(8)
        );

        left.add(completedLabel);

        left.add(
                Box.createVerticalStrut(5)
        );

        left.add(description);

        // RIGHT

        JPanel right =
                new JPanel();

        right.setLayout(
                new BoxLayout(
                        right,
                        BoxLayout.Y_AXIS
                )
        );

        right.setBackground(
                CARD
        );

        percentageLabel =
                new JLabel(
                        "0%"
                );

        percentageLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        32
                )
        );

        percentageLabel.setForeground(
                SUCCESS
        );

        percentageLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        progressBar =
                new JProgressBar(
                        0,
                        100
                );

        progressBar.setValue(0);

        progressBar.setStringPainted(
                true
        );

        progressBar.setString(
                "0%"
        );

        progressBar.setForeground(
                SUCCESS
        );

        progressBar.setBackground(
                CARD_LIGHT
        );

        progressBar.setPreferredSize(
                new Dimension(
                        300,
                        25
                )
        );

        right.add(
                percentageLabel
        );

        right.add(
                Box.createVerticalStrut(8)
        );

        right.add(
                progressBar
        );

        card.add(
                left,
                BorderLayout.CENTER
        );

        card.add(
                right,
                BorderLayout.EAST
        );

        return card;
    }

    // =========================================================
    // WEEKLY PROGRESS SECTION
    // =========================================================

    private JPanel createWeeklyProgressSection() {

        JPanel section =
                new JPanel();

        section.setLayout(
                new BorderLayout(
                        0,
                        12
                )
        );

        section.setBackground(
                CARD
        );

        section.setBorder(
                new EmptyBorder(
                        20,
                        25,
                        20,
                        25
                )
        );

        section.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        300
                )
        );

        JLabel title =
                new JLabel(
                        "📅  WEEKLY PROGRESS"
                );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        18
                )
        );

        title.setForeground(
                TEXT
        );

        section.add(
                title,
                BorderLayout.NORTH
        );

        JPanel daysPanel =
                new JPanel(
                        new GridLayout(
                                1,
                                7,
                                10,
                                0
                        )
                );

        daysPanel.setBackground(
                CARD
        );

        ExerciseCompletionDAO dao =
                new ExerciseCompletionDAO();

        List<WeeklyProgress> weeklyData =
                dao.getWeeklyProgress(
                        memberId
                );

        int totalCompleted = 0;
        int totalExercises = 0;

        for (
                WeeklyProgress day :
                weeklyData
        ) {

            daysPanel.add(
                    createDayProgressCard(
                            day
                    )
            );

            totalCompleted +=
                    day.getCompleted();

            totalExercises +=
                    day.getTotal();
        }

        section.add(
                daysPanel,
                BorderLayout.CENTER
        );

        int weeklyPercentage = 0;

        if (totalExercises > 0) {

            weeklyPercentage =
                    (totalCompleted * 100)
                            / totalExercises;
        }

        JLabel averageLabel =
                new JLabel(
                        "Weekly Average: "
                                + weeklyPercentage
                                + "%    •    "
                                + totalCompleted
                                + " / "
                                + totalExercises
                                + " exercises completed"
                );

        averageLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        averageLabel.setForeground(
                SUCCESS
        );

        section.add(
                averageLabel,
                BorderLayout.SOUTH
        );

        return section;
    }

    // =========================================================
    // DAY CARD
    // =========================================================

    private JPanel createDayProgressCard(
            WeeklyProgress day
    ) {

        JPanel card =
                new JPanel();

        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
        );

        card.setBackground(
                CARD_LIGHT
        );

        card.setBorder(
                new EmptyBorder(
                        10,
                        8,
                        10,
                        8
                )
        );

        JLabel dayLabel =
                new JLabel(
                        day.getDayName()
                );

        dayLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        dayLabel.setForeground(
                TEXT
        );

        dayLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel dateLabel =
                new JLabel(
                        day.getDate()
                                .substring(5)
                );

        dateLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        11
                )
        );

        dateLabel.setForeground(
                MUTED
        );

        dateLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JProgressBar bar =
                new JProgressBar(
                        0,
                        100
                );

        bar.setValue(
                day.getPercentage()
        );

        bar.setStringPainted(
                true
        );

        bar.setString(
                day.getPercentage() + "%"
        );

        bar.setForeground(
                SUCCESS
        );

        bar.setBackground(
                BACKGROUND
        );

        bar.setPreferredSize(
                new Dimension(
                        100,
                        22
                )
        );

        JLabel countLabel =
                new JLabel(
                        day.getCompleted()
                                + " / "
                                + day.getTotal()
                );

        countLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        countLabel.setForeground(
                MUTED
        );

        countLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        card.add(dayLabel);

        card.add(
                Box.createVerticalStrut(3)
        );

        card.add(dateLabel);

        card.add(
                Box.createVerticalStrut(10)
        );

        card.add(bar);

        card.add(
                Box.createVerticalStrut(7)
        );

        card.add(countLabel);

        return card;
    }

    // =========================================================
    // LOAD PROGRESS
    // =========================================================

    private void loadProgress() {

        exercisePanel.removeAll();

        ExerciseCompletionDAO dao =
                new ExerciseCompletionDAO();

        List<ExerciseItem> exercises =
                dao.getTodayExercises(
                        memberId
                );

        if (exercises.isEmpty()) {

            JLabel emptyLabel =
                    new JLabel(
                            "No active workout exercises available."
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

            exercisePanel.add(
                    emptyLabel
            );

            updateProgress(
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

                exercisePanel.add(
                        createExerciseCard(
                                exercise
                        )
                );

                exercisePanel.add(
                        Box.createVerticalStrut(10)
                );
            }

            updateProgress(
                    completed,
                    exercises.size()
            );
        }

        exercisePanel.revalidate();

        exercisePanel.repaint();
    }

    // =========================================================
    // EXERCISE CARD
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
                        20,
                        15,
                        20
                )
        );

        card.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        100
                )
        );

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

        JLabel exerciseName =
                new JLabel(
                        exercise.getExerciseName()
                );

        exerciseName.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        17
                )
        );

        exerciseName.setForeground(
                TEXT
        );

        JLabel workoutDetails =
                new JLabel(
                        buildExerciseDetails(
                                exercise
                        )
                );

        workoutDetails.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        workoutDetails.setForeground(
                MUTED
        );

        details.add(
                exerciseName
        );

        details.add(
                Box.createVerticalStrut(5)
        );

        details.add(
                workoutDetails
        );

        JCheckBox completed =
                new JCheckBox(
                        "Completed"
                );

        completed.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        completed.setForeground(
                SUCCESS
        );

        completed.setBackground(
                CARD
        );

        completed.setFocusPainted(
                false
        );

        completed.setSelected(
                exercise.isCompleted()
        );

        completed.addActionListener(
                e -> {

                    ExerciseCompletionDAO dao =
                            new ExerciseCompletionDAO();

                    boolean success;

                    if (completed.isSelected()) {

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
                                "Unable to update exercise progress.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        );

                        completed.setSelected(
                                exercise.isCompleted()
                        );

                        return;
                    }

                    exercise.setCompleted(
                            completed.isSelected()
                    );

                    updateProgressFromExercises();
                }
        );

        card.add(
                details,
                BorderLayout.CENTER
        );

        card.add(
                completed,
                BorderLayout.EAST
        );

        return card;
    }

    // =========================================================
    // EXERCISE DETAILS
    // =========================================================

    private String buildExerciseDetails(
            ExerciseItem exercise
    ) {

        StringBuilder text =
                new StringBuilder();

        if (exercise.getSets() != null) {

            text.append(
                    "Sets: "
            );

            text.append(
                    exercise.getSets()
            );
        }

        if (exercise.getRepetitions() != null) {

            if (text.length() > 0) {
                text.append("   •   ");
            }

            text.append(
                    "Reps: "
            );

            text.append(
                    exercise.getRepetitions()
            );
        }

        if (exercise.getDurationMinutes() != null) {

            if (text.length() > 0) {
                text.append("   •   ");
            }

            text.append(
                    "Duration: "
            );

            text.append(
                    exercise.getDurationMinutes()
            );

            text.append(
                    " min"
            );
        }

        if (text.length() == 0) {
            text.append("Workout exercise");
        }

        return text.toString();
    }

    // =========================================================
    // UPDATE DAILY PROGRESS
    // =========================================================

    private void updateProgress(
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

        percentageLabel.setText(
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

    private void updateProgressFromExercises() {

        int completed = 0;
        int total = 0;

        for (
                Component component :
                exercisePanel.getComponents()
        ) {

            if (!(component instanceof JPanel)) {
                continue;
            }

            JCheckBox checkBox =
                    findCheckBox(
                            (JPanel) component
                    );

            if (checkBox != null) {

                total++;

                if (checkBox.isSelected()) {
                    completed++;
                }
            }
        }

        updateProgress(
                completed,
                total
        );

        // Refresh weekly section too
        refreshWeeklySection();
    }

    // =========================================================
    // REFRESH WEEKLY DATA
    // =========================================================

    private void refreshWeeklySection() {

        // Rebuild the entire UI so weekly values are refreshed.
        getContentPane().removeAll();

        buildUI();

        loadProgress();

        revalidate();

        repaint();
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