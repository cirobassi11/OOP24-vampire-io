package it.unibo.vampireio.model;

public abstract class AttackFactory {
    protected final GameWorld gameWorld;
    
    public AttackFactory(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }
    
    public abstract Attack createAttack();
}