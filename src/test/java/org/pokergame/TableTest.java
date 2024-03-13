package org.pokergame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.pokergame.bots.BasicBot;
import org.pokergame.gui.Main;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class TableTest {

    Player humanPlayer;

    static final TableType TABLE_TYPE = TableType.NO_LIMIT;

    /** The size of the big blind. */
    static final BigDecimal BIG_BLIND = BigDecimal.valueOf(10);

    /** The starting cash per player. */
    static final BigDecimal STARTING_CASH = BigDecimal.valueOf(500);

    private Player player;

    private Table table;


    @BeforeEach
    void setUp() {
        /* The players at the table. */
        Map<String, Player> players = new LinkedHashMap<>();
        humanPlayer = new Player("Player", STARTING_CASH, mock(Main.class));
        players.put("Player", humanPlayer);
        players.put("Joe", new Player("Joe", STARTING_CASH, new BasicBot(0, 75)));
        players.put("Mike", new Player("Mike", STARTING_CASH, new BasicBot(25, 50)));
        players.put("Eddie", new Player("Eddie", STARTING_CASH, new BasicBot(50, 25)));

        /* The table. */
        table = new Table(TABLE_TYPE, BIG_BLIND, null);
        for (Player player : players.values()) {
            table.addPlayer(player);
        }
    }

    @Test
    void addPlayer() {
        Player player = new Player("Test Player", BigDecimal.valueOf(500), new BasicBot(0, 75));
        table.addPlayer(player);
        assertTrue(table.getPlayers().contains(player), "Player should be added to the table");
    }
}