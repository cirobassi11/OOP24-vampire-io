package it.unibo.vampireio.model;

import java.awt.geom.Point2D;

public abstract class AbstractMovableEntity extends AbstractCollidableEntity implements Movable {
    
    private static final double SPEED_MULTIPLIER = 0.2;
    
    private Point2D.Double direction;
    private double speed;

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

    @Override
    public void setDirection(final Point2D.Double direction) {
        this.direction = direction;
    }

    @Override
    public Point2D.Double getDirection() {
        return this.direction;
    }

    @Override
    public void setSpeed(final double speed) {
        this.speed = speed;
    }

    @Override
    public double getSpeed() {
        return this.speed;
    }

    @Override
    public void move(final long tickTime) {
        final double dx = this.getDirection().getX() * this.getSpeed() * tickTime * SPEED_MULTIPLIER;
        final double dy = this.getDirection().getY() * this.getSpeed() * tickTime * SPEED_MULTIPLIER;
        this.setPosition(new Point2D.Double(
            this.getPosition().getX() + dx,
            this.getPosition().getY() + dy
        ));
    }

    @Override
    public boolean isMoving() {
        return this.getDirection().getX() != 0 || this.getDirection().getY() != 0;
    }

    @Override
    public Point2D.Double getFuturePosition(final long tickTime) {
        final double newX = this.getPosition().getX() + this.getDirection().getX() * this.getSpeed() * tickTime * SPEED_MULTIPLIER;
        final double newY = this.getPosition().getY() + this.getDirection().getY() * this.getSpeed() * tickTime * SPEED_MULTIPLIER;
        return new Point2D.Double(newX, newY);
    }
}
