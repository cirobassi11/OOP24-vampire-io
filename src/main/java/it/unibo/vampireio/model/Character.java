package it.unibo.vampireio.model;

import java.awt.Shape;
import java.awt.geom.Point2D;

public class Character extends LivingEntity {

    protected Character(String id, Point2D.Double position, Shape hitbox, Point2D.Double direction, double speed) {
        super(id, position, hitbox, direction, speed);
    }

    @Override
    public void onCollision(Collidable collidable) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onCollision'");
    }
    
}
