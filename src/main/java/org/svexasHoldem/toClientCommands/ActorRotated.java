package org.svexasHoldem.toClientCommands;

import org.svexasHoldem.Player;

import java.io.Serializable;

public record ActorRotated(Player actor) implements Serializable {

}