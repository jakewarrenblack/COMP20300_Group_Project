package com.example.obstaclecourse;

import com.example.obstaclecourse.Controllers.BoardController;
import com.example.obstaclecourse.Controllers.ScoreBoardController;
import com.example.obstaclecourse.Models.Board;
import com.example.obstaclecourse.Models.Dice;
import com.example.obstaclecourse.Models.Player;
import com.example.obstaclecourse.Models.ScoreBoard;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.scene.image.ImageView;

/**
 * Main class for the Obstacle Course game application.
 * Manages the UI and initializes the game components.
 */
public class ObstacleCourse extends Application {

    /**
     * Starts the Obstacle Course game application.
     * @param stage The primary stage for the application.
     * @throws IOException If there is an error loading the FXML files.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ObstacleCourse.class.getResource("hello-view.fxml"));


        FXMLLoader boardLoader = new FXMLLoader(ObstacleCourse.class.getResource("hello-view.fxml"));
        Parent boardRoot = boardLoader.load();
        BoardController boardController = boardLoader.getController();




        FXMLLoader scoreBoardLoader = new FXMLLoader(ObstacleCourse.class.getResource("score-view.fxml"));
        Parent scoreBoardRoot = scoreBoardLoader.load();
        ScoreBoardController scoreBoardController = scoreBoardLoader.getController();

        boardController.setScoreBoardController(scoreBoardController);


        // I believe this should be okay, because right now we're instantiating Player objects from the console
        // but if we were to instantiate them from the UI, this would break - I THINK!
        ArrayList<ImageView> playerViews = boardController.initializePlayers();

        // Add player views to the game scene
        for (ImageView playerView : playerViews) {
            HBox gameScene = (HBox) boardRoot;
            VBox vBox = (VBox) gameScene.getChildren().get(0); // Root component is a HBox, with a VBox inside it
            GridPane gridPane = (GridPane) vBox.getChildren().get(0); // Within that VBox is a Gridpane

            gridPane.getChildren().add(playerView);
            playerView.toFront();
        }

        //Scene scene = new Scene(fxmlLoader.load());
        Scene scene = new Scene(boardRoot);
        stage.setTitle("Obstacle Course");
        stage.setScene(scene);

        stage.setResizable(false);
        stage.show();
    }

    /**
     * Main method to launch the application.
     */
    public static void main(String[] args) {
        launch();
    }
}