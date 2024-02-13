package org.pokergame.client;

import org.pokergame.Message;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientController {
    private Socket socket;
    private ClientOutput clientOutput;
    private ClientInput clientInput;



    public void connect(String ip, int port) {
        try {
            //change ip and port to instance variables later on...
            socket = new Socket(ip, port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        clientInput = new ClientInput(socket, this);
        clientInput.start();

        clientOutput = new ClientOutput(socket);
        clientOutput.start();

        while (true) {
            System.out.print("Enter message to send to server: ");
            Scanner scanner = new Scanner(System.in);
            String message = scanner.nextLine();
            clientOutput.sendMessage(message);
        }
    }
}



