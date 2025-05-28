package it.unibo.vampireio.controller;

public class UnlockableCharacterData {

    private String id;
    private String name;
    private String description;
    private int price;

    public UnlockableCharacterData(final String id, final String name, final String description, final int price) {
        this.id = id;
        this.name = name;
        this.description = description;
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

    public int getPrice() {
        return this.price;
    }
}