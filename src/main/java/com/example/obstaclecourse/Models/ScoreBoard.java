package com.example.obstaclecourse.Models;

import java.util.ArrayList;

public class ScoreBoard {
    private final ArrayList<Player> playerScores;  // List to store player scores
    private final int maxEntries;  // Maximum number of entries to display on the scoreboard

    /**
     * Constructor to initialize the scoreboard
     *
     */
    public ScoreBoard(ArrayList<Player> playerScores, int maxEntries) {
        if (playerScores.size() > maxEntries) {
            throw new IllegalArgumentException("Number of scores must not exceed the maximum number of entries");
        }
        this.maxEntries = maxEntries;
        this.playerScores = playerScores;
    }

    /**
     * Add a player's score to the scoreboard
     *
     */
    public void addPlayerScore(Player p) {
        if (this.playerScores.size() + 1 > this.maxEntries) {
            throw new IllegalArgumentException("Number of scores must not exceed the maximum number of entries");
        }

        if (maxEntries <= 0) {
            throw new IllegalArgumentException("Maximum number of entries must not be zero");
        }

        this.playerScores.add(p);
    }

    /**
     * Get the list of player scores stored in the scoreboard
     */
    public ArrayList<Player> getPlayerScores() {
        return this.playerScores;
    }

    public void addScore(String playerName, int scoreToAdd) {
        for (Player player : playerScores) {
            if (player.getName().equals(playerName)) {
                player.setScore(player.getScore() + scoreToAdd);
                break;
            }
        }
    }

    /**
     * Deduct a player's score
     */
    public void deductScore(String playerName, int scoreToDeduct) {
        for (Player player : playerScores) {
            if (player.getName().equals(playerName)) {
                player.setScore(player.getScore() - scoreToDeduct);
                break;
            }
        }
    }

    public void printScores() {
        System.out.println("Current Scores:");
        for (Player player : playerScores) {
            System.out.println(player.getName() + ": " + player.getScore() + " points");
        }
    }
}
