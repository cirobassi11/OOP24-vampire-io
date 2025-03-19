package it.unibo.vampireio.controller;

import java.awt.geom.Point2D;

public class PositionableData {
    private final String id;
    private final Point2D.Double position;
    private final Point2D.Double direction;

    public PositionableData(String id, Point2D.Double position, Point2D.Double direction) {
        this.id = id;
        this.position = position;
        this.direction = direction;
    }

    public String getId() {
        return this.id;
    }

    public Point2D.Double getPosition() {
        return this.position;
    }

    public Point2D.Double getDirection() {
        return this.direction;
    }
}