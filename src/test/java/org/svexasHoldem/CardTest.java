package org.svexasHoldem;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class CardTest {

    IllegalArgumentException exception;

    Card card;

    String expectedMessage, outputMessage;

    @BeforeEach
    void setUp() {
        card = new Card("3d");
    }

    @Test
    void getSuit() {

        //High
        Card card = new Card(Card.QUEEN, Card.SPADES);
        int expected = 3;
        int cardSuit = card.getSuit();
        assertEquals(expected, cardSuit);

        //Low
        card = new Card(Card.EIGHT, Card.DIAMONDS);
        expected = 0;
        cardSuit = card.getSuit();
        assertEquals(expected, cardSuit);

        //Invalid
        try {
            new Card(0, -1);
            fail("No exception thrown");
        } catch (IllegalArgumentException e) {

        }

        //HEARTS
        card = new Card(Card.JACK, Card.HEARTS);
        expected = 2;
        cardSuit = card.getSuit();
        assertEquals(expected, cardSuit);

        //CLUBS
        card = new Card(Card.FIVE, Card.CLUBS);
        expected = 1;
        cardSuit = card.getSuit();
        assertEquals(expected, cardSuit);
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
        assertEquals(expected, cardRank);

        //Low
        card = new Card(Card.DEUCE, Card.DIAMONDS);
        expected = 0;
        cardRank = card.getRank();
        assertEquals(expected, cardRank);

        //Invalid
        try {
            card = new Card(-1, 0);
            fail("No exception thrown");
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    void testEquals() {
        //Equal
        Card cardToTest = new Card(Card.ACE, Card.DIAMONDS);
        Card card2 = new Card(Card.ACE, Card.DIAMONDS);
        assertEquals(cardToTest, card2);
        //Not equal
        cardToTest = new Card(Card.QUEEN, Card.HEARTS);
        card2 = new Card(Card.KING, Card.HEARTS);
        assertNotEquals(cardToTest, card2);
    }


    //Dessa tester behöver kollas över / FILIP
    @Test
    void compareTo() {
        //LOW to HIGH
        Card cardCompareTo = new Card(Card.ACE, Card.DIAMONDS);
        Card comparesTo = new Card(Card.ACE, Card.SPADES);
        int expected = 0;
        int result = cardCompareTo.compareTo(new Card(Card.ACE, Card.DIAMONDS));
        assertEquals(expected, result);


        //HIGH to LOW
        cardCompareTo = new Card(Card.JACK, Card.SPADES);
        cardCompareTo.compareTo(new Card(Card.JACK, Card.DIAMONDS));
        assertEquals(-1, cardCompareTo.compareTo(new Card(Card.ACE, Card.HEARTS)));

        //Invalid
        try {
            cardCompareTo.compareTo(null);
            fail("No exception thrown");
        } catch (NullPointerException e) {

        }
    }

    @Test
    void addCard() {

        //Adding a card with invalid value
        exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Card(null);
        });

        expectedMessage = "Null string or of invalid length";
        outputMessage = exception.getMessage();

        assertEquals(expectedMessage, outputMessage);

        //Adding a card with invalid value
        exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Card("3dd");
        });

        expectedMessage = "Empty string or invalid length";
        outputMessage = exception.getMessage();

        assertEquals(expectedMessage, outputMessage);

        //Adding a valid card
        card = new Card("3d");
        assertEquals("3d", card.toString());

        card = null;
        card = new Card("As");

        assertEquals("As", card.toString());

    }


    @Test
    void testParseRank() {

        //Parsing a valid rank
        int expected = 0;
        assertEquals(expected, card.parseRank("2d"));

        //Parsing an invalid rank
        exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            card.parseRank("1d");
        });

        expectedMessage = "Unknown rank: 1";
        outputMessage = exception.getMessage();

        assertEquals(expectedMessage, outputMessage);

        //Parsing an invalid rank with a letter
        exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            card.parseRank("Bd");
        });

        expectedMessage = "Unknown rank: B";
        outputMessage = exception.getMessage();

        assertEquals(expectedMessage, outputMessage);
    }

    @Test
    void testCardToString() {
        card.toString();
        assertEquals("3d", card.toString());
    }


    @Test
    void testParseSuit() {

        //Parsing a valid suit
        int expected = 0;
        assertEquals(expected, card.parseSuit("3d"));

        //Parsing an invalid suit
        exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            card.parseSuit("2e");
        });

        String expectedMessage = "Unknown suit: e";
        assertEquals(expectedMessage, exception.getMessage());
    }


}