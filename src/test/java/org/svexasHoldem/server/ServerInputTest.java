package org.svexasHoldem.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.svexasHoldem.actions.AllInAction;
import org.svexasHoldem.actions.PlayerAction;
import org.svexasHoldem.toServerCommands.*;
import org.svexasHoldem.util.Buffer;

import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

class ServerInputTest {

    private ServerInput serverInput;
    private ServerInput spyServerInput;


    @BeforeEach
    void getInput() {
        @SuppressWarnings("unchecked")
        Buffer<PlayerAction> buffer = mock(Buffer.class);
        serverInput = new ServerInput(mock(Socket.class), mock(ClientHandler.class), buffer);
    }

    @Test
    void joinLobby() {
        // Arrange
        JoinLobby joinLobby = new JoinLobby(1);
        spyServerInput = spy(serverInput);
        doReturn(joinLobby).when(spyServerInput).recieveMessage();

        // Act
        spyServerInput.getInput();

        // Assert
        verify(spyServerInput.getClientHandler(), times(1)).joinTable(joinLobby);
    }

    @Test
    void register() {
        // Arrange
        Register register = new Register("test");
        spyServerInput = spy(serverInput);
        doReturn(register).when(spyServerInput).recieveMessage();

        // Act
        spyServerInput.getInput();

        // Assert
        verify(spyServerInput.getClientHandler(), times(1)).registerClient(register);
    }

    @Test
    void leaveLobby() {
        // Arrange
        LeaveLobby leaveLobby = new LeaveLobby(1);
        spyServerInput = spy(serverInput);
        doReturn(leaveLobby).when(spyServerInput).recieveMessage();

        // Act
        spyServerInput.getInput();

        // Assert
        verify(spyServerInput.getClientHandler(), times(1)).leaveLobby(leaveLobby);
    }

    @Test
    void playerAction() {
        // Arrange
        PlayerAction playerAction = mock(AllInAction.class);
        spyServerInput = spy(serverInput);
        doReturn(playerAction).when(spyServerInput).recieveMessage();

        // Act
        spyServerInput.getInput();

        // Assert
        verify(spyServerInput.getPacketBuffer(), times(1)).add(playerAction);
    }

    @Test
    void disconnect() {
        // Arrange
        Disconnect disconnect = new Disconnect();
        spyServerInput = spy(serverInput);
        doReturn(disconnect).when(spyServerInput).recieveMessage();

        // Act
        spyServerInput.getInput();

        // Assert
        verify(spyServerInput.getClientHandler(), times(1)).disconnectClient();
        assertFalse(spyServerInput.isRunning());
    }

    @Test
    void startGame() {
        // Arrange
        StartGame startGame = new StartGame(null);
        spyServerInput = spy(serverInput);
        doReturn(startGame).when(spyServerInput).recieveMessage();

        // Act
        spyServerInput.getInput();

        // Assert
        verify(spyServerInput.getClientHandler(), times(1)).startGame(startGame);
    }

    @Test
    void testNullMessage() {
        // Arrange
        spyServerInput = spy(serverInput);
        doReturn(null).when(spyServerInput).recieveMessage();

        // Act and Assert
        assertDoesNotThrow(() -> spyServerInput.getInput());
    }


}