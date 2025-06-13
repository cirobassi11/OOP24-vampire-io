package it.unibo.vampireio.model.data;

import it.unibo.vampireio.model.api.Identifiable;

/**
 * Represents the data for an enemy in the game.
 * This class contains properties such as id, name, level, damage, speed,
 * health,
 * and radius,
 * which define the characteristics of an enemy.
 */
public final class EnemyData implements Identifiable {
    private final String id;
    private final String name;
    private final int level;
    private final int damage;
    private final double speed;
    private final int health;
    private final double radius;

    /**
     * Constructs an EnemyData object with the specified parameters.
     *
     * @param id     the unique identifier of the enemy
     * @param name   the name of the enemy
     * @param level  the level of the enemy
     * @param damage the damage dealt by the enemy
     * @param speed  the speed of the enemy
     * @param health the health of the enemy
     * @param radius the radius of the enemy
     */
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

    /**
     * Returns the name of the enemy.
     *
     * @return the name of the enemy
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the level of the enemy.
     *
     * @return the level of the enemy
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * Returns the damage dealt by the enemy.
     *
     * @return the damage dealt by the enemy
     */
    public int getDamage() {
        return this.damage;
    }

    /**
     * Returns the speed of the enemy.
     *
     * @return the speed of the enemy
     */
    public double getSpeed() {
        return this.speed;
    }

    /**
     * Returns the health of the enemy.
     *
     * @return the health of the enemy
     */
    public int getHealth() {
        return this.health;
    }

    /**
     * Returns the radius of the enemy.
     *
     * @return the radius of the enemy
     */
    public double getRadius() {
        return this.radius;
    }
}
