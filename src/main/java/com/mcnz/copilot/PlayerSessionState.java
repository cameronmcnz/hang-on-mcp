package com.mcnz.copilot;

public class PlayerSessionState {

    private final String token;
    private final PlayerStats stats = new PlayerStats();
    private HangmanGame currentGame;

    public PlayerSessionState(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public PlayerStats getStats() {
        return stats;
    }

    public HangmanGame getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(HangmanGame currentGame) {
        this.currentGame = currentGame;
    }
}

