package it.unibo.vampireio.model.data;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;

/**
 * Represents a collection of statistics for a character or entity in the game.
 * Each statistic is identified by a StatType and can be modified or retrieved.
 */
public final class Stats implements Serializable {
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
    public Stats(final Stats other) {
        this.statsMap = new EnumMap<>(StatType.class);
        for (final StatType type : StatType.values()) {
            this.statsMap.put(type, other.getStat(type));
        }
    }

    /**
     * Retrieves the value of a specific statistic.
     *
     * @param type the type of statistic to retrieve
     * @return the value of the statistic, or 0.0 if it does not exist
     */
    public double getStat(final StatType type) {
        return this.statsMap.getOrDefault(type, 0.0);
    }

    /**
     * Sets the value of a specific statistic.
     *
     * @param type  the type of statistic to set
     * @param value the value to set for the statistic
     */
    public void setStat(final StatType type, final double value) {
        this.statsMap.put(type, value);
    }

    /**
     * Multiplies the value of a specific statistic by a given factor.
     *
     * @param type   the type of statistic to modify
     * @param factor the factor to multiply the statistic by
     */
    public void multiplyStat(final StatType type, final double factor) {
        final double currentValue = getStat(type);
        setStat(type, currentValue * factor);
    }

    /**
     * Retrieves a map containing all statistics and their values.
     *
     * @return a map of StatType to Double representing all statistics
     */
    public Map<StatType, Double> getAllStats() {
        return new EnumMap<>(this.statsMap);
    }
}
