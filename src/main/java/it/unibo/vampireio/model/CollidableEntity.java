package it.unibo.vampireio.model;

import java.awt.Rectangle;
import java.awt.geom.Point2D.Double;

public abstract class CollidableEntity extends PositionableEntity implements Collidable {
    private Rectangle hitbox;
    
    protected CollidableEntity(Double position, Rectangle hitbox) {
        super(position);
        this.hitbox = hitbox;
    }

    @Override
    public Rectangle getHitbox() {
        return hitbox;
    }

    @Override
    public boolean collidesWith(Collidable collidable) {
        return hitbox.intersects(collidable.getHitbox());
    }

}