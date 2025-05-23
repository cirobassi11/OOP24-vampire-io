package it.unibo.vampireio.model;

import java.awt.geom.Point2D.Double;
import java.util.Random;

public class Coin extends Lootable {

    protected Coin(Double position) {
        super("collectibles/coin", position, new Random().nextInt(50));
    }
}
