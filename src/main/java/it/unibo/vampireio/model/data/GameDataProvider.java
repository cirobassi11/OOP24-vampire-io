package it.unibo.vampireio.model.data;

import java.util.List;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.vampireio.model.api.Attack;
import it.unibo.vampireio.model.api.Collectible;
import it.unibo.vampireio.model.api.Living;
import it.unibo.vampireio.model.api.Score;
import it.unibo.vampireio.model.api.Weapon;
import it.unibo.vampireio.model.manager.EntityManager;
import it.unibo.vampireio.model.manager.SaveManager;
import it.unibo.vampireio.model.impl.Character;

/**
 * Provides access to game data such as characters, enemies, attacks, weapons,
 * collectibles,
 * player level, score, and save data.
 */
public final class GameDataProvider {

    private final EntityManager entityManager;
    private final SaveManager saveManager;
    private final Score score;

    /**
     * Constructs a GameDataProvider with the specified EntityManager, SaveManager,
     * and Score.
     *
     * @param entityManager the EntityManager to manage game entities
     * @param saveManager   the SaveManager to handle game saves
     * @param score         the Score to track player score and session time
     */
    @SuppressFBWarnings(
        value = "EI2", 
        justification = "The EntityManager and SaveManager instances are intentionally shared within GameDataProvider."
    )
    public GameDataProvider(final EntityManager entityManager, final SaveManager saveManager, final Score score) {
        this.entityManager = entityManager;
        this.saveManager = saveManager;
        this.score = score;
    }

    /**
     * Returns the character.
     *
     * @return the Character
     */
    public Character getCharacter() {
        return this.entityManager.getCharacter();
    }

    /**
     * Returns a list of enemies currently in the game.
     *
     * @return a list of Living entities representing enemies
     */
    public List<Living> getEnemies() {
        return this.entityManager.getEnemies();
    }

    /**
     * Returns a list of attacks currently in the game.
     *
     * @return a list of Attack objects
     */
    public List<Attack> getAttacks() {
        return this.entityManager.getAttacks();
    }

    /**
     * Returns a list of weapons currently available in the game.
     *
     * @return a list of Weapon objects
     */
    public List<Weapon> getWeapons() {
        return this.entityManager.getWeapons();
    }

    /**
     * Returns a list of collectibles currently available in the game.
     *
     * @return a list of Collectible objects
     */
    public List<Collectible> getCollectibles() {
        return this.entityManager.getCollectibles();
    }

    /**
     * Returns the player's current level.
     *
     * @return the player's level
     */
    public int getPlayerLevel() {
        return this.entityManager.getCharacter().getLevel();
    }

    /**
     * Returns the percentage of the current level.
     *
     * @return the level percentage as a double
     */
    public double getPlayerLevelPercentage() {
        return this.entityManager.getCharacter().getLevelPercentage();
    }

    /**
     * Returns the number of kills made by the character.
     *
     * @return the kill counter
     */
    public int getKillCounter() {
        return this.score.getKillCounter();
    }

    /**
     * Returns the number of coins collected by the character.
     *
     * @return the coin counter
     */
    public int getCoinCounter() {
        return this.entityManager.getCharacter().getCoinCounter();
    }

    /**
     * Returns a list of names of all available saves.
     *
     * @return a list of save names
     */
    public List<String> getSaveNames() {
        return this.saveManager.getSavesNames();
    }

    /**
     * Returns the elapsed time of the current session in milliseconds.
     *
     * @return the elapsed time in milliseconds
     */
    public long getElapsedTime() {
        if (this.score == null) {
            return 0;
        }
        return this.score.getSessionTime();
    }

    /**
     * Checks if the player has just levelled up.
     *
     * @return true if the player has just levelled up, false otherwise
     */
    public boolean hasJustLevelledUp() {
        return this.entityManager.hasJustLevelledUp();
    }
}
