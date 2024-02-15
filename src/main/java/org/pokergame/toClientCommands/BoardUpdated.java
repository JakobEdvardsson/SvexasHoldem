package org.pokergame.toClientCommands;

import org.pokergame.Card;

import java.math.BigDecimal;
import java.util.List;

public record BoardUpdated(List<Card> cards, BigDecimal bet, BigDecimal pot) {
}
