package org.pokergame.server;


import java.util.ArrayList;

public class ServerController {
    private ArrayList<Lobby> lobbies;


    public ServerController() {
        ServerConnection serverConnection = new ServerConnection(1337, this);
        serverConnection.start();

        lobbies = new ArrayList<Lobby>();
    }

    public ArrayList<Lobby> getLobbies() {
        return lobbies;
    }
    public synchronized void createLobby() {
        Lobby lobby = new Lobby();
        lobby.setLobbyIndex(lobbies.size());
        lobbies.add(lobby);
    }

    public Lobby joinLobby(ClientHandler clientHandler, int lobbyIndex) {
        Lobby lobby = lobbies.get(lobbyIndex);
        if (lobby.getAvailable()) {
            lobby.addPlayer(clientHandler);
            return lobby;
        }
        return null;
    }

}
