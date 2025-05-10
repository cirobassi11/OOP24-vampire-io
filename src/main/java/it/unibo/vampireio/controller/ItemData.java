package it.unibo.vampireio.controller;

public class ItemData {

    private String id;
    private String name;
    private String description;
    private int currentLevel;
    private int maxLevel;
    
    public ItemData(String id, String name, String description, int currentLevel, int maxLevel) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.currentLevel = currentLevel;
        this.maxLevel = maxLevel;
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
}