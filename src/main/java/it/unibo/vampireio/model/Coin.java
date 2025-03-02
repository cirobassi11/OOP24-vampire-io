package it.unibo.vampireio.model;

import java.awt.geom.Point2D.Double;

public class Coin extends Lootable {

    protected Coin(Double position) {
        super("coin", position); /// il nome va preso da un'altra parte??
    }

    @Override
    public void collect() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'collect'");
    }
}
