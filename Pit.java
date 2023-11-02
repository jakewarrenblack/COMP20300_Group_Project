public class Pit implements Obstacle{
    public enum Type {BOTTOMLESS, SPIKE, FIRE}
    private final Type type;

    // Default to 1 if any type but SPIKE
    private int length = 1;

    public Pit(Type type){
        this.type = type;

        if(type == Type.SPIKE){
            this.length = (int)(Math.random() * 4 + 1);
        }
    }

    // To allow specifying a pit length
    public Pit(Type type, int length){
        if(type != Type.SPIKE){
            throw new IllegalArgumentException("You can only set a custom length for spikes!");
        }

        if(length < 1 || length > 4 ){
            throw new IllegalArgumentException("Length must be between 1 and 5");
        }
        // Only spike pits have length
        this.type = Type.SPIKE;

        this.length = length;
    }

    public Type getType(){
        return this.type;
    }

    @Override
    public void applyEffect(Player p) {
        switch(this.type){
            case BOTTOMLESS: {
                p.setPosition(0);
                p.setMovesAvailable(0);
                break;
            }
            case SPIKE: {
                p.setMovesAvailable(p.getMovesAvailable()-this.length);
            }
            case FIRE: {
                p.setMovesAvailable(p.getMovesAvailable()-1);
            }
        }
    }


    /**
     * Assuming this will be useful in the future.
     * @return String
     */
    @Override
    public String printEffect() {
        String effect = "";

        switch(this.type){
            case FIRE -> effect = "Causes the player to miss a turn!";
            case SPIKE -> effect = "Consumes " + this.length + " tiles, which must be jumped over!";
            case BOTTOMLESS -> effect = "Causes the player to return to the start!";
        }

        return effect;
    }

    @Override
    public String getSymbol() {
        String symbol = "⬛";

        switch (this.type){
            case FIRE -> symbol = "🔥";
            case SPIKE -> symbol = "⚔";
            case BOTTOMLESS -> symbol = "🕳";
        }

        return symbol;
    }

    @Override
    public int getLength(){
        return this.length;
    }
}
