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

package org.pokergame.gui;

import org.pokergame.*;
import org.pokergame.actions.PlayerAction;
import org.pokergame.bots.BasicBot;
import org.pokergame.client.ClientController;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.*;

/**
 * The game's main frame.
 * 
 * This is the core class of the Swing UI client application.
 * 
 * @author Oscar Stigter
 */
public class OnlineMain extends JFrame implements Client {

    /** Serial version UID. */
    private static final long serialVersionUID = -5414633931666096443L;

    /** Table type (betting structure). */
    private static final TableType TABLE_TYPE = TableType.NO_LIMIT;

    /** The size of the big blind. */
    private static final BigDecimal BIG_BLIND = BigDecimal.valueOf(10);

    /** The GridBagConstraints. */
    private final GridBagConstraints gc;

    /** The board panel. */
    private final BoardPanel boardPanel;

    /** The control panel. */
    private final ControlPanel controlPanel;

    /** The player panels. */
    private final Map<String, PlayerPanel> playerPanels;

    /** The human player. */
    private final Player humanPlayer;

    /** The current dealer's name. */
    private String dealerName;

    /** The current actor's name. */
    private String actorName;

    private ClientController clientController;

    /**
     * Constructor.
     */
    public OnlineMain(String playerName, ClientController clientController) {
        super("Texas Hold'em poker");

        this.clientController = clientController;

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setBackground(UIConstants.TABLE_COLOR);
        setLayout(new GridBagLayout());

        gc = new GridBagConstraints();

        controlPanel = new ControlPanel(TABLE_TYPE);
        boardPanel = new BoardPanel(controlPanel);
        addComponent(boardPanel, 1, 1, 1, 1);

        /* The players at the table. */
        humanPlayer = new Player(playerName, new BigDecimal(500), this);

        playerPanels = new HashMap<String, PlayerPanel>();

        /* The table. */

        // Show the frame.

    }

    @Override
    public void joinedTable(TableType type, BigDecimal bigBlind, List<Player> players) {

        // TableType type, BigDecimal bigBlind, List<Player> players

        int i = 0;
        for (Player player : players) {
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

        for (Player player : players) {
            PlayerPanel playerPanel = playerPanels.get(player.getName());
            if (playerPanel != null) {
                playerPanel.update(player);
            }
        }

        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
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
        System.out.println(allowedActions.size());
        return controlPanel.getUserInput(minBet, humanPlayer.getCash(), allowedActions);
    }

    /**
     * Adds an UI component.
     * 
     * @param component
     *            The component.
     * @param x
     *            The column.
     * @param y
     *            The row.
     * @param width
     *            The number of columns to span.
     * @param height
     *            The number of rows to span.
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
     * @param isInTurn
     *            Whether the actor is in turn.
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
     * @param isDealer
     *            Whether the player is the dealer.
     */
    private void setDealer(boolean isDealer) {
        if (dealerName != null) {
            PlayerPanel playerPanel = playerPanels.get(dealerName);
            if (playerPanel != null) {
                playerPanel.setDealer(isDealer);
            }
        }
    }


}