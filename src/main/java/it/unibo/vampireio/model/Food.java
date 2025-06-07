package it.unibo.vampireio.model;

import java.awt.geom.Point2D.Double;
import java.util.Random;

public class Food extends AbstractCollectibleItem {

    private static final int HEAL_VALUE = 100;

    protected Food(final Double position) {
        super("collectibles/food", position, HEAL_VALUE);
    }

    @Override
    public void onCollision(Collidable collidable) { }
}
