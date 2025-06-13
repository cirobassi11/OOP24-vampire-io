package it.unibo.vampireio.model.data;

import it.unibo.vampireio.model.impl.GameWorld;
import it.unibo.vampireio.model.impl.UnlockableCharacter;
import it.unibo.vampireio.model.impl.UnlockablePowerUp;

/**
 * DataLoader is a singleton class responsible for loading various game data
 * from JSON files.
 * It provides access to loaders for characters, enemies, weapons, attacks,
 * power-ups, and configuration data.
 */
public final class DataLoader {

    private static DataLoader instance;

    private final GenericDataLoader<ConfigData> configLoader;
    private final GenericDataLoader<UnlockableCharacter> characterLoader;
    private final GenericDataLoader<EnemyData> enemyLoader;
    private final GenericDataLoader<WeaponData> weaponLoader;
    private final GenericDataLoader<AttackData> attackLoader;
    private final GenericDataLoader<UnlockablePowerUp> powerUpLoader;

    /**
     * Private constructor to prevent instantiation of this singleton class.
     * Initializes the data loaders for various game data types.
     *
     * @param model the GameWorld model to which the data loaders will be bound
     */
    private DataLoader(final GameWorld model) {
        this.characterLoader = new GenericDataLoader<>(model, "data/characters.json", UnlockableCharacter.class);
        this.enemyLoader = new GenericDataLoader<>(model, "data/enemies.json", EnemyData.class);
        this.weaponLoader = new GenericDataLoader<>(model, "data/weapons.json", WeaponData.class);
        this.attackLoader = new GenericDataLoader<>(model, "data/attacks.json", AttackData.class);
        this.powerUpLoader = new GenericDataLoader<>(model, "data/powerups.json", UnlockablePowerUp.class);
        this.configLoader = new GenericDataLoader<>(model, "data/config.json", ConfigData.class);
    }

    /**
     * Initializes the DataLoader singleton instance with the provided GameWorld
     * model.
     * This method should be called once at the start of the game to set up the data
     * loaders.
     *
     * @param model the GameWorld model to which the data loaders will be bound
     */
    public static synchronized void init(final GameWorld model) {
        if (instance == null) {
            instance = new DataLoader(model);
        }
    }

    /**
     * Returns the singleton instance of DataLoader.
     * If the instance has not been initialized, this will throw an exception.
     *
     * @return the singleton instance of DataLoader
     */
    public static DataLoader getInstance() {
        return instance;
    }

    /**
     * Returns the GenericDataLoader for UnlockableCharacter.
     *
     * @return the character loader
     */
    public GenericDataLoader<UnlockableCharacter> getCharacterLoader() {
        return this.characterLoader;
    }

    /**
     * Returns the GenericDataLoader for EnemyData.
     *
     * @return the enemy loader
     */
    public GenericDataLoader<EnemyData> getEnemyLoader() {
        return this.enemyLoader;
    }

    /**
     * Returns the GenericDataLoader for WeaponData.
     *
     * @return the weapon loader
     */
    public GenericDataLoader<WeaponData> getWeaponLoader() {
        return this.weaponLoader;
    }

    /**
     * Returns the GenericDataLoader for AttackData.
     *
     * @return the attack loader
     */
    public GenericDataLoader<AttackData> getAttackLoader() {
        return this.attackLoader;
    }

    /**
     * Returns the GenericDataLoader for UnlockablePowerUp.
     *
     * @return the power-up loader
     */
    public GenericDataLoader<UnlockablePowerUp> getPowerUpLoader() {
        return this.powerUpLoader;
    }

    /**
     * Returns the GenericDataLoader for ConfigData.
     *
     * @return the config loader
     */
    public GenericDataLoader<ConfigData> getConfigLoader() {
        return this.configLoader;
    }
}
