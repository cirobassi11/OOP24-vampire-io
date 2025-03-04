package it.unibo.vampireio.controller;

import java.awt.geom.Point2D;

public class CharacterDTO {
    private String id;
    private Point2D.Double position;
    private Point2D.Double direction;
    private int health;
    private int healthMax;

    public CharacterDTO(String id, Point2D.Double position, Point2D.Double direction, int health, int healthMax) {
        this.id = id;
        this.position = position;
        this.direction = direction;
        this.health = health;
        this.healthMax = healthMax;
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

    public int getHealth() {
        return health;
    }

    public int getHealthMax() {
        return healthMax;
    }
}
