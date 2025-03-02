package it.unibo.vampireio.model;

import java.awt.Shape;
import java.awt.geom.Point2D;

public abstract class LivingEntity extends MovableEntity implements Living {

    private int health;
    private int maxHealth;

    protected LivingEntity(String id, Point2D.Double position, Shape hitbox, Point2D.Double direction, double speed) {
        super(id, position, hitbox, direction, speed);
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public int getMaxHealth() {
        return maxHealth;
    }

    @Override
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    @Override
    public void dealDamage(int damage) {
        this.health = Math.max(this.health - ((damage < 0) ? 0 : damage), 0);
        if(this.health == 0) {
            //TODO -die
        }
    }

    @Override
    public void heal(int heal) {
        this.health = Math.min(this.health + ((heal < 0) ? 0 : heal), this.maxHealth);
    }
}