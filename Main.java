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
        System.out.println("\nMake a move:");

        while (gameState == GameState.ACTIVE) {
            int move = s.nextInt();
            s.nextLine();

            if (move != 0) {
                // After player has made a move, the board switches to the next player
                // if nextPlayer is being called before initial player has even been set, the initial player is returned instead of incrementing the index
                board.movePlayer(board.nextPlayer().getIndex(), move);
            }

            System.out.println("\nMake a move:");
        }
    }
}
