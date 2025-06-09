package it.unibo.vampireio.model;

import java.awt.geom.Point2D;

/**
 * Abstract class representing an entity that can be positioned in a 2D space.
 * It implements the Positionable interface and provides basic functionality
 * for managing the entity's position and ID.
 */
public abstract class AbstractPositionableEntity implements Positionable {

    private final String id;
    private Point2D.Double position;

    /**
     * Constructs a new AbstractPositionableEntity with the specified ID and
     * position.
     *
     * @param id       the unique identifier for this entity
     * @param position the initial position of this entity in 2D space
     */
    protected AbstractPositionableEntity(final String id, final Point2D.Double position) {
        this.id = id;
        this.position = position;
    }

    /**
     * Subclasses that override this method should call {@code super.getId()}.
     */
    @Override
    public final String getId() {
        return this.id;
    }

    /**
     * Subclasses that override this method should call {@code super.getPosition()}.
     */
    @Override
    public final Point2D.Double getPosition() {
        return this.position;
    }

    /**
     * Subclasses that override this method should call {@code super.setPosition()}.
     */
    @Override
    public final void setPosition(final Point2D.Double position) {
        this.position = position;
    }

    /**
     * Subclasses that override this method should call {@code super.getDistance()}.
     */
    @Override
    public final double getDistance(final Positionable positionable) {
        return position.distance(positionable.getPosition());
    }
}
