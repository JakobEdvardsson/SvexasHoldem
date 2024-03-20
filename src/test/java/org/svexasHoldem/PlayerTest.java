package org.svexasHoldem;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.svexasHoldem.actions.PlayerAction;

import java.math.BigDecimal;
import java.util.List;

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
    public void isAllIn_True() {
        Player player = new Player("Player", BigDecimal.ZERO, null);
        player.setCards(List.of(new Card("Ac"), new Card("2c")));
        boolean actual = player.isAllIn();
        Assertions.assertTrue(actual);
    }

    @Test
    public void isAllIn_False() {
        Player player = new Player("Player", new BigDecimal(10), null);
        player.setCards(List.of(new Card("Ac"), new Card("2c")));
        boolean actual = player.isAllIn();
        Assertions.assertFalse(actual);
    }

    @Test
    public void isAllIn_Null() {
        Player player = new Player("Player", null, null);
        player.setCards(List.of(new Card("Ac"), new Card("2c")));
        boolean actual = player.isAllIn();
        Assertions.assertFalse(actual);
    }

    @Test
    public void testGetCards() {
        Player player = new Player("Player", BigDecimal.ZERO, null);
        player.setCards(List.of(new Card("Ac"), new Card("2c")));
        Card[] cards = player.getCards();
        assertArrayEquals(new Card[] { new Card("Ac"), new Card("2c") }, cards);
    }


    /*
    @Test
    void hasCards() {
        Hand hand = Mockito.mock(Hand.class);
        hand.addCard(new Card(8, 2));
        Player player = new Player("Player", BigDecimal.ZERO, null);
        boolean cardsOnHand = player.hasCards();
        assertTrue(cardsOnHand);
    }

     */



    @Test
    public void hasCardsShouldReturnFalse() {

        Player player = new Player("Player", BigDecimal.ZERO, null);
        boolean hasCards = player.hasCards();
        Assertions.assertFalse(hasCards);
    }

    @Test
    public void hasCardsShouldReturnTrue() {

        Player player = new Player("Player", BigDecimal.ZERO, null);
        player.setCards(List.of(new Card("Ac"), new Card("2c")));
        boolean hasCards = player.hasCards();
        Assertions.assertTrue(hasCards);
    }

    @Test
    void postSmallBlind() {
        Player player = new Player("Player", BigDecimal.ZERO, null);
        player.postSmallBlind(BigDecimal.TEN);
        assertEquals(PlayerAction.SMALL_BLIND, player.getAction());
       // assertEquals(BigDecimal.TEN, player.getCash());
        assertEquals(BigDecimal.TEN, player.getBet());
    }


    @Test
    void postBigBlind() {
        Player player = new Player("Player", BigDecimal.ZERO, null);
        player.postBigBlind(BigDecimal.TEN);
        assertEquals(PlayerAction.BIG_BLIND, player.getAction());
        // assertEquals(BigDecimal.TEN, player.getCash());
        assertEquals(BigDecimal.TEN, player.getBet());
    }


    @Test
    void payCash() {

        Player player = new Player("Player", new BigDecimal(100), null);
        player.payCash(new BigDecimal(50));
        assertEquals(new BigDecimal(50), player.getCash());
    }

    @Test
    public void testPayCash_ThrowsException() {

        Player player = new Player("Player", new BigDecimal(10), null);
        assertThrows(IllegalStateException.class, () -> player.payCash(new BigDecimal(50)));
    }

    @Test
    public void win() {
        Player player = new Player("Player", BigDecimal.ZERO, null);
        player.win(BigDecimal.TEN);
        Assertions.assertEquals(BigDecimal.TEN, player.getCash());
    }



    @Test
    public void setCards_OneCard_ThrowsIllegalArgumentException() {

        List<Card> cards = List.of(new Card("Ac"));
        Player player = new Player("Player", BigDecimal.ZERO, null);
        assertThrows(IllegalArgumentException.class, () -> player.setCards(cards));
    }

    @Test
    public void setCards_ThreeCards_ThrowsIllegalArgumentException() {

        List<Card> cards = List.of(new Card("Ac"), new Card("2c"), new Card("3h"));
        Player player = new Player("Player", BigDecimal.ZERO, null);
        assertThrows(IllegalArgumentException.class, () -> player.setCards(cards));
    }



    @Test
    public void getClient() {
        Player player = new Player("Player", BigDecimal.ZERO, null);
        Client expected = null;
        Client actual = player.getClient();
        assertEquals(expected, actual);
    }

    @Test
    public void testToString() {
        String expected = "Player";
        String actual = new Player("Player", BigDecimal.ZERO, null).toString();
        assertEquals(expected, actual);
    }

    @Test
    public void testPublicClone() {
        Player player = new Player("Player", BigDecimal.ZERO, null);
        player.setCards(List.of(new Card("Ac"), new Card("2c")));
        Player clone = player.publicClone();
        assertEquals("Player", clone.getName());
        assertEquals(BigDecimal.ZERO, clone.getCash());
        assertEquals(true, clone.hasCards());
        assertEquals(BigDecimal.ZERO, clone.getBet());
        assertEquals(null, clone.getAction());
    }

    @Test
    public void testPublicCloneNoCards() {
        Player player = new Player("Player", BigDecimal.ZERO, null);
        Player clone = player.publicClone();
        assertEquals("Player", clone.getName());
        assertEquals(BigDecimal.ZERO, clone.getCash());
        assertEquals(false, clone.hasCards());
        assertEquals(BigDecimal.ZERO, clone.getBet());
        assertEquals(null, clone.getAction());
    }

    @Test
    public void testSetAction() {
        Player player = new Player("Player", BigDecimal.ZERO, null);
        player.setAction(PlayerAction.SMALL_BLIND);
        assertEquals(PlayerAction.SMALL_BLIND, player.getAction());
    }


}