package org.pokergame;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {


    @Test
    void getSuit() {

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
    void testEquals() {
        //Equal
        Card cardToTest = new Card(Card.ACE, Card.DIAMONDS);
        Card card2 = new Card(Card.ACE, Card.DIAMONDS);
        assertEquals(cardToTest,card2);
       //Not equal
        cardToTest = new Card(Card.QUEEN, Card.HEARTS);
        card2 = new Card(Card.KING, Card.HEARTS);
        assertNotEquals(cardToTest,card2);
    }

    @Test
    void compareTo() {
        //LOW to HIGH
        Card cardCompareTo = new Card(Card.ACE, Card.DIAMONDS);
        Card comparesTo = new Card(Card.ACE, Card.SPADES);
        int expected = -1;
        int result = cardCompareTo.compareTo(new Card(Card.ACE, Card.DIAMONDS));
        assertEquals(expected,result);

        //JAG SLUTADE HÄR ---------------------------------------->

        //HIGH to LOW
        cardCompareTo = new Card(Card.JACK, Card.SPADES);
        cardCompareTo.compareTo(new Card(Card.JACK, Card.DIAMONDS));
        assertEquals(1,cardCompareTo.compareTo(new Card(Card.ACE, Card.HEARTS)));

        //Invalid
        try {
            cardCompareTo.compareTo(null);
            fail("No exception thrown");
        } catch (NullPointerException e) {

        }
    }
}