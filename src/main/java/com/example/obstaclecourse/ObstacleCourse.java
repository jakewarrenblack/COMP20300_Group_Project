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
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ObstacleCourse extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ObstacleCourse.class.getResource("hello-view.fxml"));

        // 加载 BoardController 的 FXML
        FXMLLoader boardLoader = new FXMLLoader(ObstacleCourse.class.getResource("hello-view.fxml"));
        Parent boardRoot = boardLoader.load();
        BoardController boardController = boardLoader.getController();

        FXMLLoader scoreBoardLoader = new FXMLLoader(ObstacleCourse.class.getResource("score-view.fxml"));
        Parent scoreBoardRoot = scoreBoardLoader.load();
        ScoreBoardController scoreBoardController = scoreBoardLoader.getController();

        boardController.setScoreBoardController(scoreBoardController);

        //Scene scene = new Scene(fxmlLoader.load());
        Scene scene = new Scene(boardRoot);
        stage.setTitle("Obstacle Course");
        stage.setScene(scene);

        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}