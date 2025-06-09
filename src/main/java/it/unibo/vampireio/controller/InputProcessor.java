package it.unibo.vampireio.controller;

import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;

/**
 * InputProcessor is responsible for processing user input from the keyboard.
 * It computes movement directions based on key presses and checks for pause
 * requests.
 */
final class InputProcessor {

    private final InputHandler inputHandler;

    /**
     * Constructs an InputProcessor with the specified InputHandler.
     *
     * @param inputHandler the InputHandler to use for processing input
     */
    InputProcessor(final InputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }

    /**
     * Computes the movement direction based on the current key presses.
     * The direction is normalized to ensure consistent speed.
     *
     * @return a Point2D.Double representing the normalized direction vector
     */
    Point2D.Double computeDirection() {
        final Point2D.Double direction = new Point2D.Double(0, 0);
        if (inputHandler.isKeyPressed(KeyEvent.VK_W) || inputHandler.isKeyPressed(KeyEvent.VK_UP)) {
            direction.y -= 1;
        }
        if (inputHandler.isKeyPressed(KeyEvent.VK_S) || inputHandler.isKeyPressed(KeyEvent.VK_DOWN)) {
            direction.y += 1;
        }
        if (inputHandler.isKeyPressed(KeyEvent.VK_A) || inputHandler.isKeyPressed(KeyEvent.VK_LEFT)) {
            direction.x -= 1;
        }
        if (inputHandler.isKeyPressed(KeyEvent.VK_D) || inputHandler.isKeyPressed(KeyEvent.VK_RIGHT)) {
            direction.x += 1;
        }
        final double length = direction.distance(0, 0);
        if (length > 0) {
            direction.x /= length;
            direction.y /= length;
        }
        return direction;
    }

    /**
     * Checks if the pause key (Escape) is pressed.
     *
     * @return true if the pause key is pressed, false otherwise
     */
    boolean isPauseRequested() {
        return inputHandler.isKeyPressed(KeyEvent.VK_ESCAPE);
    }

    void clear() {
        inputHandler.clearPressedKeys();
    }
}
