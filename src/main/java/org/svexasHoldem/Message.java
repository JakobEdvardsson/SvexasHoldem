package org.svexasHoldem;

import java.io.Serializable;

public class Message implements Serializable {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
