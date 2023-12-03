package com.example.obstaclecourse.Controllers;

import com.example.obstaclecourse.Models.Player;
import com.example.obstaclecourse.Models.ScoreBoard;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.util.List;

public class ScoreBoardController {
    @FXML
    private Text playerOneDetails;
    @FXML
    private Text playerTwoDetails;

    private ScoreBoard scoreBoard;

    public void setScoreBoard(ScoreBoard scoreBoard) {
        this.scoreBoard = scoreBoard;
    }

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
