package it.unibo.vampireio.model.impl;

import java.awt.geom.Point2D;
import it.unibo.vampireio.model.api.Movable;
import it.unibo.vampireio.model.api.Positionable;

/**
 * AbstractMovableEntity is an abstract class that represents a movable entity
 * in the game.
 * It extends AbstractCollidableEntity and implements the Movable interface.
 * This class provides basic functionality for movement, direction, and speed.
 */
public abstract class AbstractMovableEntity extends AbstractCollidableEntity implements Movable {

    private static final double SPEED_MULTIPLIER = 0.2;

    private Point2D.Double direction;
    private double speed;

    /**
     * Constructor for AbstractMovableEntity.
     *
     * @param id        the unique identifier of the entity
     * @param position  the initial position of the entity
     * @param radius    the radius of the entity
     * @param direction the initial direction of movement
     * @param speed     the initial speed of movement
     */
    protected AbstractMovableEntity(
            final String id,
            final Point2D.Double position,
            final double radius,
            final Point2D.Double direction,
            final double speed) {
        super(id, position, radius);
        this.direction = direction;
        this.speed = speed;
    }

    /**
     * Copy constructor for AbstractMovableEntity.
     *
     * @param entity the AbstractMovableEntity to copy
     */
    AbstractMovableEntity(final AbstractMovableEntity entity) {
        super(entity);
        this.direction = new Point2D.Double(entity.getDirection().getX(), entity.getDirection().getY());
        this.speed = entity.getSpeed();
    }

    /**
     * Subclasses that override this method should call
     * {@code super.setDirection(direction)}.
     */
    @Override
    public void setDirection(final Point2D.Double direction) {
        this.direction = new Point2D.Double(direction.getX(), direction.getY());
    }

    @Override
    public final void setDirectionTorwards(final Positionable target) {
        final Point2D.Double targetPosition = target.getPosition();
        final double dx = targetPosition.getX() - this.getPosition().getX();
        final double dy = targetPosition.getY() - this.getPosition().getY();
        final double length = Math.sqrt(dx * dx + dy * dy);
        if (length > 0) {
            this.setDirection(new Point2D.Double(dx / length, dy / length));
        } else {
            this.setDirection(new Point2D.Double(0, 0));
        }
    }

    @Override
    public final Point2D.Double getDirection() {
        return new Point2D.Double(this.direction.getX(), this.direction.getY());
    }

    @Override
    public final void setSpeed(final double speed) {
        this.speed = speed;
    }

    @Override
    public final double getSpeed() {
        return this.speed;
    }

    @Override
    public final void move(final long tickTime) {
        final double dx = this.getDirection().getX() * this.getSpeed() * tickTime * SPEED_MULTIPLIER;
        final double dy = this.getDirection().getY() * this.getSpeed() * tickTime * SPEED_MULTIPLIER;
        this.setPosition(new Point2D.Double(
                this.getPosition().getX() + dx,
                this.getPosition().getY() + dy));
    }

    @Override
    public final boolean isMoving() {
        return this.getDirection().getX() != 0 || this.getDirection().getY() != 0;
    }

    @Override
    public final Point2D.Double getFuturePosition(final long tickTime) {
        final double newX = this.getPosition().getX()
                + this.getDirection().getX() * this.getSpeed() * tickTime * SPEED_MULTIPLIER;
        final double newY = this.getPosition().getY()
                + this.getDirection().getY() * this.getSpeed() * tickTime * SPEED_MULTIPLIER;
        return new Point2D.Double(newX, newY);
    }
}
