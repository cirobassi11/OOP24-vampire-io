package it.unibo.vampireio.model;

public interface Unlockable {
    boolean isUnlocked();
    void unlock();
    String getId();
    String getName();
    String getDescription();
    int getPrice();
}
