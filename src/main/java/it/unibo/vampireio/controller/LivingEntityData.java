package it.unibo.vampireio.controller;

import java.awt.geom.Point2D;

public class LivingEntityData extends PositionableData {
    private final int health;
    private final int maxHealth;
    private boolean isBeingAttacked;
    private boolean isMoving;

    public LivingEntityData(String id, Point2D.Double position, Point2D.Double direction, int health, int maxHealth, boolean isBeingAttacked, boolean isMoving) {
        super(id, position, direction);
        this.health = health;
        this.maxHealth = maxHealth;
        this.isBeingAttacked = isBeingAttacked;
        this.isMoving = isMoving;
    }

    public int getHealth() {
        return this.health;
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }

    public boolean isBeingAttacked() {
        return this.isBeingAttacked;
    }
    
    public boolean isMoving() {
        return this.isMoving;
    }
}
