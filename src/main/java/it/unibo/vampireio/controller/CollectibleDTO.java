package it.unibo.vampireio.controller;

import java.awt.geom.Point2D;

public class CollectibleDTO {
    private String id;
    private Point2D.Double position;

    public CollectibleDTO(String id, Point2D.Double position) {
        this.id = id;
        this.position = position;
    }

    public String getId() {
        return id;
    }

    public Point2D.Double getPosition() {
        return position;
    }
}
