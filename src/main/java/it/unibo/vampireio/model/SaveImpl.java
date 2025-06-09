package it.unibo.vampireio.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of the Save interface, representing a game save.
 * Contains information about unlocked characters, power-ups, scores, and money
 * amount.
 * Provides methods to modify and retrieve save data.
 */
public final class SaveImpl implements Save {
    private static final long serialVersionUID = 1L;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss");

    private final String saveTime;
    private final Set<String> unlockedCharacters;
    private final Map<String, Integer> unlockedPowerUps;
    private final List<Score> scores;

    private int moneyAmount;

    /**
     * Constructs a new SaveImpl instance with the current timestamp,
     * an empty set of unlocked characters, an empty map of unlocked power-ups,
     * and an initial money amount of zero.
     */
    SaveImpl() {
        this.saveTime = generateSaveTimestamp();
        this.unlockedCharacters = new HashSet<>();
        this.unlockedPowerUps = new HashMap<>();
        this.moneyAmount = 0;
        this.scores = new LinkedList<Score>();
    }

    @Override
    public String getSaveTime() {
        return this.saveTime;
    }

    @Override
    public List<String> getUnlockedCharacters() {
        return List.copyOf(this.unlockedCharacters);
    }

    @Override
    public Map<String, Integer> getUnlockedPowerUps() {
        return Map.copyOf(this.unlockedPowerUps);
    }

    @Override
    public int getMoneyAmount() {
        return this.moneyAmount;
    }

    @Override
    public void incrementMoneyAmount(final int amount) {
        this.moneyAmount += amount;
    }

    @Override
    public List<Score> getScores() {
        return List.copyOf(this.scores);
    }

    @Override
    public void addScore(final Score score) {
        this.scores.add(score);
    }

    @Override
    public void addUnlockedCharacter(final UnlockableCharacter unlockedCharacter) {
        this.unlockedCharacters.add(unlockedCharacter.getId());
    }

    @Override
    public void enhancePowerUp(final UnlockablePowerUp unlockedPowerUp) {
        this.unlockedPowerUps.put(unlockedPowerUp.getId(), unlockedPowerUp.getCurrentLevel());
    }

    /**
     * Generates a timestamp string in the format "dd-MM-yyyy_HH-mm-ss".
     *
     * @return A string representing the current date and time.
     */
    private static String generateSaveTimestamp() {
        return LocalDateTime.now().format(FORMATTER);
    }
}
