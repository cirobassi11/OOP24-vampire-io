package it.unibo.vampireio.model;

import it.unibo.vampireio.controller.GameController;

public class DataLoader {

    private final GenericDataLoader<UnlockableCharacter> characterLoader;
    private final GenericDataLoader<EnemyData> enemyLoader;
    private final GenericDataLoader<UnlockablePowerUp> powerUpLoader;
    private final GenericDataLoader<Lootable> lootableLoader;

    public DataLoader(GameController gameController) {
        this.characterLoader = new GenericDataLoader<>(gameController, "data/characters.json", UnlockableCharacter.class);
        this.enemyLoader = new GenericDataLoader<>(gameController, "data/enemies.json", EnemyData.class);
        this.powerUpLoader = new GenericDataLoader<>(gameController, "data/powerups.json", UnlockablePowerUp.class);
        this.lootableLoader = new GenericDataLoader<>(gameController, "data/lootables.json", Lootable.class);
    }

    public GenericDataLoader<UnlockableCharacter> getCharacterLoader() {
        return this.characterLoader;
    }
    
    public GenericDataLoader<EnemyData> getEnemyLoader() {
        return this.enemyLoader;
    }
    public GenericDataLoader<UnlockablePowerUp> getPowerUpLoader() {
        return this.powerUpLoader;
    }

    public GenericDataLoader<Lootable> getLootableLoader() {
        return this.lootableLoader;
    }

    public GenericDataLoader<Lootable> getLootabLoader() {
        return this.lootableLoader;
    }
    
}