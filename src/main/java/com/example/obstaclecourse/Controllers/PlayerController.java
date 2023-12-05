package com.example.obstaclecourse.Controllers;

import com.example.obstaclecourse.Models.Player;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Controller class for managing individual player UI elements.
 * Handles the movement and display of an individual player on the game board.
 */
public class PlayerController implements PropertyChangeListener {
    @FXML
    private ImageView player;

    @FXML
    private Text playerLabel;

    private Player playerModel;

//    public void setPlayerLabel(String playerLabel) {
//        this.playerLabel.setText(playerLabel);
//    }

    /**
     * Sets the player model associated with this controller.
     * Adds this controller as a listener to the player model.
     * @param playerModel The player model.
     */
    public void setPlayerModel(Player playerModel) {
        this.playerModel = playerModel;
        this.playerModel.addPropertyChangeListener(this);
    }


    /**
     * Moves the player's image view to a new position on the game board.
     * @param playerView The image view of the player.
     */
    public void movePlayerForward(ImageView playerView) {
        GridPane gridPane = (GridPane) player.getParent();

      // int currentColumn = GridPane.getColumnIndex(player);
      // int currentRow = GridPane.getRowIndex(player);

       GridPane.setColumnIndex(player, playerModel.getPosition()[1]);
       GridPane.setRowIndex(player, playerModel.getPosition()[0]);

       // Bring the player to the top layer, to make sure it's visible
       player.toFront();
    }

    /**
     * Handles property change events fired by the player model.
     * Moves the player's image view when the player position changes in the model.
     * @param evt The property change event.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("Player position changed, moving player in UI");
        Player currentlyMovingPlayer = (Player) evt.getSource();

        if ("playerPosition".equals(evt.getPropertyName())) {
            // get a hold of the player's ImageView

            // have to make sure these are actually pulled from the fxml and set
            ImageView currentlyMovingPlayerView = currentlyMovingPlayer.getImageView();

            // Player position has changed, move player (In the UI, not in the Board model)
            movePlayerForward(currentlyMovingPlayerView);
        }
    }


}
