package it.unibo.vampireio.model.impl.attacks;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.vampireio.model.api.Attack;
import it.unibo.vampireio.model.data.AttackData;
import it.unibo.vampireio.model.data.DataLoader;
import it.unibo.vampireio.model.manager.EntityManager;

/**
 * AbstractAttackFactory is an abstract class that serves as a base for creating
 * different types of attacks.
 * It provides methods to create attacks and manage their levels.
 */
public abstract class AbstractAttackFactory {
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
    @SuppressFBWarnings(
        value = "EI2", 
        justification = "The EntityManager instance is intentionally shared within AbstractAttackFactory."
        )
    public AbstractAttackFactory(final EntityManager entityManager, final String attackID) {
        this.entityManager = entityManager;
        this.currentLevel = 1;
        this.attackData = getAttackDataById(attackID);
    }

    /**
     * Creates an attack.
     *
     * @return a new Attack instance
     */
    public abstract Attack createAttack();

    /**
     * Returns the current level of the attack.
     *
     * @return the current level of the attack
     * 
     *         Subclasses overriding this method should call
     *         {@code super.getCurrentLevel()}.
     */
    int getCurrentLevel() {
        return this.currentLevel;
    }

    /**
     * Increases the level of the attack.
     * This method increments the current level by one.
     */
    public void increaseLevel() {
        this.currentLevel++;
    }

    /**
     * Returns the EntityManager associated with this factory.
     *
     * @return the EntityManager instance
     */
    protected EntityManager getEntityManager() {
        return this.entityManager;
    }

    /**
     * Returns the AttackData associated with this factory.
     *
     * @return the AttackData instance
     */
    protected AttackData getAttackData() {
        return this.attackData;
    }

    /**
     * Retrieves the AttackData by its ID.
     *
     * @param id the ID of the attack
     * @return the AttackData associated with the given ID
     */
    private static AttackData getAttackDataById(final String id) {
        return DataLoader.getInstance().getAttackLoader().get(id).get();
    }
}
