package it.unibo.vampireio.model;

public class ConfigData implements Identifiable {
    private String defaultCharacterId;
    private int weaponSlots;
    private long maxGameDuration;
    private double collectibleSpawnChance;
    private double coinSpawnChance;
    private double foodSpawnChance;
    private double experienceGemSpawnChance;
    
    public ConfigData(
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

    public String getDefaultCharacterId() {
        return this.defaultCharacterId;
    }

    public int getWeaponSlots() {
        return this.weaponSlots;
    }

    public long getMaxGameDuration() {
        return this.maxGameDuration;
    }
    public double getCollectibleSpawnChance() {
        return this.collectibleSpawnChance;
    }

    public double getCoinSpawnChance() {
        return this.coinSpawnChance;
    }

    public double getFoodSpawnChance() {
        return this.foodSpawnChance;
    }
    
    public double getExperienceGemSpawnChance() {
        return this.experienceGemSpawnChance;
    }
}
