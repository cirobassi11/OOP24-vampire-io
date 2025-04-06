package it.unibo.vampireio.controller;

import java.awt.geom.Point2D;

public class LivingEntityData extends PositionableData {
    private final double health;
    private final double maxHealth;
    private boolean isBeingAttacked;
    private boolean isMoving;

    public LivingEntityData(String id, Point2D.Double position, Point2D.Double direction, double health, double maxHealth, boolean isBeingAttacked, boolean isMoving) {
        super(id, position, direction);
        this.health = health;
        this.maxHealth = maxHealth;
        this.isBeingAttacked = isBeingAttacked;
        this.isMoving = isMoving;
    }

    public double getHealth() {
        return this.health;
    }

    public double getMaxHealth() {
        return this.maxHealth;
    }

    public boolean isBeingAttacked() {
        return this.isBeingAttacked;
    }
    
    public boolean isMoving() {
        return this.isMoving;
    }
}
