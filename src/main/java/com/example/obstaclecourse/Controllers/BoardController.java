package com.example.obstaclecourse.Controllers;

import com.example.obstaclecourse.Models.*;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class BoardController implements PropertyChangeListener {
    public ImageView player;
    public PlayerController playerController;
    public VBox diceComponent;

    private Board board;
    private ScoreBoard scoreBoard;
    private ScoreBoardController scoreBoardController;
    private ArrayList<Player> players = new ArrayList<>();

    @FXML
    private GridPane gameBoardGridPane;


    // need to receive user input and pass it in here to create the players
    public ArrayList<Player> addPlayers() {
        Scanner in = new Scanner(System.in);

        System.out.println("How many players are there? You need at least 2 players to play.");

        int numPlayers = in.nextInt();

        while(numPlayers < 2){
            System.out.println("You need at least 2 players to play. Please enter a valid number of players.");
            numPlayers = in.nextInt();
        }

        for (int i = 0; i < numPlayers; i++) {
            System.out.println("What is the name of player " + (i + 1) + "?");
            String name = in.next();
            Player player = new Player(name, i);
            this.players.add(player);
        }

        System.out.print("Welcome to the game");

        // When it gets to the last player, print "and" before their name, instead of a comma
        for (int i = 0; i < this.players.size(); i++) {
            if (i == this.players.size() - 1) {
                System.out.print(" and " + this.players.get(i).getName());
            } else {
                System.out.print(", " + this.players.get(i).getName());
            }
        }

        return players;
    }


    public void setScoreBoardController(ScoreBoardController controller) {
        this.scoreBoardController = controller;
        this.scoreBoardController.setScoreBoard(this.scoreBoard); // 设置ScoreBoard
    }
    @FXML
    private void initialize() {

        Dice dice = Dice.getInstance();
        dice.addPropertyChangeListener(this);

        this.players = addPlayers();

        // both the 'virtual' board and the board in the UI represent player's position as an array of two integers
        // the first integer represents the row, the second integer represents the column
        this.board = new Board(8, Dice.getInstance(), this.players);
        playerController.setPlayerModel(this.board.nextPlayer());
        setupObstacles();
        this.scoreBoard = new ScoreBoard(new ArrayList<Player>(), 2);

        // 1. player rolls the dice, dice values are updated
        // 2. board.movePlayer updates player position
        // 3. pull those values out of the player, and use them to move the player in the UI

        this.board.gameWonProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                showWinningAlert();
            }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // adding this check in case the propertyChange is fired before the board is initialized
        if(this.board != null) {
            if ("diceValue".equals(evt.getPropertyName())) {
                System.out.println("Dice value changed, moving player in virtual board");
                // Dice value has changed, move player

                // FIXME: The player is hard-coded here. It should alternate.
                try {
                    this.board.movePlayer(0, Dice.getInstance().getValue(), this.scoreBoard);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                scoreBoardController.updateScoreDisplay();
            }
        }
    }


    private void setupObstacles() {
        // Loop through the board to set up obstacles
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Board.Cell cell = board.getCell(row, col);
                if (cell.hasObstacle()) {
                    Obstacle obstacle = cell.getObstacle();
                    // Create an ImageView for the obstacle
                    ImageView obstacleView = createObstacleImageView(obstacle);
                    // Set margins and alignment for the obstacleView
                    GridPane.setMargin(obstacleView, new Insets(0, 0, 0, 5));
                    GridPane.setHalignment(obstacleView, HPos.CENTER);
                    GridPane.setValignment(obstacleView, VPos.CENTER);
                    gameBoardGridPane.add(obstacleView, col, row);
                }
            }
        }
    }

    private ImageView createObstacleImageView(Obstacle obstacle) {
        // Method to create an ImageView for the obstacle
        Image image = new Image(getClass().getResourceAsStream("/com/example/obstaclecourse/images/" + obstacle.getSymbol() + ".png"));
        ImageView obstacleView = new ImageView(image);
        obstacleView.setFitHeight(35);
        obstacleView.setFitWidth(35);
        obstacleView.setPreserveRatio(true);
        return obstacleView;
    }

        private void showWinningAlert() {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText(null);
            alert.setContentText("You win！");
            alert.showAndWait();
        }


}
