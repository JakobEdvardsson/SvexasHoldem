package org.pokergame.gui;

import javax.swing.*;
import java.awt.*;

public class StartMenu extends JFrame {

    public static final Color POKER_GREEN = new Color(0,153,0);
    private JFrame frame;

    private JLabel usernameLabel;

    private JTextField username;
   private  JButton button, button1, button2;
    private JLabel label;

    public StartMenu() {
        setTitle("Start Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Label label = new Label("TeachMe poker");
        label.setBounds(260, 55, 220, 80);
        label.setFont(new Font("Arial", Font.BOLD, 30));

        frame = new JFrame("Start menu");
        frame.add(label);


        usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(70, 130, 165, 50);
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        frame.add(usernameLabel);

        username = new JTextField();
        username.setBounds(70, 170, 345, 40);
        username.setBackground(Color.white);
        username.setVisible(true);
        frame.add(username);

        button = new JButton("New game");
        button.setBounds(70, 220, 150, 50);
        button.addChangeListener(
                ea -> System.out.println("New game button pressed")
        );
        frame.add(button);
        button1 = new JButton("Play online");
        button1.setBounds(265, 220, 150, 50);
        button1.addChangeListener(
                e
                -> System.out.println("Play online button pressed")
        );
        frame.add(button1);
        button2 = new JButton("Tutorial");
        button2.setBounds(460, 220, 150, 50);
        button2.addChangeListener(
                e
                -> System.out.println("Tutorial button pressed")
        );
        frame.add(button2);

        frame.getContentPane().setBackground(POKER_GREEN);
        frame.setSize(700, 500);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}





