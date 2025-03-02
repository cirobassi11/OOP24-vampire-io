package it.unibo.vampireio.model;

public interface Unlockable {
    boolean isUnlocked();
    void unlock();
    String getName();
    int getPrice();
}
