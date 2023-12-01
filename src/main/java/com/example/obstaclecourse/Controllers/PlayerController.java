package com.example.obstaclecourse.Controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class PlayerController {
    @FXML
    private ImageView player;

    public void movePlayerForward() {
        GridPane gridPane = (GridPane) player.getParent();

       int currentColumn = GridPane.getColumnIndex(player);
       GridPane.setColumnIndex(player, currentColumn + 1);

       // Bring the player to the top layer, to make sure it's visible
       player.toFront();
    }
}
