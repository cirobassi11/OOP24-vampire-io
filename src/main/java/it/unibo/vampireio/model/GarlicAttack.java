package it.unibo.vampireio.model;

import java.awt.geom.Point2D;
import java.util.List;

/**
 * Represents a GarlicAttack that damages enemies within its radius.
 * The attack lasts for a fixed duration and deals damage to enemies
 * that come into contact with it.
 */
public final class GarlicAttack extends AbstractAttack {
    private static final long DAMAGE_TICK_MS = 200;

    private final long creationTime;
    private long lastDamageTime;

    /**
     * Constructs a GarlicAttack with the specified parameters.
     *
     * @param id            the unique identifier for the attack
     * @param position      the position of the attack in the game world
     * @param radius        the radius of the attack's effect
     * @param damage        the amount of damage dealt by the attack
     * @param duration      the duration of the attack in milliseconds
     * @param entityManager the entity manager managing game entities
     */
    public GarlicAttack(
            final String id,
            final Point2D.Double position,
            final double radius,
            final int damage,
            final long duration,
            final EntityManager entityManager) {
        super(id, position, radius, new Point2D.Double(0, 0), 0, damage, duration, entityManager);
        this.creationTime = System.currentTimeMillis();
        this.lastDamageTime = 0;
    }

    @Override
    public void onCollision(final Collidable collidable) {
        if (collidable instanceof Enemy) {
            final Enemy enemy = (Enemy) collidable;
            enemy.setGettingAttacked(true);
            enemy.dealDamage(this.getDamage());
        }
    }

    @Override
    protected void update(final long tickTime) {
        lastDamageTime += tickTime;
        if (lastDamageTime >= DAMAGE_TICK_MS) {
            CollisionManager.checkAttackCollisions(this, this.getEntityManager().getEnemies());
        }
        this.setPosition(this.getEntityManager().getCharacter().getPosition());
    }
}
