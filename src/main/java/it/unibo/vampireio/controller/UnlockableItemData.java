package it.unibo.vampireio.controller;

public class UnlockableItemData {

    private String id;
    private String name;
    private String description;
    private int currentLevel;
    private int maxLevel;
    private int price;

    public UnlockableItemData(final String id, final String name, final String description, final int currentLevel,
            final int maxLevel, final int price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.currentLevel = currentLevel;
        this.maxLevel = maxLevel;
        this.price = price;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public int getCurrentLevel() {
        return this.currentLevel;
    }

    public int getMaxLevel() {
        return this.maxLevel;
    }

    public int getPrice() {
        return this.price;
    }
}
