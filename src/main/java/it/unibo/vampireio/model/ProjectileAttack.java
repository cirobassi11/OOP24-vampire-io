package it.unibo.vampireio.model;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

public class ProjectileAttack extends MovableEntity implements Attack {

    private int damage;
    private String type;

    public ProjectileAttack(String id, Point2D.Double position, double radius, Point2D.Double direction, double speed, int damage, String type) {
        super(id, position, radius, direction, speed);
        this.damage = damage;
        this.type = type;
    }

    @Override
    public void execute(Double position, Double direction) {
        System.out.println("ecsechiut");
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
