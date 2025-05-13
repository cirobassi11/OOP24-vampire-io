package it.unibo.vampireio.model;

public abstract class AttackFactory {
    protected final GameWorld gameWorld;
    protected final int damage;
    protected final double speed;
    
    public AttackFactory(GameWorld gameWorld, int damage, double speed) {
        this.gameWorld = gameWorld;
        this.damage = damage;
        this.speed = speed;
    }
    
    public abstract Attack createAttack();
    
    public int getDamage() {
        return damage;
    }
    
    public double getSpeed() {
        return speed;
    }

    protected GameWorld getGameWorld() {
        return gameWorld;
    }
}