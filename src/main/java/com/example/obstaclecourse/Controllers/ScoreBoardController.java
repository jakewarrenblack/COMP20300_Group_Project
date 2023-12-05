package com.example.obstaclecourse.Controllers;

import com.example.obstaclecourse.Models.Player;
import com.example.obstaclecourse.Models.ScoreBoard;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.util.List;

/**
 * Controller class for the ScoreBoard view.
 * Manages the display of player scores in the UI.
 */
public class ScoreBoardController {
    @FXML
    private Text playerOneDetails;
    @FXML
    private Text playerTwoDetails;

    private ScoreBoard scoreBoard;

    /**
     * Sets the ScoreBoard associated with this controller.
     * @param scoreBoard The ScoreBoard object.
     */
    public void setScoreBoard(ScoreBoard scoreBoard) {
        this.scoreBoard = scoreBoard;
    }

    /**
     * Updates the score display in the UI with the current player scores.
     * Retrieves player scores from the ScoreBoard and displays them.
     */
    public void updateScoreDisplay() {
        if (scoreBoard != null) {
            List<Player> players = scoreBoard.getPlayerScores();
            if (players.size() >= 1) {
                Player playerOne = players.get(0);
                playerOneDetails.setText(playerOne.getName() + ": " + playerOne.getScore());
            }
            if (players.size() >= 2) {
                Player playerTwo = players.get(1);
                playerTwoDetails.setText(playerTwo.getName() + ": " + playerTwo.getScore());
            }
        }
    }
}










//package com.example.obstaclecourse.Controllers;
//
//import javafx.fxml.FXML;
//import javafx.scene.text.Text;
//
//import java.beans.JavaBean;
//
//@JavaBean
//
//public class ScoreBoardController {
//    @FXML
//    private Text PlayerOneScore;
//    @FXML
//    private Text PlayerTwoScore;
//
//    public void upScore(String playerName){
//        if (playerName.equals("PlayerOneScore")) {
//            PlayerOneScore.setText(String.valueOf(1));
//        }
//        if (playerName.equals("PlayerTwoScore")) {
//            PlayerTwoScore.setText(String.valueOf(1));
//        }
//    }
//
//}
