package it.unibo.vampireio.model.impl.collectibles;

import java.awt.geom.Point2D.Double;

/**
 * Represents a collectible item that grants experience points when collected.
 * This item is typically used to enhance the player's abilities or progress in
 * the game.
 */
public class ExperienceGem extends AbstractCollectibleItem {

    private static final int EXPERIENCE_VALUE = 50;

    /**
     * Constructs an ExperienceGem collectible item at the specified position.
     *
     * @param position the position of the experience gem in the game world
     */
    public ExperienceGem(final Double position) {
        super("collectibles/experienceGem", position, EXPERIENCE_VALUE);
    }
}
