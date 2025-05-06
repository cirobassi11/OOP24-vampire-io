package it.unibo.vampireio.model;

import java.awt.geom.Point2D.Double;

public class Food extends Lootable {

    protected Food(Double position, int value) {
        super("collectibles/food", position, value); //il nome lo prendiamo da un'altra parte?? ci sono più tipi di cibo?
    }
}
