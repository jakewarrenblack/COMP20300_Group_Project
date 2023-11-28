public interface Obstacle {

    void applyEffect(Player p, int size);

    String printEffect();

    String getSymbol();

    int getLength();
}
