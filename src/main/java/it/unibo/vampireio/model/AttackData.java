package it.unibo.vampireio.model;

public class AttackData implements Identifiable {
    private String id;
    private double radius;
    private double speed;
    private int damage;

    public AttackData(String id, double radius, double speed, int damage) {
        this.id = id;
        this.radius = radius;
        this.speed = speed;
        this.damage = damage;
    }

    public String getId() {
        return id;
    }

    public double getRadius() {
        return radius;
    }

    public double getSpeed() {
        return speed;
    }

    public int getDamage() {
        return damage;
    }

}
