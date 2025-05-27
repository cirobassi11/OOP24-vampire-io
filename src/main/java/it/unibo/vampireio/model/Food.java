package it.unibo.vampireio.model;

import java.awt.geom.Point2D.Double;
import java.util.Random;

public class Food extends CollectibleItem {

    protected Food(Double position) {
        super("collectibles/food", position, new Random().nextInt(51) + 50);
    }

    @Override
    public void onCollision(Collidable collidable) {}
}
