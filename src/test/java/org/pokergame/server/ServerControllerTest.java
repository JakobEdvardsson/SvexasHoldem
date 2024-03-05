package org.pokergame.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pokergame.Player;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ServerControllerTest {
    private ServerController serverController;
    String name = "UnitTester";
    ClientHandler handler;


    @BeforeEach
    void setUp() {
        serverController.setServerController(null);
        serverController = ServerController.getInstance();
        handler = new ClientHandler(null, null);
        serverController.registerClient(name, handler);
    }

    @Test
    void getInstance() {
        ServerController servercontroller1 = ServerController.getInstance();
        assertEquals(serverController, servercontroller1);
    }

    @Test
    void getLobbies() {
    }

    @Test
    void createLobby() {
        Lobby lobby = serverController.createLobby();
        assertTrue(serverController.getLobbies().contains(lobby));
    }

    @Test
    void joinLobby() {
        Lobby lobby = serverController.createLobby();
        serverController.joinLobby(handler, lobby.getLobbyIndex());
        ArrayList<Player> players = lobby.getPlayers();

        boolean found = false;
        for (Player player : players) {
            if (player.getClient() == handler) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    @Test
    void leaveLobby() {
        // add a player to the lobby and make sure they are there
        Lobby lobby = serverController.createLobby();
        serverController.joinLobby(handler, lobby.getLobbyIndex());
        ArrayList<Player> players = lobby.getPlayers();

        boolean found = false;
        for (Player player : players) {
            if (player.getClient() == handler) {
                found = true;
                break;
            }
        }
        assertTrue(found);

        // remove the player from the lobby and make sure they are gone
        serverController.leaveLobby(handler, lobby.getLobbyIndex());

        players = lobby.getPlayers();

        boolean isGone = true;
        for (Player player : players) {
            if (player.getClient() == handler) {
                isGone = false;
                break;
            }
        }
        assertTrue(isGone);
    }

    @Test
    void registerClient() {
        // try to register a client with the same name
        ClientHandler handler2 = new ClientHandler(null, null);
        boolean registered = serverController.registerClient(name, handler2);
        assertFalse(registered);

        // try to register a client with a different name
        String name2 = "UnitTester2";
        ClientHandler handler3 = new ClientHandler(null, null);
        registered = serverController.registerClient(name2, handler3);
        assertTrue(registered);
    }

    @Test
    void disconnectClient() {
        // add a player to the lobby and make sure they are there
        Lobby lobby = serverController.createLobby();
        serverController.joinLobby(handler, lobby.getLobbyIndex());
        ArrayList<Player> players = lobby.getPlayers();

        boolean found = false;
        for (Player player : players) {
            if (player.getClient() == handler) {
                found = true;
                break;
            }
        }
        assertTrue(found);

        // remove the player from the lobby and make sure they are gone
        serverController.disconnectClient(handler);

        players = lobby.getPlayers();

        boolean isGone = true;
        for (Player player : players) {
            if (player.getClient() == handler) {
                isGone = false;
                break;
            }
        }
        assertTrue(isGone);
        assertFalse(serverController.getConnectedClients().containsKey(handler));
    }

    @Test
    void getLobbiesAsString() {
        Lobby lobby = serverController.createLobby();

        try {
             serverController.joinLobby(handler, lobby.getLobbyIndex());
        }catch (Exception e){
            //e.printStackTrace();
        }

        String[][] lobbies = serverController.getLobbiesAsString();

        boolean found = false;
        for (String[] lobbyInfo : lobbies) {
            if (lobbyInfo[0] != null && lobbyInfo[0].equals(name)) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }
}