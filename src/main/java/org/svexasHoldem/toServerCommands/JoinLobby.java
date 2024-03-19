package org.svexasHoldem.toServerCommands;

import java.io.Serializable;

public record JoinLobby(int tableId) implements Serializable {
}
