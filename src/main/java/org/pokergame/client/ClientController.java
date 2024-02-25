package org.pokergame.client;

import org.pokergame.gui.Main;
import org.pokergame.gui.StartMenu;
import org.pokergame.toClientCommands.*;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientController {
    private int port;
    private String ip;
    private Socket socket;
    private Main ClientGUI;

    private StartMenu startMenu;

    private ClientOutputX clientOutput;
    private ClientInputX clientInput;
    private String username = startMenu.getUsernameText();


    public ClientController(String ip, int port) throws IOException {
        this.ip=ip;
        this.port=port;
        socket = new Socket(ip, port);

        // create the input and output streams
        clientInput = new ClientInputX(socket, this);
        clientInput.start();

        clientOutput = new ClientOutputX(socket);
        clientOutput.start();

        /* The table. */

        SwingUtilities.invokeLater(() -> {
            StartMenu startMenu = new StartMenu();
        });
        while (true) {


            clientOutput.sendMessage(getUsernameText());

            System.out.print("Enter message to send to server: ");
            Scanner scanner = new Scanner(System.in);
            String message = scanner.nextLine();
            clientOutput.sendMessage(message);
            if (message.equals("exit")) {
                break;
            }
        }
        /**SwingUtilities.invokeLater(() -> {
            StartMenu startMenu = new StartMenu();
        }); */
        //StartMenu startMenu = new StartMenu();
    }

    public String getUsernameText() {
        String username = startMenu.getUsernameText();
        return username;
    }

    /**
     * Command from the server to the client
     * Acts as interface between the server and the client
     * ClientInput lyssnar på objekt -> som i sin tur kallar på denna metoden
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