public interface Obstacle {
    void applyEffect(Player p);

    String printEffect();

    String getSymbol();

    Pit.Type getType();

    int getLength();
}
