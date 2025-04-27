package it.unibo.vampireio.model;

public abstract class UnlockableItem implements Unlockable, Identifiable {
    private static final long serialVersionUID = 1L;

    private final String id;
    private final String name;
    private final String description;
    private final int price;
    private int currentLevel;
    private int maxLevel;

    public UnlockableItem(final String id, final String name, final String description, final int price, final int maxLevel) {
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
        if(this.currentLevel >= this.maxLevel) {
            return false;
        }
        this.currentLevel++;
        return true;
    }

    @Override
    public void setCurrentLevel(int currentLevel) {
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
