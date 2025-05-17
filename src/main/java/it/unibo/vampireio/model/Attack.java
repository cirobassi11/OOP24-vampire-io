package it.unibo.vampireio.model;

import java.awt.geom.Point2D;

public interface Attack extends Collidable {
    void execute();
}