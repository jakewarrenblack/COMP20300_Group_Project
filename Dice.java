public class Dice {
    private int value;

    public Dice(){
        this.value = 0;
    }

    public int roll(){
        this.value = (int)(Math.random() * 9 + 1);  // Set the dice face to some random value between 1 and 9 inclusive
        return this.value;
    }

    public int getValue(){
        return this.value;
    }
}
