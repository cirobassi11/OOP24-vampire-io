package it.unibo.vampireio.model;

import it.unibo.vampireio.controller.GameController;

public class DataLoader {

    private final CharacterDataLoader characterLoader;
    private final EnemyDataLoader enemyLoader;
    private final PowerUpDataLoader powerUpLoader;

    public DataLoader(GameController gameController) {
        this.characterLoader = new CharacterDataLoader(gameController);
        this.enemyLoader = new EnemyDataLoader(gameController);
        this.powerUpLoader = new PowerUpDataLoader(gameController);
    }

    public CharacterDataLoader getCharacterLoader() {
        return this.characterLoader;
    }

    public EnemyDataLoader getEnemyLoader() {
        return this.enemyLoader;
    }

    public PowerUpDataLoader getPowerUpLoader() {
        return this.powerUpLoader;
    }
}