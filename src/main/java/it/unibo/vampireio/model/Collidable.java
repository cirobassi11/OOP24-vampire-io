package it.unibo.vampireio.model;

import java.awt.Rectangle;

public interface Collidable {
    Rectangle getHitbox();
    boolean isCollidingWith(Collidable collidable);
    void onCollision(Collidable collidable);
}