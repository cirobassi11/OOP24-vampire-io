package it.unibo.vampireio.model;

public interface Collidable extends Positionable {
    double getRadius();

    boolean isColliding(Collidable collidable);

    void onCollision(Collidable collidable);
}