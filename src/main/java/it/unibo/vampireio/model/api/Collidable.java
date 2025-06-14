package it.unibo.vampireio.model.api;

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
     * If a collision occurs, it triggers the onCollision method.
     *
     * @param collidable the other collidable object to check for collision
     */
    void checkCollision(Collidable collidable);

    /**
     * Handles the collision response when this collidable object collides with another collidable object.
     * This method should be implemented to define what happens when a collision occurs.
     *
     * @param collidable the other collidable object that this object has collided with
     */
    void onCollision(Collidable collidable);
}
