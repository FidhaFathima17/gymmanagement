package com.gym.view;

import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {

    public DashboardFrame(String username, String role) {
        setTitle("Gym Management System - Dashboard (" + role + ")");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Top Navigation / User Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        JLabel userLabel = new JLabel("Logged in as: " + username + " (" + role + ")");
        userLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        headerPanel.add(userLabel, BorderLayout.WEST);
        headerPanel.add(logoutBtn, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        // Tabbed Shell Container
        JTabbedPane tabbedPane = new JTabbedPane();

        // Load Module Panels
        tabbedPane.addTab("Members Management", new MemberManagementPanel());
        tabbedPane.addTab("Trainers & Equipment", new TrainerManagementPanel());
        tabbedPane.addTab("Analytics Dashboard", new AnalyticsPanel());
        tabbedPane.addTab("Attendance & Reports", new AttendancePanel());

        add(tabbedPane, BorderLayout.CENTER);
    }
}