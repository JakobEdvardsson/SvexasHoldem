package org.svexasHoldem.server;


import org.svexasHoldem.actions.PlayerAction;
import org.svexasHoldem.toServerCommands.*;
import org.svexasHoldem.util.Buffer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

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
                getInput();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getInput() {
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
            if (!packetBuffer.ignorePacket()) {
                packetBuffer.add((PlayerAction) incomingMessage);
            }
        }

        if(incomingMessage instanceof Disconnect){
            clientHandler.disconnectClient();
            isRunning = false;
            return;
        }

        if (incomingMessage instanceof StartGame) {
            clientHandler.startGame(incomingMessage);
        }
    }

    public Object recieveMessage(){
        try {
            return in.readObject();

        }
        catch (IOException | ClassNotFoundException e) {
            return new Disconnect();
        }
    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    public Buffer<PlayerAction> getPacketBuffer() {
        return packetBuffer;
    }

    public boolean isRunning() {
        return isRunning;
    }
}
