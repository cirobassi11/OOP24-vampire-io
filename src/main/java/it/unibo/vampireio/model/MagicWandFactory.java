package it.unibo.vampireio.model;

/**
 * Factory class for creating MagicWandAttack instances.
 * It extends AbstractAttackFactory and provides the implementation
 * for creating a MagicWandAttack with specific parameters.
 */
final class MagicWandFactory extends AbstractAttackFactory {
    private static final String attackID = "attacks/magicWand";
    AttackData attackData = this.getAttackDataById(attackID);
    
    /**
     * Constructor for MagicWandFactory.
     * It initializes the factory with the provided EntityManager.
     *
     * @param entityManager the EntityManager to be used for creating attacks
     */
    public MagicWandFactory(EntityManager entityManager) {
        super(entityManager);
    }
    
    @Override
    public Attack createAttack() {
        Character character = this.entityManager.getCharacter();
        Stats stats = character.getStats();
        return new MagicWandAttack(
            attackData.getId(),
            character.getPosition(),
            attackData.getRadius(),
            character.getDirection(),
            attackData.getSpeed() * stats.getStat(StatType.SPEED),
            (int) (attackData.getDamage() * stats.getStat(StatType.MIGHT)),
            attackData.getDuration(),
            entityManager
        );
    }

    @Override
    public void increaseLevel() {
        super.increaseLevel();
        Weapon weapon = this.entityManager.getWeaponById("weapons/magicWand");
        weapon.multiplyCooldown(0.90);
    }
}