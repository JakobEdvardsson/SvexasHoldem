package org.pokergame.client;


import org.pokergame.Card;
import org.pokergame.Player;
import org.pokergame.TableType;
import org.pokergame.actions.PlayerAction;
import org.pokergame.gui.OnlineMain;
import org.pokergame.toClientCommands.*;
import org.pokergame.toServerCommands.StartGame;

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


    public ClientInputX(Socket socket, ClientController clientController) {
        this.socket = socket;
        this.clientController = clientController;
    }

    @Override
    public void run() {
        try {
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        while (true) {

            System.out.println("ClientInput running");
            Object incomingMessage = recieveMessage();

            if (incomingMessage instanceof String) {
                System.out.println(incomingMessage);
            }

            // Lobby information
            if (incomingMessage instanceof String[][]) {
                System.out.println(((String[][]) incomingMessage).length);

                for (int i = 0; i < ((String[][]) incomingMessage).length; i++) {
                    for (int j = 0; j < ((String[][]) incomingMessage)[i].length; j++) {
                        System.out.println(((String[][]) incomingMessage)[i][j]);
                    }
                }

                clientController.setLobbyInfo((String[][]) incomingMessage);
            }

            if (incomingMessage instanceof StartGame) {
                if (onlineMain == null) {
                    onlineMain = new OnlineMain(clientController.getUsername(), clientController);
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
                BigDecimal minBet = ((Act) incomingMessage).minBet();
                BigDecimal currentBet = ((Act) incomingMessage).currentBet();
                Set<PlayerAction> allowedActions = ((Act) incomingMessage).allowedActions();
                PlayerAction action = onlineMain.act(minBet, currentBet, allowedActions);
                clientController.sendMessage(action);
            }

            if (incomingMessage instanceof MessageReceived) {
                onlineMain.messageReceived(((MessageReceived) incomingMessage).message());
            }

        }
    }
    public synchronized Object recieveMessage(){
        Object incomingObject = null;
        try {
            incomingObject =  in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Todo - might need to make this async
        //clientController.inputObject(incomingObject);

        return incomingObject;
    }


}
