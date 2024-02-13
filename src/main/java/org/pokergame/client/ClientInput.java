package org.pokergame.client;


import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.time.LocalDateTime;

public class ClientInput extends Thread {
    private Socket socket;
    private ObjectInputStream in;
    private ClientController clientController;

    public ClientInput(Socket socket, ClientController clientController) {
        this.socket = socket;
        this.clientController = clientController;
    }

    @Override
    public void run() {
        try {

            while (true) {
                System.out.println("ClientInput running");
                Object incomingMessage = recieveMessage();

                if (incomingMessage instanceof String) {
                    System.out.println(incomingMessage);

                }
                in = new ObjectInputStream(socket.getInputStream());
            }

        } catch (IOException e) {
            e.printStackTrace();
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
        clientController.inputObject(incomingObject);

        return incomingObject;
    }


}
