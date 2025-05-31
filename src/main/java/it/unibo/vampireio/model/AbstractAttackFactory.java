package it.unibo.vampireio.model;

public abstract class AbstractAttackFactory {
    protected final GameWorld gameWorld;
    private int currentLevel;

    public AbstractAttackFactory(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
        this.currentLevel = 1;
    }

    public abstract Attack createAttack();

    protected AttackData getAttackDataById(String id) {
        AttackData attackData = gameWorld.getDataLoader().getAttackLoader().get(id).get();
        return attackData;
    }

    public int getCurrentLevel() {
        return this.currentLevel;
    }

    public void increaseLevel() {
        this.currentLevel++;
    }
}