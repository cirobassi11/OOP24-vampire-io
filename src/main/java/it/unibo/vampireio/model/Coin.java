package it.unibo.vampireio.model;

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
    protected Coin(final Double position) {
        super("collectibles/coin", position, COIN_VALUE);
    }

    @Override
    public void onCollision(final Collidable collidable) {
    }
}
