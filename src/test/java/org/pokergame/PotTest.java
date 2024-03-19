package org.pokergame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pokergame.server.ClientHandler;
import org.pokergame.server.Lobby;
import org.pokergame.server.ServerController;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

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
        //System.out.println("---------- " + pot.getContributors());
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
        System.out.println("---------- " + pot.split(player, bet));
    }

    @Test
    void clear() {
    }

    @Test
    void testToString() {
    }
}