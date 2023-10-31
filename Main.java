import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Player player = new Player("Jake");
        Dice dice = new Dice();
        ScoreBoard scoreBoard = new ScoreBoard(new ArrayList<Player>(), 1);

        player.setPosition(dice.roll());

        System.out.println(player.getPosition());

        scoreBoard.addPlayerScore(player);
    }
}
