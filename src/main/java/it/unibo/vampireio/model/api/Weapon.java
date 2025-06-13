package it.unibo.vampireio.model.api;

/**
 * Represents a weapon in the game, which can be upgraded and has a cooldown mechanism.
 */
public interface Weapon extends Identifiable {
    /**
     * Updates the internal cooldown timer and triggers attacks if the cooldown has elapsed.
     *
     * @param tickTime The amount of time that has passed since the last update call.
     */
    void update(long tickTime);

    /**
     * Returns the current level of the weapon.
     *
     * @return the weapon's level
     */
    int getCurrentLevel();

    /**
     * Change the cooldown of the weapon by the given multiplier.
     *
     * @param multiplier The factor by which the current cooldown is multiplied.
     */
    void multiplyCooldown(double multiplier);

    /**
     * Increase the weapon's level by one.
     */
    void levelUp();
}
