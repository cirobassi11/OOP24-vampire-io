package it.unibo.vampireio.model;

import java.awt.geom.Point2D;

public interface Weapon extends Item {
    void update(double tickTime);
}
