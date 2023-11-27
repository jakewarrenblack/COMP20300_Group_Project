import java.sql.SQLOutput;
import java.util.*;

public class Board {
    private final Dice dice;
    private final int size;  // Board Size eg.10*10
    private final Cell[][] cells;

    private ArrayList<Player> players = new ArrayList<>();

    private Player currentPlayer;

    public Board(int size, Dice dice) {
        this.size = size;
        this.cells = new Cell[size][size];  // Stores all the cells on the board
        initializeCells();
        this.players = this.addPlayers();
        this.dice = dice;

        this.currentPlayer = this.setInitialPlayer();
    }

    /**
     * The player to go first is determined by the dice roll
     * Simply roll the dice twice, and compare the values
     * @return Player
     */
    public Player setInitialPlayer(){

        int playerOneDiceValue = this.dice.roll();
        int playerTwoDiceValue = this.dice.roll();

        // If the two values are the same, re-roll
        while(playerOneDiceValue == playerTwoDiceValue){
            playerOneDiceValue = this.dice.roll();
            playerTwoDiceValue = this.dice.roll();
        }

        // if leftDie has a greater value, player 0 starts, otherwise, player 1 starts
        System.out.println("\n" + this.players.get(0).getName()+" has rolled "+playerOneDiceValue+", "+this.players.get(1).getName()+" has rolled "+playerTwoDiceValue + "\n");

        // if leftDie has a greater value, player 0 starts, otherwise, player 1 starts
        this.currentPlayer = this.players.get(playerOneDiceValue > playerTwoDiceValue ? 0 : 1);

        System.out.println(this.currentPlayer.getName()+" will go first");
        return this.currentPlayer;
    }

    public ArrayList<Player> addPlayers (){
        Scanner in = new Scanner(System.in);

        System.out.println("How many players are there? You need at least 2 players to play.");

        int numPlayers = in.nextInt();

        while(numPlayers < 2){
            System.out.println("You need at least 2 players to play. Please enter a valid number of players.");
            numPlayers = in.nextInt();
        }

        for (int i = 0; i < numPlayers; i++) {
            System.out.println("What is the name of player " + (i+1) + "?");
            String name = in.next();
            Player player = new Player(name, i);
            this.players.add(player);
        }




        System.out.print("Welcome to the game");

        // When it gets to the last player, print "and" before their name, instead of a comma
        for(int i=0; i<this.players.size(); i++){
            if(i == this.players.size() - 1){
                System.out.print(" and " + this.players.get(i).getName());
            }
            else{
                System.out.print(", " + this.players.get(i).getName());
            }
        }



        return players;
    }


    private void initializeCells() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }

        // at this point, we've got our board of empty cells initialised
        // we can take a random selection of cells and decide they'll be obstacles
        initialiseObstacles(20);
    }

    public void initialiseObstacles(int numObstacles){
        for(int i=0; i<numObstacles; i++){
            int randomColumn= (int)(Math.random() * this.size);
            int randomRow = (int)(Math.random() * this.size);

            // Random number based on the amount of Obstacle Types available
            int randomObstacle = (int)(Math.random() * Arrays.stream(Pit.Type.values()).count());

            Cell c = cells[randomColumn][randomRow];
            if (!c.hasObstacle()) {
                // add an obstacle
                Pit p = new Pit(Pit.Type.values()[randomObstacle]);

                if (p.getType() == Pit.Type.SPIKE) {

                    handleSpikePit(c, p);

                } else {
                    // Set a random obstacle
                    c.setObstacle(new Pit(Pit.Type.values()[randomObstacle]));
                }
            }

            else{
                i--; // Otherwise, decrement the loop to try again. We'll get another random number so a different row/cell.
            }
        }
    }


    /**
     * <p>
     * Spike pits are an outlier among the pits, since they can take up multiple cells
     * <p>
     * So, if we're adding a spike pit to a cell C, we need to make sure we account for the pit's length and also set n cells after C to spikes
     */
    public void handleSpikePit(Cell c, Pit p){
        c.setObstacle(p);

        int x = c.getPosition()[0];
        int y = c.getPosition()[1] + 1;

        for (int j = 1; j < p.getLength(); j++) {
            // Check if the next cell is within bounds
            if (x < cells.length && y < cells[0].length) {
                // Set the next cell as a spike obstacle
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

    public void printBoard(){
        int[] playerPosition = currentPlayer.getPosition();
        for (int i = 0; i < cells.length; i++) {
            Cell[] row = cells[i];
            System.out.println();
            for (int j = 0; j < row.length; j++) {
                Cell c = row[j];

                if(playerPosition[0] == i && playerPosition[1] == j){
                    System.out.print("🤠");
                }
                else{
                    if (c.hasObstacle()) {
                        System.out.print(c.getObstacle().getSymbol());
                    } else {
                        System.out.print('⬛');
                    }
                }
            }
        }
    }


    public void addPlayer(Player player) {
        players.add(player);
    }


    public void movePlayer(int playerNumber, int steps) {
        Player player = players.get(playerNumber);
        if (player == null) {
            throw new IllegalArgumentException("Player not found!");
        }

        int currentPosition = player.getPosition()[0];
        int newPosition = currentPosition + steps;

        // Ensure that newPosition does not exceed the boundaries of the game board
        if (newPosition < 0) {
            newPosition = 0;   // Players cannot move to positions less than 0
        } else if (newPosition >= size * size) {
            newPosition = size * size - 1;   // Players cannot move beyond the maximum position on the game board
        }

        // Convert 1D newPosition to 2D newX and newY
        int newX = newPosition / size;   // Row number
        int newY = newPosition % size;   // Column number

        int[] newPosition2D = new int[2];
        newPosition2D[0] = newX;
        newPosition2D[1] = newY;

        Cell newCell = cells[newX][newY];
        if (newCell.hasObstacle()) {    // Use hasObstacle() method for readability
            newCell.getObstacle().applyEffect(player);
            // Decide whether to update the player's position based on the effect of applyEffect
        } else {
            // Update the player's position
            player.setPosition(newPosition2D);
        }

        this.printBoard();
    }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public Player getPlayer(int playerNumber) {
        return players.get(playerNumber);
    }

    public void removePlayer(String playerName) {
        players.remove(playerName);
    }

    private class Cell {
        private Obstacle obstacle;
        int xPos;
        int yPos;

        public Cell(int x, int y){
            this.xPos = x;
            this.yPos = y;
        }

        public int[] getPosition(){
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

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player nextPlayer(){
        // if this function is being called for the first time, we need to set the current player
        if(this.currentPlayer == null){
            this.currentPlayer = this.setInitialPlayer();
            return this.currentPlayer;
        }

        int currentPlayerIndex = this.currentPlayer.getIndex();
        int nextPlayerIndex = currentPlayerIndex + 1;

        // If we're at the end of the players array, loop back to the start
        if(nextPlayerIndex >= this.players.size()){
            nextPlayerIndex = 0;
        }

        this.currentPlayer = this.players.get(nextPlayerIndex);

        System.out.println("\n" + this.currentPlayer.getName() + "'s turn");

        return this.currentPlayer;
    }

}

    
