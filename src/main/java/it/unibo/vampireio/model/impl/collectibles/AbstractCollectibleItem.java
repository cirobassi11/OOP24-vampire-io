package it.unibo.vampireio.model.impl.collectibles;

import java.awt.geom.Point2D.Double;
import it.unibo.vampireio.model.api.Collectible;
import it.unibo.vampireio.model.api.Collidable;
import it.unibo.vampireio.model.impl.AbstractCollidableEntity;
import it.unibo.vampireio.model.impl.Character;

/**
 * AbstractCollectibleItem is an abstract class that represents a collectible item in the game.
 * It extends AbstractCollidableEntity and implements the Collectible interface.
 * This class provides basic functionality for collectible items, including their value and radius.
 */
public abstract class AbstractCollectibleItem extends AbstractCollidableEntity implements Collectible {

    private static final double COLLECTIBLE_RADIUS = 10;
    private final int value;
    private boolean collected;

    /**
     * Constructs a new AbstractCollectibleItem with the specified parameters.
     *
     * @param id       the unique identifier for the collectible item
     * @param position the initial position of the collectible item
     * @param value    the value of the collectible item
     */
    protected AbstractCollectibleItem(final String id, final Double position, final int value) {
        super(id, position, COLLECTIBLE_RADIUS);
        this.value = value;
    }

    @Override
    public final int getValue() {
        return this.value;
    }

    @Override
    public final boolean isCollected() {
        return this.collected;
    }

    @Override
    public final void setCollected(final boolean collected) {
        this.collected = collected;
    }

    @Override
    public final void onCollision(final Collidable collidable) {
        if (collidable instanceof Character && !this.isCollected()) {
            final Character character = (Character) collidable;
            character.collect(this);
            this.setCollected(true);
        }
    }
}
