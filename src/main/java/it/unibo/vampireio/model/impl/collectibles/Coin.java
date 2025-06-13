package it.unibo.vampireio.model.impl.collectibles;

import java.awt.geom.Point2D.Double;
import it.unibo.vampireio.model.api.Collidable;

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

    @Override
    public void onCollision(final Collidable collidable) {
    }
}
