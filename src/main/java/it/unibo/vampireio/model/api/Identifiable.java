package it.unibo.vampireio.model.api;

/**
 * Interface for objects that can be identified by a unique ID.
 */
public interface Identifiable {
    /**
     * Returns the unique identifier of the object.
     *
     * @return the unique ID as a String
     */
    String getId();
}
