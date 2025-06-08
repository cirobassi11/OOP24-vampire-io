package it.unibo.vampireio.view;

/**
 * This interface defines a listener for error messages in the view.
 */
public interface ViewErrorListener {
    /**
     * This method is called when an error occurs in the view.
     *
     * @param message The error message to be displayed.
     */
    void onError(String message);
}
