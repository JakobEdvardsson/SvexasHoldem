package org.pokergame.toServerCommands;

import java.io.Serializable;

public record JoinTable(int tableId) implements Serializable {
}
