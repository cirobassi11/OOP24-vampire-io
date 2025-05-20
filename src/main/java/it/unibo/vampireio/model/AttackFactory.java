package it.unibo.vampireio.model;

import java.util.Optional;

public abstract class AttackFactory {
    protected final GameWorld gameWorld;
    
    public AttackFactory(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }
    
    public abstract Attack createAttack();

    protected AttackData getDataAttackById(String id) {
        AttackData attackData = gameWorld.getDataLoader().getAttackLoader().get(id).get();
        return attackData;
    }
}