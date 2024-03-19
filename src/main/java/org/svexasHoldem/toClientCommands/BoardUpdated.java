package org.svexasHoldem.toClientCommands;

import org.svexasHoldem.Card;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public record BoardUpdated(List<Card> cards, BigDecimal bet, BigDecimal pot) implements Serializable {
}