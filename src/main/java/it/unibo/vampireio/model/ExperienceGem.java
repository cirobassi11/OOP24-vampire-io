package it.unibo.vampireio.model;

import java.awt.geom.Point2D.Double;

public class ExperienceGem extends Lootable {

    protected ExperienceGem(Double position, int value) {
        super("collectibles/experiencegem", position, value); /// il nome va preso da un'altra parte??, ci sono più tipi di xp??
    }
}
