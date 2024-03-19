package org.svexasHoldem.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.svexasHoldem.Card;
import org.svexasHoldem.Player;
import org.svexasHoldem.TableType;
import org.svexasHoldem.actions.PlayerAction;
import org.svexasHoldem.gui.OnlineMain;
import org.svexasHoldem.toClientCommands.*;
import org.svexasHoldem.toServerCommands.StartGame;

import java.math.BigDecimal;
import java.net.Socket;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


class ClientInputXTest {

    private ClientInputX clientInputX;
    private ClientInputX spyClientInputX;

    private ClientController clientController;


    @BeforeEach
    void getInput() {
        clientController = mock(ClientController.class);


        doNothing().when(clientController).invokeLater();


        clientInputX = new ClientInputX(mock(Socket.class), clientController);
        spyClientInputX = spy(clientInputX);

    }

    @Test
    void setLobbyInfo() {
        String[][] message = new String[][]{{"test1", "test2"}, {"test3", "test4"}};
        doReturn(message).when(spyClientInputX).recieveMessage();
        spyClientInputX.getInput();

        verify(clientController, times(1)).setLobbyInfo(message);
    }

    @Test
    void startGame() {
        // Arrange
        StartGame startGame = new StartGame(null);
        doReturn(startGame).when(spyClientInputX).recieveMessage();

        // Act
        spyClientInputX.getInput();

        // Assert
        assertNotNull(spyClientInputX.getOnlineMain());
    }

    @Test
    void joinedTable() {
        // Arrange
        TableType type = TableType.NO_LIMIT;
        BigDecimal bigBlind = new BigDecimal(10);
        List<String> players = List.of("test1", "test2", "test3");

        JoinedTable joinedTable = new JoinedTable(type, bigBlind, players);
        doReturn(joinedTable).when(spyClientInputX).recieveMessage();
        OnlineMain onlineMain = mock(OnlineMain.class);
        spyClientInputX.setOnlineMain(onlineMain);

        // Act
        spyClientInputX.getInput();

        // Assert
        verify(onlineMain, times(1)).joinedTable(any(), any(), any());
    }

    @Test
    void handStarted() {
        // Arrange
        Player dealer = new Player("testDealer", new BigDecimal(500), null);
        HandStarted handStarted = new HandStarted(dealer);
        doReturn(handStarted).when(spyClientInputX).recieveMessage();
        OnlineMain onlineMain = mock(OnlineMain.class);
        spyClientInputX.setOnlineMain(onlineMain);

        // Act
        spyClientInputX.getInput();

        // Assert
        verify(onlineMain, times(1)).handStarted(dealer);
    }

    @Test
    void boardUpdated() {
        List<Card> cards = new ArrayList<>();
        BigDecimal bet = new BigDecimal(10);
        BigDecimal pot = new BigDecimal(100);

        BoardUpdated boardUpdated = new BoardUpdated(cards, bet, pot);
        doReturn(boardUpdated).when(spyClientInputX).recieveMessage();
        OnlineMain onlineMain = mock(OnlineMain.class);
        spyClientInputX.setOnlineMain(onlineMain);

        spyClientInputX.getInput();

        verify(onlineMain, times(1)).boardUpdated(cards, bet, pot);
    }

    @Test
    void actorRotated() {
        // Arrange
        Player actor = new Player("testActor", new BigDecimal(500), null);
        ActorRotated actorRotated = new ActorRotated(actor);
        doReturn(actorRotated).when(spyClientInputX).recieveMessage();
        OnlineMain onlineMain = mock(OnlineMain.class);
        spyClientInputX.setOnlineMain(onlineMain);

        // Act
        spyClientInputX.getInput();

        // Assert
        verify(onlineMain, times(1)).actorRotated(actor);
    }

    @Test
    void playerActed() {
        // Arrange
        Player player = new Player("testPlayer", new BigDecimal(500), null);
        PlayerActed playerActed = new PlayerActed(player);
        doReturn(playerActed).when(spyClientInputX).recieveMessage();
        OnlineMain onlineMain = mock(OnlineMain.class);
        spyClientInputX.setOnlineMain(onlineMain);

        // Act
        spyClientInputX.getInput();

        // Assert
        verify(onlineMain, times(1)).playerActed(player);
    }

    @Test
    void playerUpdated() {
        // Arrange
        Player player = new Player("testPlayer", new BigDecimal(500), null);
        PlayerUpdated playerUpdated = new PlayerUpdated(player);
        doReturn(playerUpdated).when(spyClientInputX).recieveMessage();
        OnlineMain onlineMain = mock(OnlineMain.class);
        spyClientInputX.setOnlineMain(onlineMain);

        // Act
        spyClientInputX.getInput();

        // Assert
        verify(onlineMain, times(1)).playerUpdated(player);
    }

    @Test
    void act() {
        // Arrange
        BigDecimal minBet = new BigDecimal(10);
        BigDecimal currentBet = new BigDecimal(20);
        Set<PlayerAction> allowedActions = new HashSet<>(Arrays.asList(PlayerAction.CHECK, PlayerAction.FOLD));
        Act act = new Act(minBet, currentBet, allowedActions, 100);
        doReturn(act).when(spyClientInputX).recieveMessage();
        OnlineMain onlineMain = mock(OnlineMain.class);
        spyClientInputX.setOnlineMain(onlineMain);

        // Act
        spyClientInputX.getInput();

        // Assert
        verify(onlineMain, times(1)).act(minBet, currentBet, allowedActions);
    }

    @Test
    void messageReceived() {
        // Arrange
        String message = "Test message";
        MessageReceived messageReceived = new MessageReceived(message);
        doReturn(messageReceived).when(spyClientInputX).recieveMessage();
        OnlineMain onlineMain = mock(OnlineMain.class);
        spyClientInputX.setOnlineMain(onlineMain);

        // Act
        spyClientInputX.getInput();

        // Assert
        verify(onlineMain, times(1)).messageReceived(message);
    }

    @Test
    void testNullMessage() {
        // Arrange
        doReturn(null).when(spyClientInputX).recieveMessage();

        // Act and Assert
        assertDoesNotThrow(() -> spyClientInputX.getInput());

    }
}