package it.unibo.vampireio.controller;

import java.awt.Dimension;

public class VisualSizeData {
    private Dimension dimension;

    public VisualSizeData(int width, int height) {
        this.dimension = new Dimension(width, height);
    }

    public Dimension getDimension() {
        return this.dimension;
    }
}
