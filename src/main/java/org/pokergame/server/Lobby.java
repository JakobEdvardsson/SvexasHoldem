package org.pokergame.server;

import org.pokergame.Client;
import org.pokergame.Player;
import org.pokergame.Table;
import org.pokergame.TableType;
import org.pokergame.bots.BasicBot;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Lobby {
    private final BigDecimal startingCash = new BigDecimal(500);
    private final BigDecimal BIG_BLIND = new BigDecimal(100);
    private final TableType TABLE_TYPE = TableType.FIXED_LIMIT;
    private int lobbyIndex;
    private int size = 4;

    /**
     * If the lobby is open to join
     */
    private Boolean available = true;

    /**
     * The table (Game Engine) in the lobby
     */
    private Table table;

    /**
     * The players at the table.
     */
    private ArrayList<Player> players;
    private Object lock = new Object();

    private int playerCount;

    private ServerController controller;

    private boolean running;


    public Lobby(ServerController controller) {
        table = new Table(TABLE_TYPE, BIG_BLIND, this);
        players = new ArrayList<>();
        this.controller = controller;
        playerCount = 0;
        running = false;
    }

    /**
     * public Player addPlayer(ClientHandler clientHandler) {
     * //TODO: Implement name
     * Player player = new Player("Implement_Name",startingCash, clientHandler);
     * players.add(player);
     * return player;
     * }
     */


    public synchronized Player addPlayer(String playerName, Client client) {
        synchronized (lock) {
            if (!table.isRunning() && playerCount < 5) {
                Player player = new Player(playerName, startingCash, client);
                players.add(player);
                table.addPlayer(player);
                playerCount++;
                return player;
            }
            return null;
        }
    }

    public synchronized void removePlayer(ClientHandler ClientHandler) {
        synchronized (lock) {
            for (Player player : players) {
                if (!table.isRunning() && player.getClient() == ClientHandler) {
                    players.remove(player);
                    table.removePlayer(player);
                    playerCount--;
                    break;
                }
            }
        }

        // If only bot players remain
        if (playerCount == 0 && table.isRunning()) {
            System.out.println("No remaining players in the lobby, exiting game.");
            table.exitGame();
        }
    }

    public void gameFinished() {
        players.clear();
        this.running = false;
        table = new Table(TABLE_TYPE, BIG_BLIND, this);
        if (controller != null) controller.updateLobbyStatus(); // Update clients with lobby status
    }

    public void startTable() {
        int playerCount = players.size();

        while (playerCount < 4) {
            Player playerToAdd = new Player("Player_" + (playerCount + 1),
                    startingCash,
                    new BasicBot(50, 50));

            players.add(playerToAdd);
            table.addPlayer(playerToAdd);

            playerCount++;
        }

        running = true;
        this.table.start();
    }

    public boolean isRunning() {
        return running;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public synchronized ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public int getLobbyIndex() {
        return lobbyIndex;
    }

    public BigDecimal getBigBlind() {
        return BIG_BLIND;
    }

    public TableType getTableType() {
        return TABLE_TYPE;
    }

    public void setLobbyIndex(int lobbyIndex) {
        this.lobbyIndex = lobbyIndex;
    }
}
