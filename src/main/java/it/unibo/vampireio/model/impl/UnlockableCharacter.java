package it.unibo.vampireio.model.impl;

import it.unibo.vampireio.model.data.Stats;

/**
 * Represents an unlockable character in the game.
 * This class extends AbstractUnlockableItem and includes character-specific
 * attributes.
 */
public final class UnlockableCharacter extends AbstractUnlockableItem {
    private static final long serialVersionUID = 1L;
    private final Stats characterStats;
    private final String defaultWeapon;
    private final double radius;

    /**
     * Constructs an UnlockableCharacter with the specified attributes.
     *
     * @param id            the unique identifier for the character
     * @param name          the name of the character
     * @param description   a brief description of the character
     * @param price         the price to unlock the character
     * @param radius        the radius of the character
     * @param stats         the character's stats, encapsulated in a Stats object
     * @param defaultWeapon the default weapon associated with the character
     */
    public UnlockableCharacter(
            final String id,
            final String name,
            final String description,
            final int price,
            final double radius,
            final Stats stats,
            final String defaultWeapon) {
        super(id, name, description, price, 1);
        this.characterStats = new Stats(stats);
        this.defaultWeapon = defaultWeapon;
        this.radius = radius;
    }

    /**
     * Gets the stats associated to the character.
     *
     * @return the character's Stats
     */
    public Stats getCharacterStats() {
        return new Stats(this.characterStats);
    }

    /**
     * Gets the default weapon associated with this character.
     *
     * @return the default weapon as a String
     */
    public String getDefaultWeapon() {
        return this.defaultWeapon;
    }

    /**
     * Gets the radius of the character.
     *
     * @return the radius as a double
     */
    public double getRadius() {
        return this.radius;
    }
}
