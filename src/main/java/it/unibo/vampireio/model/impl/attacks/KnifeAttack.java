package it.unibo.vampireio.model.impl.attacks;

import java.awt.geom.Point2D;
import it.unibo.vampireio.model.api.Collidable;
import it.unibo.vampireio.model.impl.Enemy;
import it.unibo.vampireio.model.manager.CollisionManager;
import it.unibo.vampireio.model.manager.EntityManager;

/**
 * Represents a knife attack in the game.
 * This attack deals damage to enemies it collides with and expires after a
 * certain duration.
 */
public final class KnifeAttack extends AbstractAttack {

    /**
     * Constructs a KnifeAttack with the specified parameters.
     *
     * @param id            the unique identifier for the attack
     * @param position      the initial position of the attack
     * @param radius        the radius of the attack
     * @param direction     the initial direction of the attack
     * @param speed         the speed of the attack
     * @param damage        the damage dealt by the attack
     * @param duration      the duration of the attack in milliseconds
     * @param entityManager the entity manager to manage entities in the game
     */
    public KnifeAttack(
            final String id,
            final Point2D.Double position,
            final double radius,
            final Point2D.Double direction,
            final double speed,
            final int damage,
            final long duration,
            final EntityManager entityManager) {
        super(id, position, radius, direction, speed, damage, duration, entityManager);
    }

    @Override
    public void onCollision(final Collidable collidable) {
        if (collidable instanceof Enemy) {
            final Enemy enemy = (Enemy) collidable;
            enemy.setGettingAttacked(true);
            enemy.dealDamage(this.getDamage());
            this.expire();
        }
    }

    @Override
    protected void update(final long tickTime) {
        CollisionManager.checkAttackCollisions(this, this.getEntityManager().getEnemies());
        this.move(tickTime);
    }

}
