package it.unibo.vampireio.model;

import java.awt.geom.Point2D;

public abstract class MovableEntity extends CollidableEntity implements Movable {
    private Point2D.Double direction;
    private double speed;

    private static final double SPEED_MULTIPLIER = 0.21;

    protected MovableEntity(String id, Point2D.Double position, double radius, Point2D.Double direction, double speed) {
        super(id, position, radius);
        this.direction = direction;
        this.speed = speed;
    }

    @Override
    public void setDirection(Point2D.Double direction) {
        this.direction = direction;
    }

    @Override
    public Point2D.Double getDirection() {
        return this.direction;
    }

    @Override
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public double getSpeed() {
        return this.speed;
    }

    @Override
    public void move(double tickTime) {
        double dx = this.getDirection().getX() * this.getSpeed() * tickTime * SPEED_MULTIPLIER;
        double dy = this.getDirection().getY() * this.getSpeed() * tickTime * SPEED_MULTIPLIER;
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
    public Point2D.Double getFuturePosition(double tickTime) {
        double newX = this.getPosition().getX() + this.getDirection().getX() * this.getSpeed() * tickTime * SPEED_MULTIPLIER;
        double newY = this.getPosition().getY() + this.getDirection().getY() * this.getSpeed() * tickTime * SPEED_MULTIPLIER;
        return new Point2D.Double(newX, newY);
    }
}
