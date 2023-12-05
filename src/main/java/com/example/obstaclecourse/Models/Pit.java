package com.example.obstaclecourse.Models;

import java.util.Arrays;
import java.util.Random;

public class Pit implements Obstacle {
    public enum Type {BOTTOMLESS, SPIKE, FIRE, PORTAL}

    private final Type type;

    // If it's not of spike type, default length is 1
    private int length = 1;

    /**
     * Constructor to initialize the obstacle based on type
     * If it's of spike type, set length randomly
     */
    public Pit(Type type) {
        this.type = type;

        if (type == Type.SPIKE) {
            this.length = (int) (Math.random() * 4 + 1);  // If it's of spike type, set length randomly
        }
    }

    /**
     * Constructor allowing specification of obstacle length
     * Only spike type can have a custom length
     */
    public Pit(Type type, int length) {
        if (type != Type.SPIKE) {
            throw new IllegalArgumentException("Custom length can only be set for spike type!");
        }

        if (length < 1 || length > 4) {
            throw new IllegalArgumentException("Length must be between 1 and 5");
        }
        // Only spike type can have a length
        this.type = Type.SPIKE;

        this.length = length;
    }

    /**
     * Get the type of the obstacle
     */
    public Type getType() {
        return this.type;  // Return obstacle type
    }

    /**
     * Apply obstacle effect to the player
     * Modify player's status according to the obstacle type
     */
    @Override

    public void applyEffect(Player p, int size, Board.Cell[][] cells) {
        int[] origin = {0, 0};
        switch (this.type) {
            case BOTTOMLESS: {
                p.setPosition(origin);
                p.setMovesAvailable(0);
            }
            case SPIKE: {
                p.setMovesAvailable(p.getMovesAvailable() - this.length);
                break;
            }
            case FIRE: {
                p.setMovesAvailable(p.getMovesAvailable() - 1);
                break;
            }
            case PORTAL: {
                Random random = new Random();
                p.setPosition(new int[]{random.nextInt(size), random.nextInt(size)});
                break;
            }
        }
    }

    /**
     * Get a description of the obstacle effect
     */
    @Override
    public String printEffect() {
        String effect = "";

        switch (this.type) {
            case FIRE -> effect = "Causes player to miss a turn!";
            case SPIKE -> effect = "Consumes " + this.length + " squares, must skip!";
            case BOTTOMLESS -> effect = "Sends player back to start!";
            case PORTAL -> effect = "Teleports player to another location!";
        }

        return effect;  // Return description string
    }

    /**
     * Get the symbol representation of the obstacle
     */
    @Override
    public String getSymbol() {
        String symbol = "⬛";

        switch (this.type) {
            case FIRE -> symbol = "🔥";
            case SPIKE -> symbol = "🔱";
            case BOTTOMLESS -> symbol = "🕳";
            case PORTAL -> symbol = "🌀";
        }

        return symbol;
    }

    /**
     * Get the length of spike obstacle
     */
    @Override
    public int getLength() {
        return this.length;
    }
}
