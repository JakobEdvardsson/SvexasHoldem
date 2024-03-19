package org.pokergame.toServerCommands;

import java.io.Serializable;

public record JoinLobby(int tableId) implements Serializable {
}
