package it.unibo.vampireio.model.api;

/**
 * Represents a living entity in the game, such as a player or an enemy.
 * Living entities can move, have health, and can be attacked or healed.
 */
public interface Living extends Movable, Collidable {
    /**
     * Gets the current health of the living entity.
     *
     * @return the current health
     */
    double getHealth();

    /**
     * Gets the maximum health of the living entity.
     *
     * @return the maximum health
     */
    double getMaxHealth();

    /**
     * Sets the maximum health of the living entity.
     *
     * @param maxHealth the new maximum health
     */
    void setMaxHealth(double maxHealth);

    /**
     * Deals damage to the living entity.
     *
     * @param damage the amount of damage to deal
     */
    void dealDamage(double damage);

    /**
     * Heals the living entity.
     *
     * @param heal the amount of health to restore
     */
    void heal(double heal);

    /**
     * Checks if the living entity is currently being attacked.
     *
     * @return true if the entity is being attacked, false otherwise
     */
    boolean isGettingAttacked();

    /**
     * Sets the state of whether the living entity is being attacked.
     *
     * @param isGettingAttacked true if the entity is being attacked, false otherwise
     */
    void setGettingAttacked(boolean isGettingAttacked);
}
