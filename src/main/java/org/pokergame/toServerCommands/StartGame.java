package org.pokergame.toServerCommands;

import java.io.Serializable;
import java.math.BigDecimal;

public record StartGame(BigDecimal stackSize) implements Serializable {
}
