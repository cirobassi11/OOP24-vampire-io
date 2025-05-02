package it.unibo.vampireio.model;

public class UnlockableCharacter extends UnlockableItem {
    private Stats characterStats;
    private String defaultWeapon;

    public UnlockableCharacter(String id, String name, String description, int price, Stats stats, String defaultWeapon) {
        super(id, name, description, price, 1);
        this.characterStats = stats;
        this.defaultWeapon = defaultWeapon;
    }

    public Stats getCharacterStats() {
        return this.characterStats;
    }

    public String getDefaultWeapon() {
        return this.defaultWeapon;
    }
}