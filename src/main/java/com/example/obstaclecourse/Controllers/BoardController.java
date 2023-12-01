package com.example.obstaclecourse.Controllers;

import com.example.obstaclecourse.Models.Board;
import com.example.obstaclecourse.Models.Dice;
import com.example.obstaclecourse.Models.Player;
import com.example.obstaclecourse.Models.ScoreBoard;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Scanner;

public class BoardController implements PropertyChangeListener {
    public ImageView player;
    public PlayerController playerController;
    public VBox diceComponent;

    private Board board;
    private ScoreBoard scoreBoard;

    private ArrayList<Player> players = new ArrayList<>();


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


    @FXML
    private void initialize() {
        Dice dice = Dice.getInstance();
        dice.addPropertyChangeListener(this);

        this.players = addPlayers();

        // both the 'virtual' board and the board in the UI represent player's position as an array of two integers
        // the first integer represents the row, the second integer represents the column
        this.board = new Board(8, Dice.getInstance(), this.players);

        playerController.setPlayerModel(this.board.nextPlayer());

        this.scoreBoard = new ScoreBoard(new ArrayList<Player>(), 2);

        // 1. player rolls the dice, dice values are updated
        // 2. board.movePlayer updates player position
        // 3. pull those values out of the player, and use them to move the player in the UI
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // adding this check in case the propertyChange is fired before the board is initialized
        if(this.board != null) {
            if ("diceValue".equals(evt.getPropertyName())) {
                System.out.println("Dice value changed, moving player in virtual board");
                // Dice value has changed, move player

                // FIXME: The player is hard-coded here. It should alternate.
                this.board.movePlayer(0, Dice.getInstance().getValue(), this.scoreBoard);
            }
        }
    }
}
