package org.pokergame.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.pokergame.Card;
import org.pokergame.Hand;

public class TestHand {
    Hand hand;
    String card;
    Card cards[];

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
}
