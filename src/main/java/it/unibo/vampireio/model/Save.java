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
    
    private String saveTime;
    private Set<String> unlockedCharacters;
    private Map<String, Integer> unlockedPowerUps;
    private int moneyAmount;
    private List<Score> scores;
    
    // Salvataggio già esistente
    public Save(
        String saveTime, 
        Set<String> unlockedCharacters,
        Map<String, Integer> unlockedPowerUps,
        int moneyAmount,
        List<Score> scores
    ) {
        this.saveTime = saveTime;
        this.unlockedCharacters = unlockedCharacters;
        this.unlockedPowerUps = unlockedPowerUps;
        this.moneyAmount = moneyAmount;
        this.scores = scores;
    }

    // Nuovo salvataggio vuoto
    public Save() {
        this(
            generateSaveTimestamp(), 
            new HashSet<String>(), 
            new HashMap<String, Integer>(), 
            0, 
            new LinkedList<Score>()
        );
        
        ///TESTTTTTTTT
        this.incrementMoneyAmount(200);
        //////////////
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

    public void incrementMoneyAmount(int moneyAmount) {
        this.moneyAmount += moneyAmount;
    }

    public List<Score> getScores() {
        return List.copyOf(this.scores);
    }

    public void addScore(Score score) {
        this.scores.add(score);
    }

    public void addUnlockedCharacter(UnlockableCharacter unlockedCharacter) {
        this.unlockedCharacters.add(unlockedCharacter.getId());
    }

    public void enhancePowerup(UnlockablePowerUp unlockedPowerUp) {
        this.unlockedPowerUps.put(unlockedPowerUp.getId(), unlockedPowerUp.getCurrentLevel());
    }
    
    // Generazione stringa data-ora dd-MM-yyyy_HH-mm-ss
    private static String generateSaveTimestamp() {
        return LocalDateTime.now().format(FORMATTER);
    }
}
