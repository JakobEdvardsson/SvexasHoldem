package org.pokergame.server;


import org.pokergame.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerOutput extends Thread{
    private Socket socket;
    private ObjectOutputStream out;

    public ServerOutput(Socket socket){
        this.socket = socket;

    }
    
    public synchronized void sendMessage(Object message){
        try {
            if (out == null) {
                out = new ObjectOutputStream(socket.getOutputStream());
            }
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
        }
    }
}
