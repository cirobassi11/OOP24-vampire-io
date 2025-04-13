package it.unibo.vampireio.model;

public class UnlockableCharacter extends UnlockableItem {
    private Stats characterStats;

    public UnlockableCharacter(String id, String name, String description, int price, Stats stats) {
        super(id, name, description, price, 1);
        this.characterStats = stats;
    }
}