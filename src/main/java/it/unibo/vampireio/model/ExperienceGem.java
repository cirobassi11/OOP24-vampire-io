package it.unibo.vampireio.model;

import java.awt.geom.Point2D.Double;
import java.util.Random;

public class ExperienceGem extends Lootable {

    protected ExperienceGem(Double position) {
        super("collectibles/experienceGem", position, new Random().nextInt(50));
    }
}
