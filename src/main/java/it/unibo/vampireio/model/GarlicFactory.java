package it.unibo.vampireio.model;

/**
 * Factory class for creating GarlicAttack instances.
 * Increases the attack's radius with each level up.
 */
public final class GarlicFactory extends AbstractAttackFactory {
    private static final String ATTACK_ID = "attacks/garlic";
    private static final double RADIUS_INCREASE_RATIO = 1.02;

    /**
     * Constructor for GarlicFactory.
     * Initializes the factory with the provided EntityManager.
     *
     * @param entityManager the EntityManager to be used by this factory
     */
    public GarlicFactory(final EntityManager entityManager) {
        super(entityManager, ATTACK_ID);
    }

    @Override
    public Attack createAttack() {
        final Character character = this.getEntityManager().getCharacter();
        final Stats stats = character.getStats();
        return new GarlicAttack(
                this.getAttackData().getId(),
                character.getPosition(),
                this.getAttackData().getRadius(),
                (int) (this.getAttackData().getDamage() * stats.getStat(StatType.MIGHT)),
                this.getAttackData().getDuration(),
                this.getEntityManager());
    }

    @Override
    public void increaseLevel() {
        super.increaseLevel();
        final double currentRadius = this.getAttackData().getRadius();
        final double newRadius = currentRadius * RADIUS_INCREASE_RATIO;
        this.getAttackData().setRadius(newRadius);
    }
}
