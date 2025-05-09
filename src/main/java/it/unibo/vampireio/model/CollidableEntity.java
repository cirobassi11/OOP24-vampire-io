package it.unibo.vampireio.model;

import java.awt.geom.Point2D;

public abstract class CollidableEntity extends PositionableEntity implements Collidable {
    private double radius;
    
    protected CollidableEntity(String id, Point2D.Double position, double radius) {
        super(id, position);
        this.radius = radius;
    }

    @Override
    public double getRadius() {
        return this.radius;
    }
}