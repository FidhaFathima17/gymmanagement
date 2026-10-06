package com.gym.view;

import com.gym.dao.GymPackageDAO;
import com.gym.model.GymPackage;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class PublicHomeFrame extends JFrame {

    private JPanel packageContainer;

    private final Color BACKGROUND =
            new Color(12, 15, 25);

    private final Color CARD =
            new Color(25, 29, 42);

    private final Color CARD_BORDER =
            new Color(55, 62, 82);

    private final Color PRIMARY =
            new Color(0, 210, 255);

    private final Color SECONDARY =
            new Color(130, 80, 255);

    private final Color WHITE =
            new Color(245, 247, 250);

    private final Color MUTED =
            new Color(170, 178, 195);


    public PublicHomeFrame() {

        setTitle("Gym Management System");

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setSize(1250, 800);

        setMinimumSize(
                new Dimension(1000, 700)
        );

        setLocationRelativeTo(null);

        buildUI();

        loadPackages();
    }


    // =====================================================
    // BUILD USER INTERFACE
    // =====================================================

    private void buildUI() {

        JPanel mainPanel =
                new JPanel(new BorderLayout());

        mainPanel.setBackground(BACKGROUND);


        // =================================================
        // HEADER
        // =================================================

        JPanel header =
                new JPanel(new BorderLayout());

        header.setBackground(
                new Color(18, 22, 35)
        );

        header.setBorder(
                new EmptyBorder(
                        18,
                        35,
                        18,
                        35
                )
        );


        JLabel logo =
                new JLabel(
                        "GYM MANAGEMENT SYSTEM"
                );

        logo.setForeground(WHITE);

        logo.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        22
                )
        );


        JPanel navigation =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                12,
                                0
                        )
                );

        navigation.setOpaque(false);


        JButton loginButton =
                createButton(
                        "LOGIN",
                        PRIMARY
                );


        JButton registerButton =
                createButton(
                        "REGISTER",
                        SECONDARY
                );


        loginButton.addActionListener(
                e -> openLogin()
        );


        registerButton.addActionListener(
                e -> openRegistration(-1)
        );


        navigation.add(loginButton);

        navigation.add(registerButton);


        header.add(
                logo,
                BorderLayout.WEST
        );

        header.add(
                navigation,
                BorderLayout.EAST
        );


        // =================================================
        // MAIN CONTENT
        // =================================================

        JPanel content =
                new JPanel(
                        new BorderLayout()
                );

        content.setBackground(
                BACKGROUND
        );


        // =================================================
        // HERO SECTION
        // =================================================

        JPanel hero =
                new JPanel();

        hero.setLayout(
                new BoxLayout(
                        hero,
                        BoxLayout.Y_AXIS
                )
        );

        hero.setBackground(
                BACKGROUND
        );

        hero.setBorder(
                new EmptyBorder(
                        35,
                        40,
                        25,
                        40
                )
        );


        JLabel title =
                new JLabel(
                        "Transform Your Body. Transform Your Life."
                );

        title.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        title.setForeground(
                WHITE
        );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        32
                )
        );


        JLabel subtitle =
                new JLabel(
                        "Choose a membership plan that fits your fitness journey."
                );

        subtitle.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        subtitle.setForeground(
                MUTED
        );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        16
                )
        );


        hero.add(title);

        hero.add(
                Box.createVerticalStrut(10)
        );

        hero.add(subtitle);


        // =================================================
        // PACKAGE TITLE
        // =================================================

        JPanel packageTitlePanel =
                new JPanel(
                        new BorderLayout()
                );

        packageTitlePanel.setBackground(
                BACKGROUND
        );

        packageTitlePanel.setBorder(
                new EmptyBorder(
                        5,
                        40,
                        10,
                        40
                )
        );


        JLabel packageTitle =
                new JLabel(
                        "Our Membership Packages"
                );

        packageTitle.setForeground(
                WHITE
        );

        packageTitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        24
                )
        );


        packageTitlePanel.add(
                packageTitle,
                BorderLayout.WEST
        );


        // =================================================
        // PACKAGE CONTAINER
        // =================================================

        packageContainer =
                new JPanel(
                        new GridLayout(
                                0,
                                3,
                                20,
                                20
                        )
                );

        packageContainer.setBackground(
                BACKGROUND
        );

        packageContainer.setBorder(
                new EmptyBorder(
                        10,
                        35,
                        35,
                        35
                )
        );


        JScrollPane scrollPane =
                new JScrollPane(
                        packageContainer
                );

        scrollPane.setBorder(null);

        scrollPane.setBackground(
                BACKGROUND
        );

        scrollPane.getViewport()
                .setBackground(
                        BACKGROUND
                );

        scrollPane
                .getVerticalScrollBar()
                .setUnitIncrement(16);


        // =================================================
        // CONTENT LAYOUT
        // =================================================

        JPanel topSection =
                new JPanel(
                        new BorderLayout()
                );

        topSection.setBackground(
                BACKGROUND
        );

        topSection.add(
                hero,
                BorderLayout.NORTH
        );

        topSection.add(
                packageTitlePanel,
                BorderLayout.SOUTH
        );


        content.add(
                topSection,
                BorderLayout.NORTH
        );

        content.add(
                scrollPane,
                BorderLayout.CENTER
        );


        // =================================================
        // MAIN FRAME
        // =================================================

        mainPanel.add(
                header,
                BorderLayout.NORTH
        );

        mainPanel.add(
                content,
                BorderLayout.CENTER
        );


        setContentPane(
                mainPanel
        );
    }


    // =====================================================
    // LOAD ACTIVE PACKAGES
    // =====================================================

    private void loadPackages() {

        packageContainer.removeAll();


        GymPackageDAO dao =
                new GymPackageDAO();


        List<GymPackage> packages =
                dao.getActivePackages();


        if (packages.isEmpty()) {

            packageContainer.setLayout(
                    new GridBagLayout()
            );


            JLabel noPackages =
                    new JLabel(
                            "No membership packages are currently available."
                    );

            noPackages.setForeground(
                    MUTED
            );

            noPackages.setFont(
                    new Font(
                            "Segoe UI",
                            Font.PLAIN,
                            16
                    )
            );


            packageContainer.add(
                    noPackages
            );

        } else {

            packageContainer.setLayout(
                    new GridLayout(
                            0,
                            3,
                            20,
                            20
                    )
            );


            for (
                    GymPackage gymPackage :
                    packages
            ) {

                packageContainer.add(
                        createPackageCard(
                                gymPackage
                        )
                );
            }
        }


        packageContainer.revalidate();

        packageContainer.repaint();
    }


    // =====================================================
    // PACKAGE CARD
    // =====================================================

    private JPanel createPackageCard(
            GymPackage gymPackage
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
                CARD
        );


        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                CARD_BORDER,
                                1
                        ),
                        new EmptyBorder(
                                25,
                                25,
                                25,
                                25
                        )
                )
        );


        // PACKAGE NAME
        JLabel name =
                new JLabel(
                        gymPackage.getPackageName()
                );

        name.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        name.setForeground(
                WHITE
        );

        name.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        22
                )
        );


        // DURATION
        JLabel duration =
                new JLabel(
                        gymPackage.getDuration()
                                + " "
                                + gymPackage.getDurationUnit()
                );

        duration.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        duration.setForeground(
                PRIMARY
        );

        duration.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        16
                )
        );


        // PRICE
        JLabel price =
                new JLabel(
                        "₹ "
                                + String.format(
                                "%.2f",
                                gymPackage.getPrice()
                        )
                );

        price.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        price.setForeground(
                WHITE
        );

        price.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        28
                )
        );


        // DESCRIPTION
        JTextArea description =
                new JTextArea(
                        gymPackage.getDescription()
                                == null
                                ? ""
                                : gymPackage.getDescription()
                );

        description.setEditable(false);

        description.setLineWrap(true);

        description.setWrapStyleWord(true);

        description.setOpaque(false);

        description.setForeground(
                MUTED
        );

        description.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );


        // FEATURES
        JTextArea features =
                new JTextArea(
                        gymPackage.getFeatures()
                                == null
                                ? ""
                                : gymPackage.getFeatures()
                );

        features.setEditable(false);

        features.setLineWrap(true);

        features.setWrapStyleWord(true);

        features.setOpaque(false);

        features.setForeground(
                new Color(
                        195,
                        202,
                        218
                )
        );

        features.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );


        // CHOOSE BUTTON
        JButton chooseButton =
                createButton(
                        "CHOOSE THIS PLAN",
                        SECONDARY
                );


        chooseButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        chooseButton.addActionListener(
                e -> openRegistration(
                        gymPackage.getPackageId()
                )
        );


        // ADD COMPONENTS

        card.add(name);

        card.add(
                Box.createVerticalStrut(10)
        );

        card.add(duration);

        card.add(
                Box.createVerticalStrut(8)
        );

        card.add(price);

        card.add(
                Box.createVerticalStrut(15)
        );

        card.add(description);

        card.add(
                Box.createVerticalStrut(10)
        );

        card.add(features);

        card.add(
                Box.createVerticalGlue()
        );

        card.add(
                Box.createVerticalStrut(20)
        );

        card.add(chooseButton);


        return card;
    }


    // =====================================================
    // CREATE BUTTON
    // =====================================================

    private JButton createButton(
            String text,
            Color color
    ) {

        JButton button =
                new JButton(text);


        button.setForeground(
                Color.WHITE
        );

        button.setBackground(
                color
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

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );


        button.setPreferredSize(
                new Dimension(
                        150,
                        40
                )
        );


        return button;
    }


    // =====================================================
    // OPEN LOGIN
    // =====================================================

    private void openLogin() {

        LoginFrame loginFrame =
                new LoginFrame();

        loginFrame.setVisible(true);

        dispose();
    }


    // =====================================================
    // OPEN MEMBER REGISTRATION
    // =====================================================

    private void openRegistration(
            int packageId
    ) {

        /*
         * This class will be created in the
         * next step.
         */

        MemberRegistrationFrame registrationFrame;


        if (packageId == -1) {

            registrationFrame =
                    new MemberRegistrationFrame();

        } else {

            registrationFrame =
                    new MemberRegistrationFrame(
                            packageId
                    );
        }


        registrationFrame.setVisible(true);

        dispose();
    }
}