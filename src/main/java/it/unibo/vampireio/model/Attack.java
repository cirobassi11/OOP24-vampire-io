package it.unibo.vampireio.model;

import java.awt.geom.Point2D;

public interface Attack extends Collidable {
    void execute(Point2D.Double position, Point2D.Double direction);
    int getDamage();
}