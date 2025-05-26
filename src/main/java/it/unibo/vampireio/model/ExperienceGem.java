package it.unibo.vampireio.model;

import java.awt.geom.Point2D.Double;

public class ExperienceGem extends Lootable {

    protected ExperienceGem(Double position) {
        super("collectibles/experienceGem", position, 50);
    }
}
