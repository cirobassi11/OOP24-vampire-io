package it.unibo.vampireio.model;

import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

public class ProjectileAttack extends MovableEntity implements Attack {

    private int damage;
    private String type;

    public ProjectileAttack(Point2D.Double position, Shape hitbox, Double direction, double speed, int damage, String type) {
        super(position, hitbox, direction, speed);
        this.damage = damage;
        this.type = type;
    }

    @Override
    public void onCollision(Collidable collidable) {
        // DANNO AL PERSONAGGIO
    }

    @Override
    public int getDamage() {
        return this.damage;
    }

    @Override
    public String getType() {
        return this.type;
    }
    
}
