package com.example.obstaclecourse.Models;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PitTest {

    @Test
    void getType() {
        // check that the correct type is returned for each type of pit
        for (Pit.Type type : Pit.Type.values()) {
            Pit pit = new Pit(type);
            assertEquals(type, pit.getType());
        }
    }

    @Test
    void applyEffect() {
        Player player1 = new Player("Test Player 1", 0);
        Player player2 = new Player("Test Player 2", 1);
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);

        // Create a board first, and move the player to a position
        // where the pit is, then apply the effect and check that the player is moved back to the start
        Board board = new Board(5, Dice.getInstance(), players);

        // Iterate through all the possible pit types
        for (Pit.Type type : Pit.Type.values()) {
            Player currentPlayer = board.nextPlayer();
            // Reset the current player's position to 1,1
            currentPlayer.setPosition(new int[]{1, 1});

            // Set a pit of the current type at position 1,1 in the Board
            Pit pit = new Pit(type);
            board.getCell(1, 1).setObstacle(pit);

            // Call the applyEffect method on the pit
            pit.applyEffect(currentPlayer, 5, board.getCells());

            // Check the player's position and moves available based on the type of the pit
            switch (type) {
                case BOTTOMLESS:
                    // assert player has moved back to the start
                    assertArrayEquals(new int[]{0, 0}, currentPlayer.getPosition());
                    assertEquals(-1, currentPlayer.getMovesAvailable());
                    break;
                case SPIKE:
                    int length = pit.getLength();
                    // assert player moves decrease by size of pit
                    assertEquals(length*-1, currentPlayer.getMovesAvailable());
                    break;
                case FIRE:
                    // assert player now has 1 less move available
                    assertEquals(-2, currentPlayer.getMovesAvailable());
                    break;
                case PORTAL:
                    // just check the position is not the same as the original
                    // since the player is randomly teleported
                    assertNotEquals(new int[]{1, 1}, currentPlayer.getPosition());
                    break;
            }
        }
    }

    @Test
    void printEffect() {
        // check correct output for each type of pit
        for(Pit.Type type : Pit.Type.values()) {
            Pit pit = new Pit(type);
            String expected = switch (type) {
                case BOTTOMLESS -> "You fell into a bottomless pit! You are now back at the start.";
                case SPIKE -> "You fell into a spike pit! You lose " + pit.getLength() + " moves.";
                case FIRE -> "You fell into a fire pit! You lose 1 move.";
                case PORTAL -> "You fell into a portal pit! You are now at a random location.";
            };
            assertEquals(expected, pit.printEffect());
        }
    }

    @Test
    void getSymbol() {
        // Relating only to the console version of the game
        for(Pit.Type type : Pit.Type.values()) {
            Pit pit = new Pit(type);
            String expected = "";
            switch (type) {
                case BOTTOMLESS:
                    expected = "🕳";
                    break;
                case SPIKE:
                    expected = "🔱";
                    break;
                case FIRE:
                    expected = "🔥";
                    break;
                case PORTAL:
                    expected = "🌀";
                    break;
            }
            assertEquals(expected, pit.getSymbol());
        }
    }

    @Test
    void getLength() {
        // applies only to spike pits
        for(Pit.Type type : Pit.Type.values()) {
            Pit pit = new Pit(type);
            if (type == Pit.Type.SPIKE) {
                assertTrue(pit.getLength() >= 1 && pit.getLength() <= 4);
            }
        }
    }
}