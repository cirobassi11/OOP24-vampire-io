package it.unibo.vampireio.model.data;

import it.unibo.vampireio.model.api.Identifiable;

/**
 * Represents the data for an attack in the game.
 * This class contains properties such as radius, speed, damage, and duration,
 * which define the characteristics of an attack.
 */
public final class AttackData implements Identifiable {
    private final String id;
    private double radius;
    private double speed;
    private int damage;
    private long duration;

    /**
     * Constructs an AttackData instance with the specified parameters.
     *
     * @param id       the unique identifier for the attack
     * @param radius   the radius of the attack
     * @param speed    the speed of the attack
     * @param damage   the damage dealt by the attack
     * @param duration the duration of the attack in milliseconds
     */
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

    /**
     * Returns the radius of the attack.
     *
     * @return the radius of the attack
     */
    public double getRadius() {
        return this.radius;
    }

    /**
     * Returns the speed of the attack.
     *
     * @return the speed of the attack
     */
    public double getSpeed() {
        return this.speed;
    }

    /**
     * Returns the damage dealt by the attack.
     *
     * @return the damage dealt by the attack
     */
    public int getDamage() {
        return this.damage;
    }

    /**
     * Returns the duration of the attack in milliseconds.
     *
     * @return the duration of the attack
     */
    public long getDuration() {
        return this.duration;
    }

    /**
     * Sets the radius of the attack.
     *
     * @param radius the new radius of the attack
     */
    public void setRadius(final double radius) {
        this.radius = radius;
    }

    /**
     * Sets the speed of the attack.
     *
     * @param speed the new speed of the attack
     */
    public void setSpeed(final double speed) {
        this.speed = speed;
    }

    /**
     * Sets the damage dealt by the attack.
     *
     * @param damage the new damage of the attack
     */
    public void setDamage(final int damage) {
        this.damage = damage;
    }

    /**
     * Sets the duration of the attack.
     *
     * @param duration the new duration of the attack in milliseconds
     */
    public void setDuration(final long duration) {
        this.duration = duration;
    }
}
