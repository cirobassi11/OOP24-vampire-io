package it.unibo.vampireio.model;

import java.awt.geom.Point2D;

public class MagicWandAttack extends AbstractAttack {
    
    private Enemy targetEnemy;

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
        if(this.targetEnemy == null) {
            this.setDirection(this.getRandomDirection());
        }
    }

    private Enemy findNearestEnemy() {
        double minDistance = Double.MAX_VALUE;
        Enemy nearest = null;
        
        for (Enemy enemy : entityManager.getEnemies()) {
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

            if(length < this.getRadius()) {
                this.onCollision(this.targetEnemy);
            }
        }
        this.move(tickTime);
    }
}