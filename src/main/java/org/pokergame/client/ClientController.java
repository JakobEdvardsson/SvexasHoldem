package org.pokergame.client;

import org.pokergame.gui.Main;
import org.pokergame.toClientCommands.*;

import java.io.IOException;
import java.net.Socket;

public class ClientController {
    private Socket socket;
    private ClientOutput clientOutput;
    private ClientInput clientInput;
    private Main ClientGUI;

    public ClientController(String ip, int port) {
        try {
            //change ip and port to instance variables later on...
            socket = new Socket(ip, port);
        } catch (IOException e) {
            e.printStackTrace();
        }


        //create the GUI
        ClientGUI = new Main();
        /* The table. */


        // create the input and output streams
        clientInput = new ClientInput(socket, this);
        clientInput.start();

        clientOutput = new ClientOutput(socket);
        clientOutput.start();
    }


    /**
     * Command from the server to the client
     * Acts as interface between the server and the client
     *
     * @param message
     */
    public Object inputObject(Object message) {
        /*
          - message.getClass().getSimpleName()
          Gives the class (record) name of the object
         */
        switch (message.getClass().getSimpleName()) {
            case "JoinedTable" -> {
                JoinedTable joinedTable = (JoinedTable) message;
                ClientGUI.joinedTable(joinedTable.type(), joinedTable.bigBlind(), joinedTable.players());
                return joinedTable;
            }
            case "MessageReceived" -> {
                MessageReceived messageReceived = (MessageReceived) message;
                ClientGUI.messageReceived(messageReceived.message());
                return messageReceived;
            }
            case "HandStarted" -> {
                HandStarted handStarted = (HandStarted) message;
                ClientGUI.handStarted(handStarted.dealer());
                return handStarted;
            }
            case "ActorRotated" -> {
                ActorRotated actorRotated = (ActorRotated) message;
                ClientGUI.actorRotated(actorRotated.actor());
                return actorRotated;
            }
            case "BoardUpdated" -> {
                BoardUpdated boardUpdated = (BoardUpdated) message;
                ClientGUI.boardUpdated(boardUpdated.cards(), boardUpdated.bet(), boardUpdated.pot());
                return boardUpdated;
            }
            case "PlayerUpdated" -> {
                PlayerUpdated playerUpdated = (PlayerUpdated) message;
                ClientGUI.playerUpdated(playerUpdated.player());
                return playerUpdated;
            }
            case "PlayerActed" -> {
                PlayerActed playerActed = (PlayerActed) message;
                ClientGUI.playerActed(playerActed.player());
                return playerActed;
            }
            case "Act" -> {
                Act act = (Act) message;
                ClientGUI.act(act.minBet(), act.currentBet(), act.allowedActions());
                return act;
            }
            default -> {
                System.out.println("Unknown object");
                return null;
            }
        }
    }
}