package org.svexasHoldem.toClientCommands;

import org.svexasHoldem.Player;

import java.io.Serializable;

public record HandStarted(Player dealer) implements Serializable {
}