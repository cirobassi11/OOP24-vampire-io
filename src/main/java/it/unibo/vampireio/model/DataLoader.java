package it.unibo.vampireio.model;

import it.unibo.vampireio.controller.GameController;

public class DataLoader {

    private final GenericDataLoader<UnlockableCharacter> characterLoader;
    private final GenericDataLoader<EnemyData> enemyLoader;
    private final GenericDataLoader<WeaponData> weaponDataLoader;
    private final GenericDataLoader<ProjectileAttack> projectileAttackLoader;
    private final GenericDataLoader<AreaAttack> areaAttackLoader;
    private final GenericDataLoader<UnlockablePowerUp> powerUpLoader;
    private final GenericDataLoader<Lootable> lootableLoader;

    public DataLoader(GameController gameController) {
        this.characterLoader = new GenericDataLoader<>(gameController, "data/characters.json", UnlockableCharacter.class);
        this.enemyLoader = new GenericDataLoader<>(gameController, "data/enemies.json", EnemyData.class);
        this.weaponDataLoader = new GenericDataLoader<>(gameController, "data/weapons.json", WeaponData.class);
        this.projectileAttackLoader = new GenericDataLoader<>(gameController, "data/projectiles.json", ProjectileAttack.class);
        this.areaAttackLoader = new GenericDataLoader<>(gameController, "data/areaattacks.json", AreaAttack.class);
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
        return this.weaponDataLoader;
    }

    public GenericDataLoader<ProjectileAttack> getProjectileAttackLoader() {
        return this.projectileAttackLoader;
    }

    public GenericDataLoader<AreaAttack> getAreaAttackLoader() {
        return this.areaAttackLoader;
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