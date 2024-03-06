package org.pokergame.toClientCommands;

import org.pokergame.Player;
import org.pokergame.TableType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public record JoinedTable(TableType type, BigDecimal bigBlind, List<String> players) implements Serializable {

}