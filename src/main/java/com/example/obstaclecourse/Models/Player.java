package com.example.obstaclecourse.Models;

import com.example.obstaclecourse.Utils.EventProducer;
import javafx.scene.image.ImageView;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


/**
 * Represents a player in the game.
 */
public class Player extends EventProducer {
    private ImageView imageView;
    private final int index;
    private final String name;
    private int[] position = new int[2];  // Player's current position represented by an array containing two integers
    private int movesAvailable;  // Remaining moves available for the player
    private int score;

    /**
     * Constructor for Player class.
     * @param name The name of the player.
     * @param index The index of the player.
     */
    public Player(String name, int index){
        this.name = name;
        this.movesAvailable = 0;
        this.index = index;
    }

    /**
     * Sets the ImageView associated with the player.
     * @param imageView The ImageView to set.
     */
    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
    /**
     * Gets the ImageView associated with the player.
     * @return The ImageView associated with the player.
     */
    public ImageView getImageView() {
        return imageView;
    }

    /**
     * Gets the name of the player.
     * @return The name of the player.
     */
    public String getName(){
        return this.name;
    }

    /**
     * Sets the position of the player.
     * @param position An array containing the new position coordinates.
     */
    public void setPosition(int[] position){
        System.out.println("Updating player position. Property change will be fired.");
        // keep a reference to the original, before it's changed
        int[] oldPosition = this.position;

        this.position = position;

        this.support.firePropertyChange("playerPosition", oldPosition, position);
    }

    /**
     * Gets the position of the player.
     * @return An array containing the position coordinates.
     */
    public int[] getPosition(){
        return this.position;
    }

    /**
     * Sets the number of moves available for the player.
     * @param movesAvailable The number of moves available.
     */
    public void setMovesAvailable(int movesAvailable){
        this.movesAvailable = movesAvailable;
    }

    /**
     * Gets the number of moves available for the player.
     * @return The number of moves available.
     */
    public int getMovesAvailable(){
        return this.movesAvailable;
    }

    /**
     * Sets the score of the player.
     * @param score The score to set.
     */
    public void setScore(int score){
        this.score = score;
    }

    /**
     * Gets the score of the player.
     * @return The score of the player.
     */
    public int getScore(){
        return this.score;
    }

    /**
     * Gets the index of the player.
     * @return The index of the player.
     */
    public int getIndex(){
        return this.index;
    }

}
