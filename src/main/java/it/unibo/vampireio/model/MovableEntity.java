package it.unibo.vampireio.model;

import java.awt.Shape;
import java.awt.geom.Point2D;

public abstract class MovableEntity extends CollidableEntity implements Movable {
    private Point2D.Double direction;
    private double speed;

    protected MovableEntity(String id, Point2D.Double position, Shape hitbox, Point2D.Double direction, double speed) {
        super(id, position, hitbox);
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
        return speed;
    }

    @Override
    public void move(double frameTime) {
        this.setPosition(new Point2D.Double(
            this.getPosition().getX() + this.getDirection().getX() * this.getSpeed() * frameTime, 
            this.getPosition().getY() + this.getDirection().getY() * this.getSpeed() * frameTime
        ));
    }
}
