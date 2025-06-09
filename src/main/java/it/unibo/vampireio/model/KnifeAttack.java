package it.unibo.vampireio.model;

import java.awt.geom.Point2D;

/**
 * Represents a knife attack in the game.
 * This attack deals damage to enemies it collides with and expires after a certain duration.
 */
public final class KnifeAttack extends AbstractAttack {
    
    /**
     * Constructs a KnifeAttack with the specified parameters.
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
    public void onCollision(Collidable collidable) {
        if (collidable instanceof Enemy) {
            Enemy enemy = (Enemy) collidable;
            enemy.setGettingAttacked(true);
            enemy.dealDamage(this.damage);
            this.expired = true;
        }
    }

    @Override
    protected void update(long tickTime) {
        CollisionManager.checkAttackCollisions(this, this.entityManager.getEnemies());
        this.move(tickTime);
    }
    
}
