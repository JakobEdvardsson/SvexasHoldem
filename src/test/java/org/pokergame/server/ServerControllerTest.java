package org.pokergame.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pokergame.Player;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ServerControllerTest {
    private ServerController serverController;


    @BeforeEach
    void setUp() {
        serverController = ServerController.getInstance();
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
        ClientHandler handler = new ClientHandler(null, null);
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
        ClientHandler handler = new ClientHandler(null, null);
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
    }

    @Test
    void disconnectClient() {
    }
}