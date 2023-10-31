import java.util.HashMap;
public class Board {
    private final int size;  // Board Size eg.10*10
    private Cell[][] cells;
    private HashMap<String, Player> playerMap;  //  Use HashMap to store players, key is players' name
    
    public Board(int size) {
        this.size = size;
        this.cells = new Cell[size][size];  // Stores all the cells on the board
        initializeCells();
        this.playerMap = new HashMap<>();
    }

    private void initializeCells() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j] = new Cell();
            }
        }

      // Here, can also initialize the obstacles

    }

    public void addPlayer(Player player) {
        playerMap.put(player.getName(), player);
    }

    public void movePlayer(String playerName, int steps) {
    Player player = playerMap.get(playerName);
    if (player == null) {
        throw new IllegalArgumentException("Player not found!");
    }

    int currentPosition = player.getPosition();
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

    Cell newCell = cells[newX][newY];
    if (newCell.hasObstacle()) {    // Use hasObstacle() method for readability
    newCell.getObstacle().applyEffect(player);
    // Decide whether to update the player's position based on the effect of applyEffect
    } else {
    // Update the player's position
    player.setPosition(newPosition);
    }
}

public Cell getCell(int x, int y) {
    return cells[x][y];
}

public Player getPlayer(String playerName) {
    return playerMap.get(playerName);
}

public void removePlayer(String playerName) {
    playerMap.remove(playerName);
}

class Cell {
    private Obstacle obstacle;

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

    
