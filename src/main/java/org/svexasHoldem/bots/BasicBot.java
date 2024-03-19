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

package org.svexasHoldem.bots;

import org.svexasHoldem.Card;
import org.svexasHoldem.Player;
import org.svexasHoldem.TableType;
import org.svexasHoldem.actions.BetAction;
import org.svexasHoldem.actions.PlayerAction;
import org.svexasHoldem.actions.RaiseAction;
import org.svexasHoldem.util.PokerUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * Basic Texas Hold'em poker bot. <br />
 * <br />
 * 
 * The current implementation acts purely on the bot's hole cards, based on the
 * Chen formula, combined with a configurable level of tightness (when to play
 * or fold a hand ) and aggression (how much to bet or raise in case of good
 * cards or when bluffing). <br />
 * <br />
 * 
 * TODO:
 * <ul>
 * <li>Improve basic bot AI</li>
 * <li>bluffing</li>
 * <li>consider board cards</li>
 * <li>consider current bet</li>
 * <li>consider pot</li>
 * </ul>
 * 
 * @author Oscar Stigter
 */
public class BasicBot extends Bot {
    
    /** Tightness (0 = loose, 100 = tight). */
    private final int tightness;
    
    /** Betting aggression (0 = safe, 100 = aggressive). */
    private final int aggression;
    
    /** Table type. */
    private TableType tableType;
    
    /** The hole cards. */
    private Card[] cards;
    
    /**
     * Constructor.
     * 
     * @param tightness
     *            The bot's tightness (0 = loose, 100 = tight).
     * @param aggression
     *            The bot's aggressiveness in betting (0 = careful, 100 =
     *            aggressive).
     */
    public BasicBot(int tightness, int aggression) {
        if (tightness < 0 || tightness > 100) {
            throw new IllegalArgumentException("Invalid tightness setting");
        }
        if (aggression < 0 || aggression > 100) {
            throw new IllegalArgumentException("Invalid aggression setting");
        }
        this.tightness = tightness;
        this.aggression = aggression;
    }

    /** {@inheritDoc} */
    @Override
    public void joinedTable(TableType type, BigDecimal bigBlind, List<Player> players) {
        this.tableType = type;
    }

    /** {@inheritDoc} */
    @Override
    public void messageReceived(String message) {
        // Not implemented.
    }

    /** {@inheritDoc} */
    @Override
    public void handStarted(Player dealer) {
        cards = null;
    }

    /** {@inheritDoc} */
    @Override
    public void actorRotated(Player actor) {
        // Not implemented.
    }

    /** {@inheritDoc} */
    @Override
    public void boardUpdated(List<Card> cards, BigDecimal bet, BigDecimal pot) {
        // Not implemented.
    }

    /** {@inheritDoc} */
    @Override
    public void playerUpdated(Player player) {
        if (player.getCards().length == NO_OF_HOLE_CARDS) {
            this.cards = player.getCards();
        }
    }

    /** {@inheritDoc} */
    @Override
    public void playerActed(Player player) {
        // Not implemented.
    }

    /**
     * Uses the check score of the cards and the tightness value of the bot
     * to determine if the bot should play or not.
     * @return True if the bot should play, false otherwise.
     */
    private boolean isChenActionNonPlay() {
        double chenScore = PokerUtils.getChenScore(cards);
        double chenScoreToPlay = tightness * 0.2;

        // Check if chenScore is high enough to play.
        return chenScore < chenScoreToPlay;
    }

    /**
     * Calculates the bet amount for the bot.
     * @param minBet minimum bet amount for the table.
     * @return amount to bet.
     */
    private BigDecimal calculateBetAmount(BigDecimal minBet) {
        BigDecimal bet = minBet;

        if (tableType == TableType.NO_LIMIT) {
            int betLevel = aggression / 20;
            for (int i = 0; i < betLevel; i++) {
                bet = bet.add(bet);
            }
        }

        return bet;
    }

    /**
     * Depending on the chen formula, determine the action to take.
     * @param allowedActions The set of allowed actions available to the bot.
     * @param minBet The minimum bet amount for the table.
     * @return
     */
    private PlayerAction getChenActionPlay(Set<PlayerAction> allowedActions, BigDecimal minBet) {
        double chenScore = PokerUtils.getChenScore(cards);
        double chenScoreToPlay = tightness * 0.2;

        if ((chenScore - chenScoreToPlay) >= ((20.0 - chenScoreToPlay) / 2.0)) {
            BigDecimal amount = calculateBetAmount(minBet);
            if (allowedActions.contains(PlayerAction.BET)) return new BetAction(amount);
            if (allowedActions.contains(PlayerAction.RAISE)) return new RaiseAction(amount);
            if (allowedActions.contains(PlayerAction.CALL)) return PlayerAction.CALL;
            if (allowedActions.contains(PlayerAction.CHECK)) return PlayerAction.CHECK;
        }

        // tightness too low for hand, check / call
        if (allowedActions.contains(PlayerAction.CHECK)) return PlayerAction.CHECK;
        else return PlayerAction.CALL;
    }

    /** {@inheritDoc} */
    @Override
    public PlayerAction act(BigDecimal minBet, BigDecimal currentBet, Set<PlayerAction> allowedActions) {

        // If only check is available.
        if (allowedActions.size() == 1) {
            if (!allowedActions.contains(PlayerAction.CHECK)) {
                throw new IllegalArgumentException("Check not available, broken state");
            }

            return PlayerAction.CHECK;
        }

        // If chen formula is non-play, Check if available, otherwise Fold.
        if (isChenActionNonPlay()) {
            if (allowedActions.contains(PlayerAction.CHECK)) return PlayerAction.CHECK;
            else return PlayerAction.FOLD;
        }

        return getChenActionPlay(allowedActions, minBet);
    }

    public int getTightness() {
        return tightness;
    }

    public int getAggression() {
        return aggression;
    }
}
