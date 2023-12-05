package com.example.obstaclecourse.Models;

import java.util.ArrayList;

/**
 * Represents a scoreboard that stores and manages player scores.
 */
public class ScoreBoard {
    private final ArrayList<Player> playerScores;  // List to store player scores
    private final int maxEntries;  // Maximum number of entries to display on the scoreboard

    /**
     * Constructor to initialize the scoreboard.
     * @param playerScores The list of player scores.
     * @param maxEntries Maximum number of entries to display on the scoreboard.
     * @throws IllegalArgumentException if the number of scores exceeds the maximum number of entries.
     */
    public ScoreBoard(ArrayList<Player> playerScores, int maxEntries) {
        if (playerScores.size() > maxEntries) {
            throw new IllegalArgumentException("Number of scores must not exceed the maximum number of entries");
        }
        this.maxEntries = maxEntries;
        this.playerScores = playerScores;
    }

    /**
     * Add a player's score to the scoreboard.
     * @param p The player object whose score to add.
     * @throws IllegalArgumentException if the number of scores exceeds the maximum number of entries or if the maximum number of entries is zero.
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
     * Get the list of player scores stored in the scoreboard.
     * @return The list of player scores.
     */
    public ArrayList<Player> getPlayerScores() {
        return this.playerScores;
    }

    /**
     * Add a specific score to a player's existing score.
     * @param playerName The name of the player whose score to update.
     * @param scoreToAdd The score to add.
     */
    public void addScore(String playerName, int scoreToAdd) {
        for (Player player : playerScores) {
            if (player.getName().equals(playerName)) {
                player.setScore(player.getScore() + scoreToAdd);
                break;
            }
        }
    }

    /**
     * Deduct a specific score from a player's existing score.
     * @param playerName The name of the player whose score to update.
     * @param scoreToDeduct The score to deduct.
     */
    public void deductScore(String playerName, int scoreToDeduct) {
        for (Player player : playerScores) {
            if (player.getName().equals(playerName)) {
                player.setScore(player.getScore() - scoreToDeduct);
                break;
            }
        }
    }


    public String printScores() {
        StringBuilder scores = new StringBuilder();

        /**
         * Print the current scores to the console.
         */
        System.out.println("Current Scores:");
        for (Player player : playerScores) {
            System.out.println(player.getName() + ": " + player.getScore() + " points");
            scores.append(player.getName()).append(": ").append(player.getScore()).append(" points\n");
        }

        return scores.toString();
    }
}
