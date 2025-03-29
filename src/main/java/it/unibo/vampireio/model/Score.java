package it.unibo.vampireio.model;

public class Score {
    private String characterName;
    private double sessionTime;
    private int killCounter;
    private int levelCounter;

    public Score(String characterName) {
        this.characterName = characterName;
        this.sessionTime = 0;
        this.killCounter = 0;
        this.levelCounter = 0;
    }

    public void incrementKillCounter() {
        this.killCounter++;
    }

    public void incrementLevelCounter() {
        this.levelCounter++;
    }

    public void incrementSessionTime(double tickRate) {
        this.sessionTime += tickRate;
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
        return getKillCounter() * getLevelCounter(); //LASCIAMO QUESTO??
    }
}
