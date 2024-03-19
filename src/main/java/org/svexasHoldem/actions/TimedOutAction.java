package org.svexasHoldem.actions;

import java.io.Serializable;

public class TimedOutAction extends PlayerAction implements Serializable {

    /**
     * Constructor.
     */
    /* package */ TimedOutAction() {
        super("Timed Out", "timed out");
    }
}
