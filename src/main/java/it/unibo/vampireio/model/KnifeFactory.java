package it.unibo.vampireio.model;

/**
 * KnifeFactory is responsible for creating KnifeAttack instances.
 * It extends AbstractAttackFactory to utilize common attack creation logic.
 */
public final class KnifeFactory extends AbstractAttackFactory {
    private static final String ATTACK_ID = "attacks/knife";
    private static final double COOLDOWN_MULTIPLIER = 0.90;

    /**
     * Constructor for KnifeFactory.
     * Initializes the factory with the provided EntityManager.
     *
     * @param entityManager the EntityManager to be used for creating attacks
     */
    public KnifeFactory(final EntityManager entityManager) {
        super(entityManager, ATTACK_ID);
    }

    @Override
    public Attack createAttack() {
        final Character character = this.getEntityManager().getCharacter();
        final Stats stats = character.getStats();
        return new KnifeAttack(
                this.getAttackData().getId(),
                character.getPosition(),
                this.getAttackData().getRadius(),
                character.getLastDirection(),
                this.getAttackData().getSpeed() * stats.getStat(StatType.SPEED),
                (int) (this.getAttackData().getDamage() * stats.getStat(StatType.MIGHT)),
                this.getAttackData().getDuration(),
                this.getEntityManager());
    }

    @Override
    public void increaseLevel() {
        super.increaseLevel();
        final Weapon weapon = this.getEntityManager().getWeaponById("weapons/knife");
        weapon.multiplyCooldown(this.COOLDOWN_MULTIPLIER);
    }
}
