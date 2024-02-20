package org.pokergame.server;

import org.pokergame.Player;
import org.pokergame.Table;
import org.pokergame.TableType;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Lobby{
    private final BigDecimal startingCash = new BigDecimal(1000);
    private int lobbyIndex;

    /** If the lobby is open to join */
    private Boolean available = true;

    /** The table (Game Engine) in the lobby */
    private Table table;

    /** The players at the table. */
    private ArrayList<Player> players;

    public Lobby() {
        table = new Table(TableType.FIXED_LIMIT, new BigDecimal(100));
        players = new ArrayList<Player>();
    }

   /** public Player addPlayer(ClientHandler clientHandler) {
        //TODO: Implement name
        Player player = new Player("Implement_Name",startingCash, clientHandler);
        players.add(player);
        return player;
    } */



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
