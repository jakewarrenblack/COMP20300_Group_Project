package com.example.obstaclecourse.Models;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Dice {
    public static Dice single_instance = null;
    private int value;

    // property change listener allows us to detect a change, and use that to move the player in the controller
    private final PropertyChangeSupport support;

    // Singleton pattern. Only a single instance of the dice can exist. This is so the same dice is used by ObstacleCourse, Board, BoardController, and DiceController
    public static Dice getInstance(){
        if (single_instance == null){
            single_instance = new Dice();
        }
        return single_instance;
    }

    private Dice(){
        this.value = 0;
        this.support = new PropertyChangeSupport(this);
    }

    public int roll(){
//        System.out.println("Rolling dice. Property change will be fired.");
        // keep a reference to the old value for comparison
        int oldValue = this.value;

        this.value = (int)(Math.random() * 8 + 1);  // Set the dice face to some random value between 1 and 9 inclusive

        this.support.firePropertyChange("diceValue", oldValue, this.value);

        return this.value;
    }

    // use these for registering and unregistering listeners in other files
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }


    public int getValue(){
        return this.value;
    }
}
