package it.unibo.vampireio.controller;

import java.awt.geom.Point2D;

public class PositionableData {
    private final String id;
    private final Point2D.Double position;
    private final Point2D.Double direction;
    private final double radius;

    public PositionableData(String id, Point2D.Double position, Point2D.Double direction, double radius) {
        this.id = id;
        this.position = position;
        this.direction = direction;
        this.radius = radius;
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

    public double getRadius() {
        return this.radius;
    }
}