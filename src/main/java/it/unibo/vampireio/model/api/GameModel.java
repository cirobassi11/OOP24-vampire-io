package it.unibo.vampireio.model.api;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.List;
import it.unibo.vampireio.model.data.WeaponData;
import it.unibo.vampireio.model.impl.UnlockableCharacter;
import it.unibo.vampireio.model.impl.UnlockablePowerUp;

/**
 * GameModel is the interface that defines the methods for managing the game
 * state.
 * It includes methods for initializing the game, updating the game state, and
 * retrieving
 * various game-related information such as player stats, enemies, attacks,
 * collectibles,
 * and more.
 */
public interface GameModel {
    /**
     * Sets the listener for model errors.
     *
     * @param errorListener the listener to be set
     */
    void setModelErrorListener(ModelErrorListener errorListener);

    /**
     * Initializes the game with the specified character.
     *
     * @param selectedCharacter the character to initialize the game with
     * @return true if the game was initialized successfully, false otherwise
     */
    boolean initGame(String selectedCharacter);

    /**
     * Checks if the game is over.
     *
     * @return true if the game is over, false otherwise
     */
    boolean isGameOver();

    /**
     * Updates the game state based on the current tick time and character
     * direction.
     *
     * @param tickTime           the current tick time
     * @param characterDirection the direction of the character
     */
    void update(long tickTime, Point2D.Double characterDirection);

    /**
     * Returns the visible size of the game area.
     *
     * @return the visual size as a Dimension object
     */
    Dimension getVisualSize();

    /**
     * Returns the current time elapsed in the game.
     *
     * @return the elapsed time in milliseconds
     */
    long getElapsedTime();

    /**
     * Returns the current level of the character.
     *
     * @return the level of the character
     */
    int getPlayerLevel();

    /**
     * Returns the percentage of the current level.
     *
     * @return the level percentage as a double
     */
    double getPlayerLevelPercentage();

    /**
     * Returns the number of kills made by the character.
     *
     * @return the kill counter
     */
    int getKillCounter();

    /**
     * Returns the number of coins collected by the character.
     *
     * @return the coin counter
     */
    int getCoinCounter();

    /**
     * Returns the character currently being played.
     *
     * @return the character object
     */
    Living getCharacter();

    /**
     * Returns a list of enemies currently in the game.
     *
     * @return a list of Enemy objects
     */
    List<Living> getEnemies();

    /**
     * Returns a list of attacks currently in the game.
     *
     * @return a list of Attack objects
     */
    List<Attack> getAttacks();

    /**
     * Returns a list of weapons currently available in the game.
     *
     * @return a list of Weapon objects
     */
    List<Weapon> getWeapons();

    /**
     * Returns a list of collectibles currently available in the game.
     *
     * @return a list of Collectible objects
     */
    List<Collectible> getCollectibles();

    /**
     * Retrieves a list of saved game names.
     *
     * @return a list of strings representing the names of saved games
     */
    List<String> getSaveNames();

    /**
     * Creates a new save file for the current game state.
     */
    void createNewSave();

    /**
     * Loads a save with the specified id.
     *
     * @param selectedSaving the id of the save to load
     */
    void loadSave(String selectedSaving);

    /**
     * Retrieves the current save of the game.
     *
     * @return the current Save object
     */
    Save getCurrentSave();

    /**
     * Retrieves a list of characters that can be chosen in the game.
     *
     * @return a list of UnlockableCharacter objects
     */
    List<UnlockableCharacter> getChoosableCharacters();

    /**
     * Retrieves a list of characters that are locked and cannot be chosen yet.
     *
     * @return a list of UnlockableCharacter objects that are locked
     */
    List<UnlockableCharacter> getLockedCharacters();

    /**
     * Attempts to buy the character with the specified name.
     *
     * @param selectedCharacter the name of the character to buy
     * @return true if the character was successfully bought, false otherwise
     */
    boolean buyCharacter(String selectedCharacter);

    /**
     * Retrieves a list of unlockable power-ups available in the game.
     *
     * @return a list of UnlockablePowerUp objects
     */
    List<UnlockablePowerUp> getUnlockablePowerUps();

    /**
     * Levels up the power-up with the specified name.
     *
     * @param selectedPowerUp the name of the power-up to level up
     * @return true if the power-up was successfully leveled up, false otherwise
     */
    boolean buyPowerUp(String selectedPowerUp);

    /**
     * Checks if the character has just leveled up.
     *
     * @return true if the character has just leveled up, false otherwise
     */
    boolean hasJustLevelledUp();

    /**
     * Retrieves a list of random weapons available.
     *
     * @return a list of weapons available.
     */
    List<WeaponData> getRandomLevelUpWeapons();

    /**
     * Levels up the weapon with the specified name.
     *
     * @param selectedWeapon the name of the weapon to level up
     */
    void levelUpWeapon(String selectedWeapon);

    /**
     * Exits the game and returns the final score.
     *
     * @return the final score of the game
     */
    Score exitGame();

    /**
     * Retrieves all unlockable items in the game.
     *
     * @return a collection of all unlockable items
     */
    Collection<Unlockable> getAllItems();

    /**
     * Notifies the model of an error with the specified error message.
     *
     * @param errorMessage the error message to notify
     */
    void notifyError(String errorMessage);
}
