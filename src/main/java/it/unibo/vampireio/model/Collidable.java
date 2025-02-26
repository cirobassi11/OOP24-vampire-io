package it.unibo.vampireio.model;

import java.awt.Rectangle;

public interface Collidable extends Positionable {
    Rectangle getHitbox();
    boolean collidesWith(Collidable collidable);
    void onCollision(Collidable collidable);
}