package it.unibo.vampireio.model;

import java.io.Serializable;

/**
 * Represents an unlockable item in the game.
 * This interface defines the methods required for any unlockable item,
 * such as checking if it is locked, enhancing it, and retrieving its
 * properties.
 */
public interface Unlockable extends Serializable {
    /**
     * Checks if the unlockable item is locked.
     *
     * @return true if the item is locked, false otherwise.
     */
    boolean isLocked();

    /**
     * Enhances the unlockable item, increasing its level.
     *
     * @return true if the enhancement was successful, false otherwise.
     */
    boolean enhance();

    /**
     * Sets the current level of the unlockable item.
     *
     * @param currentLevel the new current level to set.
     */
    void setCurrentLevel(int currentLevel);

    /**
     * Retrieves the unique identifier of the unlockable item.
     *
     * @return the ID of the unlockable item.
     */
    String getId();

    /**
     * Retrieves the name of the unlockable item.
     *
     * @return the name of the unlockable item.
     */
    String getName();

    /**
     * Retrieves the description of the unlockable item.
     *
     * @return the description of the unlockable item.
     */
    String getDescription();

    /**
     * Retrieves the price of the unlockable item.
     *
     * @return the price of the unlockable item.
     */
    int getPrice();

    /**
     * Retrieves the current level of the unlockable item.
     *
     * @return the current level of the unlockable item.
     */
    int getCurrentLevel();

    /**
     * Retrieves the maximum level of the unlockable item.
     *
     * @return the maximum level of the unlockable item.
     */
    int getMaxLevel();
}
