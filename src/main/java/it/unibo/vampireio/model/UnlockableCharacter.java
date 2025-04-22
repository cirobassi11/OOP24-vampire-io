package it.unibo.vampireio.model;

import java.io.Serializable;

public class UnlockableCharacter extends UnlockableItem implements Serializable {
    private Stats characterStats;

    public UnlockableCharacter(String id, String name, String description, int price, Stats stats) {
        super(id, name, description, price, 1);
        this.characterStats = stats;
    }

    public Stats getCharacterStats() {
        return this.characterStats;
    }
}