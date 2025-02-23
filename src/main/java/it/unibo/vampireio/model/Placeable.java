package it.unibo.vampireio.model;

import java.awt.Point;

public interface Placeable {
    Point getPosition();
    int getDistance(Placeable placeable);
}