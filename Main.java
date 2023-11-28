import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        enum GameState {ACTIVE, LOST, WON};
        GameState gameState = GameState.ACTIVE; // Initialize game state to active

        // Create player object and scoreboard
        Player player = new Player("Jake");
        ScoreBoard scoreBoard = new ScoreBoard(new ArrayList<Player>(), 1);
        scoreBoard.addPlayerScore(player);

        Board b = new Board(10);
        b.addPlayer(player); // Add player to the game board

        b.printBoard(); // Print the initial game board

        Dice dice = new Dice();

        System.out.println();
        System.out.println("Press any key to roll the dice, enter 'exit' to quit the game");

        Scanner s = new Scanner(System.in);

        while (gameState == GameState.ACTIVE) {

            String input = s.nextLine();

            if ("exit".equalsIgnoreCase(input)) {
                break;
            }

            int move = dice.roll();
            System.out.println("Rolled: " + move);

            b.movePlayer("Jake", move, scoreBoard);

            // Print the current scores
            scoreBoard.printScores();
        }

        s.close();
    }
}
