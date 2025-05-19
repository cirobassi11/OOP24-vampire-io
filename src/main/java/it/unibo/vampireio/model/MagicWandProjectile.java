package it.unibo.vampireio.model;

import java.awt.geom.Point2D;

public class MagicWandProjectile extends ProjectileAttack {
    
    private Enemy targetEnemy;

    public MagicWandProjectile(String id, Point2D.Double position, double radius, Point2D.Double direction, double speed, int damage, GameWorld gameWorld) {
        super(id, position, radius, direction, speed, damage, gameWorld);
        this.targetEnemy = findNearestEnemy();
        if(this.targetEnemy == null) {
            this.setDirection(this.getRandomDirection());
        }
    }
    
    @Override
    public void execute() {
        if (this.targetEnemy != null) {
            Point2D.Double enemyPos = targetEnemy.getPosition();
            Point2D.Double characterPos = getPosition();
            
            double dx = enemyPos.x - characterPos.x;
            double dy = enemyPos.y - characterPos.y;
            double length = Math.sqrt(dx * dx + dy * dy);
            
            if (length > 0) {
                setDirection(new Point2D.Double(dx / length, dy / length));
            }

            if(length < getRadius() + targetEnemy.getRadius()) {
                this.onCollision(targetEnemy);
            }
        }
    }

    private Enemy findNearestEnemy() {
        double minDistance = Double.MAX_VALUE;
        Enemy nearest = null;
        
        for (Enemy enemy : gameWorld.getEnemies()) {
            Point2D.Double enemyPos = enemy.getPosition();
            Point2D.Double characterPos = getPosition();
            
            double dx = enemyPos.x - characterPos.x;
            double dy = enemyPos.y - characterPos.y;
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
            gameWorld.removeProjectileAttack(this);
        }
    }

    private Point2D.Double getRandomDirection() {
        double angle = Math.random() * 2 * Math.PI;
        return new Point2D.Double(Math.cos(angle), Math.sin(angle));
    }
}