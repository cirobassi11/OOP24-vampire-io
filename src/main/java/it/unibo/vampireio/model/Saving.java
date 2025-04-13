package it.unibo.vampireio.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

public class Saving implements Serializable {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss");
    
    private String savingTime;
    private List<UnlockableCharacter> unlockedCharacters;
    private List<UnlockablePowerUp> unlockedPowerUps;
    private int moneyAmount;
    private List<Score> scores;
    
    // Salvataggio già esistente
    public Saving(
        String savingTime, 
        List<UnlockableCharacter> unlockedCharacters,
        List<UnlockablePowerUp> unlockedPowerUps,
        int moneyAmount,
        List<Score> scores
    ) {
        this.savingTime = savingTime;
        this.unlockedCharacters = unlockedCharacters;
        this.unlockedPowerUps = unlockedPowerUps;
        this.moneyAmount = moneyAmount;
        this.scores = scores;
    }

    // Nuovo salvataggio vuoto
    public Saving() {
        this(
            generateSavingTimestamp(), 
            new LinkedList<UnlockableCharacter>(), 
            new LinkedList<UnlockablePowerUp>(), 
            0, 
            new LinkedList<Score>()
        );
    }
    
    public String getSavingTime() {
        return this.savingTime;
    }

    public List<UnlockableCharacter> getUnlockedCharacters() {
        return List.copyOf(this.unlockedCharacters);
    }

    public List<UnlockablePowerUp> getUnlockedItems() {
        return List.copyOf(this.unlockedPowerUps);
    }

    public int getMoneyAmount() {
        return this.moneyAmount;
    }

    public List<Score> getScores() {
        return List.copyOf(this.scores);
    }

    public void addUnlockedCharacter(UnlockableCharacter unlockedCharacter) {
        this.unlockedCharacters.add(unlockedCharacter);
    }

    public void addUnlockedPowerUp(UnlockablePowerUp unlockedPowerUp) {
        this.unlockedPowerUps.add(unlockedPowerUp);
    }

    public void incrementMoneyAmount(int moneyAmount) {
        this.moneyAmount += moneyAmount;
    }
    
    public void addScore(Score score) {
        this.scores.add(score);
    }
    
    // Generazione stringa data-ora dd-MM-yyyy_HH-mm-ss
    private static String generateSavingTimestamp() {
        return LocalDateTime.now().format(FORMATTER);
    }
}
