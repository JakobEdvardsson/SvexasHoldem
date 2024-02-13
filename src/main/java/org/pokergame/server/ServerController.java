package org.pokergame.server;


public class ServerController {

    public ServerController() {
        new ServerConnection(1337, this);
    }


}
