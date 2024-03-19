package org.svexasHoldem.server;


import org.svexasHoldem.Card;
import org.svexasHoldem.Client;
import org.svexasHoldem.Player;
import org.svexasHoldem.TableType;
import org.svexasHoldem.actions.PlayerAction;
import org.svexasHoldem.toClientCommands.*;
import org.svexasHoldem.toServerCommands.JoinLobby;
import org.svexasHoldem.toServerCommands.LeaveLobby;
import org.svexasHoldem.toServerCommands.Register;
import org.svexasHoldem.util.Buffer;

import java.math.BigDecimal;
import java.net.Socket;
import java.util.*;

public class ClientHandler extends Thread implements Client {
    private Socket socket;
    private ServerController serverController;
    private ServerInput serverInput;
    private ServerOutput serverOutput;
    private Buffer<PlayerAction> packetBuffer;


    public ClientHandler(Socket socket, ServerController serverController){
        System.out.println("Client Handler created");
        this.socket = socket;
        this.serverController = serverController;
        this.packetBuffer = new Buffer<PlayerAction>(10, 60000);
    }

    @Override
    public void run() {
        serverOutput = new ServerOutput(socket, packetBuffer);
        serverInput = new ServerInput(socket, this, packetBuffer);
        serverInput.start();
    }

    /*
     TODO: Implement these methods
        They are supposed to be used to communicate with the client
        Use serverInput and serverOutput to communicate with the client
     */

    public void joinedTable(TableType tableType, BigDecimal bigBlind, List<Player> players) {

    }

    public void handStarted(Player dealer) {
        serverOutput.sendMessage(new HandStarted(dealer.publicClone()));
    }

    public void actorRotated(Player actor) {
        serverOutput.sendMessage(new ActorRotated(actor.publicClone()));
    }

    public PlayerAction act(BigDecimal minBet, BigDecimal bet, Set<PlayerAction> allowedActions) {
        long timeout = packetBuffer.getTimeoutMillis();
        long timeoutRemaining = System.currentTimeMillis() + timeout;
        serverOutput.sendMessage(new Act(minBet, bet, allowedActions, timeoutRemaining));
        return packetBuffer.get();
    }

    public void playerUpdated(Player playerToShow) {
        serverOutput.sendMessage(new PlayerUpdated(playerToShow.packetClone()));
    }

    public void messageReceived(String message) {
        serverOutput.sendMessage(new MessageReceived(message));
    }

    public void boardUpdated(List<Card> board, BigDecimal bet, BigDecimal pot) {
        serverOutput.sendMessage(new BoardUpdated(board, bet, pot));
    }

    public void playerActed(Player playerInfo) {
        serverOutput.sendMessage(new PlayerActed(playerInfo));
    }

    public void registerClient(Register register) {
        serverController.registerClient(register.name(), this);
        pushLobbyInformation();
    }

    public void joinTable(JoinLobby lobby) {
        serverController.joinLobby(this, lobby.tableId());
    }

    public void pushLobbyInformation() {
        String[][] lobbies = serverController.getLobbiesAsString();
        serverOutput.sendMessage(lobbies);
    }

    public void pushLobbyInformation(String[][] lobbyStrings) {
            serverOutput.sendMessage(lobbyStrings);
    }

    public void leaveLobby(LeaveLobby lobby) {
        serverController.leaveLobby(this, lobby.lobbyId());
    }

    public void disconnectClient() {
        serverController.disconnectClient(this);
        packetBuffer.add(null);
    }

    public void sendMessage(Object obj) {
        serverOutput.sendMessage(obj);
    }

    public void startGame(Object obj) {
        serverController.startGame(this, obj);
    }
}
