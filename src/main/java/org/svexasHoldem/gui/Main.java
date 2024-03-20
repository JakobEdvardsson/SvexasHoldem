// This file is part of the 'texasholdem' project, an open source
// Texas Hold'em poker application written in Java.
//
// Copyright 2009 Oscar Stigter
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.svexasHoldem.gui;

import org.svexasHoldem.*;
import org.svexasHoldem.actions.PlayerAction;
import org.svexasHoldem.bots.BasicBot;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.*;

import static org.svexasHoldem.gui.StartMenu.POKER_GREEN;

/**
 * The game's main frame.
 * <p>
 * This is the core class of the Swing UI client application.
 *
 * @author Oscar Stigter
 */
public class Main extends JFrame implements Client, IHandler {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -5414633931666096443L;

    /**
     * Table type (betting structure).
     */
    private static final TableType TABLE_TYPE = TableType.NO_LIMIT;

    /**
     * The size of the big blind.
     */
    private static final BigDecimal BIG_BLIND = BigDecimal.valueOf(10);

    /**
     * The starting cash per player.
     */
    private static final BigDecimal STARTING_CASH = BigDecimal.valueOf(500);

    /**
     * The GridBagConstraints.
     */
    private final GridBagConstraints gc;

    /**
     * The board panel.
     */
    private final BoardPanel boardPanel;

    /**
     * The control panel.
     */
    private final ControlPanel controlPanel;

    /**
     * The player panels.
     */
    private final Map<String, PlayerPanel> playerPanels;

    /**
     * The human player.
     */
    private final Player humanPlayer;

    /**
     * The current dealer's name.
     */
    private String dealerName;

    /**
     * The current actor's name.
     */
    private String actorName;

    /**
     * The start menu to callback.
     */
    private StartMenu menu;

    private JButton returnButton;

    private JButton tutorialButton;

    /**
     * Constructor.
     */
    public Main(String playerName, StartMenu menu) {
        super("SvexasHoldem - " + playerName);

        this.menu = menu;

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setBackground(UIConstants.TABLE_COLOR);
        setLayout(new GridBagLayout());

        gc = new GridBagConstraints();

        controlPanel = new ControlPanel(TABLE_TYPE);
        boardPanel = new BoardPanel(controlPanel, this);
        addComponent(boardPanel, 1, 1, 1, 1);

        /* The players at the table. */
        Map<String, Player> players = new LinkedHashMap<>();
        humanPlayer = new Player(playerName, STARTING_CASH, this);
        players.put("Player", humanPlayer);
        players.put("Joe", new Player("Joe", STARTING_CASH, new BasicBot(0, 75)));
        players.put("Mike", new Player("Mike", STARTING_CASH, new BasicBot(25, 50)));
        players.put("Eddie", new Player("Eddie", STARTING_CASH, new BasicBot(50, 25)));

        /* The table. */
        Table table = new Table(TABLE_TYPE, BIG_BLIND, null);
        for (Player player : players.values()) {
            table.addPlayer(player);
        }

        playerPanels = new HashMap<>();
        int i = 0;
        for (Player player : players.values()) {
            PlayerPanel panel = new PlayerPanel();
            playerPanels.put(player.getName(), panel);
            switch (i++) {
                case 0:
                    // North position.
                    addComponent(panel, 1, 0, 1, 1);
                    break;
                case 1:
                    // East position.
                    addComponent(panel, 2, 1, 1, 1);
                    break;
                case 2:
                    // South position.
                    addComponent(panel, 1, 2, 1, 1);
                    break;
                case 3:
                    // West position.
                    addComponent(panel, 0, 1, 1, 1);
                    break;
                default:
                    // Do nothing.
            }
        }

        returnButton = createCustomButton("/images/leftarrow.png");
        placeCustomButton(10,10,0,0, GridBagConstraints.NORTHWEST, returnButton);
        setUpCustomButton(returnButton);
        returnButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                JPanel panel = warningMessage("Are you sure you want to leave the game?");
                int result = getYesOrNoOption(panel);
                if (result == 1) {
                    boardPanel.returnFromGame("offline", null);
                }

            });
        });

        tutorialButton = createCustomButton("/images/questionmark.png");
        placeCustomButton(10, 10, 0, 0, GridBagConstraints.NORTHEAST, tutorialButton); // Use GridBagConstraints.NORTHEAST
        setUpCustomButton(tutorialButton);
        tutorialButton.addActionListener(e -> {
            StartMenu tutorial = new StartMenu();
            tutorial.showTutorial(tutorial.getSlides());
        });

        showHumanPlayerName();
        // Show the frame.
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        // Start the game.
        table.start();
    }

    private void showHumanPlayerName() {
        JLabel playerName = new JLabel("Player name: " + humanPlayer.getName());
        playerName.setFont(new Font("Arial", Font.BOLD, 20));
        playerName.setForeground(Color.YELLOW);
        playerName.setOpaque(true);
        playerName.setBackground(UIConstants.TABLE_COLOR);
        playerName.setHorizontalAlignment(SwingConstants.CENTER);
        playerName.setVerticalAlignment(SwingConstants.CENTER);
        playerName.setPreferredSize(new Dimension(250, 50));
        addComponent(playerName, 0, 0, 1, 1);
    }

    private JButton createCustomButton(String imgPath) {
        ImageIcon originalIcon = ResourceManager.getIcon(imgPath);
        Image originalImage = originalIcon.getImage();

        Image scaledImage = originalImage.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JButton customButton = new JButton(scaledIcon);

        return customButton;
    }

    public void placeCustomButton(int top, int left, int bottom, int right, int constraints, JButton button) {
        // Set layout constraints for the button
        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.gridx = (constraints == GridBagConstraints.NORTHWEST) ? 0 : 2; // column (0 for left, 2 for right)
        buttonConstraints.gridy = 0; // row
        buttonConstraints.anchor = constraints; // top left or top right corner
        buttonConstraints.insets = new Insets(top, left, bottom, right); // optional: adds some margin

        // Add the button to the layout
        getContentPane().add(button, buttonConstraints);
    }

    public void setUpCustomButton(JButton customButton){
        customButton.setOpaque(false);
        customButton.setContentAreaFilled(false);
        customButton.setBorderPainted(false);
    }

    @Override
    public void joinedTable(TableType type, BigDecimal bigBlind, List<Player> players) {
        for (Player player : players) {
            PlayerPanel playerPanel = playerPanels.get(player.getName());
            if (playerPanel != null) {
                playerPanel.update(player);
            }
        }
    }

    @Override
    public void messageReceived(String message) {
        boardPanel.setMessage(message);
        boardPanel.waitForUserInput();
    }

    @Override
    public void handStarted(Player dealer) {
        setDealer(false);
        dealerName = dealer.getName();
        setDealer(true);
    }

    @Override
    public void actorRotated(Player actor) {
        setActorInTurn(false);
        actorName = actor.getName();
        setActorInTurn(true);
    }

    @Override
    public void boardUpdated(List<Card> cards, BigDecimal bet, BigDecimal pot) {
        boardPanel.update(cards, bet, pot);
    }

    @Override
    public void playerUpdated(Player player) {
        PlayerPanel playerPanel = playerPanels.get(player.getName());
        if (playerPanel != null) {
            playerPanel.update(player);
        }
    }

    @Override
    public void playerActed(Player player) {
        String name = player.getName();
        PlayerPanel playerPanel = playerPanels.get(name);
        if (playerPanel != null) {
            playerPanel.update(player);
            PlayerAction action = player.getAction();
            if (action != null) {
                boardPanel.setMessage(String.format("%s %s.", name, action.getVerb()));
                if (player.getClient() != this) {
                    boardPanel.waitForUserInput();
                }
            }
        } else {
            throw new IllegalStateException(
                    String.format("No PlayerPanel found for player '%s'", name));
        }
    }

    @Override
    public PlayerAction act(BigDecimal minBet, BigDecimal currentBet, Set<PlayerAction> allowedActions) {
        boardPanel.setMessage("Please select an action:");
        return controlPanel.getUserInput(minBet, humanPlayer.getCash(), allowedActions);
    }

    /**
     * Adds an UI component.
     *
     * @param component The component.
     * @param x         The column.
     * @param y         The row.
     * @param width     The number of columns to span.
     * @param height    The number of rows to span.
     */
    private void addComponent(Component component, int x, int y, int width, int height) {
        gc.gridx = x;
        gc.gridy = y;
        gc.gridwidth = width;
        gc.gridheight = height;
        gc.anchor = GridBagConstraints.CENTER;
        gc.fill = GridBagConstraints.NONE;
        gc.weightx = 0.0;
        gc.weighty = 0.0;
        getContentPane().add(component, gc);
    }

    /**
     * Sets whether the actor  is in turn.
     *
     * @param isInTurn Whether the actor is in turn.
     */
    private void setActorInTurn(boolean isInTurn) {
        if (actorName != null) {
            PlayerPanel playerPanel = playerPanels.get(actorName);
            if (playerPanel != null) {
                playerPanel.setInTurn(isInTurn);
            }
        }
    }

    /**
     * Sets the dealer.
     *
     * @param isDealer Whether the player is the dealer.
     */
    private void setDealer(boolean isDealer) {
        if (dealerName != null) {
            PlayerPanel playerPanel = playerPanels.get(dealerName);
            if (playerPanel != null) {
                playerPanel.setDealer(isDealer);
            }
        }
    }


    @Override
    public void gameOver() {
        menu.showLobbyWindow();
        this.dispose();
    }

    @Override
    public void returnToMainMenu() {
    }


    private JPanel warningMessage(String warningMessage) {

        JPanel panel = new JPanel();
        panel.setBackground(POKER_GREEN);
        UIManager.put("OptionPane.background", POKER_GREEN);
        UIManager.put("Panel.background", POKER_GREEN);

        JLabel label = new JLabel(warningMessage);
        panel.add(label);

        return panel;
    }

    private int getYesOrNoOption(JPanel panel) {
        int result = JOptionPane.showConfirmDialog(null, panel, "Warning", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {// Koden för när användaren väljer Yes
            return 1;
        } else if (result == JOptionPane.NO_OPTION) {// Koden för när användaren väljer No
            return 0;
        }
        return -1;
    }


}
