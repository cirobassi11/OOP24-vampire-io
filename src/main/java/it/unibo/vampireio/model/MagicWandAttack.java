package it.unibo.vampireio.model;

import java.awt.geom.Point2D;
import java.util.List;

/**
 * Represents an attack with a magic wand that targets the nearest enemy.
 * If no enemies are present, it will move in a random direction.
 */
public final class MagicWandAttack extends AbstractAttack {

    private Living targetEnemy;

    /**
     * Constructs a MagicWandAttack with the specified parameters.
     *
     * @param id          the unique identifier for the attack
     * @param position    the initial position of the attack
     * @param radius      the radius of the attack
     * @param direction   the initial direction of the attack
     * @param speed       the speed of the attack
     * @param damage      the damage dealt by the attack
     * @param duration    the duration of the attack in milliseconds
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

        for (Living enemy : entityManager.getEnemies()) {
            Point2D.Double enemyPos = enemy.getPosition();
            Point2D.Double currentPos = this.getPosition();

            double dx = enemyPos.x - currentPos.x;
            double dy = enemyPos.y - currentPos.y;
            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance < minDistance) {
                minDistance = distance;
                nearest = enemy;
            }
        }
        return nearest;
    }

    @Override
    public void onCollision(Collidable collidable) {
        if (collidable instanceof Enemy) {
            Enemy enemy = (Enemy) collidable;
            enemy.setGettingAttacked(true);
            enemy.dealDamage(this.damage);
            this.expired = true;
        }
    }

    private Point2D.Double getRandomDirection() {
        double angle = Math.random() * 2 * Math.PI;
        return new Point2D.Double(Math.cos(angle), Math.sin(angle));
    }

    @Override
    protected void update(long tickTime) {
        if (this.targetEnemy != null) {
            Point2D.Double enemyPos = this.targetEnemy.getPosition();
            Point2D.Double currentPos = this.getPosition();

            double dx = enemyPos.x - currentPos.x;
            double dy = enemyPos.y - currentPos.y;
            double length = Math.sqrt(dx * dx + dy * dy);

            if (length > 0) {
                this.setDirection(new Point2D.Double(dx / length, dy / length));
            }

            CollisionManager.checkAttackCollisions(this, List.of(this.targetEnemy));
        }
        this.move(tickTime);
    }
}