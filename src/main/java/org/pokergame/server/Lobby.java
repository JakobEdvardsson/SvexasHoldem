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


    public Lobby() {
        table = new Table(TABLE_TYPE, BIG_BLIND);
        // Todo figure out where to start the table thread
        players = new ArrayList<>();
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
            Player player = new Player(playerName, startingCash, client);
            players.add(player);
            table.addPlayer(player);
            return player;
        }
    }

    public synchronized void removePlayer(ClientHandler ClientHandler) {
        synchronized (lock) {
            for (Player player : players) {
                if (player.getClient() == ClientHandler) {
                    players.remove(player);
                    table.removePlayer(player);
                    break;
                }
            }
        }
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

        this.table.start();
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
