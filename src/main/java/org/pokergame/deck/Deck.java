package org.pokergame.deck;

import javafx.scene.image.Image;
import org.pokergame.utils.PathUtil;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * A deck of cards
 *
 * @author Vedrana Zeba
 */
public class Deck {
  private ArrayList<Card> deck = new ArrayList<Card>();

  /** Creates a deck of cards */
  public Deck() {
    createDeck();
  }

  /** Shuffles the deck */
  public void shuffle() {
    Collections.shuffle(deck);
  }

  /**
   * Returns the reference of the card that's being removed
   *
   * @return the reference of the card that's being removed
   */
  public Card getCard() {
    return deck.remove(0);
  }

  /**
   * Returns the current size of the deck
   *
   * @return the current size of the deck
   */
  public int getNumberOfCardsInDeck() {
    return deck.size();
  }

  /** Creates a deck of cards */
  public void createDeck() {
    deck = new ArrayList<Card>();

    for (Suit suit : Suit.values()) {
      for (CardValue card : CardValue.values()) {
        ImageIcon cardImageView =
            new ImageIcon(
                new PathUtil().getImage(card.getCardValue() + "" + suit.getSuitLetter() + ".png"));
        deck.add(new Card(suit, card, cardImageView));
      }
    }
  }
}
