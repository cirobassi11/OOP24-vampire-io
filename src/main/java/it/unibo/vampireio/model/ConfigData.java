package it.unibo.vampireio.model;

/**
 * ConfigData is a class that holds configuration data for the game.
 * It implements the Identifiable interface, allowing it to be identified by an
 * ID.
 * This class contains various configuration parameters such as default
 * character ID,
 * weapon slots, maximum game duration, and spawn chances for different
 * collectibles.
 */
class ConfigData implements Identifiable {
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
        return "";
    }

    String getDefaultCharacterId() {
        return this.defaultCharacterId;
    }

    int getWeaponSlots() {
        return this.weaponSlots;
    }

    long getMaxGameDuration() {
        return this.maxGameDuration;
    }

    double getCollectibleSpawnChance() {
        return this.collectibleSpawnChance;
    }

    double getCoinSpawnChance() {
        return this.coinSpawnChance;
    }

    double getFoodSpawnChance() {
        return this.foodSpawnChance;
    }

    double getExperienceGemSpawnChance() {
        return this.experienceGemSpawnChance;
    }
}
