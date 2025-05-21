package it.unibo.vampireio.model;

public interface Weapon extends Identifiable {
    void update(double tickTime);
    int getCurrentLevel();
}
