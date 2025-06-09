package it.unibo.vampireio.model;

class ConfigData implements Identifiable {
    private final String defaultCharacterId;
    private final int weaponSlots;
    private final long maxGameDuration;
    private final double collectibleSpawnChance;
    private final double coinSpawnChance;
    private final double foodSpawnChance;
    private final double experienceGemSpawnChance;
    
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
