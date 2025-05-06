package it.unibo.vampireio.model;

import java.awt.geom.Point2D.Double;

public class Coin extends Lootable {

    protected Coin(Double position, int value) {
        super("collectibles/coin", position, value);
    }
}
