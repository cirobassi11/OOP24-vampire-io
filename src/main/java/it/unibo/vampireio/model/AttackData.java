package it.unibo.vampireio.model;

public class AttackData implements Identifiable {
    private String id;
    private double radius;
    private double speed;
    private int damage;
    private long duration;

    public AttackData(String id, double radius, double speed, int damage, long duration) {
        this.id = id;
        this.radius = radius;
        this.speed = speed;
        this.damage = damage;
        this.duration = duration;
    }

    public String getId() {
        return this.id;
    }

    public double getRadius() {
        return this.radius;
    }

    public double getSpeed() {
        return this.speed;
    }

    public int getDamage() {
        return this.damage;
    }

    public long getDuration() {
        return this.duration;   
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
