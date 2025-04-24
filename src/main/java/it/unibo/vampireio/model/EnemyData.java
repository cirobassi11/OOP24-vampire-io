package it.unibo.vampireio.model;

public class EnemyData {
    private String id;
    private String name;
    private int level;
    private int damage;
    private double speed;
    private int health;

    public EnemyData(String id, String name, int level, int damage, double speed, int health) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.damage = damage;
        this.speed = speed;
        this.health = health;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    public int getLevel() {
        return level;
    }
    
    public int getDamage() {
        return damage;
    }
    
    public double getSpeed() {
        return speed;
    }
    
    public int getHealth() {
        return health;
    }
}
