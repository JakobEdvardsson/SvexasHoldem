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

import org.svexasHoldem.Card;
import org.svexasHoldem.client.ClientController;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * Board panel with the community cards and general information.
 *  
 * @author Oscar Stigter
 */
public class BoardPanel extends JPanel {
    
    /** The serial version UID. */
    private static final long serialVersionUID = 8530615901667282755L;

    /** The maximum number of community cards. */
    private static final int NO_OF_CARDS = 5;
    
    /** The control panel. */
    private final ControlPanel controlPanel;
    
    /** Label with the bet. */
    private final JLabel betLabel;

    /** Label with the pot. */
    private final JLabel potLabel;

    /** Labels with the community cards. */
    private final JLabel[] cardLabels;
    
    /** Label with a custom message. */
    private final JLabel messageLabel;

    /** Label with a custom message. */
    private final JLabel timerLabel;

    /** Label with a custom message. */
    private final IHandler client;

    /** Timer to control acting **/
    Timer timer;

    
    /**
     * Constructor.
     * 
     * @param controlPanel
     *            The control panel.
     */
    public BoardPanel(ControlPanel controlPanel, IHandler client) {
        this.controlPanel = controlPanel;
        this.client = client;

        setBorder(UIConstants.PANEL_BORDER);
        setBackground(UIConstants.TABLE_COLOR);
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        
        JLabel label = new JLabel("Bet");
        label.setForeground(Color.GREEN);
        gc.gridx = 1;
        gc.gridy = 0;
        gc.gridwidth = 1;
        gc.gridheight = 1;
        gc.anchor = GridBagConstraints.CENTER;
        gc.fill = GridBagConstraints.NONE;
        gc.weightx = 1.0;
        gc.weighty = 0.0;
        gc.insets = new Insets(0, 5, 0, 5);
        add(label, gc);
        
        label = new JLabel("Pot");
        label.setForeground(Color.GREEN);
        gc.gridx = 3;
        gc.gridy = 0;
        gc.gridwidth = 1;
        gc.gridheight = 1;
        gc.anchor = GridBagConstraints.CENTER;
        gc.fill = GridBagConstraints.NONE;
        gc.weightx = 1.0;
        gc.weighty = 0.0;
        gc.insets = new Insets(0, 5, 0, 5);
        add(label, gc);
        
        betLabel = new JLabel(" ");
        betLabel.setBorder(UIConstants.LABEL_BORDER);
        betLabel.setForeground(Color.GREEN);
        betLabel.setHorizontalAlignment(JLabel.CENTER);
        gc.gridx = 1;
        gc.gridy = 1;
        gc.gridwidth = 1;
        gc.gridheight = 1;
        gc.anchor = GridBagConstraints.CENTER;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1.0;
        gc.weighty = 0.0;
        gc.insets = new Insets(5, 5, 5, 5);
        add(betLabel, gc);

        potLabel = new JLabel(" ");
        potLabel.setBorder(UIConstants.LABEL_BORDER);
        potLabel.setForeground(Color.GREEN);
        potLabel.setHorizontalAlignment(JLabel.CENTER);
        gc.gridx = 3;
        gc.gridy = 1;
        gc.gridwidth = 1;
        gc.gridheight = 1;
        gc.anchor = GridBagConstraints.CENTER;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1.0;
        gc.weighty = 0.0;
        gc.insets = new Insets(5, 5, 5, 5);
        add(potLabel, gc);

        // The five card positions.
        cardLabels = new JLabel[NO_OF_CARDS];
        for (int i = 0; i < 5; i++) {
            cardLabels[i] = new JLabel(ResourceManager.getIcon("/images/card_placeholder.png"));
            gc.gridx = i;
            gc.gridy = 2;
            gc.gridwidth = 1;
            gc.gridheight = 1;
            gc.anchor = GridBagConstraints.CENTER;
            gc.fill = GridBagConstraints.NONE;
            gc.weightx = 0.0;
            gc.weighty = 0.0;
            gc.insets = new Insets(5, 1, 5, 1);
            add(cardLabels[i], gc);
        }
        
        // Message label.
        messageLabel = new JLabel();
        messageLabel.setForeground(Color.YELLOW);
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        gc.gridx = 0;
        gc.gridy = 3;
        gc.gridwidth = 5;
        gc.gridheight = 1;
        gc.anchor = GridBagConstraints.CENTER;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1.0;
        gc.weighty = 1.0;
        gc.insets = new Insets(0, 0, 0, 0);
        add(messageLabel, gc);
        
        // Control panel.
        gc.gridx = 0;
        gc.gridy = 4;
        gc.gridwidth = 5;
        gc.gridheight = 1;
        gc.insets = new Insets(0, 0, 0, 0);
        gc.anchor = GridBagConstraints.CENTER;
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 1.0;
        gc.weighty = 1.0;
        add(controlPanel, gc);


        this.timerLabel = new JLabel("<html>Time to act<br /><center>60 s</center></html>");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 13));
        timerLabel.setForeground(Color.YELLOW);
        // Timer.
        gc.gridx = 4;
        gc.gridy = 5;
        gc.gridwidth = 1;
        // gc.gridheight = 1;
        gc.insets = new Insets(0, 0, 0, 0);
        gc.anchor = GridBagConstraints.CENTER;
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 1;
        gc.weighty = 1;
        timerLabel.setBorder(UIConstants.LABEL_BORDER);
        add(timerLabel, gc);
        this.timerLabel.setVisible(false);

        setPreferredSize(new Dimension(400, 325));

        update(null, BigDecimal.ZERO, BigDecimal.ZERO);
    }

    private String timeRemainingHTML(long time) {
        long timeLeft = time/1000;
        if (timeLeft < 10) timerLabel.setForeground(Color.RED);
        else timerLabel.setForeground(Color.YELLOW);
        return String.format("<html><center>Time to act<br /><center>%d s</center></center></html>", time/1000);
    }

    /**
     * Updates the current hand status.
     * 
     * @param bet
     *            The bet.
     * @param pot
     *            The pot.
     */
    public void update(List<Card> cards, BigDecimal bet, BigDecimal pot) {
        if (BigDecimal.ZERO.equals(bet)) {
            betLabel.setText(" ");
        } else {
            betLabel.setText("$ " + bet);
        }
        if (BigDecimal.ZERO.equals(pot)) {
            potLabel.setText(" ");
        } else {
            potLabel.setText("$ " + pot);
        }
        int noOfCards = (cards == null) ? 0 : cards.size();
        for (int i = 0; i < NO_OF_CARDS; i++) {
            if (i < noOfCards) {
                cardLabels[i].setIcon(ResourceManager.getCardImage(cards.get(i)));
            } else {
                cardLabels[i].setIcon(ResourceManager.getIcon("/images/card_placeholder.png"));
            }
        }
    }
    
    /**
     * Sets a custom message.
     * 
     * @param message
     *            The message.
     */
    public void setMessage(String message) {
        if (message.length() == 0) {
            messageLabel.setText(" ");
        } else {
            messageLabel.setText(message);
        }
    }
    
    /**
     * Waits for the user to continue.
     */
    public void waitForUserInput() {
        if (messageLabel.getText().equals("Game over.")) {
            controlPanel.waitForGameoverAck();
            client.gameOver();

        } else {
            controlPanel.waitForUserInput();
        }
    }

    public void returnFromGame(String status, ClientController clientController){
        switch(status){
            case "online":

                clientController.disconnectClient();
                int lobbyId = clientController.getLobbyId();
                clientController.leaveLobby(lobbyId);
                clientController.hideLobbyWindow();
                client.returnToMainMenu();

                break;
            case "offline":
                System.out.println("Exiting offline game");
                client.gameOver();
                break;
        }

    }

    /**
     * Update timer every 1000 ms, when timer runs out, stop the timer.
     * @param timeout
     */
    public void setTimeout(long timeout) {
        if (timer == null) {
            timer = new Timer(1000, e -> {
                long currentTime = System.currentTimeMillis();
                this.timerLabel.setText(timeRemainingHTML(timeout - currentTime));
                if (timeout < currentTime) {
                    stopTimer();
                }
            });
            timer.start();
            long currentTime = System.currentTimeMillis();
            this.timerLabel.setText(timeRemainingHTML(timeout - currentTime));
            timerLabel.setVisible(true);
        } else {
            throw new IllegalStateException("Timer is already running");
        }

    }

    public void stopTimer() {
        if (timer != null) {
            timer.stop();
            timer = null;
            timerLabel.setVisible(false);
            repaint();
        }
    }
}
