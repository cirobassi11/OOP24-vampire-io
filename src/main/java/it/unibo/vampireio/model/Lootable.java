package it.unibo.vampireio.model;

import java.awt.geom.Point2D.Double;

public abstract class Lootable extends PositionableEntity implements Collectible {

    protected int value;

    protected Lootable(String id, Double position, int value) {
        super(id, position);
        this.value = value;
    }

    @Override
    public int getValue() {
        return this.value;
    }
    
}