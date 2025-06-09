package it.unibo.vampireio.model;

import java.io.Serializable;


/**
 * Represents the score of a player in the game.
 * The score is calculated based on the number of kills, level reached, and session time.
 */
public final class Score implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final int SECONDS_PER_MINUTE = 60;

    private String characterName;
    private long sessionTime;
    private int killCounter;
    private int level;
    private int coinCounter;

    /**
     * Constructs a new Score object for a given character.
     * Initializes the score with zero values for session time, kill counter, level, and coin counter.
     *
     * @param characterName the name of the character associated with this score
     */
    public Score(final String characterName) {
        this.characterName = characterName;
        this.sessionTime = 0;
        this.killCounter = 0;
        this.level = 0;
        this.coinCounter = 0;
    }

    public void incrementKillCounter() {
        this.killCounter++;
    }

    public void setCoinCounter(final int coinCounter) {
        this.coinCounter = coinCounter;
    }

    public void setLevel(final int level) {
        this.level = level;
    }

    public void incrementSessionTime(final long tickTime) {
        this.sessionTime += tickTime;
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
        return getKillCounter() + getLevel() + (int) (getSessionTime() / SECONDS_PER_MINUTE);
    }
}
