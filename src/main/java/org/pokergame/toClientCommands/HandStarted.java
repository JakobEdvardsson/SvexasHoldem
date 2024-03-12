package org.pokergame.toClientCommands;

import org.pokergame.Player;

import java.io.Serializable;

public record HandStarted(Player dealer) implements Serializable {
}