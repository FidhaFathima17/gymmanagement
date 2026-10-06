package com.gym.view;

import com.gym.util.QRGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GymAttendanceQRFrame extends JFrame {

    private JLabel qrLabel;

    // This is the value that the scanner will read.
    private static final String GYM_QR_TOKEN =
            "GYM_ATTENDANCE_2026";

    public GymAttendanceQRFrame() {

        setTitle("Gym Attendance QR");
        setSize(600, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        buildUI();
        generateQR();
    }

    private void buildUI() {

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(
                new BoxLayout(
                        mainPanel,
                        BoxLayout.Y_AXIS
                )
        );

        mainPanel.setBackground(
                new Color(15, 18, 25)
        );

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        30,
                        30,
                        30,
                        30
                )
        );

        JLabel title = new JLabel(
                "🏋️ GYM ATTENDANCE"
        );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        28
                )
        );

        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel instruction = new JLabel(
                "Scan this QR code to mark attendance"
        );

        instruction.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        15
                )
        );

        instruction.setForeground(
                new Color(180, 190, 200)
        );

        instruction.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        qrLabel = new JLabel();

        qrLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel tokenLabel = new JLabel(
                "Gym Attendance QR"
        );

        tokenLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        16
                )
        );

        tokenLabel.setForeground(
                new Color(0, 200, 255)
        );

        tokenLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        mainPanel.add(title);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(instruction);
        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(qrLabel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(tokenLabel);

        add(mainPanel);
    }

    private void generateQR() {

        try {

            BufferedImage qrImage =
                    QRGenerator.generateQRCode(
                            GYM_QR_TOKEN,
                            400,
                            400
                    );

            qrLabel.setIcon(
                    new ImageIcon(qrImage)
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to generate QR code.\n\n"
                            + e.getMessage(),
                    "QR Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}