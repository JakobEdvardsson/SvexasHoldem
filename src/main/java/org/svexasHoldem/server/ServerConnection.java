package org.svexasHoldem.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerConnection extends Thread {
    private int port;
    private ServerController serverController;

    public ServerConnection(int port, ServerController serverController) {
        this.port = port;
        this.serverController = serverController;
    }

    @Override
    public void run() {

        try {
            ServerSocket serverSocket = new ServerSocket(this.port);
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    ClientHandler clientHandler = new ClientHandler(socket, serverController);
                    clientHandler.start();

                    System.out.println("Someone connected to the server");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }


}
