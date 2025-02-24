package it.unibo.vampireio.model;

import java.awt.geom.Point2D;

public abstract class Entity implements Positionable, Collidable {
    protected Point2D.Double position;

    protected Entity(Point2D.Double position) {
        this.position = position;
    }

    public Point2D.Double getPosition() {
        return position;
    }

    public int getDistance(Positionable positionable) {
        return (int) Math.sqrt(Math.pow(position.x - positionable.getPosition().x, 2) + Math.pow(position.y - positionable.getPosition().y, 2));
    }
}