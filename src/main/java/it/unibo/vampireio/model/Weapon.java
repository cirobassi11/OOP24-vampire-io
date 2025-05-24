package it.unibo.vampireio.model;

public interface Weapon extends Identifiable {
    void update(double tickTime);
    int getCurrentLevel();
    void multiplyCooldown(double multiplier);
    void levelUp();
}
