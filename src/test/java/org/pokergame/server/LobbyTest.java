package org.pokergame.server;

import org.junit.jupiter.api.Test;
import org.pokergame.Client;
import org.pokergame.bots.BasicBot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LobbyTest {

    private final Lobby lobby = new Lobby();

    @Test
    void addPlayer() {
        lobby.addPlayer("testName", new BasicBot(0,0));
        System.out.println("The players: " + lobby.getPlayers());
        assertEquals(new ArrayList<>(List.of("testName")), lobby.getPlayers());
    }

    @Test
    void removePlayer() {
    }

    @Test
    void startTable() {
    }

    @Test
    void getAvailable() {
    }

    @Test
    void setAvailable() {
    }

    @Test
    void getTable() {
    }

    @Test
    void setTable() {
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