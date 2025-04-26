package it.unibo.vampireio.model;

import java.awt.geom.Point2D;

public abstract class PositionableEntity implements Positionable {
    
    private String id;
    private Point2D.Double position;

    protected PositionableEntity(String id, Point2D.Double position) {
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
    public void setPosition(Point2D.Double position) {
        this.position = position;
    }

    @Override
    public double getDistance(Positionable positionable) {
        return position.distance(positionable.getPosition());
    }
}
