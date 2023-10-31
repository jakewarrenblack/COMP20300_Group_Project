public class Dice {
    private int value;

    public Dice(){
        this.roll();
    }

    public void roll(){
        this.value = (int)(Math.random() * 50 + 1);
    }

    public int getValue(){
        return this.value;
    }
}
