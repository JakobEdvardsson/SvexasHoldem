package org.pokergame.toClientCommands;

import org.pokergame.actions.PlayerAction;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

public record Act(BigDecimal minBet, BigDecimal currentBet, Set<PlayerAction> allowedActions, long timeout) implements Serializable {
}