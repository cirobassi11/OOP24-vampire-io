package it.unibo.vampireio.model;

import java.awt.geom.Point2D;

public class AreaAttack extends CollidableEntity implements Attack {

    private int damage;
    private String type;

    public AreaAttack(String id, Point2D.Double position, double radius, int damage, String type) {
        super(id, position, radius);
        this.damage = damage;
        this.type = type;
    }

    @Override
    public void onCollision(Collidable collidable) {
        //DANNO AL NEMICO
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
