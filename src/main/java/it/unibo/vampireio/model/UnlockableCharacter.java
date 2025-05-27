package it.unibo.vampireio.model;

public class UnlockableCharacter extends UnlockableItem {
    private Stats characterStats;
    private String defaultWeapon;
    private double radius;

    public UnlockableCharacter(
        final String id, 
        final String name, 
        final String description, 
        final int price, 
        final double radius, 
        final Stats stats, 
        final String defaultWeapon) {
        
        super(id, name, description, price, 1);
        this.characterStats = stats;
        this.defaultWeapon = defaultWeapon;
        this.radius = radius;
    }

    public Stats getCharacterStats() {
        return this.characterStats;
    }

    public String getDefaultWeapon() {
        return this.defaultWeapon;
    }

    public double getRadius() {
        return this.radius;
    }
}