package it.unibo.vampireio.model;

public class UnlockablePowerUp extends UnlockableItem {
    private static final double MULTIPLIER_INCREMENT = 0.10;
    private StatType statToModify;

    public UnlockablePowerUp(String id, String name, String description, int price, int maxLevel, StatType statToModify) {
        super(id, name, description, price, maxLevel);
        this.statToModify = statToModify;
    }

    public double getMultiplier() {
        return 1 + this.getCurrentLevel() * MULTIPLIER_INCREMENT;
    }
    
    public StatType getStatToModify() {
        return this.statToModify;
    } 
}