package it.unibo.vampireio.model.api;

/**
 * Interface for listening to model errors.
 * Implement this interface to handle error messages from the model.
 */
public interface ModelErrorListener {
    /**
     * Called when an error occurs in the model.
     *
     * @param message The error message.
     */
    void onError(String message);
}
