package it.unibo.vampireio.model.impl.attacks;

import java.awt.geom.Point2D;
import it.unibo.vampireio.model.api.Attack;
import it.unibo.vampireio.model.data.StatType;
import it.unibo.vampireio.model.data.Stats;
import it.unibo.vampireio.model.impl.GameModelImpl;
import it.unibo.vampireio.model.impl.Character;
import it.unibo.vampireio.model.manager.EntityManager;
import java.awt.Dimension;

/**
 * Factory class for creating Santa Water attacks.
 * This attack spawns water in a random area around the character,
 * dealing damage to enemies within its radius.
 */
public final class SantaWaterFactory extends AbstractAttackFactory {
    private static final String ATTACK_ID = "attacks/santaWater";
    private static final double SPAWN_AREA_PERCENTAGE = 0.8;
    private static final double DAMAGE_MULTIPLIER = 1.5;

    /**
     * Constructor for SantaWaterFactory.
     * It initializes the factory with the provided EntityManager.
     *
     * @param entityManager the EntityManager to be used for creating attacks
     */
    public SantaWaterFactory(final EntityManager entityManager) {
        super(entityManager, ATTACK_ID);
    }

    @Override
    public Attack createAttack() {
        final Character character = this.getEntityManager().getCharacter();
        final Stats stats = character.getStats();
        return new SantaWaterAttack(
                this.getAttackData().getId(),
                this.getRandomPosition(),
                this.getAttackData().getRadius(),
                (int) (this.getAttackData().getDamage() * stats.getStat(StatType.MIGHT)),
                this.getAttackData().getDuration(),
                this.getEntityManager());
    }

    @Override
    public void increaseLevel() {
        super.increaseLevel();
        final int currentDamage = this.getAttackData().getDamage();
        final int newDamage = (int) (currentDamage * this.DAMAGE_MULTIPLIER);
        this.getAttackData().setDamage(newDamage);
    }

    private Point2D.Double getRandomPosition() {
        final Point2D.Double characterPosition = this.getEntityManager().getCharacter().getPosition();
        final Dimension dimension = GameModelImpl.VISUAL_SIZE;
        final double x = characterPosition.getX() + (Math.random() * dimension.getWidth() * SPAWN_AREA_PERCENTAGE)
                - (dimension.getWidth() * SPAWN_AREA_PERCENTAGE / 2);
        final double y = characterPosition.getY() + (Math.random() * dimension.getHeight() * SPAWN_AREA_PERCENTAGE)
                - (dimension.getHeight() * SPAWN_AREA_PERCENTAGE / 2);
        return new Point2D.Double(x, y);
    }
}
