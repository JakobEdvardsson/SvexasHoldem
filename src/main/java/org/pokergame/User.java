package org.pokergame;

public class User {
    private String name;

    private boolean isOnline;

    public User(String name) {
        this.name = name;
    }

    public boolean getIsOnline(){
        return this.isOnline;
    }
}
