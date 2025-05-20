package it.unibo.vampireio.model;

import java.util.List;
import java.awt.geom.Point2D;

public class KnifeAttack extends AbstractAttack {
    
    private Enemy targetEnemy;

    public KnifeAttack(String id, Point2D.Double position, double radius, Point2D.Double direction, double speed, int damage, long duration, GameWorld gameWorld) {
        super(id, position, radius, direction, speed, damage, duration, gameWorld);
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
        for (Enemy enemy : gameWorld.getEnemies()) {
            if (this.getDistance(enemy) <= enemy.getRadius()) {
                this.onCollision(enemy);
                break;
            }
        }
        this.move(tickTime);
    }
    
}