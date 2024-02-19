package org.pokergame.gui;

import javax.swing.*;
import java.awt.*;

public class StartMenu extends JFrame {

    public static final Color POKER_GREEN = new Color(0, 153, 0);
    JFrame frame;
    JButton button, button1, button2;
    JLabel label;
    ImageIcon[] slides = {
            new ImageIcon("src/main/resources/images/tutorial1.png"),
            new ImageIcon("src/main/resources/images/tutorial2.png"),
            new ImageIcon("src/main/resources/images/tutorial3.png"),
            new ImageIcon("src/main/resources/images/tutorial4.png"),
            new ImageIcon("src/main/resources/images/tutorial5.png"),
            new ImageIcon("src/main/resources/images/tutorial6.png"),
            new ImageIcon("src/main/resources/images/tutorial7.png"),
            new ImageIcon("src/main/resources/images/tutorial8.png"),
            new ImageIcon("src/main/resources/images/tutorial9.png"),
            new ImageIcon("src/main/resources/images/tutorial10.png"),
            new ImageIcon("src/main/resources/images/tutorial11.png"),
            new ImageIcon("src/main/resources/images/tutorial12.png"),
            new ImageIcon("src/main/resources/images/tutorial13.png"),
            new ImageIcon("src/main/resources/images/tutorial14.png"),
            new ImageIcon("src/main/resources/images/tutorial15.png"),
            new ImageIcon("src/main/resources/images/tutorial16.png"),
            new ImageIcon("src/main/resources/images/tutorial17.png")
    };

    public StartMenu() {
        setTitle("Start Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        label = new JLabel("TeachMe poker");
        label.setBounds(260, 100, 220, 80);
        label.setFont(new Font("Arial", Font.BOLD, 28));

        frame = new JFrame("Start menu");
        frame.add(label);

        button = new JButton("New game");
        button.setBounds(70, 200, 150, 50);
        button.addActionListener(e -> System.out.println("New game button pressed"));
        frame.add(button);

        button1 = new JButton("Play online");
        button1.setBounds(265, 200, 150, 50);
        button1.addActionListener(e -> System.out.println("Play online button pressed"));
        frame.add(button1);

        button2 = new JButton("Tutorial");
        button2.setBounds(460, 200, 150, 50);
        button2.addActionListener(e -> showTutorial(slides));
        frame.add(button2);

        frame.getContentPane().setBackground(POKER_GREEN);
        frame.setSize(700, 500);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private void showTutorial(ImageIcon[] slides) {
        TutorialSlideshow tutorialSlideshow = new TutorialSlideshow(this, slides);
        tutorialSlideshow.setVisible(true);
    }
}
