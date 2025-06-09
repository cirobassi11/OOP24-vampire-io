package it.unibo.vampireio.model;

import java.io.Serializable;

/**
 * Represents the score of a player in the game.
 * The score is calculated based on the number of kills, level reached, and
 * session time.
 */
public interface Score extends Serializable {
    /**
     * Gets the name of the character associated with this score.
     *
     * @return the character's name
     */
    String getCharacterName();

    /**
     * Gets the number of kills made by the character.
     *
     * @return the kill counter
     */
    int getKillCounter();

    /**
     * Gets the level reached by the character.
     *
     * @return the level
     */
    int getLevel();

    /**
     * Gets the number of coins collected by the character.
     *
     * @return the coin counter
     */
    int getCoinCounter();

    /**
     * Gets the total session time in seconds.
     *
     * @return the session time in seconds
     */
    long getSessionTime();

    /**
     * Calculates the total score based on the number of kills, level, and session
     * time.
     *
     * @return the total score
     */
    int getScore();
}
