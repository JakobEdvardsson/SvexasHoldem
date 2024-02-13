package org.pokergame;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {

    @Test
    void addPlayer() {
        //Player player = new Player("Player");

        String name = "Player";
        String expected = "Player";
        assertEquals(name, expected);
    }

    @Test
    void run() {
    }
}