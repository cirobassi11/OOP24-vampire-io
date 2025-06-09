package it.unibo.vampireio.model;

/**
 * Abstract class representing an unlockable item in the game.
 * It implements the Unlockable and Identifiable interfaces.
 */
public abstract class AbstractUnlockableItem implements Unlockable, Identifiable {
    private static final long serialVersionUID = 1L;

    private final String id;
    private final String name;
    private final String description;
    private final int price;
    private final int maxLevel;
    private int currentLevel;

    /**
     * Constructor for AbstractUnlockableItem.
     *
     * @param id          the unique identifier of the item
     * @param name        the name of the item
     * @param description a brief description of the item
     * @param price       the price of the item
     * @param maxLevel    the maximum level this item can be enhanced to
     */
    public AbstractUnlockableItem(final String id, final String name, final String description, final int price,
            final int maxLevel) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.currentLevel = 0;
        this.maxLevel = maxLevel;
    }

    @Override
    public boolean isLocked() {
        return this.currentLevel > 0;
    }

    @Override
    public boolean enhance() {
        if (this.currentLevel >= this.maxLevel) {
            return false;
        }
        this.currentLevel++;
        return true;
    }

    @Override
    public void setCurrentLevel(final int currentLevel) {
        this.currentLevel = currentLevel;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public int getPrice() {
        return this.price;
    }

    @Override
    public int getCurrentLevel() {
        return this.currentLevel;
    }

    @Override
    public int getMaxLevel() {
        return this.maxLevel;
    }
}
