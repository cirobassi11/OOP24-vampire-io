package it.unibo.vampireio.controller.api;

/**
 * This interface defines the game controller,
 * which is responsible for handling game-related operations
 * and displaying error messages.
 */
public interface GameController {
    /**
     * Displays an error message to the user and exits the application.
     *
     * @param message The error message to be displayed.
     */
    void showError(String message);
}
