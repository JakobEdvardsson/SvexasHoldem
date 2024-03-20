package org.svexasHoldem.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.svexasHoldem.gui.StartMenu;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ClientControllerTest {
    private ClientController controller;
    private StartMenu startMenu;

    @BeforeEach
    void setUp() {
        try{
            this.controller = new ClientController("localhost", 1337);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        controller.invokeLater();
        this.startMenu = controller.getStartMenu();
    }

    @Test
    void invokeLater() {
        assertEquals(startMenu, controller.getStartMenu());
    }

    @Test
    void playOnline() {
        controller.playOnline();
        assertTrue(controller.getClientInput().isAlive());
    }

    @Test
    void disconnectClient() {
    }

    @Test
    void getUsername() {
        controller.setUsername("test");
        assertEquals("test", controller.getUsername());
    }

    @Test
    void joinLobby() {
    }

    @Test
    void setLobbyInfo() {
    }

    @Test
    void leaveLobby() {
    }

    @Test
    void setUsername() {
    }

    @Test
    void startGame() {
    }

    @Test
    void sendMessage() {
    }

    @Test
    void hideLobbyWindow() {
    }

    @Test
    void showLobbyWindow() {
    }

    @Test
    void getLobbyId() {
    }

    @Test
    void showStartMenu() {
    }
}