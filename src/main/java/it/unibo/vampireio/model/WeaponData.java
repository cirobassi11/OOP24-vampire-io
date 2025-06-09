package it.unibo.vampireio.model;

/**
 * Represents the data for a weapon in the game.
 */
public class WeaponData implements Identifiable {
    private String id;
    private String name;
    private String description;
    private long defaultCooldown;
    private int defaultAttacksPerCooldown;

    /**
     * Constructor for WeaponData.
     *
     * @param id                     the unique identifier of the weapon
     * @param name                   the name of the weapon
     * @param description            a brief description of the weapon
     * @param defaultCooldown        the default cooldown time for the weapon in milliseconds
     * @param defaultAttacksPerCooldown the number of attacks that can be performed per cooldown period
     */
    public WeaponData(
        final String id, 
        final String name, 
        final String description, 
        final long defaultCooldown, 
        final int defaultAttacksPerCooldown) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.defaultCooldown = defaultCooldown;
            this.defaultAttacksPerCooldown = defaultAttacksPerCooldown;
    }

    @Override
    public String getId() {
        return this.id;
    }

    /**
     * Returns the name of the weapon.
     *
     * @return the name of the weapon
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the description of the weapon.
     *
     * @return the description of the weapon
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns the default cooldown time for the weapon.
     *
     * @return the default cooldown time in milliseconds
     */
    public long getDefaultCooldown() {
        return this.defaultCooldown;
    }

    /**
     * Returns the number of attacks that can be performed per cooldown period.
     *
     * @return the number of attacks per cooldown
     */
    public int getDefaultAttacksPerCooldown() {
        return this.defaultAttacksPerCooldown;
    }
}
