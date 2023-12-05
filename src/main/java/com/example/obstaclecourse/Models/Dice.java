package com.example.obstaclecourse.Models;
import com.example.obstaclecourse.Utils.EventProducer;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

// property change listener allows us to detect a change, and use that to move the player in the controller
/**
 * Represents a dice used in the game.
 * This class generates a random value when rolled and notifies listeners about the value change.
 */
public class Dice extends EventProducer {
    /**
     * The single instance of the Dice class.
     */
    public static Dice single_instance = null;
    private int value;
    /**
     * Gets the single instance of the Dice class (Singleton pattern).
     * @return The single instance of Dice.
     */
    // Singleton pattern. Only a single instance of the dice can exist. This is so the same dice is used by ObstacleCourse, Board, BoardController, and DiceController
    public static Dice getInstance(){
        if (single_instance == null){
            single_instance = new Dice();
        }
        return single_instance;
    }

    private Dice(){
        this.value = 0;
    }

    /**
     * Rolls the dice to generate a random value between 1 and 9 inclusive.
     * Notifies listeners about the value change.
     * @return The generated value.
     */
    public int roll(){
        // keep a reference to the old value for comparison
        int oldValue = this.value;

        this.value = (int)(Math.random() * 8 + 1);  // Set the dice face to some random value between 1 and 9 inclusive

        this.support.firePropertyChange("diceValue", oldValue, this.value);

        return this.value;
    }

    /**
     * Gets the current value of the dice.
     * @return The current value of the dice.
     */
    public int getValue(){
        return this.value;
    }
}
