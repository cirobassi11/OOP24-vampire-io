package it.unibo.vampireio.model;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GameDataProvider {

    private final EntityManager entityManager;
    private final SaveManager saveManager;
    private final Score score;

    public GameDataProvider(final EntityManager entityManager, final SaveManager saveManager, final Score score) {
        this.entityManager = entityManager;
        this.saveManager = saveManager;
        this.score = score;
    }

    Character getCharacter() {
        return this.entityManager.getCharacter();
    }

    List<Enemy> getEnemies() {
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

    EntityManager getEntityManager() {
        return this.entityManager;
    }
}
