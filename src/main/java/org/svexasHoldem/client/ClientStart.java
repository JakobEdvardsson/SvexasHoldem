package org.svexasHoldem.client;

import java.io.IOException;

public class ClientStart {
    public static void main(String[] args) throws IOException {
        ClientController clientcontroller = new ClientController("localhost",1337);
    }

}