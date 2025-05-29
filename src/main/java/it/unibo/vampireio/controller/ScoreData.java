package it.unibo.vampireio.controller;

public class ScoreData {
    private String characterName;
    private long sessionTime;
    private int killCounter;
    private int levelCounter;
    private int coinCounter;
    private int score;

    public ScoreData(String characterName, long sessionTime, int killCounter, int levelCounter, int coinCounter,
            int score) {
        this.characterName = characterName;
        this.sessionTime = sessionTime;
        this.killCounter = killCounter;
        this.levelCounter = levelCounter;
        this.coinCounter = coinCounter;
        this.score = score;
    }

    public String getCharacterName() {
        return this.characterName;
    }

    public int getKillCounter() {
        return this.killCounter;
    }

    public int getLevelCounter() {
        return this.levelCounter;
    }

    public long getSessionTime() {
        return this.sessionTime;
    }

    public int getCoinCounter() {
        return this.coinCounter;
    }

    public int getScore() {
        return this.score;
    }
}