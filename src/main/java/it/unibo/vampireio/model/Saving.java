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
    private List<UnlockableItem> unlockedItems;
    private int moneyAmount;
    private List<Score> scores;
    
    // Salvataggio già esistente
    public Saving(
        String savingTime, 
        List<UnlockableCharacter> unlockedCharacters,
        List<UnlockableItem> unlockedItems, 
        int moneyAmount, 
        List<Score> scores
    ) {
        this.savingTime = savingTime;
        this.unlockedCharacters = unlockedCharacters;
        this.unlockedItems = unlockedItems;
        this.moneyAmount = moneyAmount;
        this.scores = scores;
    }

    // Nuovo salvataggio vuoto
    public Saving() {
        this(
            generateSaveTimestamp(), 
            new LinkedList<UnlockableCharacter>(), 
            new LinkedList<UnlockableItem>(), 
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

    public List<UnlockableItem> getUnlockedItems() {
        return List.copyOf(this.unlockedItems);
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

    public void addUnlockedItem(UnlockableItem unlockedItem) {
        this.unlockedItems.add(unlockedItem);
    }

    public void incrementMoneyAmount(int moneyAmount) {
        this.moneyAmount += moneyAmount;
    }
    
    public void addScore(Score score) {
        this.scores.add(score);
    }
    
    // Generazione stringa data-ora dd-MM-yyyy_HH-mm-ss
    private static String generateSaveTimestamp() {
        return LocalDateTime.now().format(FORMATTER);
    }
}
