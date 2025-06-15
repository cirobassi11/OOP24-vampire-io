package it.unibo.vampireio.model.impl;

import java.awt.geom.Point2D;
import it.unibo.vampireio.model.api.Collidable;

/**
 * Abstract class representing a collidable entity in the game.
 * It extends AbstractPositionableEntity and implements Collidable interface.
 * Provides methods to get the radius and check for collisions with other
 * collidable entities.
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

    /**
     * Copy constructor that creates a new AbstractCollidableEntity from an existing
     * one.
     *
     * @param entity the AbstractCollidableEntity to copy
     */
    AbstractCollidableEntity(final AbstractCollidableEntity entity) {
        super(entity);
        this.radius = entity.getRadius();
    }

    @Override
    public final double getRadius() {
        return this.radius;
    }

    @Override
    public final void checkCollision(final Collidable collidable) {
        if (collidable != null && this.isColliding(collidable)) {
            this.onCollision(collidable);
        }
    }

    @Override
    public abstract void onCollision(Collidable collidable);

    private boolean isColliding(final Collidable collidable) {
        final double dx = this.getPosition().getX() - collidable.getPosition().getX();
        final double dy = this.getPosition().getY() - collidable.getPosition().getY();
        final double distanceSquared = dx * dx + dy * dy;
        final double combinedRadius = this.getRadius() + collidable.getRadius();
        return distanceSquared <= combinedRadius * combinedRadius;
    }
}
