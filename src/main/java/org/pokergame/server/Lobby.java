package org.pokergame.server;

import org.pokergame.Client;
import org.pokergame.Player;
import org.pokergame.Table;
import org.pokergame.TableType;
import org.pokergame.bots.BasicBot;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;

public class Lobby {
    private BigDecimal startingCash = new BigDecimal(500);
    private final BigDecimal BIG_BLIND = new BigDecimal(10);
    private final TableType TABLE_TYPE = TableType.NO_LIMIT;
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

    private ArrayList<String> names;

    public Lobby(ServerController controller) {
        table = new Table(TABLE_TYPE, BIG_BLIND, this);
        players = new ArrayList<>();
        this.controller = controller;
        playerCount = 0;
        running = false;
        generateBotNames();
    }

    private ArrayList<String> generateBotNames() {
        names = new ArrayList<String>() {{
            add("Alex");
            add("Bella");
            add("Caleb");
            add("Daisy");
            add("Ethan");
            add("Fiona");
            add("Gavin");
            add("Holly");
            add("Ivan");
            add("Jenna");
        }};

        Collections.shuffle(this.names);
        return names;
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
                if (!table.isRunning()) {
                    players.remove(player);
                    table.removePlayer(player);
                    playerCount--;
                    break;
                } else {
                    System.out.println("Removing player " + player.getName() + " from table.");
                    playerCount--;
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
            Player playerToAdd = new Player(
                    getBotName(),
                    startingCash,
                    new BasicBot(50, 50));

            players.add(playerToAdd);
            table.addPlayer(playerToAdd);

            playerCount++;
        }

        running = true;

        for (Player player : players) {
            player.setCash(startingCash);
        }

        this.table.start();
    }

    private String getBotName() {
        return String.format("%s (bot)", names.removeFirst());
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

    public void setStackSize(BigDecimal stack) {
        startingCash = stack.divide(new BigDecimal(4));
    }
}
