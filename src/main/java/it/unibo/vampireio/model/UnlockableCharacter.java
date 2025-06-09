package it.unibo.vampireio.model;

/**
 * Represents an unlockable character in the game.
 * This class extends AbstractUnlockableItem and includes character-specific
 * attributes.
 */
public final class UnlockableCharacter extends AbstractUnlockableItem {
    private static final long serialVersionUID = 1L;
    
    private final Stats characterStats;
    private String defaultWeapon;
    private double radius;

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
        this.characterStats = stats;
        this.defaultWeapon = defaultWeapon;
        this.radius = radius;
    }

    Stats getCharacterStats() {
        return this.characterStats;
    }

    String getDefaultWeapon() {
        return this.defaultWeapon;
    }

    double getRadius() {
        return this.radius;
    }
}
