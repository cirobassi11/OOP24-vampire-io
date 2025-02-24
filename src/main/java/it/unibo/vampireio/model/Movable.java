package it.unibo.vampireio.model;

import java.awt.geom.Point2D;

public interface Movable {
    void setDirection(Point2D.Double direction);
    Point2D.Double getDirection();
    void setSpeed(double speed);
    double getSpeed();
    void move(Point2D.Double positionOffset);
}
