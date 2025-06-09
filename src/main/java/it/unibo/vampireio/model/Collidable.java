package it.unibo.vampireio.model;

/**
 * Represents an object in the game that can collide with other objects.
 * It extends the Positionable interface to include position-related methods.
 */
public interface Collidable extends Positionable {
    /**
     * Returns the radius of the collidable object.
     *
     * @return the radius as a double
     */
    double getRadius();

    /**
     * Checks if this collidable object is colliding with another collidable object.
     *
     * @param collidable the other collidable object to check for collision
     * @return true if colliding, false otherwise
     */
    boolean isColliding(Collidable collidable);

    /**
     * Handles the collision with another collidable object.
     *
     * @param collidable the other collidable object that this object collided with
     */
    void onCollision(Collidable collidable);
}
