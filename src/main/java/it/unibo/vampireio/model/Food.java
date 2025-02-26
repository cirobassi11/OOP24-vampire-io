package it.unibo.vampireio.model;

import java.awt.geom.Point2D.Double;

public class Food extends Lootable {

    protected Food(Double position) {
        super(position);
    }

    @Override
    public void collect() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'collect'");
    }
}
