package com.example.obstaclecourse.Controllers;
import com.example.obstaclecourse.Models.Dice;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

/**
 * Controller class for managing the dice view.
 * Handles rolling the dice and updating the UI with the dice value.
 */
public class DiceController {
    Dice dice;

    // the dice needs to listen for when the player changes
    // so it can update the UI

    @FXML
    private Text diceValues;

    /**
     * Rolls the dice and updates the UI with the rolled value.
     * Invoked when the roll button is clicked.
     */
    public void roll(){
        this.dice.roll();

        // update the text in the dice view
        diceValues.setText(Integer.toString(dice.getValue()));
    }

    /**
     * Initializes the controller by obtaining an instance of the Dice model.
     * Automatically called after the FXML file has been loaded.
     */
    @FXML
    public void initialize() {
        this.dice = Dice.getInstance();
    }
}
