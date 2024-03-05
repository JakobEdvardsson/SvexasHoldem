package org.pokergame.server;
import org.pokergame.Player;
import java.util.ArrayList;
import java.util.HashMap;

public final class ServerController {
    private volatile ArrayList<Lobby> lobbies;
    private static ServerController serverController;
    private HashMap<ClientHandler, String> connectedClients;

    public static void setServerController(ServerController serverController) {
        ServerController.serverController = serverController;
    }

    private ServerController() {
        ServerConnection serverConnection = new ServerConnection(1337, this);
        serverConnection.start();

        lobbies = new ArrayList<Lobby>();
        connectedClients = new HashMap<ClientHandler, String>();

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

    public String[][] getLobbiesAsString() {

        String[][] lobbyStrings = new String[lobbies.size()][4];

        for (int i = 0; i < lobbies.size(); i++) {
            Lobby lobby = lobbies.get(i);

            int index = 0;
            for (Player player : lobby.getPlayers()) {
                System.out.println("player found!");
                System.out.println(player.getName());
                lobbyStrings[i][index++] = player.getName();
            }
        }

        return lobbyStrings;
    }

    public synchronized Lobby createLobby() {
        Lobby lobby = new Lobby();
        lobby.setLobbyIndex(lobbies.size());
        lobbies.add(lobby);
        return lobby;
    }

    public synchronized Lobby joinLobby(ClientHandler handler, int lobbyIndex) {
        Lobby lobby = lobbies.get(lobbyIndex);
        String userName = connectedClients.get(handler);

        if (lobby.getAvailable()) {
            lobby.addPlayer(userName, handler);
            updateLobbyStatus();
            return lobby;
        }

        return null;
    }

    /**
     * Updates the status of the lobbies in the server to all connected clients
     */
    public synchronized void updateLobbyStatus() {
        String[][] lobbies = getLobbiesAsString();

        for (ClientHandler handler : connectedClients.keySet()) {
            handler.pushLobbyInformation(lobbies);
        }
    }

    public synchronized Lobby leaveLobby(ClientHandler handler, int lobbyIndex) {
        Lobby lobby = lobbies.get(lobbyIndex);
        lobby.removePlayer(handler);
        updateLobbyStatus();

        return lobby;
    }

    /**
     * Registers client to the server
     * @param userName Username of the connected client
     * @param handler Handler to communicate with the client
     * @return true if the client was successfully registered, false otherwise
     */
    public synchronized boolean registerClient(String userName, ClientHandler handler) {
        if (connectedClients.containsValue(userName)) return false;
        connectedClients.put(handler, userName);
        return true;
    }

    /**
     * Disconnect and remove client from connected clients & any lobbies
     * @param clientHandler client handler to disconnect
     */
    public synchronized void disconnectClient(ClientHandler clientHandler) {
        System.out.println("Removing client: " + connectedClients.get(clientHandler) + " from the server");

        for (Lobby lobby : lobbies) {
            for (Player player : lobby.getPlayers()) {
                if (player.getClient().equals(clientHandler)) {
                    lobby.removePlayer(clientHandler);
                    connectedClients.remove(clientHandler);
                    updateLobbyStatus();
                    return;
                }
            }
        }
    }

    public ArrayList<Lobby> getLobbies() {
        return lobbies;
    }

    public HashMap<ClientHandler, String> getConnectedClients() {
        return connectedClients;
    }
}