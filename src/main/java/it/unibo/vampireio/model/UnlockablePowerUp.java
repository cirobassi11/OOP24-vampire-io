package it.unibo.vampireio.model;

/**
 * Represents a power-up that can be unlocked in the game.
 * Each power-up modifies a specific stat and has a level that determines its
 * effectiveness.
 */
public final class UnlockablePowerUp extends AbstractUnlockableItem {
    private static final long serialVersionUID = 1L;
    private static final double MULTIPLIER_INCREMENT = 0.15;

    private final StatType statToModify;

    /**
     * Constructs an UnlockablePowerUp instance.
     *
     * @param id           the unique identifier for the power-up
     * @param name         the name of the power-up
     * @param description  a brief description of the power-up
     * @param price        the price to unlock the power-up
     * @param maxLevel     the maximum level of the power-up
     * @param statToModify the stat that this power-up modifies
     */
    public UnlockablePowerUp(
            final String id,
            final String name,
            final String description,
            final int price,
            final int maxLevel,
            final StatType statToModify) {
        super(id, name, description, price, maxLevel);
        this.statToModify = statToModify;
    }

    double getMultiplier() {
        return 1 + this.getCurrentLevel() * MULTIPLIER_INCREMENT;
    }

    StatType getStatToModify() {
        return this.statToModify;
    }
}
