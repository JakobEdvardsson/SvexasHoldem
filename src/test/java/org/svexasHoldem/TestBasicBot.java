package org.svexasHoldem;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.svexasHoldem.actions.*;
import org.svexasHoldem.bots.BasicBot;

import java.lang.IllegalArgumentException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;
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
        bot.joinedTable(TableType.FIXED_LIMIT, new BigDecimal(10), null);

        BigDecimal bet;

        try {
            Method calculateBetAmount = BasicBot.class.getDeclaredMethod("calculateBetAmount", BigDecimal.class);
            calculateBetAmount.setAccessible(true);
            bet = (BigDecimal) calculateBetAmount.invoke(bot, new BigDecimal(10));

        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }

        assertEquals(new BigDecimal(10), bet);
    }

    @Test
    @DisplayName("CalculateBetAmount: max aggression fixed limit")
    public void calculateBetAmountMaxAggression() {
        BasicBot bot = new BasicBot(50, 100);
        bot.joinedTable(TableType.FIXED_LIMIT, new BigDecimal(10), null);

        BigDecimal bet;

        try {
            Method calculateBetAmount = BasicBot.class.getDeclaredMethod("calculateBetAmount", BigDecimal.class);
            calculateBetAmount.setAccessible(true);
            bet = (BigDecimal) calculateBetAmount.invoke(bot, new BigDecimal(10));

        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }

        assertEquals(new BigDecimal(10), bet);
    }

    @Test
    @DisplayName("CalculateBetAmount: zero aggression no limit")
    public void calculateBetAmountZeroAggressionNoLimit() {
        BasicBot bot = new BasicBot(50, 0);
        bot.joinedTable(TableType.NO_LIMIT, new BigDecimal(10), null);

        BigDecimal bet;

        try {
            Method calculateBetAmount = BasicBot.class.getDeclaredMethod("calculateBetAmount", BigDecimal.class);
            calculateBetAmount.setAccessible(true);
            bet = (BigDecimal) calculateBetAmount.invoke(bot, new BigDecimal(10));

        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }

        assertEquals(new BigDecimal(10), bet);
    }

    @Test
    @DisplayName("CalculateBetAmount: max aggression fixed limit")
    public void calculateBetAmountMaxAggressionNoLimit() {
        BasicBot bot = new BasicBot(50, 100);
        bot.joinedTable(TableType.NO_LIMIT, new BigDecimal(10), null);
        BigDecimal bet;

        try {
            Method calculateBetAmount = BasicBot.class.getDeclaredMethod("calculateBetAmount", BigDecimal.class);
            calculateBetAmount.setAccessible(true);
            bet = (BigDecimal) calculateBetAmount.invoke(bot, new BigDecimal(10));

        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }

        assertEquals(new BigDecimal(320), bet);
    }

    @Test
    @DisplayName("ChenScore: Non-play - lower bound")
    public void chenActionNonPlayFalse() {
        List<Card> cards = List.of(
                new Card(12, 0), // ace of diamond
                new Card(12, 1)  // ace of clubs
        );

        BasicBot bot = new BasicBot(95, 0); // 19
        Player testPlayer = new Player("test", new BigDecimal(1000), null);
        testPlayer.setCards(cards);
        bot.playerUpdated(testPlayer);

        boolean play;

        try {
            Method isChenActionNonPlay = BasicBot.class.getDeclaredMethod("isChenActionNonPlay");
            isChenActionNonPlay.setAccessible(true);
            play = (boolean) isChenActionNonPlay.invoke(bot);

        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }

        assertFalse(play);
    }

    @Test
    @DisplayName("ChenScore: Non-play - upper bound")
    public void chenActionNonPlayTrue() {

        List<Card> cards = List.of(
                new Card(12, 0), // ace of diamond
                new Card(11, 1)  // king of clubs
        );

        BasicBot bot = new BasicBot(55, 0); // 11
        Player testPlayer = new Player("test", new BigDecimal(1000), null);
        testPlayer.setCards(cards);
        bot.playerUpdated(testPlayer);

        boolean play;

        try {
            Method isChenActionNonPlay = BasicBot.class.getDeclaredMethod("isChenActionNonPlay");
            isChenActionNonPlay.setAccessible(true);
            play = (boolean) isChenActionNonPlay.invoke(bot);

        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }

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

        List<Card> cards = List.of(
                new Card(12, 0), // ace of diamond
                new Card(12, 1)  // ace of clubs
        );

        BasicBot bot = new BasicBot(100, 50);
        Player testPlayer = new Player("test", new BigDecimal(1000), null);
        testPlayer.setCards(cards);
        bot.playerUpdated(testPlayer);

        PlayerAction action;

        try {
            Method getChenActionPlay = BasicBot.class.getDeclaredMethod("getChenActionPlay", Set.class, BigDecimal.class);
            getChenActionPlay.setAccessible(true);
            action = (PlayerAction) getChenActionPlay.invoke(bot, allowedActions, new BigDecimal(100));

        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }

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


        List<Card> cards = List.of(
                new Card(12, 0), // ace of diamond
                new Card(12, 1)  // ace of clubs
        );

        BasicBot bot = new BasicBot(100, 50);
        Player testPlayer = new Player("test", new BigDecimal(1000), null);
        testPlayer.setCards(cards);
        bot.playerUpdated(testPlayer);

        PlayerAction action;

        try {
            Method getChenActionPlay = BasicBot.class.getDeclaredMethod("getChenActionPlay", Set.class, BigDecimal.class);
            getChenActionPlay.setAccessible(true);
            action = (PlayerAction) getChenActionPlay.invoke(bot, allowedActions, new BigDecimal(100));

        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }

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


        List<Card> cards = List.of(
                new Card(12, 0), // ace of diamond
                new Card(12, 1)  // ace of clubs
        );

        BasicBot bot = new BasicBot(100, 50);
        Player testPlayer = new Player("test", new BigDecimal(1000), null);
        testPlayer.setCards(cards);
        bot.playerUpdated(testPlayer);

        PlayerAction action;

        try {
            Method getChenActionPlay = BasicBot.class.getDeclaredMethod("getChenActionPlay", Set.class, BigDecimal.class);
            getChenActionPlay.setAccessible(true);
            action = (PlayerAction) getChenActionPlay.invoke(bot, allowedActions, new BigDecimal(100));

        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }

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

        List<Card> cards = List.of(
                new Card(12, 0), // ace of diamond
                new Card(12, 1)  // ace of clubs
        );

        BasicBot bot = new BasicBot(100, 50);
        Player testPlayer = new Player("test", new BigDecimal(1000), null);
        testPlayer.setCards(cards);
        bot.playerUpdated(testPlayer);

        PlayerAction action;

        try {
            Method getChenActionPlay = BasicBot.class.getDeclaredMethod("getChenActionPlay", Set.class, BigDecimal.class);
            getChenActionPlay.setAccessible(true);
            action = (PlayerAction) getChenActionPlay.invoke(bot, allowedActions, new BigDecimal(100));

        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }

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

        List<Card> cards = List.of(
              new Card(5, 0), // ace of diamond
              new Card(5, 1)  // ace of clubs
        );

        BasicBot bot = new BasicBot(50, 50);
        Player testPlayer = new Player("test", new BigDecimal(1000), null);
        testPlayer.setCards(cards);
        bot.playerUpdated(testPlayer);

        PlayerAction action;

        try {
            Method getChenActionPlay = BasicBot.class.getDeclaredMethod("getChenActionPlay", Set.class, BigDecimal.class);
            getChenActionPlay.setAccessible(true);
            action = (PlayerAction) getChenActionPlay.invoke(bot, allowedActions, new BigDecimal(100));

        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
        assertInstanceOf(CallAction.class, action);
    }

    @Test
    @DisplayName("ChenScore: Non-Play Check (Tightness)")
    public void chenActionNonPlayCheck() {
        BasicBot bot = new BasicBot(50, 50);

        Set<PlayerAction> allowedActions = Set.of(
                // PlayerAction.BET,
                // PlayerAction.RAISE,
                // PlayerAction.CALL,
                PlayerAction.CHECK
        );

        List<Card> cards = List.of(
                new Card(5, 0), // ace of diamond
                new Card(5, 1)  // ace of clubs
        );

        Player testPlayer = new Player("test", new BigDecimal(1000), null);
        testPlayer.setCards(cards);
        bot.playerUpdated(testPlayer);

        PlayerAction action;

        try {
            Method getChenActionPlay = BasicBot.class.getDeclaredMethod("getChenActionPlay", Set.class, BigDecimal.class);
            getChenActionPlay.setAccessible(true);
            action = (PlayerAction) getChenActionPlay.invoke(bot, allowedActions, new BigDecimal(100));

        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }

        assertInstanceOf(CheckAction.class, action);
    }

    @Test
    @DisplayName("Act: allowedActions[1] (valid)")
    public void actOneActionValid() {
        BasicBot bot = new BasicBot(50, 50);

        BigDecimal minBet = new BigDecimal(10);
        BigDecimal currentBet = new BigDecimal(10);

        Set<PlayerAction> allowedActions = Set.of(
                PlayerAction.CHECK
        );

        PlayerAction action = bot.act(minBet, currentBet, allowedActions);
        assertInstanceOf(CheckAction.class, action);
    }

    @Test
    @DisplayName("Act: allowedActions[1] (invalid)")
    public void actOneActionInvalid() {
        BasicBot bot = new BasicBot(50, 50);

        BigDecimal minBet = new BigDecimal(10);
        BigDecimal currentBet = new BigDecimal(10);

        Set<PlayerAction> allowedActions = Set.of(
                PlayerAction.CALL
        );

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            bot.act(minBet, currentBet, allowedActions);
        });

        String message = exception.getMessage();
        String expected = "Check not available, broken state";

        assertEquals(expected, message);
    }

    @Test
    @DisplayName("Act: Chen Action, Non-play (Check)")
    public void actChenActionNonPlayCheck() {
        BasicBot bot = new BasicBot(50, 50);

        BigDecimal minBet = new BigDecimal(10);
        BigDecimal currentBet = new BigDecimal(10);

        Set<PlayerAction> allowedActions = Set.of(
                PlayerAction.CHECK,
                PlayerAction.FOLD
        );

        List<Card> cards = List.of(
                new Card(3, 0),
                new Card(5, 1)
        );

        Player testPlayer = new Player("test", new BigDecimal(1000), null);
        testPlayer.setCards(cards);
        bot.playerUpdated(testPlayer);

        PlayerAction action = bot.act(minBet, currentBet, allowedActions);
        assertInstanceOf(CheckAction.class, action);
    }

    @Test
    @DisplayName("Act: Chen Action, Non-play (Fold)")
    public void actChenActionNonPlayFold() {
        BasicBot bot = new BasicBot(50, 50);

        BigDecimal minBet = new BigDecimal(10);
        BigDecimal currentBet = new BigDecimal(10);

        Set<PlayerAction> allowedActions = Set.of(
                PlayerAction.RAISE, // Need to have at least 2 actions if one is not check.
                // PlayerAction.CHECK,
                PlayerAction.FOLD
        );

        List<Card> cards = List.of(
                new Card(3, 0),
                new Card(5, 1)
        );

        Player testPlayer = new Player("test", new BigDecimal(1000), null);
        testPlayer.setCards(cards);
        bot.playerUpdated(testPlayer);

        PlayerAction action = bot.act(minBet, currentBet, allowedActions);
        assertInstanceOf(FoldAction.class, action);
    }

    @Test
    @DisplayName("Act: Chen Action Play")
    public void actChenActionPlay() {
        BasicBot bot = new BasicBot(50, 50);

        BigDecimal minBet = new BigDecimal(10);
        BigDecimal currentBet = new BigDecimal(10);

        Set<PlayerAction> allowedActions = Set.of(
                PlayerAction.RAISE, // Need to have at least 2 actions if one is not check.
                // PlayerAction.CHECK,
                PlayerAction.FOLD
        );

        List<Card> cards = List.of(
                new Card(12, 0),
                new Card(12, 1)
        );

        Player testPlayer = new Player("test", new BigDecimal(1000), null);
        testPlayer.setCards(cards);
        bot.playerUpdated(testPlayer);

        PlayerAction action = bot.act(minBet, currentBet, allowedActions);
        assertInstanceOf(RaiseAction.class, action);
    }
}