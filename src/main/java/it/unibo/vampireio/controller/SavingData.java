package it.unibo.vampireio.controller;

import java.util.List;

public class SavingData {
    private final String savingTime;
    private List<String> unlockedCharacters;
    private List<String> unlockedItems;
    private int moneyAmount;
    private List<ScoreData> scores;
    
    public SavingData(
        String savingTime, 
        List<String> unlockedCharacters,
        List<String> unlockedItems, 
        int moneyAmount, 
        List<ScoreData> scores
    ) {
        this.savingTime = savingTime;
        this.unlockedCharacters = unlockedCharacters;
        this.unlockedItems = unlockedItems;
        this.moneyAmount = moneyAmount;
        this.scores = scores;
    }
    
    public List<String> getUnlockedCharacters() {
        return List.copyOf(this.unlockedCharacters);
    }

    public List<String> getUnlockedItems() {
        return List.copyOf(this.unlockedItems);
    }

    public int getMoneyAmount() {
        return this.moneyAmount;
    }

    public List<ScoreData> getScores() {
        return List.copyOf(this.scores);
    }
}