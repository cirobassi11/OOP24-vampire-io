package it.unibo.vampireio.controller;

import java.util.List;

public class ScoreData {
    private String characterName;
    private double sessionTime;
    private int killCounter;
    private int levelCounter;
    private int score;

    public ScoreData(String characterName, double sessionTime, int killCounter, int levelCounter, int score) {
        this.characterName = characterName;
        this.sessionTime = sessionTime;
        this.killCounter = killCounter;
        this.levelCounter = levelCounter;
        this.score = score;
    }

    public int getKillCounter() {
        return this.killCounter;
    }

    public int getLevelCounter() {
        return this.levelCounter;
    }

    public double getSessionTime() {
        return this.sessionTime;
    }

    public int getScore() {
        return this.score;
    }
}