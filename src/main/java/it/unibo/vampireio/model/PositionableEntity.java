package it.unibo.vampireio.model;

import java.awt.geom.Point2D;

public abstract class PositionableEntity implements Positionable {
    
    private Point2D.Double position;

    protected PositionableEntity(Point2D.Double position) {
        this.position = position;
    }

    @Override
    public Point2D.Double getPosition() {
        return position;
    }

    @Override
    public void setPosition(Point2D.Double position) {
        this.position = position;
    }

    @Override
    public double getDistance(Positionable positionable) {
        return position.distance(positionable.getPosition());
    }
}
