package it.unibo.vampireio.controller;

import java.awt.Dimension;

public class VisualSizeDTO {
    private Dimension dimension;

    public VisualSizeDTO(int width, int height) {
        this.dimension = new Dimension(width, height);
    }

    public Dimension getDimension() {
        return dimension;
    }
}
