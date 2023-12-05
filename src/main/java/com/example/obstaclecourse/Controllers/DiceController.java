package com.example.obstaclecourse.Controllers;
import com.example.obstaclecourse.Models.Dice;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
public class DiceController {
    Dice dice;

    // the dice needs to listen for when the player changes
    // so it can update the UI

    @FXML
    private Text diceValues;

    @FXML
    private Text currentPlayer;

    public void roll(){
        this.dice.roll();

        // update the text in the dice view
        diceValues.setText(Integer.toString(dice.getValue()));
    }

    @FXML
    public void initialize() {
        this.dice = Dice.getInstance();
    }
}
