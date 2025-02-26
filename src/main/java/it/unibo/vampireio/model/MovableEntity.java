package it.unibo.vampireio.model;

import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

public abstract class MovableEntity extends CollidableEntity implements Movable{
    private Point2D.Double direction;
    private double speed;

    protected MovableEntity(Double position, Rectangle hitbox, Point2D.Double direction, double speed) {
        super(position, hitbox);
        this.direction = direction;
        this.speed = speed;
    }

    @Override
    public void setDirection(Point2D.Double direction) {
        this.direction = direction;
    }

    @Override
    public Point2D.Double getDirection() {
        return direction;
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
    public void move(Point2D.Double positionOffset) {
        setPosition(new Point2D.Double(getPosition().getX() + positionOffset.x, getPosition().y + positionOffset.getY()));
    }
}
