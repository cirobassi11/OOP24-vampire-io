package it.unibo.vampireio.model.impl.attacks;

import java.awt.geom.Point2D;

import it.unibo.vampireio.model.api.Attack;
import it.unibo.vampireio.model.api.Weapon;
import it.unibo.vampireio.model.data.StatType;
import it.unibo.vampireio.model.data.Stats;
import it.unibo.vampireio.model.manager.EntityManager;
import it.unibo.vampireio.model.impl.Character;

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
                new Point2D.Double(
                        character.getPosition().getX()
                                + character.getLastDirection().getX() * this.getAttackData().getRadius(),
                        character.getPosition().getY()
                                + character.getLastDirection().getY() * this.getAttackData().getRadius()),
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
        weapon.multiplyCooldown(COOLDOWN_MULTIPLIER);
    }
}
