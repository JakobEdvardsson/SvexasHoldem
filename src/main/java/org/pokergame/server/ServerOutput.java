package org.pokergame.server;


import org.pokergame.Message;
import org.pokergame.actions.PlayerAction;
import org.pokergame.util.Buffer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerOutput {
    private Socket socket;
    private ObjectOutputStream out;
    private Buffer<PlayerAction> packetBuffer;

    public ServerOutput(Socket socket, Buffer<PlayerAction> packetBuffer) {
        this.socket = socket;
        this.packetBuffer = packetBuffer;
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
