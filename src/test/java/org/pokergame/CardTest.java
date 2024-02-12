package org.pokergame;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    //char[] SUIT_SYMBOLS = { 'd', 'c', 'h', 's' };
    //String[] RANK_SYMBOLS 2,3,4,5,6,7,8,9,T,J,Q,K,A

    @Test
    void getSuit() {

        //BVA

        //High
        Card card = new Card(Card.QUEEN,Card.SPADES);
        int expected = 3;
        int cardSuit = card.getSuit();
        assertEquals(expected,cardSuit);

        //Low
        card = new Card(Card.EIGHT,Card.DIAMONDS);
        expected = 0;
        cardSuit = card.getSuit();
        assertEquals(expected,cardSuit);

        //Invalid, Behöver fixas
        /*card = new Card(Card.JACK, 4);
        String invalid = "Invalid suit";
        cardSuit = card.getSuit();
        assertNotEquals(invalid, cardSuit);*/

        card = new Card(Card.JACK,Card.HEARTS);
        expected = 2;
        cardSuit = card.getSuit();
        assertEquals(expected,cardSuit);

        card = new Card(Card.FIVE,Card.CLUBS);
        expected = 1;
        cardSuit = card.getSuit();
        assertEquals(expected,cardSuit);


    }

    @Test
    void getRank() {

        //BVA

        //High
        Card card = new Card(Card.ACE, Card.HEARTS);
        int expected = 12;
        int cardRank = card.getRank();
        assertEquals(expected,cardRank);

        //Low
        card = new Card(Card.DEUCE, Card.DIAMONDS);
        expected = 0;
        cardRank = card.getRank();
        assertEquals(expected,cardRank);

        //Invalid
        /*card = new Card(13, 0);
        String invalid = "Invalid rank";
        cardRank = card.getRank();
        assertNotEquals(invalid, cardRank);*/

    }

    @Test
    void testHashCode() {
        Card card1 = new Card(Card.ACE, Card.HEARTS);
        int result = 0;


    }

    @Test
    void testEquals() {
    }

    @Test
    void compareTo() {
    }
}