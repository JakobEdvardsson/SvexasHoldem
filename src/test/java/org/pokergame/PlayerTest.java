package org.pokergame;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void getClient() {

    }

    @Test
    void resetHand() {
    }

    @Test
    void resetBet() {
    }

    @Test
    void setCards() {

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
    void getCash() {
    }

    @Test
    void getBet() {
        Player player = new Player("Player", BigDecimal.ZERO, null);
        player.setBet(BigDecimal.ZERO);
        BigDecimal expected = BigDecimal.ZERO;
        System.out.println(BigDecimal.ZERO);
    }


    @Test
    void getAction() {
    }

    @Test
    void setAction() {
    }

    @Test
    void isAllIn() {
    }

    @Test
    void getCards() {
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