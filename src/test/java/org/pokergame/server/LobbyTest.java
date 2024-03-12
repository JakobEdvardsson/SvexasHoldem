package org.pokergame.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pokergame.Client;
import org.pokergame.Player;
import org.pokergame.bots.BasicBot;

import java.math.BigDecimal;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LobbyTest {

    private Lobby lobby;
    private ServerController testController;
    private ClientHandler testHandler;
    private Player testPlayer;


    @BeforeEach
    void setUp() {
        this.lobby = new Lobby(testController);
        testController.setServerController(null);
        testController = ServerController.getInstance();
        testHandler = new ClientHandler(null, null);
        testController.registerClient("testName", testHandler);

        testPlayer = lobby.addPlayer("testName", testHandler);
    }

    @Test
    void addPlayer() {
        String playerName = testPlayer.getName();
        assertEquals("testName", playerName);

        BigDecimal playerCash = testPlayer.getCash();
        assertEquals(new BigDecimal(1000), playerCash);

        Client playerHandler = testPlayer.getClient();
        assertEquals(testHandler, playerHandler);
    }

    @Test
    void removePlayer() {
        Player player = lobby.removePlayer(testHandler);
        assertEquals(testPlayer, player);
    }

    @Test
    void startTable() {
        //Method not currently used
    }

    @Test
    void getAvailable() {
        boolean testAvailable = lobby.getAvailable();
        assertTrue(testAvailable);
    }

    @Test
    void setAvailable() {
        //Method not currently used
    }

    @Test
    void getTable() {
        //Method not currently used
    }

    @Test
    void setTable() {
        //Method not currently used
    }

    @Test
    void getPlayers() {
    }

    @Test
    void setPlayers() {
    }

    @Test
    void getLobbyIndex() {
    }

    @Test
    void setLobbyIndex() {
    }
}