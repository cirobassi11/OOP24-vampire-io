package it.unibo.vampireio.model.data;

import it.unibo.vampireio.model.api.Identifiable;

/**
 * ConfigData is a class that holds configuration data for the game.
 * It implements the Identifiable interface, allowing it to be identified by an
 * ID.
 * This class contains various configuration parameters such as default
 * character ID,
 * weapon slots, maximum game duration, and spawn chances for different
 * collectibles.
 */
public final class ConfigData implements Identifiable {

    /**
     * The ID used to identify this configuration data.
     */
    public static final String CONFIG_ID = "config";

    private final String defaultCharacterId;
    private final int weaponSlots;
    private final long maxGameDuration;
    private final double collectibleSpawnChance;
    private final double coinSpawnChance;
    private final double foodSpawnChance;
    private final double experienceGemSpawnChance;

    /**
     * Constructs a new ConfigData instance with the specified parameters.
     *
     * @param defaultCharacterId       the ID of the default character
     * @param weaponSlots              the number of weapon slots available
     * @param maxGameDuration          the maximum duration of the game in
     *                                 milliseconds
     * @param collectibleSpawnChance   the chance for collectibles to spawn
     * @param coinSpawnChance          the chance for coins to spawn
     * @param foodSpawnChance          the chance for food to spawn
     * @param experienceGemSpawnChance the chance for experience gems to spawn
     */
    ConfigData(
            final String defaultCharacterId,
            final int weaponSlots,
            final long maxGameDuration,
            final double collectibleSpawnChance,
            final double coinSpawnChance,
            final double foodSpawnChance,
            final double experienceGemSpawnChance) {

        this.defaultCharacterId = defaultCharacterId;
        this.weaponSlots = weaponSlots;
        this.maxGameDuration = maxGameDuration;
        this.collectibleSpawnChance = collectibleSpawnChance;
        this.coinSpawnChance = coinSpawnChance;
        this.foodSpawnChance = foodSpawnChance;
        this.experienceGemSpawnChance = experienceGemSpawnChance;
    }

    @Override
    public String getId() {
        return CONFIG_ID;
    }

    /**
     * Returns the ID of the default character.
     *
     * @return the default character ID
     */
    public String getDefaultCharacterId() {
        return this.defaultCharacterId;
    }

    /**
     * Returns the number of weapon slots available.
     *
     * @return the number of weapon slots
     */
    public int getWeaponSlots() {
        return this.weaponSlots;
    }

    /**
     * Returns the maximum duration of the game in milliseconds.
     *
     * @return the maximum game duration
     */
    public long getMaxGameDuration() {
        return this.maxGameDuration;
    }

    /**
     * Returns the chance for collectibles to spawn.
     *
     * @return the collectible spawn chance
     */
    public double getCollectibleSpawnChance() {
        return this.collectibleSpawnChance;
    }

    /**
     * Returns the chance for coins to spawn.
     *
     * @return the coin spawn chance
     */
    public double getCoinSpawnChance() {
        return this.coinSpawnChance;
    }

    /**
     * Returns the chance for food to spawn.
     *
     * @return the food spawn chance
     */
    public double getFoodSpawnChance() {
        return this.foodSpawnChance;
    }

    /**
     * Returns the chance for experience gems to spawn.
     *
     * @return the experience gem spawn chance
     */
    public double getExperienceGemSpawnChance() {
        return this.experienceGemSpawnChance;
    }
}
