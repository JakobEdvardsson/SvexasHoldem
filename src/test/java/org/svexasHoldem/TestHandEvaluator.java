package org.svexasHoldem;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestHandEvaluator {
    HandEvaluator evaluator;
    Hand hand;
    Card cards[];


    @Test
    void testIsOnePair() {
        //Test for 1 pair.
        cards = new Card[] {new Card("4d"), new Card("4h"), new Card("5d"),
                new Card("7h"), new Card("Jc")};
        hand = new Hand(cards);
        evaluator = new HandEvaluator(hand);
        assertEquals(true, evaluator.isOnePair());

        //Test for 1 pair when hand does not contain a pair.
        cards = new Card[] {new Card("4d"), new Card("5h"), new Card("6d"),
                new Card("7h"), new Card("Jc")};
        hand = new Hand(cards);
        evaluator = new HandEvaluator(hand);
        assertEquals(false, evaluator.isOnePair());
    }

    @Test
    @DisplayName("Steel Wheel testing")
    void testSteelWheel() {
        cards = new Card[] {
                new Card(12, 0),
                new Card(0, 0),
                new Card(1, 0),
                new Card(2, 0),
                new Card(3, 0),
                new Card(7, 2),
                new Card(7, 3)
        };

        hand = new Hand(cards);
        evaluator = new HandEvaluator(hand);

        assertEquals("Straight flush with wheeling ace", evaluator.isStraightFlush());
    }

    @Test
    void testIsTwoPair() {
        //Test for 2 pair.
        cards = new Card[] {new Card("4c"), new Card("4d"), new Card("5h"),
                new Card("5c"), new Card("6d"), new Card("7h")};
        hand = new Hand(cards);
        evaluator = new HandEvaluator(hand);
        assertEquals(true, evaluator.isTwoPairs());;

        //Test for 2 pair when hand does not contain two pairs.
        cards = new Card[] {new Card("4c"), new Card("4h"), new Card("6d"),
                new Card("7h"), new Card("Jc")};
        hand = new Hand(cards);
        evaluator = new HandEvaluator(hand);
        assertEquals(false, evaluator.isTwoPairs());
    }

    @Test
    void testIsThreeOfAKind() {
        //Test for three of a kind.
        cards = new Card[] {new Card("4c"), new Card("4d"), new Card("4h"),
                new Card("5c"), new Card("6d"), new Card("7h")};
        hand = new Hand(cards);
        evaluator = new HandEvaluator(hand);
        assertEquals(true, evaluator.isThreeOfAKind());;

        //Test for three of a kind when hand does not contain three of a kind.
        cards = new Card[] {new Card("4c"), new Card("4h"), new Card("6d"),
                new Card("7h"), new Card("Jc")};
        hand = new Hand(cards);
        evaluator = new HandEvaluator(hand);
        assertEquals(false, evaluator.isThreeOfAKind());
    }

    @Test
    void testIsStraight() {
        //Test for straight.
        cards = new Card[] {new Card("4c"), new Card("5d"), new Card("6h"),
                new Card("7c"), new Card("8d")};
        hand = new Hand(cards);
        evaluator = new HandEvaluator(hand);
        assertEquals(true, evaluator.isStraight());

        //Test for straight when hand does not contain a straight.
        cards = new Card[] {new Card("4c"), new Card("4h"), new Card("6d"),
                new Card("7h"), new Card("Jc")};
        hand = new Hand(cards);
        evaluator = new HandEvaluator(hand);
        assertEquals(false, evaluator.isStraight());
    }

    @Test
    void testIsFlush() {
        //Test for flush.
        cards = new Card[] {new Card("4c"), new Card("5c"), new Card("6c"),
                new Card("7c"), new Card("8c")};
        hand = new Hand(cards);
        evaluator = new HandEvaluator(hand);
        assertEquals(true, evaluator.isFlush());

        //Test for flush when hand does not contain a flush.
        cards = new Card[] {new Card("4c"), new Card("4h"), new Card("6d"),
                new Card("7h"), new Card("Jc")};
        hand = new Hand(cards);
        evaluator = new HandEvaluator(hand);
        assertEquals(false, evaluator.isFlush());
    }

    @Test
    void testIsFullHouse() {
        //Test for full house.
        cards = new Card[] {new Card("4c"), new Card("4d"), new Card("4h"),
                new Card("5c"), new Card("5d")};
        hand = new Hand(cards);
        evaluator = new HandEvaluator(hand);
        assertEquals(true, evaluator.isFullHouse());;

        //Test for full house when hand does not contain a full house.
        cards = new Card[] {new Card("4c"), new Card("4h"), new Card("6d"),
                new Card("7h"), new Card("Jc")};
        hand = new Hand(cards);
        evaluator = new HandEvaluator(hand);
        assertEquals(false, evaluator.isFullHouse());
    }

    @Test
    void testIsFourOfAKind() {
        //Test for four of a kind.
        cards = new Card[] {new Card("4c"), new Card("4d"), new Card("4h"),
                new Card("4s"), new Card("6d")};
        hand = new Hand(cards);
        evaluator = new HandEvaluator(hand);
        assertEquals(true, evaluator.isFourOfAKind());;

        //Test for four of a kind when hand does not contain four of a kind.
        cards = new Card[] {new Card("4c"), new Card("4h"), new Card("6d"),
                new Card("7h"), new Card("Jc")};
        hand = new Hand(cards);
        evaluator = new HandEvaluator(hand);
        assertEquals(false, evaluator.isFourOfAKind());
    }

    @Test
    @DisplayName("Valid: Straight flush (no wheeling ace")
    void testIsStraightFlush() {
        //Test for straight flush.
        cards = new Card[] {new Card("4c"), new Card("5c"), new Card("6c"),
                new Card("7c"), new Card("8c")};
        hand = new Hand(cards);
        evaluator = new HandEvaluator(hand);
        assertEquals("Straight flush", evaluator.isStraightFlush());
    }

    @Test
    @DisplayName("Invalid: Straight flush")
    void testInvalidStraightFlush() {
        cards = new Card[] {new Card("4c"), new Card("4h"), new Card("6d"),
                new Card("7h"), new Card("Jc")};
        hand = new Hand(cards);
        evaluator = new HandEvaluator(hand);
        assertEquals("Not a Straight Flush", evaluator.isStraightFlush());
    }

    @Test
    @DisplayName("Valid: Royal straight flush")
    void testIsRoyalFlush() {
        cards = new Card[] {
                new Card(12, 0),
                new Card(11, 0),
                new Card(10, 0),
                new Card(9, 0),
                new Card(8, 0),
                new Card(6, 2),
                new Card(3, 1)
        };

        Hand hand = new Hand(cards);
        evaluator = new HandEvaluator(hand);

        assertEquals("Royal straight flush", evaluator.isStraightFlush());
    }

    @Test
    @DisplayName("Invalid: Royal straight flush")
    void testIsNotRoyalFlush() {
        cards = new Card[] {
                new Card(12, 0),
                new Card(11, 0),
                new Card(10, 0),
                new Card(4, 0),
                new Card(8, 0),
                new Card(6, 2),
                new Card(3, 1)
        };

        Hand hand = new Hand(cards);
        evaluator = new HandEvaluator(hand);

        assertEquals("Not a Straight Flush", evaluator.isStraightFlush());
    }
}
