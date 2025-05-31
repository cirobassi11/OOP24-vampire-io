package it.unibo.vampireio.model;

import java.awt.geom.Point2D;

public abstract class AbstractCollidableEntity extends AbstractPositionableEntity implements Collidable {
    private final double radius;
    
    protected AbstractCollidableEntity(final String id, final Point2D.Double position, final double radius) {
        super(id, position);
        this.radius = radius;
    }

    @Override
    public double getRadius() {
        return this.radius;
    }
}