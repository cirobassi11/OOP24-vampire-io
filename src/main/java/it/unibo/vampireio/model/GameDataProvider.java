package it.unibo.vampireio.model;

import java.util.List;

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
    public GameDataProvider(final EntityManager entityManager, final SaveManager saveManager, final Score score) {
        this.entityManager = entityManager;
        this.saveManager = saveManager;
        this.score = score;
    }

    Character getCharacter() {
        return this.entityManager.getCharacter();
    }

    List<Living> getEnemies() {
        return this.entityManager.getEnemies();
    }

    List<Attack> getAttacks() {
        return this.entityManager.getAttacks();
    }

    List<Weapon> getWeapons() {
        return this.entityManager.getWeapons();
    }

    List<Collectible> getCollectibles() {
        return this.entityManager.getCollectibles();
    }

    int getPlayerLevel() {
        return this.entityManager.getCharacter().getLevel();
    }

    double getPlayerLevelPercentage() {
        return this.entityManager.getCharacter().getLevelPercentage();
    }

    int getKillCounter() {
        return this.score.getKillCounter();
    }

    int getCoinCounter() {
        return this.entityManager.getCharacter().getCoinCounter();
    }

    List<String> getSaveNames() {
        return this.saveManager.getSavesNames();
    }

    Save getCurrentSave() {
        return this.saveManager.getCurrentSave();
    }

    long getElapsedTime() {
        if (this.score == null) {
            return 0;
        }
        return this.score.getSessionTime();
    }

    List<WeaponData> getRandomLevelUpWeapons() {
        return this.entityManager.getRandomWeaponsForLevelUp();
    }
}
