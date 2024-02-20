package org.pokergame.client;


import org.pokergame.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientOutput extends Thread{
    private Socket socket;
    private ObjectOutputStream out;

    public ClientOutput(Socket socket){
        this.socket = socket;

        try {
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void sendMessage(Object message){
        try {
            out.writeObject(message);
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
