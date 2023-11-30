package com.example.obstaclecourse.Controllers;
import com.example.obstaclecourse.Models.Dice;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
public class diceController {
    Dice dice = new Dice();

    @FXML
    private Text diceValues;

    public void roll(){
        dice.roll();

        // update the text in the dice view
        diceValues.setText(Integer.toString(dice.getValue()));
    }
}
