package it.unibo.vampireio.controller;

public class CharacterData {

    private String id;
    private String name;

    public CharacterData(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
}