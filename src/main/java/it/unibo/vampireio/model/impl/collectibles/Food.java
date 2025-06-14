package it.unibo.vampireio.model.impl.collectibles;

import java.awt.geom.Point2D.Double;

/**
 * Represents a food collectible item in the game.
 * This item heals the player when collected.
 */
public final class Food extends AbstractCollectibleItem {

    private static final int HEAL_VALUE = 100;

    /**
     * Constructs a Food collectible item at the specified position.
     *
     * @param position the position of the food item in the game world
     */
    public Food(final Double position) {
        super("collectibles/food", position, HEAL_VALUE);
    }
}
