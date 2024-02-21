package org.pokergame;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.pokergame.actions.*;
import org.pokergame.bots.BasicBot;
import org.pokergame.util.PokerUtils;

import java.lang.IllegalArgumentException;
import java.math.BigDecimal;
import java.util.Set;

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

    @Test
    @DisplayName("CalculateBetAmount: zero aggression fixed limit")
    public void calculateBetAmountZeroAggression() {
        BasicBot bot = new BasicBot(50, 0);
        bot.setTableType(TableType.FIXED_LIMIT);
        BigDecimal bet = bot.calculateBetAmount(new BigDecimal(10));
        assertEquals(new BigDecimal(10), bet);
    }

    @Test
    @DisplayName("CalculateBetAmount: max aggression fixed limit")
    public void calculateBetAmountMaxAggression() {
        BasicBot bot = new BasicBot(50, 100);
        bot.setTableType(TableType.FIXED_LIMIT);
        BigDecimal bet = bot.calculateBetAmount(new BigDecimal(10));
        assertEquals(new BigDecimal(10), bet);
    }

    @Test
    @DisplayName("CalculateBetAmount: zero aggression no limit")
    public void calculateBetAmountZeroAggressionNoLimit() {
        BasicBot bot = new BasicBot(50, 0);
        bot.setTableType(TableType.NO_LIMIT);
        BigDecimal bet = bot.calculateBetAmount(new BigDecimal(10));
        assertEquals(new BigDecimal(10), bet);
    }

    @Test
    @DisplayName("CalculateBetAmount: max aggression fixed limit")
    public void calculateBetAmountMaxAggressionNoLimit() {
        BasicBot bot = new BasicBot(50, 100);
        bot.setTableType(TableType.NO_LIMIT);
        BigDecimal bet = bot.calculateBetAmount(new BigDecimal(10));
        assertEquals(new BigDecimal(320), bet);
    }

    @Test
    @DisplayName("ChenScore: Non-play - lower bound")
    public void chenActionNonPlayFalse() {
        Card[] cards = new Card[] {
                new Card(12, 0), // ace of diamond
                new Card(12, 1)  // ace of clubs
        };

        assert(PokerUtils.getChenScore(cards) == 20);

        BasicBot bot = new BasicBot(95, 0); // 19
        boolean play = bot.isChenActionNonPlay(cards);

        assertFalse(play);
    }

    @Test
    @DisplayName("ChenScore: Non-play - upper bound")
    public void chenActionNonPlayTrue() {
        Card[] cards = new Card[] {
                new Card(12, 0), // ace of diamond
                new Card(11, 1)  // king of clubs
        };

        assert(PokerUtils.getChenScore(cards) == 10);

        BasicBot bot = new BasicBot(55, 0); // 11
        boolean play = bot.isChenActionNonPlay(cards);

        assertTrue(play);
    }

    @Test
    @DisplayName("ChenScore: Play (Bet)")
    public void chenActionPlayBet() {
        Set<PlayerAction> allowedActions = Set.of(
                PlayerAction.BET,
                PlayerAction.RAISE,
                PlayerAction.CALL,
                PlayerAction.CHECK
        );

        Card[] cards = new Card[] { // Chen: 20
                new Card(12, 0), // ace of diamond
                new Card(12, 1)  // ace of clubs
        };

        BasicBot bot = new BasicBot(100, 50);
        PlayerAction action = bot.getChenActionPlay(allowedActions, cards, new BigDecimal(100));

        assertInstanceOf(BetAction.class, action);
    }

    @Test
    @DisplayName("ChenScore: Play (Raise)")
    public void chenActionPlayRaise() {
        Set<PlayerAction> allowedActions = Set.of(
                // PlayerAction.BET,
                PlayerAction.RAISE,
                PlayerAction.CALL,
                PlayerAction.CHECK
        );

        Card[] cards = new Card[] { // Chen: 20
                new Card(12, 0), // ace of diamond
                new Card(12, 1)  // ace of clubs
        };

        BasicBot bot = new BasicBot(100, 50);
        PlayerAction action = bot.getChenActionPlay(allowedActions, cards, new BigDecimal(100));

        assertInstanceOf(RaiseAction.class, action);
    }

    @Test
    @DisplayName("ChenScore: Play (Call)")
    public void chenActionPlayCall() {
        Set<PlayerAction> allowedActions = Set.of(
                // PlayerAction.BET,
                // PlayerAction.RAISE,
                PlayerAction.CALL,
                PlayerAction.CHECK
        );

        Card[] cards = new Card[] { // Chen: 20
                new Card(12, 0), // ace of diamond
                new Card(12, 1)  // ace of clubs
        };

        BasicBot bot = new BasicBot(100, 50);
        PlayerAction action = bot.getChenActionPlay(allowedActions, cards, new BigDecimal(100));

        assertInstanceOf(CallAction.class, action);
    }

    @Test
    @DisplayName("ChenScore: Play (Check)")
    public void chenActionPlayCheck() {
        Set<PlayerAction> allowedActions = Set.of(
                // PlayerAction.BET,
                // PlayerAction.RAISE,
                // PlayerAction.CALL,
                PlayerAction.CHECK
        );

        Card[] cards = new Card[] { // Chen: 20
                new Card(12, 0), // ace of diamond
                new Card(12, 1)  // ace of clubs
        };

        BasicBot bot = new BasicBot(100, 50);
        PlayerAction action = bot.getChenActionPlay(allowedActions, cards, new BigDecimal(100));

        assertInstanceOf(CheckAction.class, action);
    }

    @Test
    @DisplayName("ChenScore: Non-Play Call (Tightness)")
    public void chenActionNonPlayCall() {
        Set<PlayerAction> allowedActions = Set.of(
                PlayerAction.BET,
                PlayerAction.RAISE,
                PlayerAction.CALL
                // PlayerAction.CHECK
        );

        Card[] cards = new Card[] { // Chen: 20
                new Card(5, 0), // ace of diamond
                new Card(5, 1)  // ace of clubs
        };

        BasicBot bot = new BasicBot(50, 50);
        PlayerAction action = bot.getChenActionPlay(allowedActions, cards, new BigDecimal(100));

        assertInstanceOf(CallAction.class, action);
    }

    @Test
    @DisplayName("ChenScore: Non-Play Check (Tightness)")
    public void chenActionNonPlayCheck() {
        Set<PlayerAction> allowedActions = Set.of(
                // PlayerAction.BET,
                // PlayerAction.RAISE,
                // PlayerAction.CALL,
                PlayerAction.CHECK
        );

        Card[] cards = new Card[] { // Chen: 20
                new Card(5, 0), // ace of diamond
                new Card(5, 1)  // ace of clubs
        };

        BasicBot bot = new BasicBot(50, 50);
        PlayerAction action = bot.getChenActionPlay(allowedActions, cards, new BigDecimal(100));

        assertInstanceOf(CheckAction.class, action);
    }



}
