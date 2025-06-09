package it.unibo.vampireio.model;

import java.awt.geom.Point2D;

/**
 * Interface for objects that can be positioned in a 2D space.
 * It extends the Identifiable interface to ensure that each positionable object
 * has a unique identifier.
 */
public interface Positionable extends Identifiable {
    /**
     * Gets the current position of the positionable object.
     *
     * @return the position as a Point2D.Double object.
     */
    Point2D.Double getPosition();

    /**
     * Sets the position of the positionable object.
     *
     * @param position the new position as a Point2D.Double object.
     */
    void setPosition(Point2D.Double position);

    /**
     * Calculates the distance from this positionable object to another positionable
     * object.
     *
     * @param positionable the other positionable object.
     * @return the distance as a double.
     */
    double getDistance(Positionable positionable);
}
