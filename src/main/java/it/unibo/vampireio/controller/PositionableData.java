package it.unibo.vampireio.controller;

import java.awt.geom.Point2D;

/**
 * Represents a positionable entity in the game.
 * This class encapsulates the entity's ID, position, direction, and radius.
 */
public class PositionableData {
    private final String id;
    private final Point2D.Double position;
    private final Point2D.Double direction;
    private final double radius;

    /**
     * Constructs a PositionableData instance with the specified parameters.
     *
     * @param id        the unique identifier of the entity
     * @param position  the position of the entity in the game world
     * @param direction the direction the entity is facing
     * @param radius    the radius of the entity, used for collision detection
     */
    public PositionableData(
            final String id,
            final Point2D.Double position,
            final Point2D.Double direction,
            final double radius) {
        this.id = id;
        this.position = position;
        this.direction = direction;
        this.radius = radius;
    }

    /**
     * Returns the id of the entity.
     *
     * @return the entity's ID
     */
    public String getId() {
        return this.id;
    }

    /**
     * Returns the position of the entity.
     *
     * @return the entity's position
     */
    public Point2D.Double getPosition() {
        return this.position;
    }

    /**
     * Returns the direction of the entity.
     *
     * @return the entity's direction
     */
    public Point2D.Double getDirection() {
        return this.direction;
    }

    /**
     * Returns the radius of the entity.
     *
     * @return the entity's radius
     */
    public double getRadius() {
        return this.radius;
    }
}