package it.unibo.vampireio.model;

import java.awt.geom.Point2D.Double;
import java.util.Random;

public class Coin extends CollectibleItem {

    protected Coin(Double position) {
        super("collectibles/coin", position, new Random().nextInt(9) + 1);
    }

    @Override
    public void onCollision(Collidable collidable) {
    }
}
