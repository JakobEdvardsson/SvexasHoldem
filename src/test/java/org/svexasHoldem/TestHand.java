package org.svexasHoldem;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;

/**
 * Note to self. Not possible to add a new card to the hand with null Value as
 * the constructor for the card class already checks for null values.
 * This means that the check in the different methods will never be true.
 * Maybe they should be deleted?
 */
public class TestHand {
    Hand hand;
    String card;
    Card cards[];
    IllegalArgumentException exception;

    String expectedMessage, actualMessage;

    List<Card> cardList;

    @BeforeEach
    void setUp() {

        card = "3d";
        hand = new Hand(card);
        cards = new Card[] {new Card("3d"), new Card("4d"), new Card("5d")};
    }

    @Test
    void testHandSize() {
        //Test initial size of hand
        assertEquals(1, hand.size());

        //Test size of hand after adding a single card
        hand.addCard(new Card("Qd"));
        assertEquals(2, hand.size());

        //Test size of hand after adding multiple cards.
        //Player can only hold 2 cards at a time. But this tests the addCards method.
        hand.addCards(cards);
        assertEquals(5, hand.size());

        //Test size of hand after removing all cards
        hand.removeAllCards();
        assertEquals(0, hand.size());
    }

    @Test
    void testHandToString() {
        hand.addCard(new Card("Qd"));
        assertEquals("Qd 3d", hand.toString());
    }



    @Test
    void testGetCards() {

        //Test initial size of hand
        assertEquals(1, hand.size());

        //Test size of hand after adding a single card
        hand.addCard(new Card("Qd"));
        assertEquals(2, hand.size());

        //Test size of hand after adding multiple cards.
        //Player can only hold 2 cards at a time. But this tests the addCards method.
        hand.addCards(cards);
        assertEquals(5, hand.size());

        hand.addCard(new Card("Ad"));


        cards = hand.getCards();
        assertEquals(6, cards.length);
    }

    @Test
    void testMaxLevelOfCards() {

        exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            hand = new Hand("Ac Qd Td 8h 5s 4c 2d 3d");
        });

        String expectedMessage = "Too many cards in hand";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testAddingCardWithEmptyString() {

        exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            hand = new Hand("");
        });

        String expectedMessage = "Null or empty string";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }


    @Test
    void testAddNullCard(){
        exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            hand = new Hand(new Card[]{null});
        });

        String expectedMessage = "Null card";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);

    }
    /*

    @Test
    void testAddNullCardArray(){
       cards = null;
        exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            hand = new Hand(cards);
        });

        String expectedMessage = "Null array";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);

    }

     */

    @Test
    void testAddCardArray(){
        //Adding 9 cards to the hand
        exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            hand = new Hand(new Card[]{new Card("3d"), new Card("4d"),
                    new Card("5d"), new Card("6d"), new Card("7d"),
                    new Card("8d"), new Card("9d"), new Card("Td"),
                    new Card("Jd")});
        });

        expectedMessage = "Too many cards";
        actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);

        //Adding a "null array" to the hand
        cards = null;
        exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            hand = new Hand(cards);
        });

        expectedMessage = "Null array";
        actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);


    }

    /*
    @Test
    void testAddCardArrayMin(){
        exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Hand((Collection<Card>) null);
        });

        String expectedMessage = "Null array";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);

    }


     */
    @Test
    void testAddCardArrayWithCollection(){
        Collection<Card> cards = new ArrayList<>();
        cards.add(new Card("3d"));
        cards.add(new Card("4d"));
        cards.add(new Card("5d"));
        Hand hand1 = new Hand(cards);

        assertEquals(3, hand1.size());

        Hand hand2 = new Hand();
        hand2.addCards(cards);

        assertEquals(3, hand2.size());

        exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Hand((Collection<Card>) null);
        });

        expectedMessage = "Null array";
        actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);

    }



}
