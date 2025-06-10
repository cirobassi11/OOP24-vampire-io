package it.unibo.vampireio.controller;

/**
 * Represents an item in the game with its ID, name, and description.
 * This class is used to encapsulate the data related to an item.
 */
public final class ItemData {

    private final String id;
    private final String name;
    private final String description;

    /**
     * Constructs an ItemData instance with the specified parameters.
     *
     * @param id          the unique identifier of the item
     * @param name        the name of the item
     * @param description a brief description of the item
     */
    public ItemData(
            final String id,
            final String name,
            final String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    /**
     * Returns the id of the item.
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
     * Returns the description of the item.
     *
     * @return the item's description
     */
    public String getDescription() {
        return this.description;
    }
}
