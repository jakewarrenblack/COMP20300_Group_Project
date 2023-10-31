public class Dice {
    private int value;

    public Dice(){
        this.value = 0;
    }

    public int roll(){
        // Set the dice face to some random value between 1 and 9 inclusive
        this.value = (int)(Math.random() * 9 + 1);
        return this.value;
    }

    public int getValue(){
        return this.value;
    }
}
