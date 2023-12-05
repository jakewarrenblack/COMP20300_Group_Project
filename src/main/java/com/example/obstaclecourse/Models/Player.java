package com.example.obstaclecourse.Models;

import com.example.obstaclecourse.Utils.EventProducer;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Player extends EventProducer {
    private final int index;
    private final String name;
    private int[] position = new int[2];  // Player's current position represented by an array containing two integers
    private int movesAvailable;  // Remaining moves available for the player
    private int score;

    public Player(String name, int index){
        this.name = name;
        this.movesAvailable = 0;
        this.index = index;
    }


    public String getName(){
        return this.name;
    }
    
    public void setPosition(int[] position){
        System.out.println("Updating player position. Property change will be fired.");
        // keep a reference to the original, before it's changed
        int[] oldPosition = this.position;

        this.position = position;

        this.support.firePropertyChange("playerPosition", oldPosition, position);
    }


    public int[] getPosition(){
        return this.position;
    }

    public void setMovesAvailable(int movesAvailable){
        this.movesAvailable = movesAvailable;
    }
    
    
    public int getMovesAvailable(){
        return this.movesAvailable;
    }


    public void setScore(int score){
        this.score = score;
    }


    public int getScore(){
        return this.score;
    }

    public int getIndex(){
        return this.index;
    }

}
