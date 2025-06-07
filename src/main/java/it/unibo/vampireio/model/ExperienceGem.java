package it.unibo.vampireio.model;

import java.awt.geom.Point2D.Double;

public class ExperienceGem extends AbstractCollectibleItem {

    private static final int EXPERIENCE_VALUE = 50;

    protected ExperienceGem(final Double position) {
        super("collectibles/experienceGem", position, EXPERIENCE_VALUE);
    }

    @Override
    public void onCollision(Collidable collidable) {}
}
