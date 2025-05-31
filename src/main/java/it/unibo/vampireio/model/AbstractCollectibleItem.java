package it.unibo.vampireio.model;

import java.awt.geom.Point2D.Double;

public abstract class AbstractCollectibleItem extends AbstractCollidableEntity implements Collectible {

    private static final double COLLECTIBLE_RADIUS = 10;
    protected int value;

    protected AbstractCollectibleItem(final String id, final Double position, final int value) {
        super(id, position, COLLECTIBLE_RADIUS);
        this.value = value;
    }

    @Override
    public int getValue() {
        return this.value;
    }

}