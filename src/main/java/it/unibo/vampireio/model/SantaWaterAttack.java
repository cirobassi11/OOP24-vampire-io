package it.unibo.vampireio.model;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.ArrayList;

public class SantaWaterAttack extends AbstractAttack {
    
    private static final long DURATION_MS = 3000;
    private static final long DAMAGE_TICK_MS = 500;
    
    private long creationTime;
    private List<Enemy> damagedEnemies;
    private long lastDamageTime;
    
    public SantaWaterAttack(
        final String id, 
        final Point2D.Double position, 
        final double radius, 
        final int damage, 
        final long duration, 
        final EntityManager entityManager) {
        super(id, position, radius, new Point2D.Double(0, 0), 0, damage, duration, entityManager);
        this.creationTime = System.currentTimeMillis();
        this.damagedEnemies = new ArrayList<>();
        this.lastDamageTime = 0;
    }
    
    private void applyAreaDamage() {
        for (Enemy enemy : this.entityManager.getEnemies()) {
            if (damagedEnemies.contains(enemy)) {
                continue;
            }
            
            if (isInAOE(enemy)) {
                onCollision(enemy);
                damagedEnemies.add(enemy);
            }
        }
    }
    
    private boolean isInAOE(Enemy enemy) {
        Point2D.Double enemyPos = enemy.getPosition();
        Point2D.Double aoePos = getPosition();
        
        double dx = enemyPos.x - aoePos.x;
        double dy = enemyPos.y - aoePos.y;
        double distance = Math.sqrt(dx * dx + dy * dy);
        
        return distance < (getRadius() + enemy.getRadius());
    }
    
    @Override
    public void onCollision(Collidable collidable) {
        if (collidable instanceof Enemy) {
            Enemy enemy = (Enemy) collidable;
            enemy.setGettingAttacked(true);
            enemy.dealDamage(this.damage);
        }
    }
    
    public long getRemainingTime() {
        return DURATION_MS - (System.currentTimeMillis() - creationTime);
    }

    @Override
    protected void update(long tickTime) {
        lastDamageTime += tickTime;
        
        if (lastDamageTime >= DAMAGE_TICK_MS) {
            applyAreaDamage();
            lastDamageTime = 0; 
            damagedEnemies.clear();
        }
    }
}