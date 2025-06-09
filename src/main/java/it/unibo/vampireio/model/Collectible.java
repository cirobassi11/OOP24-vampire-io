package it.unibo.vampireio.model;

/**
 * Represents a collectible item in the game that can be collected by the character.
 */
public interface Collectible extends Collidable {
    /**
     * Returns the value of the collectible item.
     *
     * @return the value of the collectible
     */
    int getValue();
}
