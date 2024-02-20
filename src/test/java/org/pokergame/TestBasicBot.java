package org.pokergame;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.pokergame.bots.BasicBot;

import java.lang.IllegalArgumentException;

import static org.junit.jupiter.api.Assertions.*;

public class TestBasicBot {


    @Test
    @DisplayName("Tightness: lower-invalid")
    public void tightnessLowerInvalid() {

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
                    new BasicBot(-1, 50);
        });

        String expectedMessage = "Invalid tightness setting";
        String output = exception.getMessage();

        assertEquals(output, expectedMessage);

    }

    @Test
    @DisplayName("Tightness: lower-valid")
    public void tightnessLowerValid() {
        BasicBot bot = new BasicBot(0, 50);
        int tightness = bot.getTightness();

        assert(tightness == 0);
    }

    @Test
    @DisplayName("Tightness: upper-valid")
    public void tightnessUpperValid() {
        BasicBot bot = new BasicBot(100, 50);
        int tightness = bot.getTightness();

        assert(tightness == 100);
    }

    @Test
    @DisplayName("Tightness: upper-invalid")
    public void tightnessUpperInvalid() {

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new BasicBot(101, 50);
        });

        String expectedMessage = "Invalid tightness setting";
        String output = exception.getMessage();

        assertEquals(output, expectedMessage);
    }

    @Test
    @DisplayName("Aggression: lower-invalid")
    public void aggressionLowerInvalid() {

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new BasicBot(50, -1);
        });

        String expectedMessage = "Invalid aggression setting";
        String output = exception.getMessage();

        assertEquals(output, expectedMessage);

    }

    @Test
    @DisplayName("Aggression: lower-valid")
    public void aggressionLowerValid() {
        BasicBot bot = new BasicBot(50, 0);
        int aggression = bot.getAggression();

        assert(aggression == 0);
    }

    @Test
    @DisplayName("Aggression: upper-valid")
    public void aggressionUpperValid() {
        BasicBot bot = new BasicBot(50, 100);
        int aggression = bot.getAggression();

        assert(aggression == 100);
    }

    @Test
    @DisplayName("Aggression: upper-invalid")
    public void aggressionUpperInvalid() {

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new BasicBot(50, 101);
        });

        String expectedMessage = "Invalid aggression setting";
        String output = exception.getMessage();

        assertEquals(output, expectedMessage);
    }



}
