package it.unibo.vampireio.model;

import java.awt.geom.Point2D.Double;

public abstract class Lootable extends PositionableEntity implements Collectible {

    protected Lootable(Double position) {
        super(position);
    }
    
}
