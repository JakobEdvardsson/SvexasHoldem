package org.pokergame.toClientCommands;

import java.io.Serializable;

public record MessageReceived(String message) implements Serializable {
}