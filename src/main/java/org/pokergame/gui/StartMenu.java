package org.pokergame.gui;

import javax.swing.*;
import java.awt.*;

public class StartMenu extends JFrame {

    public static final Color POKER_GREEN = new Color(0,153,0);
    JFrame frame;
    JButton button, button1, button2;
    JLabel label;

    public StartMenu() {
        setTitle("Start Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        frame = new JFrame("Start menu");

        button = new JButton("New game");
        button.setBounds(70, 200, 150, 50);
        frame.add(button);
        button1 = new JButton("Play online");
        button1.setBounds(265, 200, 150, 50);
        frame.add(button1);
        button2 = new JButton("Tutorial");
        button2.setBounds(460, 200, 150, 50);
        frame.add(button2);


        frame.setSize(700, 500);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setVisible(true);

        JPanel panel = new JPanel();
        panel.setBackground(POKER_GREEN);
        frame.add(panel);

        /*GridBagConstraints gbc = new GridBagConstraints();
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


        JButton button1 = new JButton("Test1");
        gbc.gridy = 2;
        add(button1, gbc);


        pack();
        setLocationRelativeTo(null);
        setVisible(true);*/
    }
}





