package it.unibo.vampireio.model.impl;

import java.awt.geom.Point2D;
import it.unibo.vampireio.model.api.Living;

/**
 * Abstract class representing a living entity in the game.
 * It extends AbstractMovableEntity and implements Living interface.
 * Provides methods to manage health, maximum health, and attack status.
 */
public abstract class AbstractLivingEntity extends AbstractMovableEntity implements Living {

    private double maxHealth;
    private double health;
    private boolean isGettingAttacked;

    /**
     * Constructs a new AbstractLivingEntity with the specified parameters.
     *
     * @param id        the unique identifier for the entity
     * @param position  the initial position of the entity
     * @param radius    the radius of the entity
     * @param direction the initial direction of movement
     * @param speed     the speed of the entity
     * @param maxHealth the maximum health of the entity
     */
    protected AbstractLivingEntity(final String id, final Point2D.Double position, final double radius,
            final Point2D.Double direction, final double speed, final double maxHealth) {
        super(id, position, radius, direction, speed);
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.isGettingAttacked = false;
    }

    /**
     * Copy constructor that creates a new AbstractLivingEntity from an existing
     * one.
     *
     * @param entity the AbstractLivingEntity to copy
     */
    AbstractLivingEntity(final AbstractLivingEntity entity) {
        super(entity);
        this.maxHealth = entity.getMaxHealth();
        this.health = entity.getHealth();
        this.isGettingAttacked = entity.isGettingAttacked();
    }

    @Override
    public final double getHealth() {
        return this.health;
    }

    @Override
    public final double getMaxHealth() {
        return this.maxHealth;
    }

    @Override
    public final void setMaxHealth(final double maxHealth) {
        this.maxHealth = maxHealth;
    }

    /**
     * Subclasses overriding this method should call
     * {@code super.dealDamage(damage)}.
     */
    @Override
    public void dealDamage(final double damage) {
        this.health = Math.max(this.health - ((damage < 0) ? 0 : damage), 0);
    }

    @Override
    public final void heal(final double heal) {
        this.health = Math.min(this.health + ((heal < 0) ? 0 : heal), this.maxHealth);
    }

    @Override
    public final boolean isGettingAttacked() {
        return this.isGettingAttacked;
    }

    @Override
    public final void setGettingAttacked(final boolean attacked) {
        this.isGettingAttacked = attacked;
    }
}
