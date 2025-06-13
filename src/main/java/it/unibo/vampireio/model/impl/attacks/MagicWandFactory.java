package it.unibo.vampireio.model.impl.attacks;

import it.unibo.vampireio.model.api.Attack;
import it.unibo.vampireio.model.api.Weapon;
import it.unibo.vampireio.model.data.StatType;
import it.unibo.vampireio.model.data.Stats;
import it.unibo.vampireio.model.manager.EntityManager;
import it.unibo.vampireio.model.impl.Character;

/**
 * Factory class for creating MagicWandAttack instances.
 * It extends AbstractAttackFactory and provides the implementation
 * for creating a MagicWandAttack with specific parameters.
 */
public final class MagicWandFactory extends AbstractAttackFactory {
    private static final String ATTACK_ID = "attacks/magicWand";
    private static final double COOLDOWN_MULTIPLIER = 0.90;

    /**
     * Constructor for MagicWandFactory.
     * It initializes the factory with the provided EntityManager.
     *
     * @param entityManager the EntityManager to be used for creating attacks
     */
    public MagicWandFactory(final EntityManager entityManager) {
        super(entityManager, ATTACK_ID);
    }

    @Override
    public Attack createAttack() {
        final Character character = this.getEntityManager().getCharacter();
        final Stats stats = character.getStats();
        return new MagicWandAttack(
                this.getAttackData().getId(),
                character.getPosition(),
                this.getAttackData().getRadius(),
                character.getDirection(),
                this.getAttackData().getSpeed() * stats.getStat(StatType.SPEED),
                (int) (this.getAttackData().getDamage() * stats.getStat(StatType.MIGHT)),
                this.getAttackData().getDuration(),
                this.getEntityManager());
    }

    @Override
    public void increaseLevel() {
        super.increaseLevel();
        final Weapon weapon = this.getEntityManager().getWeaponById("weapons/magicWand");
        weapon.multiplyCooldown(COOLDOWN_MULTIPLIER);
    }
}
