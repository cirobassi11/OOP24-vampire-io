package it.unibo.vampireio.model;

import java.awt.Shape;
import java.awt.geom.Point2D;

public class AreaAttack extends CollidableEntity implements Attack {

    private int damage;
    private String type;

    public AreaAttack(String id, Point2D.Double position, Shape hitbox, int damage, String type) {
        super(id, position, hitbox);
        this.damage = damage;
        this.type = type;
    }

    @Override
    public void onCollision(Collidable collidable) {
        //DANNO AL PERSONAGGIO
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
