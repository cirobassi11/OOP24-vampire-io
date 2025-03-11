package it.unibo.vampireio.model;

public class UnlockablePowerUp extends UnlockableItem {
    private int might;
    private int armor;
    private int maxHealth;
    private int recovery;
    private int cooldown;
    private int amount;
    private int movementSpeed;
    private int growth;
    private int greed;
    private int projectileSpeed;
    private int area;
    
    public UnlockablePowerUp(String name, int price) {
        super(name, price);
        this.might = 0;
        this.armor = 0;
        this.maxHealth = 0;
        this.recovery = 0;
        this.cooldown = 0;
        this.amount = 0;
        this.movementSpeed = 0;
        this.growth = 0;
        this.greed = 0;
        this.projectileSpeed = 0;
        this.area = 0;
    }

    public int getMight() {
        return this.might;
    }

    public int getArmor() {
        return this.armor;
    }
    
    public int getMaxHealth() {
        return this.maxHealth;
    }
    
    public int getRecovery() {
        return this.recovery;
    }
    
    public int getCooldown() {
        return this.cooldown;
    }

    public int getAmount() {
        return this.amount;
    }
    
    public int getMovementSpeed() {
        return this.movementSpeed;
    }
    
    public int getGrowth() {
        return this.growth;
    }

    public int getGreed() {
        return this.greed;
    }
    
    public int getProjectileSpeed() {
        return this.projectileSpeed;
    }
    
    public int getArea() {
        return this.area;
    }

    //@Override
    //public int getPrice() {
        //return this.price;
    //}
}
