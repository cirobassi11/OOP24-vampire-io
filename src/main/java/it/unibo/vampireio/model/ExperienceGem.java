package it.unibo.vampireio.model;

import java.awt.geom.Point2D.Double;

public class ExperienceGem extends Lootable {

    protected ExperienceGem(Double position) {
        super("xp", position); /// il nome va preso da un'altra parte??, ci sono più tipi di xp??
    }

    @Override
    public void collect() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'collect'");
    }
}
