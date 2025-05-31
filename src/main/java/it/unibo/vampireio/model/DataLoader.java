package it.unibo.vampireio.model;

import it.unibo.vampireio.controller.GameController;

public class DataLoader {

    private final GenericDataLoader<ConfigData> configLoader;
    private final GenericDataLoader<UnlockableCharacter> characterLoader;
    private final GenericDataLoader<EnemyData> enemyLoader;
    private final GenericDataLoader<WeaponData> weaponLoader;
    private final GenericDataLoader<AttackData> attackLoader;
    private final GenericDataLoader<UnlockablePowerup> powerupLoader;

    public DataLoader(final GameController gameController) {
        this.characterLoader = new GenericDataLoader<>(gameController, "data/characters.json", UnlockableCharacter.class);
        this.enemyLoader = new GenericDataLoader<>(gameController, "data/enemies.json", EnemyData.class);
        this.weaponLoader = new GenericDataLoader<>(gameController, "data/weapons.json", WeaponData.class);
        this.attackLoader = new GenericDataLoader<>(gameController, "data/attacks.json", AttackData.class);
        this.powerupLoader = new GenericDataLoader<>(gameController, "data/powerups.json", UnlockablePowerup.class);
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
    
    public GenericDataLoader<UnlockablePowerup> getPowerupLoader() {
        return this.powerupLoader;
    }

    public GenericDataLoader<ConfigData> getConfigLoader() {
        return this.configLoader;
    }
}