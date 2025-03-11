package it.unibo.vampireio.model;

public class UnlockablePowerUp extends UnlockableItem {
    private double value;
    private int level;
    
    public UnlockablePowerUp(String id, String name, String description, int price, double value) {
        super(id, name, description, price);
        this.value = value;
        this.level = 0;    
    }
}