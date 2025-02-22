package it.unibo.vampireio.model;

import java.awt.Point;

public interface Placeable {
    Point getPosition();
    void setPosition(Point position);
    int getDistance(Placeable placeable);
}