package it.unibo.vampireio.model;

import java.io.Serializable;

public class UnlockablePowerUp extends UnlockableItem {
    private static final double MULTIPLIER_INCREMENT = 0.05;

    public UnlockablePowerUp(String id, String name, String description, int price, int maxLevel) {
        super(id, name, description, price, maxLevel);
    }

    public double getMultiplier() {
        return this.getLevel() * MULTIPLIER_INCREMENT;
    }
}