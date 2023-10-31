import java.util.ArrayList;
public class ScoreBoard {
    private final ArrayList<Player> playerScores;
    private final int maxEntries;

    public ScoreBoard(ArrayList<Player> playerScores, int maxEntries){
        if(playerScores.size() > maxEntries){
            throw new IllegalArgumentException("Number of score values must not exceed maxEntries");
        }
        // To determine the number of entries displayed, e.g. top 10 or top 5 scores.
        this.maxEntries = maxEntries;
        this.playerScores = playerScores;
    }

    public void addPlayerScore(Player p){
        if(this.playerScores.size() + 1 > this.maxEntries){
            throw new IllegalArgumentException("Number of score values must not exceed maxEntries");
        }

        this.playerScores.add(p);
    }

    public ArrayList<Player> getPlayerScores(){
        return this.playerScores;
    }
}
