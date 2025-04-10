package it.unibo.vampireio.model;

import java.awt.Shape;
import java.awt.geom.Point2D;

public abstract class LivingEntity extends MovableEntity implements Living {

    private double maxHealth;
    private double health;

    protected LivingEntity(String id, Point2D.Double position, Shape hitbox, Point2D.Double direction, double speed, double maxHealth) {
        super(id, position, hitbox, direction, speed);
        this.maxHealth = maxHealth;
        this.health = maxHealth;
    }

    @Override
    public double getHealth() {
        return this.health;
    }

    @Override
    public double getMaxHealth() {
        return this.maxHealth;
    }

    @Override
    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    @Override
    public void dealDamage(double damage) {
        this.health = Math.max(this.health - ((damage < 0) ? 0 : damage), 0);
        if(this.health == 0) {
            //TODO -die
        }
    }

    @Override
    public void heal(double heal) {
        this.health = Math.min(this.health + ((heal < 0) ? 0 : heal), this.maxHealth);
    }
}