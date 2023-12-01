package com.example.obstaclecourse;

import com.example.obstaclecourse.Models.Board;
import com.example.obstaclecourse.Models.Dice;
import com.example.obstaclecourse.Models.Player;
import com.example.obstaclecourse.Models.ScoreBoard;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ObstacleCourse extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ObstacleCourse.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Obstacle Course");
        stage.setScene(scene);

        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
//
//        Scanner s = new Scanner(System.in);
//        enum GameState {ACTIVE, LOST, WON};
//        GameState gameState = GameState.ACTIVE; // Initialize game state to active
//
//        Dice dice = Dice.getInstance();
//
//        System.out.println();
//        System.out.println("Press any key to roll the dice, enter 'exit' to quit the game");
//
//        ScoreBoard scoreBoard = new ScoreBoard(new ArrayList<Player>(), 2);
//
//        //Board board = new Board(10, dice);
//
////        for(Player p: board.getPlayers()) {
////            scoreBoard.addPlayerScore(p);
////        }
////
////        board.printBoard();
//
//        System.out.println("\nMake a move:");
//
//        while (gameState == GameState.ACTIVE) {
//            String input = s.nextLine();
//
//            if ("exit".equalsIgnoreCase(input)) {
//                break;
//            }
//
//            int move = dice.roll();
//            System.out.println("Rolled: " + move);
//
//
////
////            if (move != 0) {
////                // After player has made a move, the board switches to the next player
////                // if nextPlayer is being called before initial player has even been set, the initial player is returned instead of incrementing the index
////                board.movePlayer(board.nextPlayer().getIndex(), move, scoreBoard);
////            }
//
//            // Print the current scores
//            scoreBoard.printScores();
//
//            System.out.println("\nMake a move:");
//
//        }

        //s.close();
    }
}