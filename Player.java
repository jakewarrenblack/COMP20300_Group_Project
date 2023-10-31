public class Player {
    private final String name;
    private int position;
    private int movesAvailable;
    private int score;

    public Player(String name){
        this.name = name;
        this.position = 0;
        this.movesAvailable = 0;
    }

    public String getName(){
        return this.name;
    }

    public void setPosition(int position){
        this.position = position;
    }

    public int getPosition(){
        return this.position;
    }

    public void setMovesAvailable(int movesAvailable){
        this.movesAvailable = movesAvailable;
    }

    public int getMovesAvailable(){
        return this.movesAvailable;
    }

    public void setScore(int score){
        this.score = score;
    }

    public int getScore(){
        return this.score;
    }

}
