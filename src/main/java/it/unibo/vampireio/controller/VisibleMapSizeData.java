package it.unibo.vampireio.controller;

import java.awt.Dimension;

public class VisibleMapSizeData {
    private Dimension dimension;

    public VisibleMapSizeData(int width, int height) {
        this.dimension = new Dimension(width, height);
    }

    public Dimension getDimension() {
        return new Dimension(this.dimension);
    }
}
