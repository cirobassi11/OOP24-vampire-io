package it.unibo.vampireio.model;

import java.awt.geom.Point2D;

public abstract class ProjectileAttack extends MovableEntity implements Attack {

    protected GameWorld gameWorld;
    protected int damage;

    public ProjectileAttack(String id, Point2D.Double position, double radius, Point2D.Double direction, double speed, int damage, GameWorld gameWorld) {
        super(id, position, radius, direction, speed);
        this.damage = damage;
        this.gameWorld = gameWorld;
    }

    public abstract void execute();

    @Override
    public abstract void onCollision(Collidable collidable);
}
