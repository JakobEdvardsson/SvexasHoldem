package org.pokergame.toClientCommands;

import org.pokergame.Player;

import java.io.Serializable;

public record ActorRotated(Player actor) implements Serializable {

}