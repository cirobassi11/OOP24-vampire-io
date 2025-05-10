package it.unibo.vampireio.model;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;

public class Stats implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private final Map<StatType, Double> statsMap;
    
    public Stats() {
        statsMap = new EnumMap<>(StatType.class);
        initializeDefaultStats();
    }
    
    public Stats(Stats other) {
        statsMap = new EnumMap<>(StatType.class);
        for (StatType type : StatType.values()) {
            statsMap.put(type, other.getStat(type));
        }
    }
    
    private void initializeDefaultStats() {
        statsMap.put(StatType.MAX_HEALTH, 100.0);
        statsMap.put(StatType.ARMOR, 0.0);
        statsMap.put(StatType.SPEED, 1.0);
        statsMap.put(StatType.MOVE_SPEED, 1.0);
        statsMap.put(StatType.RECOVERY, 1.0);
        statsMap.put(StatType.MIGHT, 1.0);
        statsMap.put(StatType.AREA, 1.0);
        statsMap.put(StatType.DURATION, 1.0);
        statsMap.put(StatType.AMOUNT, 1.0);
        statsMap.put(StatType.COOLDOWN, 1.0);
        statsMap.put(StatType.GROWTH, 1.0);
        statsMap.put(StatType.GREED, 1.0);
        statsMap.put(StatType.CURSE, 0.0);
        statsMap.put(StatType.MAGNET, 1.0);
        statsMap.put(StatType.REVIVAL, 0.0);
        statsMap.put(StatType.REROLL, 0.0);
        statsMap.put(StatType.SKIP, 0.0);
        statsMap.put(StatType.BANISH, 0.0);
    }
    
    public double getStat(StatType type) {
        return statsMap.getOrDefault(type, 0.0);
    }

    public void setStat(StatType type, double value) {
        statsMap.put(type, value);
    }
    
    public void modifyStat(StatType type, double amount) {
        double currentValue = getStat(type);
        setStat(type, currentValue + amount);
    }
    
    public void multiplyStat(StatType type, double factor) {
        double currentValue = getStat(type);
        setStat(type, currentValue * factor);
    }
    
    public boolean hasStatValue(StatType type) {
        return getStat(type) > 0;
    }
    
    public Map<StatType, Double> getAllStats() {
        return new EnumMap<>(statsMap);
    }

    public void resetStat(StatType type) {
        switch (type) {
            case MAX_HEALTH:
                setStat(type, 100.0);
                break;
            case ARMOR:
            case RECOVERY:
            case REVIVAL:
            case REROLL:
            case SKIP:
            case BANISH:
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
    
    public void combineStats(Stats other) {
        for (StatType type : StatType.values()) {
            setStat(type, getStat(type) * other.getStat(type));
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Stats{");
        for (StatType type : StatType.values()) {
            sb.append(type.name()).append("=").append(getStat(type)).append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());  // Rimuove l'ultima virgola e spazio
        sb.append("}");
        return sb.toString();
    }
}