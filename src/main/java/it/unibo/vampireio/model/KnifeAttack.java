package it.unibo.vampireio.model;

import java.awt.geom.Point2D;

public class KnifeAttack extends AbstractAttack {
    
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