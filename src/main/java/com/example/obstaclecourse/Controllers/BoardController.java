package com.example.obstaclecourse.Controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class BoardController {
    public ImageView player;

    public PlayerController playerController;


    @FXML
    private void initialize() {
        playerController.movePlayerForward();
    }
}
