package org.pokergame.server;


import java.util.ArrayList;

public final class ServerController {
    private ArrayList<Lobby> lobbies;
    private static ServerController serverController;

    private ServerController() {
        ServerConnection serverConnection = new ServerConnection(1337, this);
        serverConnection.start();

        lobbies = new ArrayList<Lobby>();
    }

    public static ServerController getInstance() {
        if (serverController == null) {
            serverController = new ServerController();
        }
        return serverController;
    }

    public ArrayList<Lobby> getLobbies() {
        return lobbies;
    }

    public synchronized void createLobby() {
        Lobby lobby = new Lobby();
        lobby.setLobbyIndex(lobbies.size());
        lobbies.add(lobby);
    }

    public Lobby joinLobby(String userName, int lobbyIndex) {
        Lobby lobby = lobbies.get(lobbyIndex);
        if (lobby.getAvailable()) {
            lobby.addPlayer(userName);
            return lobby;
        }
        return null;
    }

}
