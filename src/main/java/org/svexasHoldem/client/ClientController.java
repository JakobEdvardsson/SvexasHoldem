package org.svexasHoldem.client;

import org.svexasHoldem.actions.PlayerAction;
import org.svexasHoldem.gui.Main;
import org.svexasHoldem.gui.StartMenu;
import org.svexasHoldem.toServerCommands.*;

import javax.swing.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.Socket;

public class ClientController {
    private int port;
    private String ip;
    private Socket socket;
    private Main ClientGUI;
    private StartMenu startMenu;
    private ClientOutputX clientOutput;
    private ClientInputX clientInput;
    private String username;

    public ClientController(String ip, int port) throws IOException {
        /* The table. */

        this.ip = ip;
        this.port = port;

        invokeLater();
    }

    public void invokeLater() {
        SwingUtilities.invokeLater(() -> {
                this.startMenu = new StartMenu(this);
                startMenu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        });
    }

    public void playOnline() {
        try {
            socket = new Socket(ip, port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // create the input and output streams
        clientInput = new ClientInputX(socket, this);
        clientInput.start();

        clientOutput = new ClientOutputX(socket);

        clientOutput.sendMessage(new Register(this.username));
    }

    public void disconnectClient() {
        clientOutput.sendMessage(new Disconnect());
    }

    public String getUsername() {
        return username;
    }

    public void joinLobby(int lobbyId) {
        JoinLobby lobby = new JoinLobby(lobbyId);
        clientOutput.sendMessage(lobby);
    }

    public void setLobbyInfo(String[][] info) {
        startMenu.setLobbyInfo(info);
    }

    public void leaveLobby(int lobbyId){
        clientOutput.sendMessage(new LeaveLobby(lobbyId));
    }

    public void setUsername(String text) {
        this.username = text;
    }

    public void startGame(BigDecimal stackSize) {
        // Checks if the stack size is divisible by 4.
        if (stackSize.remainder(new BigDecimal(4)).equals(BigDecimal.ZERO)) {
            clientOutput.sendMessage(new StartGame(stackSize));
        }
    }

    public void sendMessage(PlayerAction action) {
        clientOutput.sendMessage(action);
    }

    public void hideLobbyWindow() {
        startMenu.hideLobbyWindow();
    }

    public void showLobbyWindow() {
        startMenu.showLobbyWindow();
        clientInput.gameOver();
    }

    public int getLobbyId() {
        return startMenu.getLobbyId();
    }

    public void showStartMenu() {

        startMenu.showStartMenu();
        clientInput.gameOver();

    }

    public StartMenu getStartMenu() {
        return startMenu;
    }

    public ClientInputX getClientInput() {
        return clientInput;
    }


}