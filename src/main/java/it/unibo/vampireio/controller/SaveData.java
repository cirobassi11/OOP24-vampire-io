package it.unibo.vampireio.controller;

import java.util.List;

public class SaveData {
    private final String saveTime;
    private List<String> unlockedCharacters;
    private List<String> unlockedItems;
    private int moneyAmount;
    private List<ScoreData> scores;

    public SaveData(
            String saveTime,
            List<String> unlockedCharacters,
            List<String> unlockedItems,
            int moneyAmount,
            List<ScoreData> scores) {
        this.saveTime = saveTime;
        this.unlockedCharacters = unlockedCharacters;
        this.unlockedItems = unlockedItems;
        this.moneyAmount = moneyAmount;
        this.scores = scores;
    }

    public String getSaveTime() {
        return this.saveTime;
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