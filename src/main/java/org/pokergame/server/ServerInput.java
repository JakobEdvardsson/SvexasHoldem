package org.pokergame.server;


import org.pokergame.Player;
import org.pokergame.actions.PlayerAction;
import org.pokergame.client.ClientController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ServerInput extends Thread {
    private Socket socket;
    private ObjectInputStream in;
    private ClientHandler clientHandler;
    private Lobby lobby;

    public ServerInput(Socket socket, ClientHandler clientHandler) {
        this.socket = socket;
        this.clientHandler = clientHandler;
    }

    @Override
    public void run() {
        try {
            in = new ObjectInputStream(socket.getInputStream());

            while (true) {
                Object incomingMessage = recieveMessage();
                //System.out.println(incomingMessage.toString());
                if (incomingMessage instanceof String) {
                    System.out.println(incomingMessage);
                    ServerController.getInstance().joinLobby(incomingMessage.toString(), 1);

                }if(incomingMessage instanceof PlayerAction){
                    System.out.println("Player: " + ((PlayerAction) incomingMessage).getVerb());
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
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


}
