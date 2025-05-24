package it.unibo.vampireio.model;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;

public class Stats implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private final Map<StatType, Double> statsMap;
    
    public Stats() {
        this.statsMap = new EnumMap<>(StatType.class);
        initializeDefaultStats();
    }
    
    public Stats(Stats other) {
        this.statsMap = new EnumMap<>(StatType.class);
        for (StatType type : StatType.values()) {
            this.statsMap.put(type, other.getStat(type));
        }
    }
    
    private void initializeDefaultStats() {
        this.statsMap.put(StatType.MAX_HEALTH, 100.0);
        this.statsMap.put(StatType.ARMOR, 0.0);
        this.statsMap.put(StatType.SPEED, 1.0);
        this.statsMap.put(StatType.MOVE_SPEED, 1.0);
        this.statsMap.put(StatType.MIGHT, 1.0);
        this.statsMap.put(StatType.AMOUNT, 1.0);
        this.statsMap.put(StatType.COOLDOWN, 1.0);
        this.statsMap.put(StatType.CURSE, 0.0);
        this.statsMap.put(StatType.MAGNET, 1.0);
    }
    
    public double getStat(StatType type) {
        return this.statsMap.getOrDefault(type, 0.0);
    }

    public void setStat(StatType type, double value) {
        this.statsMap.put(type, value);
    }
    
    public void modifyStat(StatType type, double amount) {
        double currentValue = getStat(type);
        setStat(type, currentValue + amount);
    }
    
    public void multiplyStat(StatType type, double factor) {
        double currentValue = getStat(type);
        setStat(type, currentValue * factor);
    }
        
    public Map<StatType, Double> getAllStats() {
        return new EnumMap<>(this.statsMap);
    }

    public void resetStat(StatType type) {
        switch (type) {
            case MAX_HEALTH:
                setStat(type, 100.0);
                break;
            case ARMOR:
                setStat(type, 0.0);
                break;
            default:
                setStat(type, 1.0);
                break;
        }
    }
    
    public void resetAllStats() {
        initializeDefaultStats();
    }
}