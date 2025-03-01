package it.unibo.vampireio.model;

import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Point2D;

public abstract class CollidableEntity extends PositionableEntity implements Collidable {
    private Area hitBoxArea;
    
    protected CollidableEntity(Point2D.Double position, Shape hitbox) {
        super(position);
        this.hitBoxArea = new Area(hitbox);
    }

    @Override
    public Area getHitbox() {
        return this.hitBoxArea;
    }

    @Override
    public boolean collidesWith(Collidable collidable) {
        Area intersection = new Area(this.getHitbox());
        intersection.intersect(collidable.getHitbox());
        return !intersection.isEmpty();
    }
}