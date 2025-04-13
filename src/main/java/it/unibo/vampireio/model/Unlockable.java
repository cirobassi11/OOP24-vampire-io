package it.unibo.vampireio.model;

public interface Unlockable {
    boolean isLocked();
    void enhance();
    String getId();
    String getName();
    String getDescription();
    int getPrice();
    int getLevel();
}
