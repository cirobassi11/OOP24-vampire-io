package it.unibo.vampireio.model;

import java.awt.geom.Point2D;

public abstract class AbstractAttack extends MovableEntity implements Attack {

    protected GameWorld gameWorld;
    protected int damage;
    protected long duration;
    protected boolean expired = false;
    private long elapsedTime = 0;

    public AbstractAttack(
        final String id, 
        final Point2D.Double position, 
        final double radius, 
        final Point2D.Double direction, 
        final double speed, 
        final int damage, 
        final long duration, 
        final GameWorld gameWorld) {
        super(id, position, radius, direction, speed);
        this.damage = damage;
        this.gameWorld = gameWorld;
        this.duration = duration;
    }

    @Override
    public void execute(long tickTime) {
        this.elapsedTime += tickTime;
        if (elapsedTime > duration) {
            this.expired = true;
            return;
        }
        this.update(tickTime);
    }

    protected abstract void update(long tickTime);

    @Override
    public abstract void onCollision(Collidable collidable);

    @Override
    public boolean isExpired() {
        return this.expired;
    }
}
