package it.unibo.vampireio.model;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;

/**
 * Represents a collection of statistics for a character or entity in the game.
 * Each statistic is identified by a StatType and can be modified or retrieved.
 */
class Stats implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Map<StatType, Double> statsMap;

    /**
     * Default constructor initializes all stats to zero.
     */
    Stats() {
        this.statsMap = new EnumMap<>(StatType.class);
    }

    /**
     * Copy constructor that creates a new Stats object with the same values as
     * another Stats object.
     *
     * @param other the Stats object to copy from
     */
    Stats(final Stats other) {
        this.statsMap = new EnumMap<>(StatType.class);
        for (final StatType type : StatType.values()) {
            this.statsMap.put(type, other.getStat(type));
        }
    }

    double getStat(final StatType type) {
        return this.statsMap.getOrDefault(type, 0.0);
    }

    void setStat(final StatType type, final double value) {
        this.statsMap.put(type, value);
    }

    void modifyStat(final StatType type, final double amount) {
        final double currentValue = getStat(type);
        setStat(type, currentValue + amount);
    }

    void multiplyStat(final StatType type, final double factor) {
        final double currentValue = getStat(type);
        setStat(type, currentValue * factor);
    }

    Map<StatType, Double> getAllStats() {
        return new EnumMap<>(this.statsMap);
    }
}
