package org.svexasHoldem.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.svexasHoldem.Client;
import org.svexasHoldem.Player;
import org.svexasHoldem.Table;
import org.svexasHoldem.TableType;

import java.math.BigDecimal;
import java.util.ArrayList;

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
    void generateBotNames() {
        lobby.generateBotNames();
        ArrayList<String> names = new ArrayList<>() {{
            add("Alex");
            add("Bella");
            add("Caleb");
            add("Daisy");
            add("Ethan");
            add("Fiona");
            add("Gavin");
            add("Holly");
            add("Ivan");
            add("Jenna");
        }};

        for (String name : names) {
            assertTrue(lobby.getNames().contains(name));
        }
    }

    @Test
    void addPlayer() {
        String playerName = testPlayer.getName();
        assertEquals("testName", playerName);

        BigDecimal playerCash = testPlayer.getCash();
        assertEquals(new BigDecimal(500), playerCash);

        Client playerHandler = testPlayer.getClient();
        assertEquals(testHandler, playerHandler);

        //To test null check if more than 5 players are added
        // (There's one player in the lobby from the beginning)
        for(int i = 0; i < 4; i++){
            lobby.addPlayer("testPlayer", testHandler);
        }

        assertNull(lobby.addPlayer("testPlayer", testHandler));
    }

    @Test
    void removePlayer() {
        Player player = lobby.removePlayer(testHandler);
        assertEquals(testPlayer, player);

        assertEquals(0, lobby.getPlayerCount());
        assertFalse(lobby.getTable().isRunning());
    }

    @Test
    void gameFinished() {
        lobby.gameFinished();

        assertTrue(lobby.getPlayers().isEmpty());
        assertFalse(lobby.isRunning());
    }

    @Test
    void startTable() {
        //Revisit
    }

    @Test
    void getBotName() {
        assertEquals(lobby.getNames().getFirst() + " (bot)", lobby.getBotName());
    }

    @Test
    void getAvailable() {
        boolean testAvailable = lobby.getAvailable();
        assertTrue(testAvailable);
    }

    @Test
    void setAvailable() {
        lobby.setAvailable(true);
        assertTrue(lobby.getAvailable());

        lobby.setAvailable(false);
        assertFalse(lobby.getAvailable());
    }

    @Test
    void getTable() {
    }

    @Test
    void setTable() {
        Table table =  new Table(TableType.FIXED_LIMIT, BigDecimal.ZERO, lobby);
        lobby.setTable(table);

        assertEquals(table, lobby.getTable());
    }

    @Test
    void getPlayers() {
    }

    @Test
    void setPlayers() {
        ArrayList<Player> players = new ArrayList<>();
        lobby.setPlayers(players);

        assertEquals(players, lobby.getPlayers());
    }

    @Test
    void getLobbyIndex() {
    }

    @Test
    void setLobbyIndex() {
        int lobbyIndex = 0;
        lobby.setLobbyIndex(lobbyIndex);

        assertEquals(lobbyIndex, lobby.getLobbyIndex());
    }

    @Test
    void testSetStackSize() {
    }

    @Test
    void testGetStackSize() {
        BigDecimal decimal = new BigDecimal(100);
        lobby.setStackSize(decimal);

        assertEquals(decimal.divide(new BigDecimal(4)), lobby.getStackSize());
    }
}