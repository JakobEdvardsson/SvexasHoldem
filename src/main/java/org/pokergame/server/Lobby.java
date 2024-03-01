package org.pokergame.server;

import org.pokergame.Client;
import org.pokergame.Player;
import org.pokergame.Table;
import org.pokergame.TableType;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Lobby{
    private final BigDecimal startingCash = new BigDecimal(1000);
    private int lobbyIndex;
    private int size = 4;
    private String[] player;

    /** If the lobby is open to join */
    private Boolean available = true;

    /** The table (Game Engine) in the lobby */
    private Table table;

    /** The players at the table. */
    private ArrayList<Player> players;
    private Object lock = new Object();


    public Lobby() {
        table = new Table(TableType.FIXED_LIMIT, new BigDecimal(100));
        // Todo figure out where to start the table thread
        // table.start();
        players = new ArrayList<Player>();
        player = new String[size];
    }

   /** public Player addPlayer(ClientHandler clientHandler) {
        //TODO: Implement name
        Player player = new Player("Implement_Name",startingCash, clientHandler);
        players.add(player);
        return player;
    } */


   public synchronized void addPlayer(String playerName, Client client) {
       synchronized (lock) {
           for (int i = 0; i < size; i++) {
               if (player[i] == null) {
                   player[i] = playerName;
                   players.add(new Player(playerName, startingCash, client));
                   break;
               }
           }
       }
   }

   public synchronized void removePlayer(String userName) {
       synchronized (lock) {
           for (int i = 0; i < player.length; i++) {
               if (player[i] != null && player[i].equals(userName)) {
                   player[i] = null;
               }
           }

           Player playerToRemove = null;

           for (Player player : players) {
               if (player.getName().equals(userName)) {
                   playerToRemove = player;
                   break;
               }
           }

           players.remove(playerToRemove);

       }
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
