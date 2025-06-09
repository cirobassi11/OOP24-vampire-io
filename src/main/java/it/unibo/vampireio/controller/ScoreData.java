package it.unibo.vampireio.controller;

/**
 * Represents the score data for a game session.
 * This class encapsulates various statistics such as character name, session time,
 * kill counter, level counter, coin counter, and total score.
 */
public final class ScoreData {
    private final String characterName;
    private final long sessionTime;
    private final int killCounter;
    private final int levelCounter;
    private final int coinCounter;
    private final int score;

    /**
     * Constructs a ScoreData instance with the specified parameters.
     *
     * @param characterName the name of the character
     * @param sessionTime   the time spent in the session
     * @param killCounter   the number of kills made during the session
     * @param levelCounter  the number of levels completed during the session
     * @param coinCounter   the number of coins collected during the session
     * @param score         the total score achieved during the session
     */
    public ScoreData(
            final String characterName,
            final long sessionTime,
            final int killCounter,
            final int levelCounter,
            final int coinCounter,
            final int score) {
        this.characterName = characterName;
        this.sessionTime = sessionTime;
        this.killCounter = killCounter;
        this.levelCounter = levelCounter;
        this.coinCounter = coinCounter;
        this.score = score;
    }

    /**
     * Returns the name of the character.
     *
     * @return the character's name
     */
    public String getCharacterName() {
        return this.characterName;
    }

    /**
     * Returns the number of kills made during the session.
     *
     * @return the kill counter
     */
    public int getKillCounter() {
        return this.killCounter;
    }

    /**
     * Returns the level reached during the session.
     *
     * @return the level counter
     */
    public int getLevelCounter() {
        return this.levelCounter;
    }

    /**
     * Returns the total time spent in the session.
     *
     * @return the session time in milliseconds
     */
    public long getSessionTime() {
        return this.sessionTime;
    }

    /**
     * Returns the number of coins collected during the session.
     *
     * @return the coin counter
     */
    public int getCoinCounter() {
        return this.coinCounter;
    }

    /**
     * Returns the total score achieved during the session.
     *
     * @return the total score
     */
    public int getScore() {
        return this.score;
    }
}
