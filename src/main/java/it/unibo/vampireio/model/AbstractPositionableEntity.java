package it.unibo.vampireio.model;

import java.awt.geom.Point2D;

public abstract class AbstractPositionableEntity implements Positionable {
    
    private final String id;
    private Point2D.Double position;

    protected AbstractPositionableEntity(final String id, final Point2D.Double position) {
        this.id = id;
        this.position = position;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public Point2D.Double getPosition() {
        return this.position;
    }

    @Override
    public void setPosition(final Point2D.Double position) {
        this.position = position;
    }

    @Override
    public double getDistance(final Positionable positionable) {
        return position.distance(positionable.getPosition());
    }
}
