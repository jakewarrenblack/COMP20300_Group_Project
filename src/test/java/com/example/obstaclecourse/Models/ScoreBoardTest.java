package com.example.obstaclecourse.Models;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ScoreBoardTest {

    @Test
    void addPlayerScore() {
        ArrayList<Player> players = new ArrayList<>();

        ScoreBoard scoreBoard = new ScoreBoard(players, 2);

        scoreBoard.addPlayerScore(new Player("Player 1", 0));

        // add one player and ensure it's in the list
        assertEquals(1, scoreBoard.getPlayerScores().size());
    }

    @Test
    void getPlayerScores() {
        ScoreBoard scoreBoard = new ScoreBoard(new ArrayList<>(), 2);
        scoreBoard.addPlayerScore(new Player("Player 1", 0));
        scoreBoard.addPlayerScore(new Player("Player 2", 0));

        // add two players and ensure they're both in the list
        assertEquals(2, scoreBoard.getPlayerScores().size());
    }

    @Test
    void addScore() {
        ScoreBoard scoreBoard = new ScoreBoard(new ArrayList<>(), 2);
        scoreBoard.addPlayerScore(new Player("Player 1", 0));

        scoreBoard.addScore("Player 1", 10);

        // verify that the player's score has increased by 10
        assertEquals(10, scoreBoard.getPlayerScores().get(0).getScore());

    }

    @Test
    void deductScore() {
        ScoreBoard scoreBoard = new ScoreBoard(new ArrayList<>(), 2);
        scoreBoard.addPlayerScore(new Player("Player 1", 10));

        scoreBoard.deductScore("Player 1", 10);

        // verify that the player's score has decreased by 10
        assertEquals(0, scoreBoard.getPlayerScores().get(0).getScore());
    }

    @Test
    void printScores() {
        ScoreBoard scoreBoard = new ScoreBoard(new ArrayList<>(), 2);
        scoreBoard.addPlayerScore(new Player("Player 1", 10));
        scoreBoard.addPlayerScore(new Player("Player 2", 20));

        // verify that the scores are printed correctly
        assertEquals("Player 1: 10\nPlayer 2: 20\n", scoreBoard.printScores());
    }
}