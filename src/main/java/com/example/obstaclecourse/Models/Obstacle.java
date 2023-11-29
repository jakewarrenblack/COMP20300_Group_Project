package com.example.obstaclecourse.Models;

public interface Obstacle {

    void applyEffect(Player p, int size);

    String printEffect();

    String getSymbol();

    Pit.Type getType();

    int getLength();
}
