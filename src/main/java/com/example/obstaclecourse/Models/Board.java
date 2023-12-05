package com.example.obstaclecourse.Models;

import com.example.obstaclecourse.Controllers.ScoreBoardController;
import com.example.obstaclecourse.Utils.EventProducer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.*;

/**
 * Represents the game board for the obstacle course game.
 * The board consists of a grid of cells, and it tracks the positions of players and obstacles.
 */
public class Board extends EventProducer {
    private final Dice dice;
    private final int size;  // Board Size eg.8*8
    private final Cell[][] cells;

    private static List<int[]> Contrast;
    private static HashMap<String, Integer> CONTRAST_MAP = new HashMap<>();

    private ArrayList<Player> players = new ArrayList<>();

    private Player currentPlayer;

    private BooleanProperty gameWon = new SimpleBooleanProperty(false);

    // just like the player fires an event when its position changes,
    // the board should fire an event when the currently active player changes
    // in order for the '<PLAYER>'s turn' text to be updated
    private final PropertyChangeSupport support;

    /**
     * Constructs a new Board with specified size, dice, and players.
     *
     * @param size    The size of the board (e.g., 8x8).
     * @param dice    The dice used for the game.
     * @param players The list of players participating in the game.
     */
    public Board(int size, Dice dice, ArrayList<Player> players) {
        this.support = new PropertyChangeSupport(this);
        this.size = size;
        this.cells = new Cell[size][size];
        this.dice = dice;
        this.players = players;

        List<int[]> coordinatesList = new ArrayList<>();

        int value = 1;
        int rows = size;
        int cols = size;

        int top = 0;
        int bottom = rows - 1;
        int left = 0;
        int right = cols - 1;

        while (value <= rows * cols) {
            // Left to Right
            for (int i = left; i <= right && value <= rows * cols; i++) {
                int[] a = new int[2];
                a[0] = top;
                a[1] = i;
                coordinatesList.add(a);
                CONTRAST_MAP.put(Arrays.toString(a), value);
                value++;
            }
            top++;

            // Top to Bottom
            for (int i = top; i <= bottom && value <= rows * cols; i++) {
                int[] a = new int[2];
                a[0] = i;
                a[1] = right;
                coordinatesList.add(a);
                CONTRAST_MAP.put(Arrays.toString(a), value);
                value++;
            }
            right--;

            // Right to Left
            for (int i = right; i >= left && value <= rows * cols; i--) {
                int[] a = new int[2];
                a[0] = bottom;
                a[1] = i;
                coordinatesList.add(a);
                CONTRAST_MAP.put(Arrays.toString(a), value);
                value++;
            }
            bottom--;

            // Bottom to Top
            for (int i = bottom; i >= top && value <= rows * cols; i--) {
                int[] a = new int[2];
                a[0] = i;
                a[1] = left;
                coordinatesList.add(a);
                CONTRAST_MAP.put(Arrays.toString(a), value);
                value++;
            }
            left++;
        }
        this.Contrast = Collections.unmodifiableList(coordinatesList);

        this.currentPlayer = this.setInitialPlayer();
        initializeCells();
    }


    /**
     * The player to go first is determined by the dice roll
     * Simply roll the dice twice, and compare the values
     *
     * @return Player
     */
    public Player setInitialPlayer() {

        int playerOneDiceValue = this.dice.roll();
        int playerTwoDiceValue = this.dice.roll();

        // If the two values are the same, re-roll
        while (playerOneDiceValue == playerTwoDiceValue) {
            playerOneDiceValue = this.dice.roll();
            playerTwoDiceValue = this.dice.roll();
        }

        // if leftDie has a greater value, player 0 starts, otherwise, player 1 starts
        System.out.println("\n" + this.players.get(0).getName() + " has rolled " + playerOneDiceValue + ", " + this.players.get(1).getName() + " has rolled " + playerTwoDiceValue + "\n");

        // if leftDie has a greater value, player 0 starts, otherwise, player 1 starts
        this.currentPlayer = this.players.get(playerOneDiceValue > playerTwoDiceValue ? 0 : 1);

        System.out.println(this.currentPlayer.getName() + " will go first");
        return this.currentPlayer;
    }

    /**
     * Initializes the cells of the board and generates obstacles.
     */
    private void initializeCells() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }

/**
 * Randomly generates obstacles on the board.
 *
 * @param numObstacles The number of obstacles to generate.
 */
        initialiseObstacles(20);
    }

    public void initialiseObstacles(int numObstacles) {
        for (int i = 0; i < numObstacles; i++) {
            // Generate random coordinates
            int randomColumn = (int) (Math.random() * this.size);
            int randomRow = (int) (Math.random() * this.size);
            if (randomColumn == 0 && randomRow == 0) {
                i--;
                continue;
            }
            int size = Contrast.size();
            int[] ints = Contrast.get(size - 1);
            if (ints[0] == randomColumn && ints[1] == randomRow) {
                i--;
                continue;
            }

            // Generate a random number based on the number of obstacle types (randomly generate obstacles)
            int randomObstacle = (int) (Math.random() * Arrays.stream(Pit.Type.values()).count());
            // Add obstacles to the board
            Cell c = cells[randomColumn][randomRow];
            if (!c.hasObstacle()) {

                Pit p = new Pit(Pit.Type.values()[randomObstacle]);

                if (p.getType() == Pit.Type.SPIKE) {
                    // Handle spike pits
                    handleSpikePit(c, p);
                } else {
                    // Set a random obstacle
                    c.setObstacle(new Pit(Pit.Type.values()[randomObstacle]));
                }
            } else {
                i--;
            }
        }
    }

    /**
     * Handles the creation and placement of spike pit obstacles.
     *
     * @param c The cell where the spike pit starts.
     * @param p The pit object representing the spike pit.
     */
    public void handleSpikePit(Cell c, Pit p) {
        c.setObstacle(p);

        int x = c.getPosition()[0];
        int y = c.getPosition()[1] + 1;

        for (int j = 1; j < p.getLength(); j++) {
            // Check if the next cell is within bounds
            if (x < cells.length && y < cells[0].length) {
                // Set the next cell as a spike obstacle
                int size = Contrast.size();
                int[] ints = Contrast.get(size - 1);
                if (ints[0] == x && ints[1] == y) {
                    continue;
                }
                cells[x][y].setObstacle(new Pit(Pit.Type.SPIKE, 1));
            } else {
                // if the cell we tried isn't within bounds, to continue our spike trap:
                x++; // move down to the next row
                y = 0; // and start with its first cell

                // FIXME: I don't think there's a problem here, but I'm calling attention to it, in case I'm wrong.
                // If the next row is out of bounds, break the loop.
                // Even if we're on the last row/col, this is fine, we don't need to reset to 0.
                // It shouldn't impact the gameplay if a spike has more spikes than are represented on the screen in this case, since the player is at the end anyway?
                if (x >= cells.length) {
                    break;
                }

                // Set the first cell in the new row as a spike obstacle
                cells[x][y].setObstacle(new Pit(Pit.Type.SPIKE, 1));
            }

            // Move to the next cell in the same row
            y++;
        }
    }

    /**
     * Prints the current state of the board to the console.
     */
    public void printBoard() {
        int[] playerPosition = currentPlayer.getPosition();

        for (int i = 0; i < cells.length; i++) {
            Cell[] row = cells[i];
            System.out.println();
            for (int j = 0; j < row.length; j++) {
                Cell c = row[j];

                if (playerPosition[0] == i && playerPosition[1] == j) {
                    System.out.print("🤠");
                } else {
                    if (c.hasObstacle()) {
                        System.out.print(c.getObstacle().getSymbol());
                    } else {
                        System.out.print('⬛');
                    }
                }
            }
        }
    }



    /**
     * Moves a player a specified number of steps on the board.
     *
     * @param playerNumber The index of the player to move.
     * @param steps        The number of steps to move the player.
     * @param scoreBoard   The score board to update scores if necessary.
     * @return The moved player.
     * @throws IOException If an I/O error occurs.
     */
    public Player movePlayer(int playerNumber, int steps, ScoreBoard scoreBoard) throws IOException {
        Player player = players.get(playerNumber);

        if (player == null) {
            throw new IllegalArgumentException("Player not found!");
        }
        System.out.println("Y:" + player.getPosition()[0] + "    x:" + player.getPosition()[1]);

        int[] a = new int[2];
        a[0] = player.getPosition()[0];
        a[1] = player.getPosition()[1];
        Integer integer = CONTRAST_MAP.get(Arrays.toString(a));

        int newPosition = integer + steps - 1;

        if (newPosition >= 63) {
            System.out.println("Game over! " + currentPlayer.getName() + " wins");
            newPosition = 63;
            gameWon.set(true);
        }
        // Convert 1D newPosition to 2D newX and newY
        int newX = Contrast.get(newPosition)[0]; // Row number
        int newY = Contrast.get(newPosition)[1]; // Column number

        int[] newPosition2D = new int[2];
        newPosition2D[0] = newX;
        newPosition2D[1] = newY;

        Cell newCell = cells[newX][newY];
        if (newCell.hasObstacle()) {    // Use hasObstacle() method for readability
            // Decide whether to update the player's position based on applyEffect's outcome
            scoreBoard.deductScore(currentPlayer.getName(), 3);
            System.out.println("Player " + playerNumber + " has encountered a " + newCell.getObstacle().getType().toString() + " pit!");
            System.out.println("Effect:" + newCell.getObstacle().printEffect());

            newCell.getObstacle().applyEffect(player, size, cells);
            player.setScore(-3);
            // Decide whether to update the player's position based on the effect of applyEffect
        } else {
            // Update the player's position
            player.setPosition(newPosition2D);
            scoreBoard.addScore(currentPlayer.getName(), steps);
            player.setScore(-1);

        }

        this.printBoard();

        return player;
    }

    /**
     * Retrieves a specific cell from the board.
     *
     * @param x The x-coordinate of the cell.
     * @param y The y-coordinate of the cell.
     * @return The cell at the specified coordinates.
     */
    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    /**
     * Retrieves a player by their player number.
     *
     * @param playerNumber The index of the player.
     * @return The player with the specified index.
     */
    public Player getPlayer(int playerNumber) {
        return players.get(playerNumber);
    }


    /**
     * Represents a single cell on the game board.
     */
    public class Cell {
        private Obstacle obstacle;
        int xPos;
        int yPos;

        /**
         * Constructs a new Cell with specified coordinates.
         *
         * @param x The x-coordinate of the cell.
         * @param y The y-coordinate of the cell.
         */
        public Cell(int x, int y) {
            this.xPos = x;
            this.yPos = y;
        }

        public int[] getPosition() {
            int[] pos = new int[2];
            pos[0] = this.xPos;
            pos[1] = this.yPos;

            return pos;
        }

        public boolean hasObstacle() {
            return obstacle != null;
        }

        public Obstacle getObstacle() {
            return obstacle;
        }

        public void setObstacle(Obstacle obstacle) {
            this.obstacle = obstacle;
        }
    }

    /**
     * Retrieves the list of players on the board.
     *
     * @return The list of players.
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Determines the next player to take their turn.
     *
     * @return The next player.
     */
    public Player nextPlayer() {
        // if this function is being called for the first time, we need to set the current player
        if (this.currentPlayer == null) {
            this.currentPlayer = this.setInitialPlayer();
            return this.currentPlayer;
        }


        int currentPlayerIndex = this.currentPlayer.getIndex();
        int nextPlayerIndex = currentPlayerIndex + 1;

        // If we're at the end of the players array, loop back to the start
        if (nextPlayerIndex >= this.players.size()) {
            nextPlayerIndex = 0;
        }

        // keep a reference to the old player for comparison
        Player oldPlayer = this.currentPlayer;

        this.currentPlayer = this.players.get(nextPlayerIndex);

        System.out.println("\n" + this.currentPlayer.getName() + "'s turn");

        return this.currentPlayer;
    }

    // return cells
    public Cell[][] getCells() {
        return cells;
    }


    /**
     * Provides a property to observe whether the game has been won.
     *
     * @return The game won property.
     */
    public BooleanProperty gameWonProperty() {
        return gameWon;
    }
}
