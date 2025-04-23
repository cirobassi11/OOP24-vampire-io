package it.unibo.vampireio.model;

import java.io.Serializable;

public interface Unlockable extends Serializable {
    boolean isLocked();
    void enhance();
    String getId();
    String getName();
    String getDescription();
    int getPrice();
    int getLevel();
}
