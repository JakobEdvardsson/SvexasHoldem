package org.pokergame.server;


import org.pokergame.Card;
import org.pokergame.Client;
import org.pokergame.Player;
import org.pokergame.TableType;
import org.pokergame.actions.PlayerAction;
import org.pokergame.toClientCommands.BoardUpdated;
import org.pokergame.toClientCommands.HandStarted;
import org.pokergame.toClientCommands.JoinedTable;
import org.pokergame.toServerCommands.JoinLobby;
import org.pokergame.toServerCommands.LeaveLobby;
import org.pokergame.toServerCommands.Register;
import org.pokergame.util.Buffer;

import java.math.BigDecimal;
import java.net.Socket;
import java.util.List;
import java.util.Set;

public class ClientHandler extends Thread implements Client {
    private Socket socket;
    private ServerController serverController;
    private ServerInput serverInput;
    private ServerOutput serverOutput;
    private Buffer<Player> packetBuffer;


    public ClientHandler(Socket socket, ServerController serverController){
        System.out.println("Client Handler created");
        this.socket = socket;
        this.serverController = serverController;
    }

    @Override
    public void run() {
        serverOutput = new ServerOutput(socket);
        serverInput = new ServerInput(socket, this);
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
    }

    public PlayerAction act(BigDecimal minBet, BigDecimal bet, Set<PlayerAction> allowedActions) {
        return null;
    }

    public void playerUpdated(Player playerToShow) {
    }

    public void messageReceived(String message) {
    }

    public void boardUpdated(List<Card> board, BigDecimal bet, BigDecimal pot) {
        serverOutput.sendMessage(new BoardUpdated(board, bet, pot));
    }

    public void playerActed(Player playerInfo) {
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
    }

    public void sendMessage(Object obj) {
        serverOutput.sendMessage(obj);
    }

    public void startGame() {
        serverController.startGame(this);
    }
}
