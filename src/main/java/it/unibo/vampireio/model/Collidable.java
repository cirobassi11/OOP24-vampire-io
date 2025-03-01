package it.unibo.vampireio.model;

import java.awt.geom.Area;

public interface Collidable extends Positionable {
    Area getHitbox();
    boolean collidesWith(Collidable collidable);
    void onCollision(Collidable collidable);
}