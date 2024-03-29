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

package org.svexasHoldem;

import org.svexasHoldem.actions.BetAction;
import org.svexasHoldem.actions.PlayerAction;
import org.svexasHoldem.actions.RaiseAction;
import org.svexasHoldem.bots.BasicBot;
import org.svexasHoldem.server.Lobby;
import org.svexasHoldem.util.PokerUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * Limit Texas Hold'em poker table. <br />
 * <br />
 * <p>
 * This class forms the heart of the poker engine. It controls the game flow for a single poker table.
 *
 * @author Oscar Stigter
 */

public class Table extends Thread {


    /**
     * In fixed-limit games, the maximum number of raises per betting round.
     */
    private static final int MAX_RAISES = 3;

    /**
     * Whether players will always call the showdown, or fold when no chance.
     */
    private static final boolean ALWAYS_CALL_SHOWDOWN = false;

    /**
     * Table type (poker variant).
     */
    private final TableType tableType;

    /**
     * The size of the big blind.
     */
    private final BigDecimal bigBlind;

    /**
     * The players at the table.
     */
    private final List<Player> players;

    /**
     * The active players in the current hand.
     */
    private final List<Player> activePlayers;

    /**
     * The deck of cards.
     */
    private final Deck deck;

    /**
     * The community cards on the board.
     */
    private final List<Card> board;
    /**
     * All pots in the current hand (main pot and any side pots).
     */
    private final List<Pot> pots;
    /**
     * The current dealer position.
     */
    private int dealerPosition;
    /**
     * The current dealer.
     */
    private Player dealer;
    /**
     * The position of the acting player.
     */
    private int actorPosition;
    /**
     * The acting player.
     */
    private Player actor;
    /**
     * The minimum bet in the current hand.
     */
    private BigDecimal minBet;
    /**
     * The current bet in the current hand.
     */
    private BigDecimal bet;
    /**
     * The player who bet or raised last (aggressor).
     */
    private Player lastBettor;

    /**
     * Number of raises in the current betting round.
     */
    private int raises;

    private boolean gameRunning;

    private Lobby lobby;

    // private List<Player> showingPlayers;

    /**
     * Constructor.
     *
     * @param bigBlind The size of the big blind.
     */
    public Table(TableType type, BigDecimal bigBlind, Lobby lobby) {
        this.tableType = type;
        this.bigBlind = bigBlind;
        players = new ArrayList<>();
        activePlayers = new ArrayList<>();
        deck = new Deck();
        board = new ArrayList<>();
        pots = new ArrayList<>();
        this.lobby = lobby;
    }

    /**
     * Adds a player.
     *
     * @param player The player.
     */
    public void addPlayer(Player player) {
        players.add(player);
    }

    /**
     * Main game loop.
     */
    public void run() {

        gameRunning = true;

        for (Player player : players) {
            player.getClient().joinedTable(tableType, bigBlind, players);
        }
        dealerPosition = -1;
        actorPosition = -1;

        // Main game loop. The actual game is played here.
        gameLoop();

        // Game over.
        gameOver();
    }

    private void gameLoop() {
        while (gameRunning) {
            int noOfActivePlayers = 0;
            for (Player player : players) {
                if (player.getCash().compareTo(bigBlind) >= 0) {
                    noOfActivePlayers++;
                }
            }
            if (noOfActivePlayers > 1) {
                playHand();
            } else {
                break;
            }
        }
    }

    private void gameOver() {
        board.clear();
        pots.clear();
        bet = BigDecimal.ZERO;
        notifyBoardUpdated();
        for (Player player : players) {
            player.resetHand();
        }
        notifyPlayersUpdated(false);
        notifyMessage("Game over.");

        // If there's an associated lobby, notify that the game is over
        if (lobby != null) lobby.gameFinished();
    }

    public void exitGame() {
        gameRunning = false;
    }

    public boolean isRunning() {
        return gameRunning;
    }

    /**
     * Plays a single hand.
     */
    public void playHand() {
        resetHand();

        // Small blind.
        if (activePlayers.size() > 2) {
            rotateActor();
        }
        postSmallBlind();

        // Big blind.
        rotateActor();
        postBigBlind();

        // Pre-Flop.
        dealHoleCards();
        doBettingRound();

        // Flop.
        if (activePlayers.size() > 1 && gameRunning) {
            bet = BigDecimal.ZERO;
            dealCommunityCards("Flop", 3);
            doBettingRound();

            // Turn.
            if (activePlayers.size() > 1) {
                bet = BigDecimal.ZERO;
                dealCommunityCards("Turn", 1);
                minBet = bigBlind.add(bigBlind);
                doBettingRound();

                // River.
                if (activePlayers.size() > 1) {
                    bet = BigDecimal.ZERO;
                    dealCommunityCards("River", 1);
                    doBettingRound();

                    // Showdown.
                    if (activePlayers.size() > 1) {
                        bet = BigDecimal.ZERO;
                        doShowdown();
                    }
                }
            }
        }
    }

    /**
     * Resets the game for a new hand.
     */
    private void resetHand() {
        // Clear the board.
        board.clear();
        pots.clear();
        notifyBoardUpdated();

        // Determine the active players.
        activePlayers.clear();
        for (Player player : players) {
            player.resetHand();
            // Player must be able to afford at least the big blind.
            if (player.getCash().compareTo(bigBlind) >= 0) {
                activePlayers.add(player);
            }
        }

        // Rotate the dealer button.
        dealerPosition = (dealerPosition + 1) % activePlayers.size();
        dealer = activePlayers.get(dealerPosition);

        // Shuffle the deck.
        deck.shuffle();

        // Determine the first player to act.
        actorPosition = dealerPosition;
        actor = activePlayers.get(actorPosition);

        // Set the initial bet to the big blind.
        minBet = bigBlind;
        bet = minBet;

        // Notify all clients a new hand has started.
        for (Player player : players) {
            player.getClient().handStarted(dealer);
        }
        notifyPlayersUpdated(false);
        notifyMessage("New hand, %s is the dealer.", dealer);
    }

    /**
     * Rotates the position of the player in turn (the actor).
     */
    private void rotateActor() {
        actorPosition = (actorPosition + 1) % activePlayers.size();
        actor = activePlayers.get(actorPosition);
        for (Player player : players) {
            player.getClient().actorRotated(actor);
        }
    }

    /**
     * Posts the small blind.
     */
    private void postSmallBlind() {
        final BigDecimal smallBlind = bigBlind.divide(BigDecimal.valueOf(2)); //TODO
        actor.postSmallBlind(smallBlind);
        contributePot(smallBlind);
        notifyBoardUpdated();
        notifyPlayerActed();
    }

    /**
     * Posts the big blind.
     */
    private void postBigBlind() {
        actor.postBigBlind(bigBlind);
        contributePot(bigBlind);
        notifyBoardUpdated();
        notifyPlayerActed();
    }

    /**
     * Deals the Hole Cards.
     */
    private void dealHoleCards() {
        for (Player player : activePlayers) {
            player.setCards(deck.deal(2));
        }
        notifyPlayersUpdated(false);
        notifyMessage("%s deals the hole cards.", dealer);
    }

    /**
     * Deals a number of community cards.
     *
     * @param phaseName The name of the phase.
     * @param noOfCards The number of cards to deal.
     */
    private void dealCommunityCards(String phaseName, int noOfCards) {
        for (int i = 0; i < noOfCards; i++) {
            board.add(deck.deal());
        }
        notifyPlayersUpdated(false);
        notifyMessage("%s deals the %s.", dealer, phaseName);
    }

    /**
     * Replaces the player with a bot if the player leaves the round.
     *
     * @param actor The player to replace
     * @return The new client of the player
     */
    private Client replacePlayer(Player actor) {
        int[] stats = PokerUtils.getRandomBotStats();

        Client playerClient = actor.getClient();

        actor.setClient(new BasicBot(stats[0], stats[1]));
        actor.setName(String.format("%s (bot)", actor.getName()));

        return actor.getClient();
    }

    /**
     * Performs a betting round.
     */
    private void doBettingRound() {
        // Determine the number of active players.
        int playersToAct = activePlayers.size();
        // Determine the initial player and bet size.
        if (board.size() == 0) {
            // Pre-Flop; player left of big blind starts, bet is the big blind.
            bet = bigBlind;
        } else {
            // Otherwise, player left of dealer starts, no initial bet.
            actorPosition = dealerPosition;
            bet = BigDecimal.ZERO;
        }

        if (playersToAct == 2) {
            // Heads Up mode; player who is not the dealer starts.
            actorPosition = dealerPosition;
        }

        lastBettor = null;
        raises = 0;
        notifyBoardUpdated();

        while (playersToAct > 0) {
            rotateActor();
            PlayerAction action;
            if (actor.isAllIn()) {
                // Player is all-in, so must check.
                action = PlayerAction.CHECK;
                playersToAct--;
            } else {
                // Otherwise allow client to act.
                Set<PlayerAction> allowedActions = getAllowedActions(actor);
                action = actor.getClient().act(minBet, bet, allowedActions);

                if (action == null) {
                    System.out.printf("Player '%s' disconnected, replacing with bot.%n", actor.getName());
                    replacePlayer(actor); // Replaces the player with a bot-client.
                    actor.getClient().playerUpdated(actor); // Updates the new client with the necessary information.
                    action = actor.getClient().act(minBet, bet, allowedActions);
                }

                if (action == PlayerAction.TIMED_OUT) {
                    // Defaulting in order: fold, check, call
                    if (allowedActions.contains(PlayerAction.FOLD)) action = PlayerAction.FOLD;
                    else if (allowedActions.contains(PlayerAction.CHECK)) action = PlayerAction.CHECK;
                    else if (allowedActions.contains(PlayerAction.CALL)) action = PlayerAction.CALL;

                    System.out.printf("Player '%s' didn't act in time, defaulting action %s.%n",
                            actor.getName(),
                            action.getVerb());
                }

                // Verify chosen action to guard against broken clients (accidental or on purpose).
                if (!allowedActions.contains(action)) {
                    if (action instanceof BetAction && !allowedActions.contains(PlayerAction.BET)) {
                        throw new IllegalStateException(String.format("Player '%s' acted with illegal Bet action", actor));
                    } else if (action instanceof RaiseAction && !allowedActions.contains(PlayerAction.RAISE)) {
                        throw new IllegalStateException(String.format("Player '%s' acted with illegal Raise action", actor));
                    }
                }
                playersToAct--;
                if (action.getVerb().equals("checks") || action.getVerb().equals("continues")) {
                    // Do nothing.
                } else if (action.getVerb().equals("calls")) {
                    BigDecimal betIncrement = bet.subtract(actor.getBet());
                    if (betIncrement.compareTo(actor.getCash()) > 0) {
                        betIncrement = actor.getCash();
                    }
                    actor.payCash(betIncrement);
                    actor.setBet(actor.getBet().add(betIncrement));
                    contributePot(betIncrement);
                } else if (action instanceof BetAction) {
                    BigDecimal amount = (tableType == TableType.FIXED_LIMIT) ? minBet : action.getAmount();
                    if (amount.compareTo(minBet) < 0 && amount.compareTo(actor.getCash()) < 0) {
                        throw new IllegalStateException("Illegal client action: bet less than minimum bet!");
                    }
                    actor.setBet(amount);
                    actor.payCash(amount);
                    contributePot(amount);
                    bet = amount;
                    minBet = amount;
                    lastBettor = actor;
                    playersToAct = activePlayers.size();
                } else if (action instanceof RaiseAction) {
                    BigDecimal amount = (tableType == TableType.FIXED_LIMIT) ? minBet : action.getAmount();
                    if (amount.compareTo(minBet) < 0 && amount.compareTo(actor.getCash()) < 0) {
                        throw new IllegalStateException("Illegal client action: raise less than minimum bet!");
                    }
                    bet = bet.add(amount);
                    minBet = amount;
                    BigDecimal betIncrement = bet.subtract(actor.getBet());
                    if (betIncrement.compareTo(actor.getCash()) > 0) {
                        betIncrement = actor.getCash();
                    }
                    actor.setBet(bet);
                    actor.payCash(betIncrement);
                    contributePot(betIncrement);
                    lastBettor = actor;
                    raises++;
                    if (tableType == TableType.NO_LIMIT || raises < MAX_RAISES || activePlayers.size() == 2) {
                        // All players get another turn.
                        playersToAct = activePlayers.size();
                    } else {
                        // Max. number of raises reached; other players get one more turn.
                        playersToAct = activePlayers.size() - 1;
                    }
                } else if (action.getVerb().equals("folds")) {
                    actor.setCards(null);
                    activePlayers.remove(actor);
                    actorPosition--;
                    if (activePlayers.size() == 1) {
                        // Only one player left, so he wins the entire pot.
                        notifyBoardUpdated();
                        notifyPlayerActed();
                        Player winner = activePlayers.get(0);
                        BigDecimal amount = getTotalPot();
                        winner.win(amount);
                        notifyBoardUpdated();
                        notifyMessage("%s wins $ %.2f.", winner, amount.setScale(2));
                        playersToAct = 0;
                    }
                } else {
                    // Programming error, should never happen.
                    throw new IllegalStateException("Invalid action: " + action);
                }
            }
            actor.setAction(action);
            if (playersToAct > 0) {
                notifyBoardUpdated();
                notifyPlayerActed();
            }
        }

        // Reset player's bets.
        for (Player player : activePlayers) {
            player.resetBet();
        }
        notifyBoardUpdated();
        notifyPlayersUpdated(false);
    }

    /**
     * Returns the allowed actions of a specific player.
     *
     * @param player The player.
     * @return The allowed actions.
     */
    public Set<PlayerAction> getAllowedActions(Player player) {
        Set<PlayerAction> actions = new HashSet<>();
        if (player.isAllIn()) {
            actions.add(PlayerAction.CHECK);
            return actions;

        }

        BigDecimal actorBet = actor.getBet();
        if (bet.equals(BigDecimal.ZERO)) {
            // No bet yet in this betting round.
            actions.add(PlayerAction.CHECK);
            if (tableType == TableType.NO_LIMIT || raises < MAX_RAISES || activePlayers.size() == 2) {
                // Allow bet only if no bet yet in this betting round, and we're not at the raise limit.
                actions.add(PlayerAction.BET);
            }
            actions.add(PlayerAction.FOLD);
            return actions;
        }


        if (actorBet.compareTo(bet) < 0) {
            actions.add(PlayerAction.CALL);
            if (tableType == TableType.NO_LIMIT || raises < MAX_RAISES || activePlayers.size() == 2) {
                actions.add(PlayerAction.RAISE);
            }
        } else {
            actions.add(PlayerAction.CHECK);
            if (tableType == TableType.NO_LIMIT || raises < MAX_RAISES || activePlayers.size() == 2) {
                actions.add(PlayerAction.RAISE);
            }
        }
        actions.add(PlayerAction.FOLD);

        return actions;
    }

    /**
     * Contributes to the pot.
     *
     * @param amount The amount to contribute.
     */
    private void contributePot(BigDecimal amount) {
        for (Pot pot : pots) {
            if (!pot.hasContributer(actor)) {
                BigDecimal potBet = pot.getBet();
                if (amount.compareTo(potBet) >= 0) {
                    // Regular call, bet or raise.
                    pot.addContributer(actor);
                    amount = amount.subtract(pot.getBet());
                } else {
                    // Partial call (all-in); redistribute pots.
                    pots.add(pot.split(actor, amount));
                    amount = BigDecimal.ZERO;
                }
            }
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                break;
            }
        }
        if (amount.compareTo(BigDecimal.ZERO) > 0) {
            Pot pot = new Pot(amount);
            pot.addContributer(actor);
            pots.add(pot);
        }
    }

    /**
     * Performs the showdown.
     */
    private void doShowdown() {
//        System.out.println("\n[DEBUG] Pots:");
//        for (Pot pot : pots) {
//            System.out.format("  %s\n", pot);
//        }
//        System.out.format("[DEBUG]  Total: %d\n", getTotalPot());

        List<Player> showingPlayers = showPlayerOrder();
        int pos = lastPlayerToBetOrRaise(showingPlayers);

        // Players automatically show or fold in order.
        boolean firstToShow = true;
        int bestHandValue = -1;

        decideOrderToShowCards(firstToShow, bestHandValue, showingPlayers);
        Map<HandValue, List<Player>> rankedPlayers = sortByHandValue();

        // Per rank (single or multiple winners), calculate pot distribution.
        BigDecimal totalPot = getTotalPot();

        Map<Player, BigDecimal> potDivision = new HashMap<>();
        for (HandValue handValue : rankedPlayers.keySet()) {
            List<Player> winners = rankedPlayers.get(handValue);
            for (Pot pot : pots) {
                // Determine how many winners share this pot.
                int noOfWinnersInPot = getNbrOfWinnersInPot(winners, pot);

                if (noOfWinnersInPot > 0) {
                    // Divide pot over winners.
                    BigDecimal potShare = pot.getValue().divide(new BigDecimal(String.valueOf(noOfWinnersInPot)), RoundingMode.FLOOR); //TODO
                    dividePotOverWinners(winners, pot, potDivision, potShare);

                    // Determine if we have any odd chips left in the pot.
                    BigDecimal oddChips = pot.getValue().remainder(new BigDecimal(String.valueOf(noOfWinnersInPot))); //TODO
                    if (oddChips.compareTo(BigDecimal.ZERO) > 0) {
                        // Divide odd chips over winners, starting left of the dealer.
                        pos = dealerPosition;
                        divideOddChipsOverWinners(oddChips, pos, potDivision);

                    }
                    pot.clear();
                }
            }
        }

        StringBuilder winnerText = divideWinnings(potDivision, totalPot);
        notifyMessage(winnerText.toString());


    }

    private void decideOrderToShowCards(boolean firstToShow, int bestHandValue, List<Player> showingPlayers) {
        for (Player playerToShow : showingPlayers) {
            Hand hand = new Hand(board);
            hand.addCards(playerToShow.getCards());
            HandValue handValue = new HandValue(hand);
            boolean doShow = ALWAYS_CALL_SHOWDOWN;
            doShow = decideWhoToShow(playerToShow, handValue, doShow, firstToShow, bestHandValue);
            if (doShow) {
                showHand(playerToShow, handValue);
            } else {
                fold(playerToShow);
            }
        }
    }

    private StringBuilder divideWinnings(Map<Player, BigDecimal> potDivision, BigDecimal totalPot) {
        // Divide winnings.
        StringBuilder winnerText = new StringBuilder();
        BigDecimal totalWon = BigDecimal.ZERO;
        for (Player winner : potDivision.keySet()) {
            BigDecimal potShare = potDivision.get(winner);
            winner.win(potShare);
            totalWon = totalWon.add(potShare);
            if (winnerText.length() > 0) {
                winnerText.append(", ");
            }
            winnerText.append(winner + " wins $ " + potShare);
            notifyPlayersUpdated(true);
        }
        winnerText.append('.');
        // Sanity check.
        if (!totalWon.equals(totalPot)) {
            throw new IllegalStateException("Incorrect pot division!");
        }
        return winnerText;
    }

    private void divideOddChipsOverWinners(BigDecimal oddChips, int pos, Map<Player, BigDecimal> potDivision) {
        while (oddChips.compareTo(BigDecimal.ZERO) > 0) {
            pos = (pos + 1) % activePlayers.size();
            Player winner = activePlayers.get(pos);
            BigDecimal oldShare = potDivision.get(winner);
            if (oldShare != null) {
                potDivision.put(winner, oldShare.add(BigDecimal.ONE));
//                                System.out.format("[DEBUG] %s receives an odd chip from the pot.\n", winner);
                oddChips = oddChips.subtract(BigDecimal.ONE);
            }
        }
    }

    private void dividePotOverWinners(List<Player> winners, Pot pot, Map<Player, BigDecimal> potDivision, BigDecimal potShare) {
        for (Player winner : winners) {
            if (pot.hasContributer(winner)) {
                BigDecimal oldShare = potDivision.get(winner);
                if (oldShare != null) {
                    potDivision.put(winner, oldShare.add(potShare));
                } else {
                    potDivision.put(winner, potShare);
                }

            }
        }
    }

    private int getNbrOfWinnersInPot(List<Player> winners, Pot pot) {

        int noOfWinnersInPot = 0;
        for (Player winner : winners) {
            if (pot.hasContributer(winner)) {
                noOfWinnersInPot++;
            }
        }
        return noOfWinnersInPot;
    }

    private boolean decideWhoToShow(Player playerToShow, HandValue handValue, boolean doShow, boolean firstToShow, int bestHandValue) {
        if (!doShow) {
            if (playerToShow.isAllIn()) {
                // All-in players must always show.
                doShow = true;
                //firstToShow = false;
            } else if (firstToShow) {
                // First player must always show.
                doShow = true;
                //bestHandValue = handValue.getValue();
                //firstToShow = false;
            } else {
                // Remaining players only show when having a chance to win.
                if (handValue.getValue() >= bestHandValue) {
                    doShow = true;
                    //bestHandValue = handValue.getValue();
                }
            }
        }
        return doShow;
    }

    private Map<HandValue, List<Player>> sortByHandValue() {
        // Sort players by hand value (highest to lowest).
        Map<HandValue, List<Player>> rankedPlayers = new TreeMap<>();
        for (Player player : activePlayers) {
            // Create a hand with the community cards and the player's hole cards.
            Hand hand = new Hand(board);
            hand.addCards(player.getCards());
            // Store the player together with other players with the same hand value.
            HandValue handValue = new HandValue(hand);
//            System.out.format("[DEBUG] %s: %s\n", player, handValue);
            List<Player> playerList = rankedPlayers.get(handValue);
            if (playerList == null) {
                playerList = new ArrayList<>();
            }
            playerList.add(player);
            rankedPlayers.put(handValue, playerList);
        }
        return rankedPlayers;
    }

    private void fold(Player playerToShow) {
        // Fold.
        playerToShow.setCards(null);
        activePlayers.remove(playerToShow);
        for (Player player : players) {
            if (player.equals(playerToShow)) {
                player.getClient().playerUpdated(playerToShow);
            } else {
                hideSecretInfo(player, playerToShow);
            }
        }
        notifyMessage("%s folds.", playerToShow);
    }

    private void hideSecretInfo(Player player, Player playerToShow) {
        // Hide secret information to other players.
        player.getClient().playerUpdated(playerToShow.publicClone());
    }

    private void showHand(Player playerToShow, HandValue handValue) {
        // Show hand.
        for (Player player : players) {
            player.getClient().playerUpdated(playerToShow);
        }
        notifyMessage("%s has %s.", playerToShow, handValue.getDescription());
    }


    private List<Player> showPlayerOrder() {
        // Determine show order; start with all-in players...
        ArrayList orderedPlayerList = new ArrayList<>();
        for (Pot pot : pots) {
            for (Player contributor : pot.getContributors()) {
                if (!orderedPlayerList.contains(contributor) && contributor.isAllIn()) {
                    orderedPlayerList.add(contributor);
                }
            }
        }
        return orderedPlayerList;
    }

    private int lastPlayerToBetOrRaise(List<Player> showingPlayers) {
        // ...then last player to bet or raise (aggressor)...
        if (lastBettor != null) {
            if (!showingPlayers.contains(lastBettor)) {
                showingPlayers.add(lastBettor);
            }
        }

        //...and finally the remaining players, starting left of the button.
        int pos = (dealerPosition + 1) % activePlayers.size();
        while (showingPlayers.size() < activePlayers.size()) {
            Player player = activePlayers.get(pos);
            if (!showingPlayers.contains(player)) {
                showingPlayers.add(player);
            }
            pos = (pos + 1) % activePlayers.size();
        }
        return pos;
    }

    /**
     * Notifies listeners with a custom game message.
     *
     * @param message The formatted message.
     * @param args    Any arguments.
     */
    private void notifyMessage(String message, Object... args) {
        message = String.format(message, args);
        for (Player player : players) {
            player.getClient().messageReceived(message);
        }
    }

    /**
     * Notifies clients that the board has been updated.
     */
    private void notifyBoardUpdated() {
        BigDecimal pot = getTotalPot();
        for (Player player : players) {
            player.getClient().boardUpdated(board, bet, pot);
        }
    }

    /**
     * Returns the total pot size.
     *
     * @return The total pot size.
     */
    private BigDecimal getTotalPot() {
        BigDecimal totalPot = BigDecimal.ZERO;
        for (Pot pot : pots) {
            totalPot = totalPot.add(pot.getValue());
        }
        return totalPot;
    }

    /**
     * Notifies clients that one or more players have been updated. <br />
     * <br />
     * <p>
     * A player's secret information is only sent its own client; other clients
     * see only a player's public information.
     *
     * @param showdown Whether we are at the showdown phase.
     */
    private void notifyPlayersUpdated(boolean showdown) {
        for (Player playerToNotify : players) {
            for (Player player : players) {
                player = hideSecretInfoToOtherPlayers(player, showdown, playerToNotify);
                playerToNotify.getClient().playerUpdated(player);
            }
        }
    }

    private Player hideSecretInfoToOtherPlayers(Player player, boolean showdown, Player playerToNotify) {
        if (!showdown && !player.equals(playerToNotify)) {
            // Hide secret information to other players.
            player = player.publicClone();
        }
        return player;
    }

    /**
     * Notifies clients that a player has acted.
     */
    private void notifyPlayerActed() {
        for (Player p : players) {
            Player playerInfo = p.equals(actor) ? actor.packetClone() : actor.publicClone();
            p.getClient().playerActed(playerInfo);
        }
    }

    public Player getActor() {
        return actor;
    }

    public void setActor(Player actor) {
        this.actor = actor;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public void setBet(BigDecimal bet) {
        this.bet = bet;
    }
}
