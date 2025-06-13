package it.unibo.vampireio.controller.data;

import java.awt.geom.Point2D;

/**
 * Represents a living entity in the game, such as a player or an enemy.
 * This class encapsulates the entity's position, direction, radius,
 * health, maximum health, and states for being attacked and moving.
 */
public final class LivingEntityData extends PositionableData {
    private final double health;
    private final double maxHealth;
    private final boolean isBeingAttacked;
    private final boolean isMoving;

    /**
     * Constructs a LivingEntityData instance with the specified parameters.
     *
     * @param id              the unique identifier of the entity
     * @param position        the position of the entity in the game world
     * @param direction       the direction the entity is facing
     * @param radius          the radius of the entity, used for collision detection
     * @param health          the current health of the entity
     * @param maxHealth       the maximum health of the entity
     * @param isBeingAttacked whether the entity is currently being attacked
     * @param isMoving        whether the entity is currently moving
     */
    public LivingEntityData(
            final String id,
            final Point2D.Double position,
            final Point2D.Double direction,
            final double radius,
            final double health,
            final double maxHealth,
            final boolean isBeingAttacked,
            final boolean isMoving
        ) {
        super(id, position, direction, radius);
        this.health = health;
        this.maxHealth = maxHealth;
        this.isBeingAttacked = isBeingAttacked;
        this.isMoving = isMoving;
    }

    /**
     * Returns the current health of the entity.
     *
     * @return the entity's current health
     */
    public double getHealth() {
        return this.health;
    }

    /**
     * Returns the maximum health of the entity.
     *
     * @return the entity's maximum health
     */
    public double getMaxHealth() {
        return this.maxHealth;
    }

    /**
     * Returns whether the entity is currently being attacked.
     *
     * @return true if the entity is being attacked, false otherwise
     */
    public boolean isBeingAttacked() {
        return this.isBeingAttacked;
    }

    /**
     * Returns whether the entity is currently moving.
     *
     * @return true if the entity is moving, false otherwise
     */
    public boolean isMoving() {
        return this.isMoving;
    }
}
