package it.unibo.vampireio.model.api;

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

    /**
     * Checks if the collectible item has been collected.
     *
     * @return true if the item is collected, false otherwise
     */
    boolean isCollected();

    /**
     * Sets the collected status of the collectible item.
     *
     * @param collected true if the item is collected, false otherwise
     */
    void setCollected(boolean collected);
}
