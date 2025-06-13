package it.unibo.vampireio.controller.manager;

import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;

import javax.swing.JComponent;

/**
 * InputProcessor is responsible for processing user input from the keyboard.
 * It computes movement directions based on key presses and checks for pause
 * requests.
 */
public final class InputProcessor {

    private final InputHandler inputHandler;

    /**
     * Constructs an InputProcessor instance, initializing the InputHandler.
     * This constructor is used to set up the input handling mechanism for the
     * game.
     */
    public InputProcessor() {
        this.inputHandler = new InputHandler();
    }

    /**
     * Computes the movement direction based on the current key presses.
     * The direction is normalized to ensure consistent speed.
     *
     * @return a Point2D.Double representing the normalized direction vector
     */
    synchronized Point2D.Double computeDirection() {
        final Point2D.Double direction = new Point2D.Double(0, 0);
        if (this.inputHandler.isKeyPressed(KeyEvent.VK_W) || this.inputHandler.isKeyPressed(KeyEvent.VK_UP)) {
            direction.y -= 1;
        }
        if (this.inputHandler.isKeyPressed(KeyEvent.VK_S) || this.inputHandler.isKeyPressed(KeyEvent.VK_DOWN)) {
            direction.y += 1;
        }
        if (this.inputHandler.isKeyPressed(KeyEvent.VK_A) || this.inputHandler.isKeyPressed(KeyEvent.VK_LEFT)) {
            direction.x -= 1;
        }
        if (this.inputHandler.isKeyPressed(KeyEvent.VK_D) || this.inputHandler.isKeyPressed(KeyEvent.VK_RIGHT)) {
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
    synchronized boolean isPauseRequested() {
        return this.inputHandler.isKeyPressed(KeyEvent.VK_ESCAPE);
    }

    synchronized void clear() {
        this.inputHandler.clearPressedKeys();
    }

    /**
     * Sets up key bindings for the specified component using the InputHandler.
     *
     * @param component the JComponent to set up key bindings for
     */
    public synchronized void setupKeyBindings(final JComponent component) {
        this.inputHandler.setupKeyBindings(component);
    }
}
