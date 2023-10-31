public class Board {
    private final int size;  // Board Size eg.10*10
    private Cell[][] cells;

    public Board(int size) {
        this.size = size;
        this.cells = new Cell[size][size];  // Stores all the cells on the board
        initializeCells();
    }

    private void initializeCells() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j] = new Cell();
            }
        }

      // Here, can also initialize the obstacles

    }
