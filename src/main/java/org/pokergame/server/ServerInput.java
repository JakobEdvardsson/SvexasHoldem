package org.pokergame.server;


import org.pokergame.actions.PlayerAction;
import org.pokergame.toServerCommands.*;
import org.pokergame.util.Buffer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;

public class ServerInput extends Thread {
    private Socket socket;
    private ObjectInputStream in;
    private ClientHandler clientHandler;
    private boolean isRunning;
    private Buffer<PlayerAction> packetBuffer;
    private Lobby lobby;

    public ServerInput(Socket socket, ClientHandler clientHandler, Buffer<PlayerAction> packetBuffer) {
        this.socket = socket;
        this.clientHandler = clientHandler;
        this.packetBuffer = packetBuffer;
    }

    @Override
    public void run() {
        try {

            isRunning = true;
            in = new ObjectInputStream(socket.getInputStream());

            while (isRunning) {
                Object incomingMessage = recieveMessage();

                if (incomingMessage instanceof JoinLobby) {
                    System.out.println("Player tried to join table " + ((JoinLobby) incomingMessage).tableId() + "!");
                    clientHandler.joinTable((JoinLobby) incomingMessage);
                }

                if (incomingMessage instanceof Register) {
                    Register message = (Register) incomingMessage;
                    clientHandler.registerClient(message);
                }

                if(incomingMessage instanceof LeaveLobby){
                    clientHandler.leaveLobby((LeaveLobby) incomingMessage);
                }

                if(incomingMessage instanceof PlayerAction){
                    packetBuffer.add((PlayerAction) incomingMessage);
                }

                if(incomingMessage instanceof Disconnect){
                    clientHandler.disconnectClient();
                    isRunning = false;
                    continue;
                }

                if (incomingMessage instanceof StartGame) {
                    clientHandler.startGame();
                }

                else {
                    System.out.print(" ");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object recieveMessage(){
        try {
            return in.readObject();

        }
        catch (SocketException e) {
            return new Disconnect();
        }
        catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
