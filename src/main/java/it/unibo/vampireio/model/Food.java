package it.unibo.vampireio.model;

import java.awt.geom.Point2D.Double;

public class Food extends Lootable {

    protected Food(Double position) {
        super("food", position); //il nome lo prendiamo da un'altra parte?? ci sono più tipi di cibo?
    }

    @Override
    public void collect() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'collect'");
    }
}
