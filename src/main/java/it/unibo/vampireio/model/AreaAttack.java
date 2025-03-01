package it.unibo.vampireio.model;

import java.awt.Shape;
import java.awt.geom.Point2D;

public class AreaAttack extends CollidableEntity implements Attack {

    private int damage;
    private String type;

    public AreaAttack(Point2D.Double position, Shape hitbox, int damage, String type) {
        super(position, hitbox);
        this.damage = damage;
        this.type = type;
    }

    @Override
    public void onCollision(Collidable collidable) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onCollision'");
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
