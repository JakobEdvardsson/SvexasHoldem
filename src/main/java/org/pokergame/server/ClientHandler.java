package org.pokergame.server;


import org.pokergame.Card;
import org.pokergame.Player;
import org.pokergame.TableType;
import org.pokergame.actions.PlayerAction;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigDecimal;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class ClientHandler extends Thread {
    private Socket socket;
    private ServerController serverController;
    private ObjectInputStream ois;


    public ClientHandler(Socket socket, ServerController serverController) throws IOException {
        System.out.println("Client Handler created");
        this.socket = socket;
        this.serverController = serverController;
        //ois = new ObjectInputStream(socket.getInputStream());

    }

    @Override
    public void run() {
        try {
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Thread in clienthandler? ");
        while (true) {
            System.out.print("Enter message to send to client: ");
            Scanner scanner = new Scanner(System.in);
        }

        /*TODO:
        - Server send available lobbies
        - Client sends what lobby to join
         */
    }


    /*
     TODO: Implement these methods
        They are supposed to be used to communicate with the client
        Use serverInput and serverOutput to communicate with the client
     */

    public void joinedTable(TableType tableType, BigDecimal bigBlind, List<Player> players) {
    }

    public void handStarted(Player dealer) {
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
    }

    public void playerActed(Player playerInfo) {
    }
}
