package org.pokergame.server;

import org.pokergame.Client;
import org.pokergame.Player;
import org.pokergame.Table;
import org.pokergame.TableType;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Lobby {
    private final BigDecimal startingCash = new BigDecimal(1000);
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
        table = new Table(TableType.FIXED_LIMIT, new BigDecimal(100));
        // Todo figure out where to start the table thread
        // table.start();
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
            return player;
        }
    }

    public synchronized Player removePlayer(ClientHandler ClientHandler) {
        synchronized (lock) {
            for (Player player : players) {
                if (player.getClient() == ClientHandler) {
                    players.remove(player);
                    return player;
                }
            }
        }
        return null;
    }

    public void startTable() {
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

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public int getLobbyIndex() {
        return lobbyIndex;
    }

    public void setLobbyIndex(int lobbyIndex) {
        this.lobbyIndex = lobbyIndex;
    }
}
