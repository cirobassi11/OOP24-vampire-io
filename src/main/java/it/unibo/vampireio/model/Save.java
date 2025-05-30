package it.unibo.vampireio.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Save implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss");
    
    private final String saveTime;
    private final Set<String> unlockedCharacters;
    private final Map<String, Integer> unlockedPowerUps;
    private final List<Score> scores;

    private int moneyAmount;

    // Nuovo salvataggio vuoto
    public Save() {
        this.saveTime = generateSaveTimestamp();
        this.unlockedCharacters = new HashSet<>();
        this.unlockedPowerUps = new HashMap<>(); 
        this.moneyAmount = 0;
        this.scores = new LinkedList<Score>();
    }

    public String getSaveTime() {
        return this.saveTime;
    }

    public List<String> getUnlockedCharacters() {
        return List.copyOf(this.unlockedCharacters);
    }

    public Map<String, Integer> getUnlockedPowerUps() {
        return Map.copyOf(this.unlockedPowerUps);
    }

    public int getMoneyAmount() {
        return this.moneyAmount;
    }

    public void incrementMoneyAmount(final int moneyAmount) {
        this.moneyAmount += moneyAmount;
    }

    public List<Score> getScores() {
        return List.copyOf(this.scores);
    }

    public void addScore(final Score score) {
        this.scores.add(score);
    }

    public void addUnlockedCharacter(final UnlockableCharacter unlockedCharacter) {
        this.unlockedCharacters.add(unlockedCharacter.getId());
    }

    public void enhancePowerup(final UnlockablePowerUp unlockedPowerUp) {
        this.unlockedPowerUps.put(unlockedPowerUp.getId(), unlockedPowerUp.getCurrentLevel());
    }

    // Generazione stringa data-ora dd-MM-yyyy_HH-mm-ss
    private static String generateSaveTimestamp() {
        return LocalDateTime.now().format(FORMATTER);
    }
}
