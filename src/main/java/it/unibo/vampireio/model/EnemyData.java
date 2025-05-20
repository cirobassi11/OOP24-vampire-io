package it.unibo.vampireio.model;

public class EnemyData implements Identifiable {
    private String id;
    private String name;
    private int level;
    private int damage;
    private double speed;
    private int health;
    private double radius;

    public EnemyData(String id, String name, int level, int damage, double speed, int health, double radius) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.damage = damage;
        this.speed = speed;
        this.health = health;
        this.radius = radius;
    }

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
