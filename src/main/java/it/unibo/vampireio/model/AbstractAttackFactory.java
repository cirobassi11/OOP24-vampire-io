package it.unibo.vampireio.model;

public abstract class AbstractAttackFactory {
    protected final EntityManager entityManager;
    private int currentLevel;

    public AbstractAttackFactory(final EntityManager entityManager) {
        this.entityManager = entityManager;
        this.currentLevel = 1;
    }

    public abstract Attack createAttack();

    protected AttackData getAttackDataById(final String id) {
        final AttackData attackData = DataLoader.getInstance().getAttackLoader().get(id).get();
        return attackData;
    }

    public int getCurrentLevel() {
        return this.currentLevel;
    }

    public void increaseLevel() {
        this.currentLevel++;
    }
}
