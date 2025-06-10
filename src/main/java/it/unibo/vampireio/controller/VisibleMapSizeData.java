package it.unibo.vampireio.controller;

import java.awt.Dimension;

/**
 * Represents the size of the visible map in the game.
 * This class encapsulates the width and height of the visible area.
 */
public final class VisibleMapSizeData {

    private final Dimension dimension;

    /**
     * Constructs a VisibleMapSizeData instance with the specified width and height.
     *
     * @param width  the width of the visible map
     * @param height the height of the visible map
     */
    public VisibleMapSizeData(final int width, final int height) {
        this.dimension = new Dimension(width, height);
    }

    /**
     * Returns the dimension of the visible map.
     *
     * @return a Dimension object representing the width and height of the visible
     *         map
     */
    public Dimension getDimension() {
        return new Dimension(this.dimension.width, this.dimension.height);
    }
}
