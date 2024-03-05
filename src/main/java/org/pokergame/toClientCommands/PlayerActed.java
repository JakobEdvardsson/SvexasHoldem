package org.pokergame.toClientCommands;

import org.pokergame.Player;

import java.io.Serializable;

public record PlayerActed(Player player)implements Serializable {
}