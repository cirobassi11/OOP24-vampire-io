package it.unibo.vampireio.controller.data;

import java.io.Serializable;

/**
 * Represents an unlockable item in the game.
 * This class encapsulates the item's ID, name, description,
 * current level, maximum level, and price.
 */
public final class UnlockableItemData implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String id;
    private final String name;
    private final String description;
    private final int currentLevel;
    private final int maxLevel;
    private final int price;

    /**
     * Constructs an UnlockableItemData instance with the specified parameters.
     *
     * @param id           the unique identifier of the item
     * @param name         the name of the item
     * @param description  a brief description of the item
     * @param currentLevel the current level of the item
     * @param maxLevel     the maximum level the item can reach
     * @param price        the price of the item in game currency
     */
    public UnlockableItemData(
            final String id,
            final String name,
            final String description,
            final int currentLevel,
            final int maxLevel,
            final int price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.currentLevel = currentLevel;
        this.maxLevel = maxLevel;
        this.price = price;
    }

    /**
     * Returns the unique identifier of the item.
     *
     * @return the item's ID
     */
    public String getId() {
        return this.id;
    }

    /**
     * Returns the name of the item.
     *
     * @return the item's name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns a description of the item.
     *
     * @return the item's description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns the current level of the item.
     *
     * @return the item's current level
     */
    public int getCurrentLevel() {
        return this.currentLevel;
    }

    /**
     * Returns the maximum level the item can reach.
     *
     * @return the item's maximum level
     */
    public int getMaxLevel() {
        return this.maxLevel;
    }

    /**
     * Returns the price of the item in game currency.
     *
     * @return the item's price
     */
    public int getPrice() {
        return this.price;
    }
}
