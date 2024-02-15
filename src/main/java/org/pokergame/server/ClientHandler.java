package org.pokergame.server;


import java.net.Socket;
import java.util.Scanner;

public class ClientHandler extends Thread {
    private Socket socket;
    private ServerInput serverInput;
    private ServerOutput serverOutput;
    private ServerController serverController;

    public ClientHandler(Socket socket, ServerController serverController) {
        System.out.println("Client Handler created");
        this.socket = socket;
        this.serverController = serverController;
    }

    @Override
    public void run() {
        serverInput = new ServerInput(socket, this);
        serverInput.start();
        serverOutput = new ServerOutput(socket);
        serverOutput.start();

        while (true) {
            System.out.print("Enter message to send to client: ");
            Scanner scanner = new Scanner(System.in);
            String message = scanner.nextLine();
            serverOutput.sendMessage(message);
        }
    }
}
