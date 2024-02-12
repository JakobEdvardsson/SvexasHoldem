package org.pokergame;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    //char[] Suit_Symbols d,c,h,s

    @Test
    void getSuit() {
        Card card = new Card(Card.JACK,Card.HEARTS);
        int expected = 2;
        int cardSuit = card.getSuit();
        assertEquals(expected,cardSuit);
        card = new Card(Card.FIVE,Card.CLUBS);
        expected = 1;
        cardSuit = card.getSuit();
        assertEquals(expected,cardSuit);
        card = new Card(Card.EIGHT,Card.DIAMONDS);
        expected = 0;
        cardSuit = card.getSuit();
        assertEquals(expected,cardSuit);
        card = new Card(Card.QUEEN,Card.SPADES);
        expected = 3;
        cardSuit = card.getSuit();
        assertEquals(expected,cardSuit);
        card = new Card(Card.THREE,5);
        expected = 1;
        cardSuit = card.getSuit();
        assertNotEquals(expected,cardSuit);

    }

    @Test
    void getRank() {
        Card card = new Card(Card.TEN, Card.HEARTS);

        assertEquals(Card.TEN, card.getRank());
    }

    @Test
    void testHashCode() {
    }

    @Test
    void testEquals() {
    }

    @Test
    void compareTo() {
    }
}