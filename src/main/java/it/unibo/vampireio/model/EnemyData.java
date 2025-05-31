package it.unibo.vampireio.model;

public class EnemyData implements Identifiable {
    private final String id;
    private final String name;
    private final int level;
    private final int damage;
    private final double speed;
    private final int health;
    private final double radius;

    public EnemyData(final String id, final String name, final int level, final int damage, final double speed,
            final int health, final double radius) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.damage = damage;
        this.speed = speed;
        this.health = health;
        this.radius = radius;
    }

    @Override
    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getLevel() {
        return this.level;
    }

    public int getDamage() {
        return this.damage;
    }

    public double getSpeed() {
        return this.speed;
    }

    public int getHealth() {
        return this.health;
    }

    public double getRadius() {
        return this.radius;
    }
}
