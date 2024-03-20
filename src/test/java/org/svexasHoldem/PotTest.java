package org.svexasHoldem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.svexasHoldem.server.ClientHandler;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PotTest {

    private BigDecimal bet;
    private Pot pot;
    private Player player;

    @BeforeEach
    void setUp() {
        bet = new BigDecimal(100);
        pot = new Pot(bet);

        String name = "test";
        BigDecimal cash = new BigDecimal(100);
        ClientHandler handler = new ClientHandler(null, null);
        player = new Player(name, cash, handler);

        pot.addContributer(player);
    }

    @Test
    void getBet() {
        assertEquals(bet, pot.getBet());
    }

    @Test
    void getContributors() {
    }

    @Test
    void addContributer() {
        assertTrue(pot.hasContributer(player));
    }

    @Test
    void hasContributer() {
    }

    @Test
    void getValue() {
        assertEquals(bet, pot.getValue());
    }

    @Test
    void split() {
        pot.split(player, bet.divide(new BigDecimal(10)));

        assertEquals(new BigDecimal(10), pot.getBet());
        assertEquals(new BigDecimal(10), pot.getValue());
        assertFalse(pot.getContributors().isEmpty());
    }

    @Test
    void clear() {
        pot.clear();

        assertEquals(new BigDecimal(0), pot.getBet());
        assertTrue(pot.getContributors().isEmpty());
    }

    @Test
    void testToString() {
        assertTrue(pot.toString().contains("100"));
        assertTrue(pot.toString().contains("test"));
        assertTrue(pot.toString().contains("Total: 100"));
    }
}