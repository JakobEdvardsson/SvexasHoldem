package org.svexasHoldem.server;

import org.svexasHoldem.Client;
import org.svexasHoldem.Player;
import org.svexasHoldem.Table;
import org.svexasHoldem.TableType;
import org.svexasHoldem.bots.BasicBot;
import org.svexasHoldem.util.PokerUtils;

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

    public void generateBotNames() {
        names = new ArrayList<>() {{
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

    public synchronized Player removePlayer(ClientHandler ClientHandler) {
        Player ret = null;

        synchronized (lock) {
            if (!table.isRunning()) {
                for (Player player : players) {
                    if (player.getClient().equals(ClientHandler)) {
                        players.remove(player);
                        table.removePlayer(player);
                        playerCount--;
                        ret = player;
                        break;
                    }
                }
            } else {
                System.out.println("Removing player from table.");
                playerCount--;
            }
        }

        // If only bot players remain
        if (playerCount == 0 && table.isRunning()) {
            System.out.println("No remaining players in the lobby, exiting game.");
            table.exitGame();
        }
        return ret;
    }

    public void gameFinished() {
        players.clear();
        running = false;
        table = new Table(TABLE_TYPE, BIG_BLIND, this);
        if (controller != null) controller.updateLobbyStatus(); // Update clients with lobby status
    }

    public void startTable() {
        int playerCount = players.size();

        while (playerCount < 4) {

            int[] stats = PokerUtils.getRandomBotStats();
            String botName = getBotName();

            System.out.printf("Bot %s tightness: %d, aggressiveness: %d%n", botName, stats[0], stats[1]);

            Player playerToAdd = new Player(
                    botName,
                    startingCash,
                    new BasicBot(stats[0], stats[1]));

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

    public String getBotName() {
        return String.format("%s (bot)", names.removeFirst());
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public ArrayList<String> getNames() {
        return names;
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

    public void setLobbyIndex(int lobbyIndex) {
        this.lobbyIndex = lobbyIndex;
    }

    public BigDecimal getBigBlind() {
        return BIG_BLIND;
    }

    public TableType getTableType() {
        return TABLE_TYPE;
    }

    public void setStackSize(BigDecimal stack) {
        startingCash = stack.divide(new BigDecimal(4));
    }

    public BigDecimal getStackSize() {
        return startingCash;
    }
}
