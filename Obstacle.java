public interface Obstacle {
    void applyEffect(Player p);

    String printEffect();

    String getSymbol();

    int getLength();
}
