package com.example.obstaclecourse.Controllers;

import com.example.obstaclecourse.Models.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * Controller class managing the game board and player actions.
 */
public class BoardController implements PropertyChangeListener {
    public Text PlayerOneDetails;
    public Text PlayerTwoDetails;
    private ArrayList<PlayerController> playerControllers = new ArrayList<>();



    public ImageView player;
    public PlayerController playerController;
    public VBox diceComponent;

    private Board board;
    private ScoreBoard scoreBoard;
    private ScoreBoardController scoreBoardController;
    private ArrayList<Player> players = new ArrayList<>();


    @FXML
    private GridPane gameBoardGridPane;

    @FXML
    private Text currentPlayer;

    /**
     * Retrieves the number of players and their names from the console, creating player objects and adding them to the player list.
     * Prompts the user to enter a valid number of players if the input is less than 2.
     * Prints a welcome message on the console, including the names of the players.
     *
     * @return The list of created players
     */
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

            if (i==0){
                PlayerOneDetails.setText(name+":"+0);
            }else {
                PlayerTwoDetails.setText(name+":"+0);
            }
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

    /**
     * Sets the scoreboard controller.
     *
     * @param controller The scoreboard controller to be set
     */
    public void setScoreBoardController(ScoreBoardController controller) {
        this.scoreBoardController = controller;
        this.scoreBoardController.setScoreBoard(this.scoreBoard);
    }

    /**
     * Initializes the controller and the game board.
     * Called after the FXML file has been loaded.
     */
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

        // add the listener after the board is initialized, so we don't get a null pointer exception
        System.out.println("Adding BoardController as PropertyChangeListener to Board");
        this.board.addPropertyChangeListener(this);

        this.board.gameWonProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                showWinningAlert();
            }
        });
    }

    /**
     * Handles property change events triggered by the game model.
     * Updates the UI based on changes in the game state.
     * @param evt The property change event.
     */

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // adding this check in case the propertyChange is fired before the board is initialized
        if(this.board != null) {
            if ("diceValue".equals(evt.getPropertyName())) {
                System.out.println("Dice value changed, moving player in virtual board");
                // Dice value has changed, move player in the virtual board
                try {
                    Player nextPlayer = this.board.nextPlayer();
                    currentPlayer.setText(nextPlayer.getName().toUpperCase() + "'S TURN");

                    Player player = this.board.movePlayer(nextPlayer.getIndex(), Dice.getInstance().getValue(), this.scoreBoard);
                    int index = PlayerOneDetails.getText().indexOf(":");
                    String PlayerOneName = PlayerOneDetails.getText().substring(0, index); // 获取 "o" 前面的部分
                    int PlayerOneScore = Integer.parseInt(PlayerOneDetails.getText().substring(index + 1));
                    int index2 = PlayerTwoDetails.getText().indexOf(":");
                    String PlayerTwoName = PlayerTwoDetails.getText().substring(0, index2); // 获取 "o" 前面的部分
                    int PlayerTwoScore = Integer.parseInt(PlayerTwoDetails.getText().substring(index2 + 1));
                    if (player.getName().equals(PlayerOneName)){
                        if (player.getScore()==-1){
                            int i=PlayerOneScore + Dice.getInstance().getValue();
                            System.out.println("one"+i);
                            PlayerOneDetails.setText(player.getName()+":"+i);
                        }
                        if(player.getScore()==-3){
                            if (PlayerOneScore-3>0){
                                int i=PlayerOneScore - 3;
                                System.out.println("one"+i);
                                PlayerOneDetails.setText(player.getName()+":"+i);
                            }else {
                                int i=0;
                                PlayerOneDetails.setText(player.getName()+":"+i);
                            }
                        }
                    }
                    if (player.getName().equals(PlayerTwoName)){
                        if (player.getScore()==-1){
                            int i=PlayerTwoScore +Dice.getInstance().getValue();
                            System.out.println("one"+i);
                            PlayerTwoDetails.setText(player.getName()+":"+i);
                        }
                        if(player.getScore()==-3){
                            if (PlayerTwoScore-3>0){
                                int i=PlayerTwoScore - 3;
                                System.out.println("two"+i);
                                PlayerTwoDetails.setText(player.getName()+":"+i);
                            }else {
                                int i=0;
                                PlayerTwoDetails.setText(player.getName()+":"+i);
                            }
                        }

                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * Adds player views to the game board.
     * Initializes and sets up player controllers for each player.
     * @return An ArrayList containing ImageViews for each player.
     */
     public ArrayList<ImageView> initializePlayers(){
            ArrayList<ImageView> playerViews = new ArrayList<>();
            for (Player player: this.players) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/obstaclecourse/player-view.fxml"));
                    ImageView playerView = loader.load();
                    PlayerController playerController = loader.getController();

                    playerController.setPlayerModel(player);

                    //playerController.setPlayerLabel(player.getName());

                    // Set the ImageView in the Player model
                    player.setImageView(playerView);

                    playerControllers.add(playerController);

                    // Add the player view to the list
                    playerViews.add(playerView);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return playerViews;
        }

    /**
     * Sets up obstacles on the game board based on their presence in each cell of the board.
     * Loops through the board to identify cells containing obstacles and adds their ImageView representations to the game board UI.
     */
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

    /**
     * Creates an ImageView representation for a given obstacle.
     *
     * @param obstacle The obstacle for which an ImageView is to be created
     * @return The ImageView representing the obstacle
     */
    private ImageView createObstacleImageView(Obstacle obstacle) {
        // Method to create an ImageView for the obstacle
        Image image = new Image(getClass().getResourceAsStream("/com/example/obstaclecourse/images/" + obstacle.getSymbol() + ".png"));
        ImageView obstacleView = new ImageView(image);
        obstacleView.setFitHeight(35);
        obstacleView.setFitWidth(35);
        obstacleView.setPreserveRatio(true);
        return obstacleView;
    }

    /**
     * Displays an alert indicating the game has been won.
     * Shows a dialog box with a success message when the game is won.
     */
    private void showWinningAlert() {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText(null);
            alert.setContentText("You win！");
            alert.showAndWait();
        }



}
