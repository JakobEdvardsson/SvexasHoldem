package org.svexasHoldem.toClientCommands;

import org.svexasHoldem.Player;

import java.io.Serializable;

public record PlayerUpdated(Player player) implements Serializable {
}