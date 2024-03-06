package org.pokergame.client;

import org.pokergame.actions.PlayerAction;
import org.pokergame.gui.Main;
import org.pokergame.gui.StartMenu;
import org.pokergame.toClientCommands.*;
import org.pokergame.toServerCommands.*;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;

public class ClientController {
    private int port;
    private String ip;
    private Socket socket;
    private Main ClientGUI;
    private StartMenu startMenu;
    private ClientOutputX clientOutput;
    private ClientInputX clientInput;
    private String username;

    public ClientController(String ip, int port) throws IOException {
        /* The table. */

        this.ip = ip;
        this.port = port;

        SwingUtilities.invokeLater(() -> {
                this.startMenu = new StartMenu(this);
                startMenu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        });


            //LÄGG IN VAD SPELAREN GÖR HÄR--->

            //Gammalt test för att skicka en sträng över socket.
            /*
            //System.out.print("Enter message to send to server: ");
            Scanner scanner = new Scanner(System.in);
            //String message = scanner.nextLine();
            /**clientOutput.sendMessage(message);
            if (message.equals("exit")) {
                break;
            } */
    }

    public void playOnline() {
        try {
            socket = new Socket(ip, port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // create the input and output streams
        clientInput = new ClientInputX(socket, this);
        clientInput.start();

        clientOutput = new ClientOutputX(socket);

        clientOutput.sendMessage(new Register(this.username));
    }

    public void disconnectClient() {
        clientOutput.sendMessage(new Disconnect());
    }

    public String getUsername() {
        return username;
    }

    public void joinLobby(int lobbyId) {
        JoinLobby lobby = new JoinLobby(lobbyId);
        clientOutput.sendMessage(lobby);
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
                // ClientGUI.joinedTable(joinedTable.type(), joinedTable.bigBlind(), joinedTable.players());
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
                ClientGUI.playerActed(playerActed.player().publicClone());
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

    public void setLobbyInfo(String[][] info) {
        startMenu.setLobbyInfo(info);
    }

    public void leaveLobby(int lobbyId){
        clientOutput.sendMessage(new LeaveLobby(lobbyId));
    }

    public void setUsername(String text) {
        this.username = text;
    }

    public void startGame() {
        clientOutput.sendMessage(new StartGame());
    }

    public void sendMessage(PlayerAction action) {
        clientOutput.sendMessage(action);
    }
}