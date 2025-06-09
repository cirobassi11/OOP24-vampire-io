package it.unibo.vampireio.model;

abstract class AbstractAttackFactory {
    protected final EntityManager entityManager;
    private int currentLevel;

    AbstractAttackFactory(final EntityManager entityManager) {
        this.entityManager = entityManager;
        this.currentLevel = 1;
    }

    abstract Attack createAttack();

    AttackData getAttackDataById(final String id) {
        final AttackData attackData = DataLoader.getInstance().getAttackLoader().get(id).get();
        return attackData;
    }

    int getCurrentLevel() {
        return this.currentLevel;
    }

    void increaseLevel() {
        this.currentLevel++;
    }
}
