package org.pokergame.toClientCommands;

import org.pokergame.Player;

import java.io.Serializable;

public record PlayerUpdated(Player player) implements Serializable {
}