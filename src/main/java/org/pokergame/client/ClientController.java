package org.pokergame.client;

import org.pokergame.Message;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;

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
        //clientInput = new ClientInput(socket, this);
        //clientInput.start();
        clientOutput = new ClientOutput(socket, this);
        clientOutput.start();
        Message message = new Message();
        message.setMessage("Hello from client");
        clientOutput.sendMessage(message);
    }
}



