package it.unibo.vampireio.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Represents a save in the game, containing information about unlocked
 * characters,
 * power-ups, scores, and the amount of money.
 * This interface defines the methods for accessing and modifying save data.
 */
public interface Save extends Serializable {
    /**
     * Gets the timestamp of when the save was created.
     *
     * @return a string representing the save time in the format
     *         "dd-MM-yyyy_HH-mm-ss"
     */
    String getSaveTime();

    /**
     * Gets the list of unlocked characters in the save.
     *
     * @return a list of strings representing the IDs of unlocked characters
     */
    List<String> getUnlockedCharacters();

    /**
     * Gets the map of unlocked power-ups in the save.
     * The keys are power-up IDs and the values are their enhancement levels.
     *
     * @return a map where keys are power-up IDs and values are their enhancement
     *         levels
     */
    Map<String, Integer> getUnlockedPowerUps();

    /**
     * Gets the amount of money in the save.
     *
     * @return the amount of money as an integer
     */
    int getMoneyAmount();

    /**
     * Increments the money amount in the save by the specified amount.
     *
     * @param moneyAmount the amount to add to the current money amount
     */
    void incrementMoneyAmount(int moneyAmount);

    /**
     * Gets the list of scores associated with the save.
     *
     * @return a list of Score objects representing the scores
     */
    List<Score> getScores();

    /**
     * Adds a new score to the save.
     *
     * @param score the Score object to be added
     */
    void addScore(Score score);

    /**
     * Adds a new unlocked character to the save.
     *
     * @param characterName the UnlockableCharacter object representing the
     *                      character to be added
     */
    void addUnlockedCharacter(UnlockableCharacter characterName);

    /**
     * Enhances a power-up in the save.
     *
     * @param powerUpName the UnlockablePowerUp object representing the power-up to
     *                    be enhanced
     */
    void enhancePowerUp(UnlockablePowerUp powerUpName);
}
