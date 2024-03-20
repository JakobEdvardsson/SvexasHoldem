package org.svexasHoldem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    private Deck deck;

    @BeforeEach
    void setUp() {
        deck = new Deck();
    }

    @Test
    void shuffle() {
        System.out.println("-------------------");

        Deck newDeck = new Deck();
        boolean foundMismatchCard = false;

        for(int i = 0; i < deck.getCards().length; i++) {
            //Checks if two new decks are identical
            if(!(deck.getCards()[i].equals(newDeck.getCards()[i]))) {
                foundMismatchCard = true;
            }
        }
        assertFalse(foundMismatchCard);

        deck.shuffle();

        for(int i = 0; i < deck.getCards().length; i++) {
            //Checks if shuffled deck is equal to a non-shuffled one
            if(!(deck.getCards()[i].equals(newDeck.getCards()[i]))) {
                foundMismatchCard = true;
            }
        }
        assertTrue(foundMismatchCard);
    }

    @Test
    void reset() {
        Card card = deck.deal();
        deck.reset();
        assertEquals(card, deck.deal());
    }

    @Test
    void deal() {
        for(int i = 0; i < deck.getCards().length - 1; i++) {
            deck.deal();
        }

        boolean gotException = false;

        try {
            deck.deal();
        } catch (IllegalStateException exception) {
            gotException = true;
        }

        assertTrue(gotException);
    }

    @Test
    void Deal1() {
        boolean gotException = false;

        try {
            deck.deal(0);
        } catch (IllegalArgumentException exception) {
            gotException = true;
        }

        assertTrue(gotException);

        gotException = false;

        try {
            deck.deal(64);
        } catch (IllegalStateException exception) {
            gotException = true;
        }

        assertTrue(gotException);

        boolean foundMismatchedCard = false;

        List<Card> cards = deck.deal(5);

        for(int i = 0; i < cards.size(); i++){
            if(!(cards.get(i).equals(deck.getCards()[i]))){
                foundMismatchedCard = true;
            }
        }

        assertFalse(foundMismatchedCard);
    }

    @Test
    void Deal2() {
        boolean noCardLeft = false;

        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 13; j++){

                try{
                    deck.deal(j, i);
                } catch (IllegalStateException exception) {
                    noCardLeft = true;
                }
            }
        }

        assertTrue(noCardLeft);
    }

    @Test
    void testToString() {
        deck.shuffle();
        boolean cardNotContained = false;

        for(Card card : deck.getCards()) {
            if(!(deck.toString().contains(card.toString()))){
                cardNotContained = true;
            }
        }
        assertFalse(cardNotContained);
    }
}