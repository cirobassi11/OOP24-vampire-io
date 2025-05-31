package it.unibo.vampireio.model;

import java.awt.geom.Point2D;

public abstract class AbstractLivingEntity extends AbstractMovableEntity implements Living {

    private double maxHealth;
    private double health;
    private boolean isGettingAttacked;

    protected AbstractLivingEntity(final String id, final Point2D.Double position, final double radius,
            final Point2D.Double direction, final double speed, final double maxHealth) {
        super(id, position, radius, direction, speed);
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.isGettingAttacked = false;
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
    public void setMaxHealth(final double maxHealth) {
        this.maxHealth = maxHealth;
    }

    @Override
    public void dealDamage(final double damage) {
        this.health = Math.max(this.health - ((damage < 0) ? 0 : damage), 0);
    }

    @Override
    public void heal(final double heal) {
        this.health = Math.min(this.health + ((heal < 0) ? 0 : heal), this.maxHealth);
    }

    @Override
    public boolean isGettingAttacked() {
        return this.isGettingAttacked;
    }

    @Override
    public void setGettingAttacked(final boolean isGettingAttacked) {
        this.isGettingAttacked = isGettingAttacked;
    }
}