package org.svexasHoldem.client;


import org.svexasHoldem.Card;
import org.svexasHoldem.Player;
import org.svexasHoldem.TableType;
import org.svexasHoldem.actions.PlayerAction;
import org.svexasHoldem.gui.OnlineMain;
import org.svexasHoldem.toClientCommands.*;
import org.svexasHoldem.toServerCommands.StartGame;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigDecimal;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ClientInputX extends Thread {
    private Socket socket;
    private ObjectInputStream in;
    private ClientController clientController;
    private OnlineMain onlineMain;
    public Object incomingMessage;


    public ClientInputX(Socket socket, ClientController clientController) {
        this.socket = socket;
        this.clientController = clientController;
   }

    @Override
    public void run() {
        createInputStream();

        while (true) {
            getInput();
        }
    }

    public void getInput() {
        incomingMessage = recieveMessage();

        // Lobby information
        if (incomingMessage instanceof String[][]) {
            clientController.setLobbyInfo((String[][]) incomingMessage);
        }

        if (incomingMessage instanceof StartGame) {
            if (onlineMain == null) {
                onlineMain = new OnlineMain(clientController.getUsername(), clientController);
                clientController.hideLobbyWindow();
            }
        }

        if (incomingMessage instanceof JoinedTable) {
            if (onlineMain != null) {

                TableType type = ((JoinedTable) incomingMessage).type();
                BigDecimal bigBlind = ((JoinedTable) incomingMessage).bigBlind();
                List<String> players = ((JoinedTable) incomingMessage).players();

                ArrayList<Player> playerObjects = new ArrayList<Player>();

                for (String player : players) {
                    playerObjects.add(new Player(player, new BigDecimal(500), null));
                }


                onlineMain.joinedTable(type, bigBlind, playerObjects);
            }
        }

        if (incomingMessage instanceof HandStarted) {
            onlineMain.handStarted(((HandStarted) incomingMessage).dealer());
        }

        if (incomingMessage instanceof BoardUpdated) {
            List<Card> cards = ((BoardUpdated) incomingMessage).cards();
            BigDecimal bet = ((BoardUpdated) incomingMessage).bet();
            BigDecimal pot = ((BoardUpdated) incomingMessage).pot();
            onlineMain.boardUpdated(cards, bet, pot);
        }

        if (incomingMessage instanceof ActorRotated) {
            onlineMain.actorRotated(((ActorRotated) incomingMessage).actor());
        }

        if (incomingMessage instanceof PlayerActed) {
            onlineMain.playerActed(((PlayerActed) incomingMessage).player());
        }

        if (incomingMessage instanceof PlayerUpdated) {
            onlineMain.playerUpdated(((PlayerUpdated) incomingMessage).player());
        }

        if (incomingMessage instanceof Act) {
            long timeout = ((Act) incomingMessage).timeout();
            BigDecimal minBet = ((Act) incomingMessage).minBet();
            BigDecimal currentBet = ((Act) incomingMessage).currentBet();
            Set<PlayerAction> allowedActions = ((Act) incomingMessage).allowedActions();
            onlineMain.setTimeout(timeout);
            PlayerAction action = onlineMain.act(minBet, currentBet, allowedActions);
            clientController.sendMessage(action);
        }

        if (incomingMessage instanceof MessageReceived) {
            onlineMain.messageReceived(((MessageReceived) incomingMessage).message());
        }
    }

    private void createInputStream() {
        try {
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized Object recieveMessage(){
        Object incomingObject = null;
        try {
            incomingObject =  in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return incomingObject;
    }

    public void setOnlineMain(OnlineMain onlineMain) {
        this.onlineMain = onlineMain;
    }

    public OnlineMain getOnlineMain() {
        return onlineMain;
    }

    /**
     * Destroys the online main instance.
     */
    public void gameOver() {
        this.onlineMain.dispose();
        this.onlineMain = null;
    }
}
