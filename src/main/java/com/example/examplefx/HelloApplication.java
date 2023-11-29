package com.example.examplefx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.geometry.Pos;

public class HelloApplication extends Application {
//    int attempts = 0;
//    Text diceMessage, hasWon, attemptsText;
//    public void rollDice() {
//        int[] dice = new int[2];
//        dice[0] = (int)(Math.random() * 6 + 1);
//        dice[1] = (int)(Math.random() * 6 + 1);
//
//        diceMessage.setText("Dice: " + dice[0] + " " + dice[1]);
//
//        if(dice[0] == dice[1]) {
//            hasWon.setText("Current status: WIN");
//        }
//        else{
//            hasWon.setText("Current status: LOSE: try again!");
//        }
//
//        attempts++;
//        attemptsText.setText("Attempts: " + attempts);
//    }

//    @Override
//    public void start(Stage primaryStage) {
//
//        try {
//            Text message = new Text();
//            Button bt = new Button("Roll dice");
//
//            attemptsText = new Text("Attempts: 0");
//            diceMessage = new Text("Dice: ");
//            hasWon = new Text("Current status: ");
//
//            bt.setOnAction((ActionEvent event) -> {
//                rollDice();
//            });
//
//            VBox root = new VBox(10, bt, message, diceMessage, hasWon, attemptsText);
//            root.setAlignment(Pos.CENTER);
//            Scene scene = new Scene(root, 200, 250);
//
//            primaryStage.setTitle("Hello World FX");
//            primaryStage.setScene(scene);
//            primaryStage.show();
//
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        primaryStage.setTitle("Hello World");
        //Remove the 300, 275!
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}