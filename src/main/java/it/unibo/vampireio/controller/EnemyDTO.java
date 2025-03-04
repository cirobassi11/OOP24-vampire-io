package it.unibo.vampireio.controller;

import java.awt.geom.Point2D;

public class EnemyDTO {
    private String id;
    private Point2D.Double position;
    private Point2D.Double direction;

    public EnemyDTO(String id, Point2D.Double position, Point2D.Double direction) {
        this.id = id;
        this.position = position;
        this.direction = direction;
    }

    public String getId() {
        return id;
    }

    public Point2D.Double getPosition() {
        return position;
    }

    public Point2D.Double getDirection() {
        return direction;
    }
}
