package it.unibo.vampireio.model;

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

    void incrementKillCounter() {
        this.killCounter++;
    }

    void setCoinCounter(final int coinCounter) {
        this.coinCounter = coinCounter;
    }

    void setLevel(final int level) {
        this.level = level;
    }

    void incrementSessionTime(final long tickTime) {
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
