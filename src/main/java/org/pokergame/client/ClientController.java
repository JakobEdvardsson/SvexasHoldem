package org.pokergame.client;

import org.pokergame.gui.Main;
import org.pokergame.gui.StartMenu;
import org.pokergame.toClientCommands.*;
import org.pokergame.toServerCommands.JoinLobby;
import org.pokergame.toServerCommands.LeaveLobby;
import org.pokergame.toServerCommands.Register;

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
    private String username = startMenu.getUsernameText();

    public ClientController(String ip, int port) throws IOException {
        this.ip=ip;
        this.port=port;
        socket = new Socket(ip, port);

        // create the input and output streams
        clientInput = new ClientInputX(socket, this);
        clientInput.start();

        clientOutput = new ClientOutputX(socket);

        /* The table. */

        SwingUtilities.invokeLater(() -> {
                this.startMenu = new StartMenu(this);
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
        String playerName = getUsernameText() != null ? getUsernameText() : "Player";
        clientOutput.sendMessage(new Register(getUsernameText()));
    }

    public String getUsernameText() {
        String username = startMenu.getUsernameText();
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

    public void setLobbyInfo(String[][] info) {
        startMenu.setLobbyInfo(info);
    }

    public void leaveLobby(int lobbyId){
        clientOutput.sendMessage(new LeaveLobby(lobbyId));
    }

}