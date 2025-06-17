package it.unibo.vampireio.model.impl.attacks;

import java.awt.geom.Point2D;
import java.util.List;
import it.unibo.vampireio.model.api.Collidable;
import it.unibo.vampireio.model.api.Living;
import it.unibo.vampireio.model.impl.Enemy;
import it.unibo.vampireio.model.manager.CollisionManager;
import it.unibo.vampireio.model.manager.EntityManager;

/**
 * Represents an attack with a magic wand that targets the nearest enemy.
 * If no enemies are present, it will move in a random direction.
 */
public final class MagicWandAttack extends AbstractAttack {

    private final Living targetEnemy;

    /**
     * Constructs a MagicWandAttack with the specified parameters.
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
    public MagicWandAttack(
            final String id,
            final Point2D.Double position,
            final double radius,
            final Point2D.Double direction,
            final double speed,
            final int damage,
            final long duration,
            final EntityManager entityManager) {

        super(id, position, radius, direction, speed, damage, duration, entityManager);
        this.targetEnemy = findNearestEnemy();
        if (this.targetEnemy == null) {
            this.setDirection(this.getRandomDirection());
        }
    }

    private Living findNearestEnemy() {
        double minDistance = Double.MAX_VALUE;
        Living nearest = null;

        for (final Living enemy : this.getEntityManager().getEnemies()) {
            final Point2D.Double enemyPos = enemy.getPosition();
            final Point2D.Double currentPos = this.getPosition();

            final double dx = enemyPos.x - currentPos.x;
            final double dy = enemyPos.y - currentPos.y;
            final double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance < minDistance) {
                minDistance = distance;
                nearest = enemy;
            }
        }
        return nearest;
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

    private Point2D.Double getRandomDirection() {
        final double angle = Math.random() * 2 * Math.PI;
        return new Point2D.Double(Math.cos(angle), Math.sin(angle));
    }

    @Override
    protected void update(final long tickTime) {
        if (this.targetEnemy != null) {
            this.setDirectionTorwards(targetEnemy);
            CollisionManager.checkAttackCollisions(this, List.of(this.targetEnemy));
        }
        this.move(tickTime);
    }
}
