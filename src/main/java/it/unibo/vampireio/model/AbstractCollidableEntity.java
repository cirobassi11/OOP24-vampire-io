package it.unibo.vampireio.model;

import java.awt.geom.Point2D;

/**
 * Abstract class representing a collidable entity in the game.
 * It extends AbstractPositionableEntity and implements Collidable interface.
 * Provides methods to get the radius and check for collisions with other collidable entities.
 */
public abstract class AbstractCollidableEntity extends AbstractPositionableEntity implements Collidable {
    private final double radius;

    /**
     * Constructs a new AbstractCollidableEntity with the specified parameters.
     *
     * @param id       the unique identifier for the entity
     * @param position the initial position of the entity
     * @param radius   the radius of the entity
     */
    protected AbstractCollidableEntity(final String id, final Point2D.Double position, final double radius) {
        super(id, position);
        this.radius = radius;
    }

    @Override
    public final double getRadius() {
        return this.radius;
    }

    @Override
    public final boolean isColliding(final Collidable collidable) {
        final double dx = this.getPosition().getX() - collidable.getPosition().getX();
        final double dy = this.getPosition().getY() - collidable.getPosition().getY();
        final double distanceSquared = dx * dx + dy * dy;
        final double combinedRadius = this.getRadius() + collidable.getRadius();
        return distanceSquared <= combinedRadius * combinedRadius;
    }
}
