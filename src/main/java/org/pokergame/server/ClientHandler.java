package org.pokergame.server;



import org.pokergame.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLOutput;
import java.time.LocalDateTime;

public class ClientHandler extends Thread {
    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private ServerConnection serverConnection;
    private ServerController serverController;

    public ClientHandler(Socket socket, ServerConnection serverConnection, ServerController serverController) {
        System.out.println("Client Handler created");
        this.socket = socket;
        this.serverConnection = serverConnection;
        this.serverController = serverController;
        // start();
    }

    @Override
    public void run() {
        try {
            // Client handshake
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            //objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

            // Asking for User object
            //objectOutputStream.writeObject("Request User Object");
            //objectOutputStream.flush();


            // Listening for messages
            System.out.println("Client Handler is running");
            while (true) {
                Message message;
                message = ((Message) this.objectInputStream.readObject());
                System.out.println(message.getMessage());
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
