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

    @Override
    public boolean isColliding(Collidable collidable) {
        double dx = this.getPosition().getX() - collidable.getPosition().getX();
        double dy = this.getPosition().getY() - collidable.getPosition().getY();
        double distanceSquared = dx * dx + dy * dy;
        double combinedRadius = this.getRadius() + collidable.getRadius();
        return distanceSquared <= combinedRadius * combinedRadius;
    }
}