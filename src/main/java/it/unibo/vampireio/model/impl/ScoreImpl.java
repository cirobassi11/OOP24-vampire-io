package it.unibo.vampireio.model.impl;

import it.unibo.vampireio.model.api.Score;

/**
 * Represents the score of a player in the game.
 * The score is calculated based on the number of kills, level reached, and
 * session time.
 */
public final class ScoreImpl implements Score {
    private static final long serialVersionUID = 1L;
    private static final int SECONDS_PER_MINUTE = 60;

    private final String characterName;
    private long sessionTime;
    private int killCounter;
    private int level;
    private int coinCounter;

    /**
     * Constructs a new Score object for a given character.
     * Initializes the score with zero values for session time, kill counter, level,
     * and coin counter.
     *
     * @param characterName the name of the character associated with this score
     */
    public ScoreImpl(final String characterName) {
        this.characterName = characterName;
        this.sessionTime = 0;
        this.killCounter = 0;
        this.level = 0;
        this.coinCounter = 0;
    }

    /**
     * Copy constructor to create a new ScoreImpl object from an existing one.
     * This allows for cloning or resetting scores while retaining the character
     * name.
     *
     * @param score the ScoreImpl object to copy from
     */
    public ScoreImpl(final ScoreImpl score) {
        this.characterName = score.getCharacterName();
        this.sessionTime = score.getSessionTime();
        this.killCounter = score.getKillCounter();
        this.level = score.getLevel();
        this.coinCounter = score.getCoinCounter();
    }

    @Override
    public void incrementKillCounter() {
        this.killCounter++;
    }

    @Override
    public void setCoinCounter(final int coinCounter) {
        this.coinCounter = coinCounter;
    }

    @Override
    public void setLevel(final int level) {
        this.level = level;
    }

    @Override
    public void incrementSessionTime(final long tickTime) {
        this.sessionTime += tickTime;
    }

    @Override
    public String getCharacterName() {
        return this.characterName;
    }

    @Override
    public int getKillCounter() {
        return this.killCounter;
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public int getCoinCounter() {
        return this.coinCounter;
    }

    @Override
    public long getSessionTime() {
        return this.sessionTime;
    }

    @Override
    public int getScore() {
        return getKillCounter() + getLevel() + (int) (getSessionTime() / SECONDS_PER_MINUTE);
    }
}
