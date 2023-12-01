package com.example.obstaclecourse.Controllers;

import com.example.obstaclecourse.Models.Player;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class PlayerController implements PropertyChangeListener {
    @FXML
    private ImageView player;

    private Player playerModel;

    public void setPlayerModel(Player playerModel) {
        this.playerModel = playerModel;
        this.playerModel.addPropertyChangeListener(this);
    }



    public void movePlayerForward() {
        GridPane gridPane = (GridPane) player.getParent();

      // int currentColumn = GridPane.getColumnIndex(player);
      // int currentRow = GridPane.getRowIndex(player);

       GridPane.setColumnIndex(player, playerModel.getPosition()[1]);
       GridPane.setRowIndex(player, playerModel.getPosition()[0]);

       // Bring the player to the top layer, to make sure it's visible
       player.toFront();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("Player position changed, moving player in UI");
        if ("playerPosition".equals(evt.getPropertyName())) {
            // Player position has changed, move player
            movePlayerForward();
        }
    }


}
