package com.example.obstaclecourse.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;


/**
 * Controller class for the HelloView FXML.
 * Manages actions related to the welcome text label.
 */
public class HelloController {
    @FXML
    private Label welcomeText;

    /**
     * Handles the button click event by updating the welcome text.
     * Changes the text to "Welcome to JavaFX Application!" when the button is clicked.
     */
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}