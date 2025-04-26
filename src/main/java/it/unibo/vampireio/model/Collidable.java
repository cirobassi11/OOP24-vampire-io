package it.unibo.vampireio.model;

import java.awt.Shape;

public interface Collidable extends Positionable {
    Shape getHitbox();
    boolean collidesWith(Collidable collidable);
    void onCollision(Collidable collidable);
}