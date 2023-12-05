package com.example.obstaclecourse;

import com.example.obstaclecourse.Models.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    private Player player;
    private final int initialIndex = 1;
    private final String initialName = "TestPlayer";

    @Before
    public void setUp() {
        // Create a new Player object before each test
        player = new Player(initialName, initialIndex);
    }

    @Test
    public void testGetName() {
        assertEquals("Player name should match the constructor parameter.", initialName, player.getName());
    }

    @Test
    public void testGetIndex() {
        assertEquals("Player index should match the constructor parameter.", initialIndex, player.getIndex());
    }

    @Test
    public void testPosition() {
        int[] newPosition = {5, 5};
        player.setPosition(newPosition);
        assertArrayEquals("Player position should be updated with setPosition.", newPosition, player.getPosition());
    }

    @Test
    public void testMovesAvailable() {
        int moves = 3;
        player.setMovesAvailable(moves);
        assertEquals("Player moves available should be set and get correctly.", moves, player.getMovesAvailable());
    }

    @Test
    public void testScore() {
        int score = 10;
        player.setScore(score);
        assertEquals("Player score should be set and get correctly.", score, player.getScore());
    }

    // Test for ImageView can be omitted if JavaFX is not initialized.
}
