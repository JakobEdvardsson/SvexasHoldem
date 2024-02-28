package org.pokergame.gui;

import org.pokergame.client.ClientOutputX;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class StartMenu extends JFrame {

    public static final Color POKER_GREEN = new Color(0,153, 0);
    private JFrame frame;
    private JLabel usernameLabel;
    private JTextField username;
    private  JButton button, button1, button2, button3, saveUsernameButton;
    private JLabel label, label1, playerStackLabel;
    private JSlider stackSlide;
    private int playersStack;
    private ClientOutputX clientOutput;
    private static String usernameText;
    private JList onlineUserRoomList;
    private Main main;
    private JList lobby1, lobby2, lobby3;
    private String[] lobby1Players = {"Player1", "Player2", "Player3", "Player4", "Player5"};
    private String[] lobby2Players = {"Player6", "Player7", "Player8", "Player9", "Player10"};
    private String[] lobby3Players = {"Player11", "Player12", "Player13", "Player14", "Player15"};
    LanguageState state = LanguageState.ENGLISH;
    //private Main clientGUI;
  
    public enum LanguageState {
        ENGLISH,
        SWEDISH
    }

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
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("New game button pressed. Username: " + username.getText());
                main = new Main();
                frame.setVisible(false);
            }
        });
        frame.add(button);

        button1 = new JButton("Play online");
        button1.setBounds(265, 220, 150, 50);
        button1.addActionListener(e -> {
            usernameText = username.getText();
            System.out.println("Play online button pressed. Username: " + getUsernameText());
            lobbyView();
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
        playerStackLabel.setBounds(220, 400, 250, 50);
        frame.add(playerStackLabel);

        stackSlide = new JSlider(1000, 10000);
        stackSlide.setBounds(320, 400, 250, 50);
        stackSlide.setMajorTickSpacing(100);
        stackSlide.setPaintTicks(true);
        stackSlide.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider)e.getSource();
                if (!source.getValueIsAdjusting()) {
                    playersStack = source.getValue();
                }
            }
        });
        frame.add(stackSlide);

        lobby1 = new JList<>(lobby1Players);
        lobby1.setSize(70, 150);
        lobby1.setLocation(10, 100);
        lobby1.setVisible(true);
        frame.add(lobby1);

        lobby2 = new JList<>(lobby2Players);
        lobby2.setSize(70, 100);
        lobby2.setLocation(100, 100);
        lobby2.setVisible(true);
        frame.add(lobby2);

        lobby3 = new JList<>(lobby3Players);
        lobby1.setSize(70, 100);
        lobby1.setLocation(250, 100);
        lobby1.setVisible(true);
        frame.add(lobby3);

        frame.revalidate();
        frame.repaint();

        /**onlineUserRoomList = new JList<>();
        //onlineUserRoomList.setModel(null);
        onlineUserRoomList.setSize(300,600);
        onlineUserRoomList.setLocation(350,20);
        onlineUserRoomList.setVisible(true);
        frame.add(onlineUserRoomList);*/

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

    private void showTutorial(ImageIcon[] slides) {
        TutorialSlideshow tutorialSlideshow = new TutorialSlideshow(this, slides);
        tutorialSlideshow.setVisible(true);
    }
}
