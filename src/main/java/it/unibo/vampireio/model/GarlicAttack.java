package it.unibo.vampireio.model;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.ArrayList;

public class GarlicAttack extends AbstractAttack {

    private static final long DURATION_MS = 1000;
    private static final long DAMAGE_TICK_MS = 200;

    private long creationTime;
    private List<Enemy> damagedEnemies;
    private long lastDamageTime;

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
    protected void update(final long tickTime) {
        lastDamageTime += tickTime;
        if (lastDamageTime >= DAMAGE_TICK_MS) {
            CollisionManager.checkAttackCollisions(this, this.entityManager.getEnemies());
        }
        this.setPosition(this.entityManager.getCharacter().getPosition());
    }
}