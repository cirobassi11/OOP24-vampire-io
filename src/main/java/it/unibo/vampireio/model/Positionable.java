package it.unibo.vampireio.model;

import java.awt.geom.Point2D;

public interface Positionable {
    Point2D.Double getPosition();
    int getDistance(Positionable positionable);
    void update();
}