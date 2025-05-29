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

    public Stats(final Stats other) {
        this.statsMap = new EnumMap<>(StatType.class);
        for (final StatType type : StatType.values()) {
            this.statsMap.put(type, other.getStat(type));
        }
    }

    public double getStat(final StatType type) {
        return this.statsMap.getOrDefault(type, 0.0);
    }

    public void setStat(final StatType type, final double value) {
        this.statsMap.put(type, value);
    }

    public void modifyStat(final StatType type, final double amount) {
        final double currentValue = getStat(type);
        setStat(type, currentValue + amount);
    }

    public void multiplyStat(final StatType type, final double factor) {
        final double currentValue = getStat(type);
        setStat(type, currentValue * factor);
    }
                        
    public Map<StatType, Double> getAllStats() {
        return new EnumMap<>(this.statsMap);
    }
}
