package it.unibo.vampireio.model;

public interface Collidable extends Positionable {
    double getRadius();
    void onCollision(Collidable collidable);
}