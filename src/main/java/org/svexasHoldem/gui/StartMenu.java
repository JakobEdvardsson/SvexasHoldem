package org.svexasHoldem.gui;

import org.svexasHoldem.client.ClientController;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

public class StartMenu extends JFrame {
    public static final Color POKER_GREEN = new Color(0, 128, 0);
    private JFrame frame;
    private JLabel usernameLabel;
    private JTextField username;
    private JButton button, button1, button2, button3, saveUsernameButton,
            joinLobby1, joinLobby2, joinLobby3, startGame, arrowButton;
    private JLabel label, stackValue, playerStackLabel, playersStackLabel;
    private JSlider stackSlide;
    private int playersStack;
    private static String usernameText;
    private Main main;
    private JList lobby1, lobby2, lobby3;
    private String[] lobby1Players = {"Player1", "Player2", "Player3", "Player4", "Player5"};
    private String[] lobby2Players = {"Player6", "Player7", "Player8", "Player9", "Player10"};
    private String[] lobby3Players = {"Player11", "Player12", "Player13", "Player14", "Player15"};
    LanguageState state = LanguageState.ENGLISH;
    private boolean playerInLobby;

    private ImageIcon flagIcon;

    ClientController controller;

    private int lobbyId;

    public void hideLobbyWindow() {
        frame.setVisible(false);
        playerInLobby = false;
        buttonDefaults();
    }

    private void buttonDefaults() {
        SwingUtilities.invokeLater(() -> {
            joinLobby1.setEnabled(true);
            joinLobby2.setEnabled(true);
            joinLobby3.setEnabled(true);
            joinLobby1.setText("Join lobby");
            joinLobby2.setText("Join lobby");
            joinLobby3.setText("Join lobby");
            repaint();
        });
    }

    public void showLobbyWindow() {
        frame.setVisible(true);
    }

    public void showStartMenu() {
        returnToMainMenu();
        frame.setVisible(true);
    }


    public enum LanguageState {
        ENGLISH,
        SWEDISH
    }

    ImageIcon[] slides = {
            ResourceManager.getIcon("/images/tutorial/Tutorial_1.png"),
            ResourceManager.getIcon("/images/tutorial/Tutorial_2.png"),
            ResourceManager.getIcon("/images/tutorial/Tutorial_3.png"),
            ResourceManager.getIcon("/images/tutorial/Tutorial_4.png"),
            ResourceManager.getIcon("/images/tutorial/Tutorial_5.png"),
            ResourceManager.getIcon("/images/tutorial/Tutorial_6.png"),
            ResourceManager.getIcon("/images/tutorial/Tutorial_7.png"),
            ResourceManager.getIcon("/images/tutorial/Tutorial_8.png")
    };

    public StartMenu(ClientController controller) {

        this.playerInLobby = false;
        this.controller = controller;

        setTitle("Start Menu");
        label = new JLabel("SvexasHoldem");
        label.setBounds(240, 80, 220, 80);
        label.setFont(new Font("Arial", Font.BOLD, 30));

        frame = new JFrame("Start menu");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(label);

        usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(300, 200, 165, 50);
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        frame.add(usernameLabel);

        username = new JTextField();
        username.setBounds(178, 250, 345, 40);
        username.setBackground(Color.white);
        username.setVisible(true);
        frame.add(username);

        saveUsernameButton = new JButton("Save username");
        saveUsernameButton.setBounds(275, 300, 150, 50);
        saveUsernameButton.setVisible(true);
        frame.add(saveUsernameButton);
        saveUsernameButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                controller.setUsername(username.getText());
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

    public StartMenu(){
    }

    public ImageIcon[] getSlides() {
        return slides;
    }

    public void initializeGUI() {
        frame.getContentPane().remove(username);
        frame.getContentPane().remove(usernameLabel);
        frame.getContentPane().remove(saveUsernameButton);

        frame.revalidate();
        frame.repaint();

        button = new JButton("Play offline");
        button.setBounds(70, 220, 150, 50);
        button.addActionListener(e -> {
            System.out.println("New game button pressed. Username: " + username.getText());
            main = new Main(getUsernameText(), this);
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

        String imgSource = getImagePath();
        createFlagButton(imgSource);

    }

    private void createFlagButton(String imgSource) {

        flagIcon = ResourceManager.getIcon(imgSource);
        Image originalImage = flagIcon.getImage();

        int newWidth = 55;
        int newHeight = 40;

        Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        button3 = new JButton(scaledIcon);
        button3.setBounds(323, 400, newWidth, newHeight);
        button3.addActionListener(e -> changeLanguage());
        button3.setOpaque(false);
        button3.setContentAreaFilled(false);
        button3.setBorderPainted(false);
        frame.add(button3);
    }

    public int getLobbyId() {
        return lobbyId;
    }

    public void setLobbyId(int lobbyId) {
        this.lobbyId = lobbyId;
    }

    private void lobbyView() {
        button.setVisible(false);
        button1.setVisible(false);
        button2.setVisible(false);
        button3.setVisible(false);
        username.setVisible(false);
        usernameLabel.setVisible(false);

        label.setBounds(300,50,220,80);
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
        stackSlide.setBounds(410, 150, 250, 75);
        stackSlide.setMajorTickSpacing(1000);
        stackSlide.setMinorTickSpacing(200);
        stackSlide.setPaintLabels(true);
        stackSlide.setSnapToTicks(true);
        stackSlide.setMinimum(500);
        stackSlide.setMaximum(5000);
        stackSlide.setPaintTicks(true);
        playersStackLabel = new JLabel("Value: ");
        playersStackLabel.setBounds(360, 250, 100, 50);
        frame.add(playersStackLabel);

        stackValue = new JLabel("5000");
        stackValue.setBackground(Color.white);
        stackValue.setOpaque(true);
        stackValue.setBounds(410, 250, 100, 50);
        stackValue.setHorizontalAlignment(JLabel.CENTER);
        frame.add(stackValue);

        joinLobby1 = new JButton("Join lobby");
        joinLobby1.setBounds(20, 320, 100, 40);
        joinLobby1.addActionListener(e -> {
            if (joinLobby1.getText().equals("Join lobby")) {
                playerInLobby = true;
                startGame.setEnabled(true);
                //joinLobby1.setEnabled(!button.isEnabled());
                joinLobby2.setEnabled(false);
                joinLobby3.setEnabled(false);
                setLobbyId(0);
                controller.joinLobby(lobbyId);
                joinLobby1.setText("Leave lobby");
            } else {
                playerInLobby = false;
                //controller.leaveLobby(0);
                joinLobby2.setEnabled(true);
                joinLobby3.setEnabled(true);
                joinLobby1.setText("Join lobby");
                startGame.setEnabled(false);
                controller.leaveLobby(lobbyId);
            }
        });
        frame.add(joinLobby1);

        joinLobby2 = new JButton("Join lobby");
        joinLobby2.setBounds(130, 320, 100, 40);
        joinLobby2.addActionListener(e -> {
            if(joinLobby2.getText().equals("Join lobby")) {
                playerInLobby = true;
                startGame.setEnabled(true);
                //joinLobby2.setEnabled(!button.isEnabled());
                joinLobby1.setEnabled(false);
                joinLobby3.setEnabled(false);
                setLobbyId(1);
                controller.joinLobby(lobbyId);
                joinLobby2.setText("Leave lobby");
            } else {
                playerInLobby = false;
                //controller.leaveLobby(1);
                joinLobby1.setEnabled(true);
                joinLobby3.setEnabled(true);
                joinLobby2.setText("Join lobby");
                startGame.setEnabled(false);
                controller.leaveLobby(lobbyId);
            }
        });
        frame.add(joinLobby2);

        joinLobby3 = new JButton("Join lobby");
        joinLobby3.setBounds(240, 320, 100, 40);
        joinLobby3.addActionListener(e -> {
            if(joinLobby3.getText().equals("Join lobby")) {
                playerInLobby = true;
                startGame.setEnabled(true);
                //joinLobby3.setEnabled(!button.isEnabled());
                joinLobby1.setEnabled(false);
                joinLobby2.setEnabled(false);
                setLobbyId(2);
                controller.joinLobby(lobbyId);
                joinLobby3.setText("Leave lobby");
            } else {
                playerInLobby = false;
                //controller.leaveLobby(2);
                joinLobby1.setEnabled(true);
                joinLobby2.setEnabled(true);
                joinLobby3.setText("Join lobby");
                startGame.setEnabled(false);
                controller.leaveLobby(lobbyId);
            }
        });
        frame.add(joinLobby3);

        startGame = new JButton("Start Game");
        startGame.setBounds(275, 390, 150, 40);
        startGame.addActionListener(e -> {
            controller.startGame(new BigDecimal(stackSlide.getValue()));
            System.out.println("Game started");
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


        ImageIcon originalIcon = ResourceManager.getIcon("/images/leftarrow.png");
        Image originalImage = originalIcon.getImage();

        int newWidth = 40;
        int newHeight = 40;

        Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        arrowButton = new JButton(scaledIcon);
        arrowButton.setBounds(20, 20, newWidth, newHeight);
        arrowButton.setOpaque(false);
        arrowButton.setContentAreaFilled(false);
        arrowButton.setBorderPainted(false);
        arrowButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                returnToMainMenu();
            });
        });
        frame.add(arrowButton);

        lobby1 = new JList<>(lobby1Players);
        lobby1.setBounds(20, 150, 100, 150);
        addListWithBorder(lobby1, "Lobby 1", 20, 150, 100, 150);
        lobby1.setVisible(true);
        frame.add(lobby1);

        lobby2 = new JList<>(lobby2Players);
        lobby2.setBounds(130, 150, 100, 150);
        addListWithBorder(lobby2, "Lobby 2", 130, 150, 100, 150);
        lobby2.setVisible(true);
        frame.add(lobby2);

        lobby3 = new JList<>(lobby3Players);
        lobby3.setBounds(240, 150, 100, 150);
        addListWithBorder(lobby3, "Lobby 3", 240, 150, 100, 150);
        lobby3.setVisible(true);
        frame.add(lobby3);

        frame.revalidate();
        frame.repaint();

    }

    private void returnToMainMenu() {
        controller.disconnectClient();
        arrowButton.setVisible(false);
        stackSlide.setVisible(false);
        startGame.setVisible(false);
        joinLobby1.setVisible(false);
        joinLobby2.setVisible(false);
        joinLobby3.setVisible(false);
        lobby1.setVisible(false);
        lobby2.setVisible(false);
        lobby3.setVisible(false);
        stackSlide.setVisible(false);
        stackValue.setVisible(false);
        playerStackLabel.setVisible(false);
        playersStackLabel.setVisible(false);
        label.setBounds(240, 80, 220, 80);
        label.setText("SvexasHoldem");
        frame.repaint();
        initializeGUI();
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
                label.setText("SvexasHoldem");
                label.setBounds(260, 80, 220, 80);
                button.setText("Spela offline");
                button1.setText("Spela online");
                button2.setText("Instruktioner");
                state = LanguageState.SWEDISH;
                break;
            case SWEDISH:
                label.setText("SvexasHoldem");
                label.setBounds(240, 80, 220, 80);
                button.setText("Play offline");
                button1.setText("Play Online");
                button2.setText("Tutorial");
                state = LanguageState.ENGLISH;
                break;
        }
        // Ta bort den befintliga knappen (button3)
        frame.remove(button3);
        String imgPath = getImagePath();
        createFlagButton(imgPath);

        frame.revalidate();
        frame.repaint();

    }

    private String getImagePath() {
        String imgPath = (state == LanguageState.ENGLISH) ?
                "/images/sverige.png" :
                "/images/uk.png";
        return imgPath;
    }

    /**
     * If the lobby is joinable, allow a user to join.
     * @param lobby String[] with the lobby information
     * @return true if joinable, false otherwise
     */
    private boolean lobbyIsJoinable(String[] lobby) {
        if (lobby[0].equals("Running")) return false;
        boolean hasNull = false;

        for (int i = 1; i < lobby.length; i++) {
            if (lobby[i] == null) return true;
        }

        return hasNull;
    }

    public void setLobbyInfo(String[][] info) {

        String[] lobby1PlayerInfo = new String[4];
        System.arraycopy(info[0], 1, lobby1PlayerInfo, 0, 4);

        String[] lobby2PlayerInfo = new String[4];
        System.arraycopy(info[1], 1, lobby2PlayerInfo, 0, 4);

        String[] lobby3PlayerInfo = new String[4];
        System.arraycopy(info[2], 1, lobby3PlayerInfo, 0, 4);

        SwingUtilities.invokeLater(() -> {
            if (lobby1 != null) {
                lobby1.setListData(lobby1PlayerInfo);
                lobby2.setListData(lobby2PlayerInfo);
                lobby3.setListData(lobby3PlayerInfo);
                repaint();

                if (!playerInLobby) {
                    if (joinLobby1.getText().equals("Join lobby")) {
                        joinLobby1.setEnabled(lobbyIsJoinable(info[0]));
                    }

                    if (joinLobby2.getText().equals("Join lobby")) {
                        joinLobby2.setEnabled(lobbyIsJoinable(info[1]));
                    }

                    if (joinLobby3.getText().equals("Join lobby")) {
                        joinLobby3.setEnabled(lobbyIsJoinable(info[2]));
                    }
                }
            }
        });
    }

    public void disableButtonIfEmpty(String[] lobby) {
        for (int i = 0; i < lobby.length; i++) {
            if (lobby[i] != null) break;

        }
    }

    public void disconnectPlayer(){
        controller.disconnectClient();
    }
    public void showTutorial(ImageIcon[] slides) {
        TutorialSlideshow tutorialSlideshow = new TutorialSlideshow(this, slides);
        tutorialSlideshow.setVisible(true);
    }

    private void addListWithBorder(JList<String> list, String title, int x, int y, int width, int height) {
        list.setBounds(x, y, width, height);
        list.setVisible(true);

        // Skapa en TitledBorder med den angivna titeln
        TitledBorder border = BorderFactory.createTitledBorder(title);
        // Ställ in titelns justering till mitten
        border.setTitleJustification(TitledBorder.CENTER);
        // Använd TitledBorder som en ram runt listan
        list.setBorder(border);

        frame.add(list);
    }
}
