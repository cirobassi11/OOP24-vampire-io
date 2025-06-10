package it.unibo.vampireio.model;

/**
 * AbstractAttackFactory is an abstract class that serves as a base for creating
 * different types of attacks.
 * It provides methods to create attacks and manage their levels.
 */
abstract class AbstractAttackFactory {
    private final EntityManager entityManager;
    private int currentLevel;
    private final AttackData attackData;

    /**
     * Constructor for AbstractAttackFactory.
     * Initializes the factory with the provided EntityManager and attack ID.
     *
     * @param entityManager the EntityManager to be used by this factory
     * @param attackID      the ID of the attack to be created
     */
    AbstractAttackFactory(final EntityManager entityManager, final String attackID) {
        this.entityManager = entityManager;
        this.currentLevel = 1;
        this.attackData = getAttackDataById(attackID);
    }

    abstract Attack createAttack();

    int getCurrentLevel() {
        return this.currentLevel;
    }

    void increaseLevel() {
        this.currentLevel++;
    }

    protected EntityManager getEntityManager() {
        return this.entityManager;
    }

    protected AttackData getAttackData() {
        return this.attackData;
    }

    private static AttackData getAttackDataById(final String id) {
        return DataLoader.getInstance().getAttackLoader().get(id).get();
    }
}
