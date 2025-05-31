package it.unibo.vampireio.model;

public class AttackData implements Identifiable {
    private final String id;
    private double radius;
    private double speed;
    private int damage;
    private long duration;

    public AttackData(final String id, final double radius, final double speed, final int damage, final long duration) {
        this.id = id;
        this.radius = radius;
        this.speed = speed;
        this.damage = damage;
        this.duration = duration;
    }

    @Override
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

    public void setRadius(final double radius) {
        this.radius = radius;
    }

    public void setSpeed(final double speed) {
        this.speed = speed;
    }

    public void setDamage(final int damage) {
        this.damage = damage;
    }

    public void setDuration(final long duration) {
        this.duration = duration;
    }
}
