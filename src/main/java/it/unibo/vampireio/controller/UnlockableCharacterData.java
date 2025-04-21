package it.unibo.vampireio.controller;

public class UnlockableCharacterData {

    private String id;
    private String name;

    public UnlockableCharacterData(String id, String name) {
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