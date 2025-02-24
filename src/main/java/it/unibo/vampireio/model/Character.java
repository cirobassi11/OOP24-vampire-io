package it.unibo.vampireio.model;

import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

public class Character extends LivingEntity {
    
    private int damage;

    public Character(Point2D.Double position, int maxHealth, int damage, int speed, Point2D.Double direction) {
        super(position, maxHealth, speed, direction);
        this.damage = damage;
    }

    @Override
    public void setDirection(Double direction) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setDirection'");
    }

    @Override
    public void move(Double positionOffset) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'move'");
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public Rectangle getHitbox() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getHitbox'");
    }

    @Override
    public boolean isCollidingWith(Collidable collidable) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isCollidingWith'");
    }

    @Override
    public void onCollision(Collidable collidable) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onCollision'");
    }
}
