package org.svexasHoldem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HandValueTest {
    private Hand hand;
    private HandValue zeroHandValue; //Value of 0
    private HandValue handValue; //Value of 0
    private HandValue bigHandValue; //Value of 2941783
    @BeforeEach
    void setUp() {
        this.hand = new Hand();
        this.zeroHandValue = new HandValue(hand);
        this.handValue = new HandValue(hand);

        Card[] cards = new Card[4];

        for(int i = 0; i < 4; i++){
            Card card = new Card(12, i);
            cards[i] = card;
        }

        Hand handTest = new Hand(cards);
        this.bigHandValue = new HandValue(handTest);
    }

    @Test
    void getHand() {
        assertEquals(hand, handValue.getHand());
    }

    @Test
    void getType() {
        assertEquals(HandValueType.HIGH_CARD, handValue.getType());
    }

    @Test
    void getDescription() {
        assertEquals(HandValueType.HIGH_CARD.getDescription(), handValue.getDescription());
    }

    @Test
    void getValue() {
        assertEquals(0, handValue.getValue());
    }

    @Test
    void testHashCode() {
        assertEquals(0, handValue.getValue());
    }

    @Test
    void testEquals() {
        assertTrue(handValue.equals(zeroHandValue));
        assertFalse(handValue.equals(bigHandValue));
    }

    @Test
    void compareTo() {
        assertEquals(1, handValue.compareTo(bigHandValue));
        assertEquals(0, handValue.compareTo(zeroHandValue));
        assertEquals(-1, bigHandValue.compareTo(handValue));
    }

    @Test
    void testToString() {
        assertEquals("a High Card (0)", handValue.toString());
    }
}