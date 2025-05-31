package it.unibo.vampireio.model;

import java.awt.geom.Point2D.Double;

public class ExperienceGem extends AbstractCollectibleItem {

    protected ExperienceGem(final Double position) {
        super("collectibles/experienceGem", position, 50);
    }

    @Override
    public void onCollision(Collidable collidable) {}
}
