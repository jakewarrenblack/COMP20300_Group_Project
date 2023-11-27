import java.util.*;


public class Board {
    private final Dice dice;
    private final int size;  // Board Size eg.10*10
    private final Cell[][] cells;

    private static List<int[]> Contrast;
    private static HashMap<String, Integer> CONTRAST_MAP = new HashMap<>();

    private ArrayList<Player> players = new ArrayList<>();

    private Player currentPlayer;

    public Board(int size, Dice dice) {
        this.size = size;
        this.cells = new Cell[size][size];
        initializeCells();
        this.dice = dice;

        this.players = new ArrayList<>();

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


    private void initializeCells() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }

        // Generate obstacles
        initialiseObstacles(20);
    }

    public void initialiseObstacles(int numObstacles) {
        for (int i = 0; i < numObstacles; i++) {
            // Generate random coordinates
            int randomColumn = (int) (Math.random() * this.size);
            int randomRow = (int) (Math.random() * this.size);
            if (randomColumn == 0 && randomRow == 0) {
                i--;
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


    public void handleSpikePit(Cell c, Pit p) {
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

    public void printBoard() {
        int[] playerPosition = getPlayer(0).getPosition();

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

    public void addPlayer(Player player) {
        players.add(player);
    }


    public void movePlayer(int playerNumber, int steps, ScoreBoard scoreBoard) {
        Player player = players.get(playerNumber);

        if (player == null) {
            throw new IllegalArgumentException("Player not found!");
        }
        System.out.println("Y:" + player.getPosition()[0] + "    x:" + player.getPosition()[1]);
        int[] a = new int[2];
        a[0] = player.getPosition()[0];
        a[1] = player.getPosition()[1];
        Integer integer = CONTRAST_MAP.get(Arrays.toString(a));
        System.out.println(integer);
        int newPosition = integer + steps - 1;

        if (newPosition >= 99) {
            System.out.println("Game over! " + currentPlayer.getName() + " wins");
            newPosition = 99;
        }
        // Convert 1D newPosition to 2D newX and newY
        int newX = Contrast.get(newPosition)[0]; // Row number
        int newY = Contrast.get(newPosition)[1]; // Column number

        int[] newPosition2D = new int[2];
        newPosition2D[0] = newX;
        newPosition2D[1] = newY;

        Cell newCell = cells[newX][newY];
        if (newCell.hasObstacle()) {
            newCell.getObstacle().applyEffect(player, size);
            // Decide whether to update the player's position based on applyEffect's outcome
            if ((player.getScore() - 3) < 0) {
                scoreBoard.deductScore(currentPlayer.getName(), player.getScore());
            }
            scoreBoard.deductScore(currentPlayer.getName(), 3);
        } else {
            // Update the player's position
            player.setPosition(newPosition2D);
            scoreBoard.addScore(currentPlayer.getName(), steps);
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


    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player nextPlayer() {
        int currentPlayerIndex = this.currentPlayer.getIndex();
        int nextPlayerIndex = currentPlayerIndex + 1;

        // If we're at the end of the players array, loop back to the start
        if (nextPlayerIndex >= this.players.size()) {
            nextPlayerIndex = 0;
        }

        this.currentPlayer = this.players.get(nextPlayerIndex);

        System.out.println("\n" + this.currentPlayer.getName() + "'s turn");

        return this.currentPlayer;
    }

}
