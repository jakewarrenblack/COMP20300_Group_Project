import java.util.HashMap;
public class Board {
    private final int size;  // Board Size eg.10*10
    private Cell[][] cells;
    private HashMap<String, Player> playerMap;  //
    
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

    // 确保newPosition不超过棋盘边界
    if (newPosition < 0) {
        newPosition = 0; // 玩家不能移动到小于0的位置
    } else if (newPosition >= size * size) {
        newPosition = size * size - 1; // 玩家不能移动超过棋盘的最大位置
    }

    // 将1D的newPosition转换为2D的newX和newY
    int newX = newPosition / size; // 行号
    int newY = newPosition % size; // 列号

    Cell newCell = cells[newX][newY];
    if (newCell.hasObstacle()) {  // 使用 hasObstacle() 方法，使代码更易读
    newCell.getObstacle().applyEffect(player);
    // 根据 applyEffect 的效果，决定是否更新玩家的位置
    } else {
    // 更新玩家位置
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

    
