package com.gym.view;

import com.github.sarxos.webcam.Webcam;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class QRScannerFrame extends JFrame {

    private final int memberId;

    private Webcam webcam;

    private JLabel cameraLabel;
    private JLabel statusLabel;

    private volatile boolean scanning = true;

    private static final String VALID_TOKEN =
            "GYM_ATTENDANCE_2026";

    public QRScannerFrame(int memberId) {

        this.memberId = memberId;

        setTitle("Scan Attendance QR");
        setSize(700, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        buildUI();
        startCamera();
    }

    // =========================================================
    // BUILD UI
    // =========================================================

    private void buildUI() {

        JPanel panel = new JPanel(
                new BorderLayout(10, 10)
        );

        panel.setBackground(
                new Color(15, 18, 25)
        );

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        15,
                        15,
                        15
                )
        );

        // Camera preview
        cameraLabel = new JLabel(
                "Starting camera...",
                SwingConstants.CENTER
        );

        cameraLabel.setForeground(Color.WHITE);

        cameraLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        18
                )
        );

        // Status
        statusLabel = new JLabel(
                "Point the camera at the gym QR code",
                SwingConstants.CENTER
        );

        statusLabel.setForeground(
                new Color(0, 200, 255)
        );

        statusLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        15
                )
        );

        panel.add(
                cameraLabel,
                BorderLayout.CENTER
        );

        panel.add(
                statusLabel,
                BorderLayout.SOUTH
        );

        add(panel);
    }

    // =========================================================
    // START CAMERA
    // =========================================================

    private void startCamera() {

        Thread cameraThread = new Thread(() -> {

            try {

                webcam = Webcam.getDefault();

                // -------------------------------------------------
                // Check webcam
                // -------------------------------------------------

                if (webcam == null) {

                    SwingUtilities.invokeLater(() ->
                            statusLabel.setText(
                                    "No webcam detected."
                            )
                    );

                    return;
                }

                // -------------------------------------------------
                // Open webcam
                // -------------------------------------------------

                webcam.open();

                SwingUtilities.invokeLater(() ->
                        statusLabel.setText(
                                "Camera ready - Scan the QR code"
                        )
                );

                // -------------------------------------------------
                // Camera loop
                // -------------------------------------------------

                while (
                        scanning &&
                                webcam.isOpen()
                ) {

                    BufferedImage image =
                            webcam.getImage();

                    if (image == null) {
                        continue;
                    }

                    // -------------------------------------------------
                    // Display camera image
                    // -------------------------------------------------

                    BufferedImage displayImage =
                            image;

                    SwingUtilities.invokeLater(() ->
                            cameraLabel.setIcon(
                                    new ImageIcon(
                                            displayImage
                                    )
                            )
                    );

                    // -------------------------------------------------
                    // Try to decode QR
                    // -------------------------------------------------

                    String result =
                            decodeQRCode(image);

                    if (result != null) {

                        // -------------------------------------------------
                        // Valid QR
                        // -------------------------------------------------

                        if (result.equals(
                                VALID_TOKEN
                        )) {

                            scanning = false;

                            SwingUtilities.invokeLater(
                                    this::processAttendance
                            );

                            break;

                        } else {

                            // -------------------------------------------------
                            // Invalid QR
                            // -------------------------------------------------

                            SwingUtilities.invokeLater(() ->
                                    statusLabel.setText(
                                            "Invalid gym QR code"
                                    )
                            );
                        }
                    }
                }

            } catch (Exception e) {

                e.printStackTrace();

                SwingUtilities.invokeLater(() ->
                        JOptionPane.showMessageDialog(
                                this,
                                "Camera error:\n\n"
                                        + e.getMessage(),
                                "Scanner Error",
                                JOptionPane.ERROR_MESSAGE
                        )
                );

            } finally {

                // -------------------------------------------------
                // Close webcam
                // -------------------------------------------------

                if (
                        webcam != null &&
                                webcam.isOpen()
                ) {

                    webcam.close();
                }
            }

        });

        cameraThread.setDaemon(true);
        cameraThread.start();
    }

    // =========================================================
    // DECODE QR CODE
    // =========================================================

    private String decodeQRCode(
            BufferedImage image
    ) {

        try {

            BufferedImageLuminanceSource source =
                    new BufferedImageLuminanceSource(
                            image
                    );

            BinaryBitmap bitmap =
                    new BinaryBitmap(
                            new HybridBinarizer(
                                    source
                            )
                    );

            Result result =
                    new MultiFormatReader()
                            .decode(bitmap);

            return result.getText();

        } catch (NotFoundException e) {

            // No QR found in this frame
            return null;

        } catch (Exception e) {

            return null;
        }
    }

    // =========================================================
    // PROCESS ATTENDANCE
    // =========================================================

    private void processAttendance() {

        JOptionPane.showMessageDialog(
                this,
                "QR code scanned successfully!\n\n"
                        + "Member ID: "
                        + memberId
                        + "\n\n"
                        + "QR Token: "
                        + VALID_TOKEN,
                "QR Attendance",
                JOptionPane.INFORMATION_MESSAGE
        );

        dispose();
    }

    // =========================================================
    // CLOSE WINDOW
    // =========================================================

    @Override
    public void dispose() {

        scanning = false;

        if (
                webcam != null &&
                        webcam.isOpen()
        ) {

            webcam.close();
        }

        super.dispose();
    }
}