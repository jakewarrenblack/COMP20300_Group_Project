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

        System.out.println("\nMake a move:");

        while (gameState == GameState.ACTIVE) {
            String input = s.nextLine();

            if ("exit".equalsIgnoreCase(input)) {
                break;
            }

            int move = dice.roll();
            System.out.println("Rolled: " + move);



            if (move != 0) {
                // After player has made a move, the board switches to the next player
                // if nextPlayer is being called before initial player has even been set, the initial player is returned instead of incrementing the index
                board.movePlayer(board.nextPlayer().getIndex(), move, scoreBoard);
            }

            // Print the current scores
            scoreBoard.printScores();

            System.out.println("\nMake a move:");

        }

        s.close();
    }
}
