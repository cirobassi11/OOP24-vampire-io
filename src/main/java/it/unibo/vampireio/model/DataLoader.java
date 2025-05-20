package it.unibo.vampireio.model;

import it.unibo.vampireio.controller.GameController;

public class DataLoader {

    private final GenericDataLoader<UnlockableCharacter> characterLoader;
    private final GenericDataLoader<EnemyData> enemyLoader;
    private final GenericDataLoader<WeaponData> weaponLoader;
    private final GenericDataLoader<AttackData> attackLoader;
    private final GenericDataLoader<UnlockablePowerUp> powerUpLoader;
    private final GenericDataLoader<Lootable> lootableLoader;

    public DataLoader(GameController gameController) {
        this.characterLoader = new GenericDataLoader<>(gameController, "data/characters.json", UnlockableCharacter.class);
        this.enemyLoader = new GenericDataLoader<>(gameController, "data/enemies.json", EnemyData.class);
        this.weaponLoader = new GenericDataLoader<>(gameController, "data/weapons.json", WeaponData.class);
        this.attackLoader = new GenericDataLoader<>(gameController, "data/attacks.json", AttackData.class);
        this.powerUpLoader = new GenericDataLoader<>(gameController, "data/powerups.json", UnlockablePowerUp.class);
        this.lootableLoader = new GenericDataLoader<>(gameController, "data/lootables.json", Lootable.class);
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

    public GenericDataLoader<Lootable> getLootableLoader() {
        return this.lootableLoader;
    }

    public GenericDataLoader<Lootable> getLootabLoader() {
        return this.lootableLoader;
    }
    
}