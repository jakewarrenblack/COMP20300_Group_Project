package com.example.obstaclecourse.Models;

public interface Obstacle {

    void applyEffect(Player p, int size, Board.Cell[][] cells);

    String printEffect();

    String getSymbol();

    Pit.Type getType();

    int getLength();
}
