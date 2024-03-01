package org.pokergame.gui;

import org.pokergame.client.ClientController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartMenu extends JFrame {
    public static final Color POKER_GREEN = new Color(0, 153, 0);
    private JFrame frame;
    private JLabel usernameLabel;
    private JTextField username;
    private JButton button, button1, button2, button3, saveUsernameButton, joinLobby1, joinLobby2, joinLobby3, startGame;
    private JLabel label, label1, playerStackLabel;
    private JSlider stackSlide;
    private int playersStack;
    private static String usernameText;
    private Main main;
    private JList lobby1, lobby2, lobby3;
    private String[] lobby1Players = {"Player1", "Player2", "Player3", "Player4", "Player5"};
    private String[] lobby2Players = {"Player6", "Player7", "Player8", "Player9", "Player10"};
    private String[] lobby3Players = {"Player11", "Player12", "Player13", "Player14", "Player15"};
    LanguageState state = LanguageState.ENGLISH;

    ClientController controller;

    public enum LanguageState {
        ENGLISH,
        SWEDISH
    }

    ImageIcon[] slides = {
            new ImageIcon("src/main/resources/images/tutorial/Tutorial_1.png"),
            new ImageIcon("src/main/resources/images/tutorial/Tutorial_2.png"),
            new ImageIcon("src/main/resources/images/tutorial/Tutorial_3.png"),
            new ImageIcon("src/main/resources/images/tutorial/Tutorial_4.png"),
            new ImageIcon("src/main/resources/images/tutorial/Tutorial_5.png"),
            new ImageIcon("src/main/resources/images/tutorial/Tutorial_6.png"),
            new ImageIcon("src/main/resources/images/tutorial/Tutorial_7.png"),
            new ImageIcon("src/main/resources/images/tutorial/Tutorial_8.png"),

    };

    public StartMenu(ClientController controller) {

        this.controller = controller;

        setTitle("Start Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        label = new JLabel("TeachMe poker");
        label.setBounds(250, 25, 220, 80);
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

        saveUsernameButton = new JButton("Save username");
        saveUsernameButton.setBounds(70, 220, 150, 50);
        saveUsernameButton.setVisible(true);
        frame.add(saveUsernameButton);
        saveUsernameButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setUsernameText(username.getText());
                SwingUtilities.invokeLater(() -> {
                    initializeGUI();
                });
            }
        });
        frame.getContentPane().setBackground(POKER_GREEN);
        frame.setSize(700, 500);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

    }

    private void initializeGUI() {
        frame.getContentPane().remove(username);
        frame.getContentPane().remove(usernameLabel);
        frame.getContentPane().remove(saveUsernameButton);

        frame.revalidate();
        frame.repaint();

        button = new JButton("Play offline");
        button.setBounds(70, 220, 150, 50);
        button.addActionListener(e -> {
            System.out.println("New game button pressed. Username: " + username.getText());
            main = new Main();
            frame.setVisible(false);
        });
        frame.add(button);

        button1 = new JButton("Play online");
        button1.setBounds(265, 220, 150, 50);
        button1.addActionListener(e -> {
            usernameText = username.getText();
            System.out.println("Play online button pressed. Username: " + getUsernameText());
            lobbyView();
            controller.playOnline();
        });
        frame.add(button1);

        button2 = new JButton("Tutorial");
        button2.setBounds(460, 220, 150, 50);
        button2.addActionListener(e -> showTutorial(slides));
        frame.add(button2);

        button3 = new JButton("Change language to Swedish");
        button3.setBounds(220, 400, 250, 50);
        button3.addActionListener(e -> changeLanguage());
        frame.add(button3);

    }

    private void lobbyView() {
        button.setVisible(false);
        button1.setVisible(false);
        button2.setVisible(false);
        button3.setVisible(false);
        username.setVisible(false);
        usernameLabel.setVisible(false);

        switch (state) {
            case ENGLISH:
                label.setText("Lobbies");
                playerStackLabel = new JLabel("Stack: ");
                break;
            case SWEDISH:
                label.setText("Spelrum");
                playerStackLabel = new JLabel("Pott: ");
                break;
        }
        playerStackLabel.setBounds(360, 175, 250, 50);
        frame.add(playerStackLabel);

        stackSlide = new JSlider(1000, 10000);
        stackSlide.setBounds(400, 150, 275, 75);
        stackSlide.setMajorTickSpacing(2500);
        stackSlide.setPaintLabels(true);
        stackSlide.setPaintTicks(true);
        JLabel playersStackLabel = new JLabel("Value: ");
        playersStackLabel.setBounds(360, 250, 100, 50);
        frame.add(playersStackLabel);

        JLabel stackValue = new JLabel("5000");
        stackValue.setBackground(Color.white);
        stackValue.setOpaque(true);
        stackValue.setBounds(400, 250, 100, 50);
        frame.add(stackValue);

        joinLobby1 = new JButton("Join lobby");
        joinLobby1.setBounds(10, 320, 100, 40);
        joinLobby1.addActionListener(e -> {
            if (joinLobby1.getText().equals("Join lobby")) {
                System.out.println("You joined lobby 1");
                startGame.setEnabled(true);
                //joinLobby1.setEnabled(!button.isEnabled());
                joinLobby2.setEnabled(false);
                joinLobby3.setEnabled(false);
                controller.joinLobby(0);
                joinLobby1.setText("Leave lobby");
            } else {
                //controller.leaveLobby(0);
                joinLobby2.setEnabled(true);
                joinLobby3.setEnabled(true);
                joinLobby1.setText("Join lobby");
                startGame.setEnabled(false);
                controller.leaveLobby(0);
            }
        });
        frame.add(joinLobby1);

        joinLobby2 = new JButton("Join lobby");
        joinLobby2.setBounds(120, 320, 100, 40);
        joinLobby2.addActionListener(e -> {
            if(joinLobby2.getText().equals("Join lobby")) {
                System.out.println("You joined lobby 2");
                startGame.setEnabled(true);
                //joinLobby2.setEnabled(!button.isEnabled());
                joinLobby1.setEnabled(false);
                joinLobby3.setEnabled(false);
                controller.joinLobby(1);
                joinLobby2.setText("Leave lobby");
            } else {
                //controller.leaveLobby(1);
                joinLobby1.setEnabled(true);
                joinLobby3.setEnabled(true);
                joinLobby2.setText("Join lobby");
                startGame.setEnabled(false);
                controller.leaveLobby(1);
            }
        });
        frame.add(joinLobby2);

        joinLobby3 = new JButton("Join lobby");
        joinLobby3.setBounds(230, 320, 100, 40);
        joinLobby3.addActionListener(e -> {
            if(joinLobby3.getText().equals("Join lobby")) {
                System.out.println("You joined lobby 3");
                startGame.setEnabled(true);
                //joinLobby3.setEnabled(!button.isEnabled());
                joinLobby1.setEnabled(false);
                joinLobby2.setEnabled(false);
                controller.joinLobby(2);
                joinLobby3.setText("Leave lobby");
            } else {
                //controller.leaveLobby(2);
                joinLobby1.setEnabled(true);
                joinLobby2.setEnabled(true);
                joinLobby3.setText("Join lobby");
                startGame.setEnabled(false);
                controller.leaveLobby(2);
            }
        });
        frame.add(joinLobby3);

        startGame = new JButton("Start Game");
        startGame.setBounds(275, 390, 150, 40);
        startGame.addActionListener(e -> {System.out.println("Game started");
        });
        startGame.setEnabled(false);
        frame.add(startGame);

        stackSlide.addChangeListener(e -> {
            JSlider source = (JSlider) e.getSource();
            if (!source.getValueIsAdjusting()) {
                playersStack = source.getValue();
                stackValue.setText(String.valueOf(playersStack));
            }
        });
        frame.add(stackSlide);

        lobby1 = new JList<>(lobby1Players);
        lobby1.setBounds(10, 150, 100, 150);
        lobby1.setVisible(true);
        frame.add(lobby1);

        lobby2 = new JList<>(lobby2Players);
        lobby2.setBounds(120, 150, 100, 150);
        lobby2.setVisible(true);
        frame.add(lobby2);

        lobby3 = new JList<>(lobby3Players);
        lobby3.setBounds(230, 150, 100, 150);
        lobby3.setVisible(true);
        frame.add(lobby3);

        frame.revalidate();
        frame.repaint();

    }

    public static String getUsernameText() {
        return usernameText;
    }


    public void setUsernameText(String usernameText) {
        this.usernameText = usernameText;
    }

    private void changeLanguage() {
        switch (state) {
            case ENGLISH:
                label.setText("LärMigPoker");
                button.setText("Spela offline");
                button1.setText("Spela online");
                button2.setText("Instruktioner");
                button3.setText("Ändra språk till Engelska");
                state = LanguageState.SWEDISH;
                break;
            case SWEDISH:
                label.setText("TeachMePoker");
                button.setText("Play offline");
                button1.setText("Play Online");
                button2.setText("Tutorial");
                button3.setText("Change language to Swedish");
                state = LanguageState.ENGLISH;
                break;
        }

    }

    public void setLobbyInfo(String[][] info) {
        SwingUtilities.invokeLater(() -> {
            lobby1Players = info[0];
            lobby2Players = info[1];
            lobby3Players = info[2];
            if (lobby1 != null) {
                lobby1.setListData(lobby1Players);
                lobby2.setListData(lobby2Players);
                lobby3.setListData(lobby3Players);
                repaint();
            }
        });
    }

    public void disableButtonIfEmpty(String[] lobby) {
        for (int i = 0; i < lobby.length; i++) {
            if (lobby[i] != null) break;

        }
    }

    private void showTutorial(ImageIcon[] slides) {
        TutorialSlideshow tutorialSlideshow = new TutorialSlideshow(this, slides);
        tutorialSlideshow.setVisible(true);
    }
}
