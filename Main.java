import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        enum GameState {ACTIVE, LOST, WON};
        GameState gameState = GameState.ACTIVE;

        Player player = new Player("Jake");
        ScoreBoard scoreBoard = new ScoreBoard(new ArrayList<Player>(), 1);

        scoreBoard.addPlayerScore(player);

        Board b= new Board(10);
        b.addPlayer(player);

        b.printBoard();

        Scanner s = new Scanner(System.in);

        System.out.println("Make a move:");

        while (gameState == GameState.ACTIVE) {
            int move = s.nextInt();
            s.nextLine();

            if (move != 0) {
                b.movePlayer("Jake", move);
            }

            System.out.println("\nMake a move:");
        }
    }
}
