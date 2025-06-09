package it.unibo.vampireio.model;

/**
 * KnifeFactory is responsible for creating KnifeAttack instances.
 * It extends AbstractAttackFactory to utilize common attack creation logic.
 */
public class KnifeFactory extends AbstractAttackFactory {
    private static final String attackID = "attacks/knife";
    AttackData attackData = this.getAttackDataById(attackID);

    public KnifeFactory(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Attack createAttack() {
        Character character = this.entityManager.getCharacter();
        Stats stats = character.getStats();
        return new KnifeAttack(
                attackData.getId(),
                character.getPosition(),
                attackData.getRadius(),
                character.getLastDirection(),
                attackData.getSpeed() * stats.getStat(StatType.SPEED),
                (int) (attackData.getDamage() * stats.getStat(StatType.MIGHT)),
                attackData.getDuration(),
                entityManager);
    }

    @Override
    public void increaseLevel() {
        super.increaseLevel();
        Weapon weapon = this.entityManager.getWeaponById("weapons/knife");
        weapon.multiplyCooldown(0.90);
    }
}