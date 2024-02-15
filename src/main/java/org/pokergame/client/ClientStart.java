package org.pokergame.client;

public class ClientStart {
    public static void main(String[] args) {
        startClient();
    }

    public static void startClient() {
        new ClientController().connect("localhost",1337);
    }

}
