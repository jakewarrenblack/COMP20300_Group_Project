package com.example.obstaclecourse.Controllers;
import com.example.obstaclecourse.Models.Dice;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
public class DiceController {
    Dice dice;

    @FXML
    private Text diceValues;

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
