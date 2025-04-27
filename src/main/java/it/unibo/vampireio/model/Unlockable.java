package it.unibo.vampireio.model;

import java.io.Serializable;

public interface Unlockable extends Serializable {
    boolean isLocked();
    boolean enhance();
    void setCurrentLevel(int currentLevel);
    String getId();
    String getName();
    String getDescription();
    int getPrice();
    int getCurrentLevel();
    int getMaxLevel();
}
