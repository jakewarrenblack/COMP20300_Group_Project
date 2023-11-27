import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        enum GameState {ACTIVE, LOST, WON};
        GameState gameState = GameState.ACTIVE; // Initialize game state to active

        Dice dice = new Dice();

        System.out.println();
        System.out.println("Press any key to roll the dice, enter 'exit' to quit the game");

        ScoreBoard scoreBoard = new ScoreBoard(new ArrayList<Player>(), 2);

        Board board = new Board(10, new Dice());

        for(Player p: board.getPlayers()) {
            scoreBoard.addPlayerScore(p);
        }

        board.printBoard();

        // The player to go first is determined by the dice roll
        Player currentPlayer = board.setInitialPlayer();

        while (gameState == GameState.ACTIVE) {
            String input = s.nextLine();

            if ("exit".equalsIgnoreCase(input)) {
                break;
            }

            int move = dice.roll();
            System.out.println("Rolled: " + move);

            // Print the current scores
            scoreBoard.printScores();

            if (move != 0) {
                board.movePlayer(currentPlayer.getIndex(), move, scoreBoard);
            }

            // After player has made a move, switch to the next player
            currentPlayer = board.nextPlayer();

            System.out.println("\nMake a move:");

        }

        s.close();
    }
}
