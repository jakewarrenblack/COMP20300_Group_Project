import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        enum GameState {ACTIVE, LOST, WON};
        GameState gameState = GameState.ACTIVE;

        ScoreBoard scoreBoard = new ScoreBoard(new ArrayList<Player>(), 2);

        Board board = new Board(10, new Dice());

        for(Player p: board.getPlayers()) {
            scoreBoard.addPlayerScore(p);
        }

        board.printBoard();

        // The player to go first is determined by the dice roll
        Player currentPlayer = board.setInitialPlayer();

        System.out.println("Make a move:");

        while (gameState == GameState.ACTIVE) {
            int move = s.nextInt();
            s.nextLine();

            if (move != 0) {
                board.movePlayer(currentPlayer.getIndex(), move);
            }

            // After player has made a move, switch to the next player
            currentPlayer = board.nextPlayer();

            System.out.println("\nMake a move:");
        }
    }
}
