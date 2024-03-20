package org.svexasHoldem.toClientCommands;

import org.svexasHoldem.Player;

import java.io.Serializable;

public record PlayerActed(Player player)implements Serializable {
}