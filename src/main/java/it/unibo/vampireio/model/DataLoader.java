package it.unibo.vampireio.model;

import it.unibo.vampireio.controller.GameController;

public class DataLoader {

    private final GenericDataLoader<ConfigData> configLoader;
    private final GenericDataLoader<UnlockableCharacter> characterLoader;
    private final GenericDataLoader<EnemyData> enemyLoader;
    private final GenericDataLoader<WeaponData> weaponLoader;
    private final GenericDataLoader<AttackData> attackLoader;
    private final GenericDataLoader<UnlockablePowerUp> powerUpLoader;

    public DataLoader(GameController gameController) {
        this.characterLoader = new GenericDataLoader<>(gameController, "data/characters.json", UnlockableCharacter.class);
        this.enemyLoader = new GenericDataLoader<>(gameController, "data/enemies.json", EnemyData.class);
        this.weaponLoader = new GenericDataLoader<>(gameController, "data/weapons.json", WeaponData.class);
        this.attackLoader = new GenericDataLoader<>(gameController, "data/attacks.json", AttackData.class);
        this.powerUpLoader = new GenericDataLoader<>(gameController, "data/powerups.json", UnlockablePowerUp.class);
        this.configLoader = new GenericDataLoader<>(gameController, "data/config.json", ConfigData.class);
    }

    public GenericDataLoader<UnlockableCharacter> getCharacterLoader() {
        return this.characterLoader;
    }
    
    public GenericDataLoader<EnemyData> getEnemyLoader() {
        return this.enemyLoader;
    }

    public GenericDataLoader<WeaponData> getWeaponLoader() {
        return this.weaponLoader;
    }

    public GenericDataLoader<AttackData> getAttackLoader() {
        return this.attackLoader;
    }
    
    public GenericDataLoader<UnlockablePowerUp> getPowerUpLoader() {
        return this.powerUpLoader;
    }

    public GenericDataLoader<ConfigData> getConfigLoader() {
        return this.configLoader;
    }
}