// This file is part of the 'texasholdem' project, an open source
// Texas Hold'em poker application written in Java.
//
// Copyright 2009 Oscar Stigter
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.svexasHoldem.util.oldTests;

import org.svexasHoldem.Card;
import org.svexasHoldem.util.PokerUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Test suite for the <code>PokerUtils</code> class.
 * 
 * @author Oscar Stigter
 */
public class PokerUtilsTest {
    
    /**
     * Tests the implementation of the Chen formula calculator.
     */
    @Test
    public void chenFormula() {
        Card card1;
        Card card2;
        
        card1 = new Card(Card.ACE, Card.SPADES);
        card2 = new Card(Card.ACE, Card.HEARTS);
        assertEquals(20.0, PokerUtils.getChenScore(new Card[] {card1, card2}));

        card1 = new Card(Card.ACE, Card.SPADES);
        card2 = new Card(Card.KING, Card.SPADES);
        assertEquals(12.0, PokerUtils.getChenScore(new Card[] {card1, card2}));

        card1 = new Card(Card.KING, Card.SPADES);
        card2 = new Card(Card.KING, Card.HEARTS);
        assertEquals(16.0, PokerUtils.getChenScore(new Card[] {card1, card2}));

        card1 = new Card(Card.TEN, Card.CLUBS);
        card2 = new Card(Card.TEN, Card.DIAMONDS);
        assertEquals(10.0, PokerUtils.getChenScore(new Card[] {card1, card2}));

        card1 = new Card(Card.FIVE, Card.CLUBS);
        card2 = new Card(Card.SEVEN, Card.CLUBS);
        assertEquals(6.0, PokerUtils.getChenScore(new Card[] {card1, card2}));

        card1 = new Card(Card.DEUCE, Card.CLUBS);
        card2 = new Card(Card.SEVEN, Card.DIAMONDS);
        assertEquals(0.0, PokerUtils.getChenScore(new Card[] {card1, card2}));
    }
    
}
