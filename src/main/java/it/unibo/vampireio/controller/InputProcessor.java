package it.unibo.vampireio.controller;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;

public final class InputProcessor {

    private final InputHandler inputHandler;

    public InputProcessor(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }

    public Point2D.Double computeDirection() {
        Point2D.Double direction = new Point2D.Double(0, 0);
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
        double length = direction.distance(0, 0);
        if (length > 0) {
            direction.x /= length;
            direction.y /= length;
        }
        return direction;
    }

    public boolean isPauseRequested() {
        return inputHandler.isKeyPressed(KeyEvent.VK_ESCAPE);
    }

    public void clear() {
        inputHandler.clearPressedKeys();
    }
}
