package it.unibo.vampireio.model;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;

public class Stats implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private final Map<StatType, Double> statsMap;
    
    public Stats() {
        this.statsMap = new EnumMap<>(StatType.class);
    }
    
    public Stats(Stats other) {
        this.statsMap = new EnumMap<>(StatType.class);
        for (StatType type : StatType.values()) {
            this.statsMap.put(type, other.getStat(type));
        }
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
}