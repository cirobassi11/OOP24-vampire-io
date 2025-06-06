package it.unibo.vampireio.model;

public class DataLoader {

    private static DataLoader instance;

    private final GenericDataLoader<ConfigData> configLoader;
    private final GenericDataLoader<UnlockableCharacter> characterLoader;
    private final GenericDataLoader<EnemyData> enemyLoader;
    private final GenericDataLoader<WeaponData> weaponLoader;
    private final GenericDataLoader<AttackData> attackLoader;
    private final GenericDataLoader<UnlockablePowerUp> powerUpLoader;

    private DataLoader(final GameWorld model) {
        this.characterLoader = new GenericDataLoader<>(model, "data/characters.json", UnlockableCharacter.class);
        this.enemyLoader = new GenericDataLoader<>(model, "data/enemies.json", EnemyData.class);
        this.weaponLoader = new GenericDataLoader<>(model, "data/weapons.json", WeaponData.class);
        this.attackLoader = new GenericDataLoader<>(model, "data/attacks.json", AttackData.class);
        this.powerUpLoader = new GenericDataLoader<>(model, "data/powerUps.json", UnlockablePowerUp.class);
        this.configLoader = new GenericDataLoader<>(model, "data/config.json", ConfigData.class);
    }

    public static void init(GameWorld model) {
        if (instance == null) {
            instance = new DataLoader(model);
        }
    }

    public static DataLoader getInstance() {
        return instance;
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