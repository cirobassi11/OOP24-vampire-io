package it.unibo.vampireio.model;

import java.awt.geom.Point2D;

public interface Positionable {
    Point2D.Double getPosition();
    void setPosition(Point2D.Double position);
    int getDistance(Positionable positionable);
}