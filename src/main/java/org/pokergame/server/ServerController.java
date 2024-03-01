package org.pokergame.server;


import org.pokergame.Player;

import java.util.ArrayList;
import java.util.HashMap;

public final class ServerController {
    private ArrayList<Lobby> lobbies;
    private static ServerController serverController;

    private HashMap<String, ClientHandler> connectedClients;

    private ServerController() {
        ServerConnection serverConnection = new ServerConnection(1337, this);
        serverConnection.start();

        lobbies = new ArrayList<Lobby>();
        connectedClients = new HashMap<String, ClientHandler>();

        lobbies.add(new Lobby());
        lobbies.add(new Lobby());
        lobbies.add(new Lobby());

    }

    public static ServerController getInstance() {
        if (serverController == null) {
            serverController = new ServerController();
        }

        return serverController;
    }

    public String[][] getLobbies() {

        String[][] lobbyStrings = new String[3][4];

        for (int i = 0; i < 3; i++) {
            Lobby lobby = lobbies.get(i);

            int index = 0;
            for (Player player : lobby.getPlayers()) {
                lobbyStrings[i][index++] = player.getName();
            }
        }

        return lobbyStrings;
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

    /**
     * Registers client to the server
     * @param userName Username of the connected client
     * @param handler Handler to communicate with the client
     * @return true if the client was successfully registered, false otherwise
     */
    public boolean registerClient(String userName, ClientHandler handler) {
        if (connectedClients.containsKey(userName)) return false;
        connectedClients.put(userName, handler);
        return true;
    }



}
