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

        //Invalid
        try {
            new Card(0, -1);
            fail("No exception thrown");
        } catch (IllegalArgumentException e) {

        }

        //HEARTS
        card = new Card(Card.JACK,Card.HEARTS);
        expected = 2;
        cardSuit = card.getSuit();
        assertEquals(expected,cardSuit);

        //CLUBS
        card = new Card(Card.FIVE,Card.CLUBS);
        expected = 1;
        cardSuit = card.getSuit();
        assertEquals(expected,cardSuit);


    }



   /*Suit order:
    Spades
    Hearts
    Diamonds
    Clubs
    */
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
        try {
           card =  new Card(-1, 0);
            fail("No exception thrown");
        } catch (IllegalArgumentException e) {

        }
    }


    @Test
    void testHashCode() {
        Card Kh = new Card("Kh");
        int result = 0;


    }

    @Test
    void testEquals() {

    }

    @Test
    void compareTo() {
    }
}