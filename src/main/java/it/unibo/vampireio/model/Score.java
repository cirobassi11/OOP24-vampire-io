package it.unibo.vampireio.model;

import java.io.Serializable;

public class Score implements Serializable {
    private static final long serialVersionUID = 1L;

    private String characterName;
    private long sessionTime;
    private int killCounter;
    private int level;
    private int coinCounter;

    public Score(String characterName) {
        this.characterName = characterName;
        this.sessionTime = 0;
        this.killCounter = 0;
        this.level = 0;
        this.coinCounter = 0;
    }

    public void incrementKillCounter() {
        this.killCounter++;
    }

    public void setCoinCounter(int coinCounter) {
        this.coinCounter = coinCounter;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void incrementSessionTime(double tickRate) {
        this.sessionTime += tickRate;
    }

    public String getCharacterName() {
        return this.characterName;
    }

    public int getKillCounter() {
        return this.killCounter;
    }

    public int getLevel() {
        return this.level;
    }

    public int getCoinCounter() {
        return this.coinCounter;
    }

    public long getSessionTime() {
        return this.sessionTime;
    }

    public int getScore() {
        return getKillCounter() * getLevel() * (int) (getSessionTime() / 60);
    }
}
