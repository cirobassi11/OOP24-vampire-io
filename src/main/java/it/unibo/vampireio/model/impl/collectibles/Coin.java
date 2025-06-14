package it.unibo.vampireio.model.impl.collectibles;

import java.awt.geom.Point2D.Double;

/**
 * Represents a collectible coin in the game.
 */
public class Coin extends AbstractCollectibleItem {

    private static final int COIN_VALUE = 1;

    /**
     * Constructs a new Coin at the specified position.
     *
     * @param position the position of the coin
     */
    public Coin(final Double position) {
        super("collectibles/coin", position, COIN_VALUE);
    }
}
