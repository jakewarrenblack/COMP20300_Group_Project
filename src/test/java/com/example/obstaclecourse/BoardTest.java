package com.example.obstaclecourse;


import com.example.obstaclecourse.Models.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class BoardTest {

    private Board board;
    private Dice dice;
    private ScoreBoard scoreBoard;
    private ArrayList<Player> players;
    private final int boardSize = 8;

    @Before
    public void setUp() {
        dice = Dice.getInstance();
        players = new ArrayList<>();
        players.add(new Player("Jake", 0));
        players.add(new Player("Yu", 1));
        board = new Board(boardSize, dice, players);
    }

    @Test
    public void testInitialPlayerSet() {
        assertNotNull("Initial player should not be null", board.nextPlayer());
    }

    @Test
    public void testGetCell() {
        assertNotNull("Cell should not be null", board.getCell(0, 0));
    }


    @Test
    public void testInitialiseObstacles() {
        board.initialiseObstacles(10); // Initialise 10 obstacles for testing

        int obstacleCount = 0;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board.getCell(i, j).hasObstacle()) {
                    obstacleCount++;
                }
            }
        }

        assertTrue("Number of obstacles should be at least 10", obstacleCount >= 10);
    }

    @Test
    public void testHandleSpikePit() {
        Board.Cell cell = board.getCell(0, 0);
        Pit spikePit = new Pit(Pit.Type.SPIKE, 3);
        board.handleSpikePit(cell, spikePit);

        assertEquals("First cell should have a spike pit", Pit.Type.SPIKE, cell.getObstacle().getType());
        for (int i = 1; i <= 2; i++) {
            assertTrue("Adjacent cells should have spike pits", board.getCell(0, i).hasObstacle());
            assertEquals("Spike pit should have length 1", 1, ((Pit) board.getCell(0, i).getObstacle()).getLength());
        }
    }



}