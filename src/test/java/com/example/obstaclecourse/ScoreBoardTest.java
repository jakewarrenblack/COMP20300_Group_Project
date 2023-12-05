package com.example.obstaclecourse;

import com.example.obstaclecourse.Models.Player;
import com.example.obstaclecourse.Models.ScoreBoard;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class ScoreBoardTest {

    private ScoreBoard scoreBoard;
    private Player player1;
    private Player player2;
    private final int maxEntries = 10;

    @Before
    public void setUp() {
        ArrayList<Player> players = new ArrayList<>();
        player1 = new Player("Jake", 0);
        player2 = new Player("Yu", 1);
        players.add(player1);
        players.add(player2);
        scoreBoard = new ScoreBoard(players, maxEntries);
    }

    @Test
    public void testAddPlayerScore() {
        Player player3 = new Player("Amy", 2);
        scoreBoard.addPlayerScore(player3);
        assertEquals("ScoreBoard should contain the new player score", 3, scoreBoard.getPlayerScores().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddPlayerScoreBeyondMaxEntries() {
        for (int i = 2; i <= maxEntries; i++) {
            scoreBoard.addPlayerScore(new Player("Player" + i, i));
        }
        scoreBoard.addPlayerScore(new Player("ExtraPlayer", maxEntries + 1));
    }

    @Test
    public void testAddScore() {
        scoreBoard.addScore(player1.getName(), 5);
        assertEquals("Player score should be increased by the added score", 5, (int) scoreBoard.getByPlayerName(player1.getName()));
    }

    @Test
    public void testDeductScore() {
        int initialScore = player1.getScore();
        scoreBoard.addScore(player1.getName(), 10); // Player score is now 10
        scoreBoard.deductScore(player1.getName(), 4); // Deduct 4 points
        assertEquals("Player score should be decreased by the deducted score", initialScore + 6, (int) scoreBoard.getByPlayerName(player1.getName()));
    }

    @Test
    public void testPrintScores() {
        // This test just ensures the method runs without exception
        scoreBoard.printScores();
    }

}
