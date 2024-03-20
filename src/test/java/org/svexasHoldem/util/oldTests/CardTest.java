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
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suite for the Card class.
 * 
 * @author Oscar Stigter
 */
public class CardTest {
    
    /**
     * Tests the basics (good-weather).
     */
    @Test
    public void basics() {
        Card card = new Card(Card.TEN, Card.HEARTS);
        assertNotNull(card);
        assertEquals(Card.TEN, card.getRank());
        assertEquals(Card.HEARTS, card.getSuit());
        assertEquals("Th", card.toString());
        card = new Card("   As "); // Automatic trimming.
        assertNotNull(card);
        assertEquals(Card.ACE, card.getRank());
        assertEquals(Card.SPADES, card.getSuit());
        assertEquals("As", card.toString());
    }
    
    /**
     * Tests the constructors (bad-weather).
     */
    @Test
    public void testConstructors() {
        // Numeric rank too low.
        try {
             new Card(-1, 0);
            fail("No exception thrown");
        } catch (IllegalArgumentException e) {
            // OK.
        }

        // Numeric rank too high.
        try {
             new Card(Card.NO_OF_RANKS, 0);
            fail("No exception thrown");
        } catch (IllegalArgumentException e) {
            // OK.
        }

        // Numeric suit too low.
        try {
             new Card(0, -1);
            fail("No exception thrown");
        } catch (IllegalArgumentException e) {
            // OK.
        }
        
        // Numeric suit too high.
        try {
             new Card(0, Card.NO_OF_SUITS);
            fail("No exception thrown");
        } catch (IllegalArgumentException e) {
            // OK.
        }
        
        // Null string.
        try {
             new Card(null);
            fail("No exception thrown");
        } catch (IllegalArgumentException e) {
            // OK.
        }
        
        // Empty string.
        try {
             new Card("");
            fail("No exception thrown");
        } catch (IllegalArgumentException e) {
            // OK.
        }
        
        // String too short.
        try {
             new Card("A");
            fail("No exception thrown");
        } catch (IllegalArgumentException e) {
            // OK.
        }
        
        // String too long.
        try {
             new Card("Ahx");
            fail("No exception thrown");
        } catch (IllegalArgumentException e) {
            // OK.
        }
        
        // Unknown rank character.
        try {
             new Card("xh");
            fail("No exception thrown");
        } catch (IllegalArgumentException e) {
            // OK.
        }
        
        // Unknown rank character.
        try {
             new Card("xh");
            fail("No exception thrown");
        } catch (IllegalArgumentException e) {
            // OK.
        }
        
        // Unknown suit character.
        try {
           new Card("Ax");
            fail("No exception thrown");
        } catch (IllegalArgumentException e) {
            // OK.
        }
    }
    
    /**
     * Tests the card ordering.
     */
    @Test
    public void sortOrder() {
        // Diamond is lower, Clubs is higher.
        Card _2d = new Card("2d");
        Card _3d = new Card("3d");
        Card _2c = new Card("2c");
        Card _3c = new Card("3c");
        assertEquals(_2d, _2d);
        assertFalse(_2d.equals(_3d));
        assertFalse(_2d.equals(_2c));
        assertEquals(0, _2d.hashCode());
        assertEquals(1, _2c.hashCode());
        assertEquals(4, _3d.hashCode());
        assertEquals(5, _3c.hashCode());
        assertTrue(_2d.compareTo(_2d) == 0);
        assertTrue(_2d.compareTo(_3d) < 0);
        assertTrue(_3d.compareTo(_2d) > 0);
        assertTrue(_2d.compareTo(_2c) < 0);
        assertTrue(_2c.compareTo(_2d) > 0);
    }

}
