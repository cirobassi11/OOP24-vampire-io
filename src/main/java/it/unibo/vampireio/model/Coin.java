package it.unibo.vampireio.model;

import java.awt.geom.Point2D.Double;
import java.util.Random;

public class Coin extends AbstractCollectibleItem {

    private static final int COIN_VALUE = 1;

    protected Coin(final Double position) {
        super("collectibles/coin", position, COIN_VALUE);
    }

    @Override
    public void onCollision(Collidable collidable) {
    }
}
