package org.svexasHoldem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.svexasHoldem.actions.PlayerAction;
import org.svexasHoldem.bots.BasicBot;
import org.svexasHoldem.gui.Main;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class TableTest {

    static final TableType TABLE_TYPE = TableType.NO_LIMIT;
    /**
     * The size of the big blind.
     */
    static final BigDecimal BIG_BLIND = BigDecimal.valueOf(10);
    /**
     * The starting cash per player.
     */
    static final BigDecimal STARTING_CASH = BigDecimal.valueOf(500);
    Player humanPlayer;
    private Player player;

    private Table table;


    @BeforeEach
    void setUp() {
        this.player = mock(Player.class);


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


    @Test
    void getAllowedActionsAllIn() {
        doReturn(true).when(player).isAllIn();
        player.setAction(PlayerAction.ALL_IN);

        Set<PlayerAction> results = table.getAllowedActions(player);

        assertTrue(results.contains(PlayerAction.CHECK), "Player should be able to check");
    }

    @Test
    void getAllowedActionsFold() {
        doReturn(false).when(player).isAllIn();
        player.setAction(PlayerAction.FOLD);

        Player player = new Player("Test Player", BigDecimal.valueOf(500), new BasicBot(0, 75));
        table.setActor(player);

        // Bet > 0
        table.setBet(BigDecimal.valueOf(100));
        Set<PlayerAction> results = table.getAllowedActions(player);
        assertTrue(results.contains(PlayerAction.FOLD), "Player should be able to fold");


        // Bet = 0
        table.setBet(BigDecimal.valueOf(0));
        results = table.getAllowedActions(player);
        assertTrue(results.contains(PlayerAction.FOLD), "Player should be able to fold");
    }


    @Test
    void getAllowedActionsCheck() {
        doReturn(false).when(player).isAllIn();
        player.setAction(PlayerAction.CHECK);

        Player player = new Player("Test Player", BigDecimal.valueOf(500), new BasicBot(0, 75));
        table.setActor(player);

        // Expected True

        // Bet = 0 and player bet = 0
        table.setBet(BigDecimal.valueOf(0));
        table.getActor().setBet(BigDecimal.valueOf(0));
        player.setBet(BigDecimal.valueOf(0));
        Set<PlayerAction> results = table.getAllowedActions(player);
        assertTrue(results.contains(PlayerAction.CHECK), "Player should be able to check");

        // Bet = 100 and player bet = 100
        table.setBet(BigDecimal.valueOf(100));
        table.getActor().setBet(BigDecimal.valueOf(0));
        player.setBet(BigDecimal.valueOf(100));
        results = table.getAllowedActions(player);
        assertTrue(results.contains(PlayerAction.CHECK), "Player should be able to check");

        // Should not be possible to happen

        // Bet = 0 and player bet = 100
        table.setBet(BigDecimal.valueOf(0));
        player.setBet(BigDecimal.valueOf(100));
        table.getActor().setBet(BigDecimal.valueOf(100));
        results = table.getAllowedActions(player);
        assertTrue(results.contains(PlayerAction.CHECK), "Player should be able to check");


        // Expected False

        // Bet = 100 and player bet = 0
        table.setBet(BigDecimal.valueOf(100));
        player.setBet(BigDecimal.valueOf(0));
        table.getActor().setBet(BigDecimal.valueOf(0));
        results = table.getAllowedActions(player);
        assertFalse(results.contains(PlayerAction.CHECK), "Player should be able to check");
    }
}