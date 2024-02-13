package org.pokergame;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void playerAct() {
        Player player = new Player("Player", BigDecimal.ZERO, null);
        player.setBet(BigDecimal.ZERO);

    }

    @Test
    void resetBet() {
        Player player = new Player("Player", BigDecimal.ZERO, null);
        player.setBet(BigDecimal.valueOf(1000));
        int expected = 0;
        player.resetBet();
        int actual = player.getBet().intValue();
        assertEquals(expected, actual);
    }

    @Test
    void getName() {
        //Valid
        String expected = "Player";
        String actual = "Player";
        assertEquals(expected, actual);
        //Invalid
        expected = "Player";
        actual = null;
        assertNotEquals(expected, actual);
    }

    @Test
    void getBet() {
        Player player = new Player("Player", BigDecimal.ZERO, null);
        player.setBet(BigDecimal.valueOf(100000));
        int expected = 100000;
        int actual = player.getBet().intValue();
        assertEquals(expected, actual);
    }

    @Test
    void getAction() {
    }


    @Test
    void isAllIn() {
    }

    @Test
    void getCards() {


    }

    @Test
    void hasCards() {
        Hand hand = Mockito.mock(Hand.class);
        hand.addCard(new Card(8, 2));
        Player player = new Player("Player", BigDecimal.ZERO, null);
        boolean cardsOnHand = player.hasCards();
        assertTrue(cardsOnHand);
    }

    @Test
    void postSmallBlind() {
    }

    @Test
    void postBigBlind() {
    }

    @Test
    void payCash() {
    }

    @Test
    void win() {
    }
}