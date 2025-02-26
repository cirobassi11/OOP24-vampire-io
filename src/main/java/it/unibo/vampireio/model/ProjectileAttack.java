package it.unibo.vampireio.model;

import java.awt.Rectangle;
import java.awt.geom.Point2D.Double;

public abstract class ProjectileAttack extends MovableEntity implements Attack {

    protected ProjectileAttack(Double position, Rectangle hitbox, Double direction, double speed) {
        super(position, hitbox, direction, speed);
    }

    @Override
    public void onCollision(Collidable collidable) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onCollision'");
    }
    
}
