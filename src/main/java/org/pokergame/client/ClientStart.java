package org.pokergame.client;

public class ClientStart {
    public static void main(String[] args) {
        startClient();
    }

    public static void startClient() {
        ClientController clientcontroller = new ClientController("localhost",1337);
    }

}
