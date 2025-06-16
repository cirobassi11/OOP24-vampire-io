package it.unibo.vampireio.model.api;

import java.awt.geom.Point2D;

/**
 * Interface for objects that can move in a 2D space.
 * It extends the Positionable interface to ensure that each movable object
 * has a unique identifier and a position.
 */
public interface Movable extends Collidable {
    /**
     * Sets the direction of movement for the movable object.
     *
     * @param direction the new direction as a Point2D.Double object.
     */
    void setDirection(Point2D.Double direction);

    /**
     * Sets the direction of movement towards a target positionable object.
     *
     * @param target the target Positionable object to move towards.
     */
    void setDirectionTorwards(Positionable target);

    /**
     * Gets the current direction of movement for the movable object.
     *
     * @return the direction as a Point2D.Double object.
     */
    Point2D.Double getDirection();

    /**
     * Sets the speed of the movable object.
     *
     * @param speed the new speed as a double.
     */
    void setSpeed(double speed);

    /**
     * Gets the current speed of the movable object.
     *
     * @return the speed as a double.
     */
    double getSpeed();

    /**
     * Moves the object based on the current speed and direction.
     *
     * @param tickTime the amount of time that has passed since the last move.
     */
    void move(long tickTime);

    /**
     * Checks if the movable object is currently moving.
     *
     * @return true if the object is moving, false otherwise.
     */
    boolean isMoving();

    /**
     * Gets the future position of the movable object after a specified amount of time.
     *
     * @param tickTime the amount of time to project into the future.
     * @return the future position as a Point2D.Double object.
     */
    Point2D.Double getFuturePosition(long tickTime);
}
