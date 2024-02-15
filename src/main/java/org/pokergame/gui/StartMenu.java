package org.pokergame.gui;

import javax.swing.*;
import java.awt.*;

public class StartMenu extends JFrame {
    public StartMenu() {
        setTitle("Start Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); //Padding

        JLabel titleLabel = new JLabel("Start Menu");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4; //Left/right Padding
        add(titleLabel, gbc);

        String[] buttonNames = {"Button1", "Button2", "Button3", "Button4"};
        gbc.gridwidth = 1;
        gbc.gridy = 1;

        for (int i = 0; i < buttonNames.length; i++) {
            JButton button = new JButton(buttonNames[i]);
            gbc.gridx = i;
            add(button, gbc);
        }

        /*
        JButton button1 = new JButton("Test1");
        gbc.gridy = 2;
        add(button1, gbc);
         */

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
