package org.pokergame.client;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientInputX extends Thread {
    private Socket socket;
    private ObjectInputStream in;
    private ClientController clientController;

    public ClientInputX(Socket socket, ClientController clientController) {
        this.socket = socket;
        this.clientController = clientController;
    }

    @Override
    public void run() {

        while (true) {
            try {
                in = new ObjectInputStream(socket.getInputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("ClientInput running");
            Object incomingMessage = recieveMessage();

            if (incomingMessage instanceof String) {
                System.out.println(incomingMessage);
            }

            // Lobby information
            if (incomingMessage instanceof String[][]) {
                System.out.println(incomingMessage);
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
