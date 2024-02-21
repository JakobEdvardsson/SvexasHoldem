package org.pokergame.client;

import org.pokergame.gui.Main;
import org.pokergame.toClientCommands.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientController {
    private int port;
    private String ip;
    private Socket socket;
    private Main ClientGUI;
    private ClientOutput cOut;
    private ClientInput cIn;

    public ClientController(String ip, int port) throws IOException {
        this.ip=ip;
        this.port=port;
        socket = new Socket(ip, port);

        //create the GUI
        ClientGUI = new Main();
        /* The table. */


        // create the input and output streams
        cOut = new ClientOutput(socket);
        cOut.start();
        cIn = new ClientInput(socket);
        cIn.start();
    }

    private class ClientOutput extends Thread {
        Socket socket;
        private ObjectOutputStream oos;



        public ClientOutput(Socket socket) throws IOException {
            this.socket = socket;
            oos = new ObjectOutputStream(socket.getOutputStream());

        }

        @Override
        public void run() {
            try {

                String message = "Hejsandejsansvejsan";
                oos.writeObject(message);
                oos.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    private class ClientInput extends Thread{
        Socket socket;
        private ObjectInputStream ois;

        public ClientInput(Socket socket){
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                socket = new Socket(ip, port);
                ois = new ObjectInputStream(socket.getInputStream());
                Object object = ois.readObject();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    ois.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
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