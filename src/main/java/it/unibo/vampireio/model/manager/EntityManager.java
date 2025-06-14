package it.unibo.vampireio.model.manager;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.vampireio.model.impl.ScoreImpl;
import it.unibo.vampireio.model.impl.UnlockableCharacter;
import it.unibo.vampireio.model.impl.UnlockablePowerUp;
import it.unibo.vampireio.model.impl.WeaponImpl;
import it.unibo.vampireio.model.impl.collectibles.Coin;
import it.unibo.vampireio.model.impl.collectibles.ExperienceGem;
import it.unibo.vampireio.model.impl.collectibles.Food;
import it.unibo.vampireio.model.impl.attacks.AbstractAttackFactory;
import it.unibo.vampireio.model.api.Attack;
import it.unibo.vampireio.model.data.ConfigData;
import it.unibo.vampireio.model.data.DataLoader;
import it.unibo.vampireio.model.data.StatType;
import it.unibo.vampireio.model.data.Stats;
import it.unibo.vampireio.model.data.WeaponData;
import it.unibo.vampireio.model.impl.Enemy;
import it.unibo.vampireio.model.api.Collectible;
import it.unibo.vampireio.model.api.Living;
import it.unibo.vampireio.model.api.Weapon;
import it.unibo.vampireio.model.impl.Character;
import it.unibo.vampireio.model.impl.attacks.MagicWandFactory;
import it.unibo.vampireio.model.impl.attacks.SantaWaterFactory;
import it.unibo.vampireio.model.impl.attacks.GarlicFactory;
import it.unibo.vampireio.model.impl.attacks.KnifeFactory;

import java.util.Iterator;
import java.awt.geom.Point2D;

/**
 * EntityManager is responsible for managing all entities in the game, including
 * the character, enemies, attacks, and collectibles.
 * It handles their updates, collisions, and interactions.
 */
public final class EntityManager {
    private final Character character;
    private final List<Enemy> enemies = new LinkedList<>();
    private final List<Attack> attacks = new LinkedList<>();
    private final List<Collectible> collectibles = new LinkedList<>();
    private final EnemySpawner enemySpawner;
    private final ConfigData config;
    private final ScoreImpl score;
    private final SaveManager saveManager;
    private final WeaponRandomizer weaponRandomizer;
    private final LevelUpManager levelUpManager;

    /**
     * Constructs an EntityManager with the specified configuration, score,
     * saveManager, and selected character.
     *
     * @param config            the game configuration data
     * @param score             the score manager
     * @param saveManager       the save manager
     * @param selectedCharacter the character selected by the player
     */
    @SuppressFBWarnings(
        value = "EI2", 
        justification = "The Score instance is intentionally shared within EntityManager."
    )
    public EntityManager(
            final ConfigData config,
            final ScoreImpl score,
            final SaveManager saveManager,
            final UnlockableCharacter selectedCharacter) {
        this.config = config;
        this.score = score;
        this.saveManager = saveManager;
        this.enemySpawner = new EnemySpawner(this, new EnemyFactory(this), this.config.getMaxGameDuration());

        final Stats stats = applyBuffs(selectedCharacter.getCharacterStats());

        final WeaponData defaultWeaponData = DataLoader.getInstance().getWeaponLoader()
                .get(selectedCharacter.getDefaultWeapon()).get();
        final AbstractAttackFactory attackFactory = this.getAttackFactory(defaultWeaponData.getId());

        final Weapon defaultWeapon = new WeaponImpl(this,
                defaultWeaponData.getId(),
                (long) (defaultWeaponData.getDefaultCooldown() * (2 - stats.getStat(StatType.COOLDOWN))),
                defaultWeaponData.getDefaultAttacksPerCooldown(),
                attackFactory);

        this.character = new Character(
                selectedCharacter.getId(),
                stats,
                selectedCharacter.getRadius(),
                defaultWeapon,
                this.config.getWeaponSlots());

        this.weaponRandomizer = new WeaponRandomizer(
                DataLoader.getInstance().getWeaponLoader().getAll().stream().map(WeaponData::getId).toList(),
                this.character);

        this.levelUpManager = new LevelUpManager(this, this.weaponRandomizer);
    }

    private Stats applyBuffs(final Stats baseStats) {
        final Stats modifiedStats = new Stats(baseStats);
        final Map<String, Integer> unlockedPowerUps = this.saveManager.getUnlockedPowerUps();
        for (final Entry<String, Integer> unlockedPowerUp : unlockedPowerUps.entrySet()) {
            final String powerUpID = unlockedPowerUp.getKey();
            final int level = unlockedPowerUp.getValue();
            final UnlockablePowerUp powerUp = DataLoader.getInstance().getPowerUpLoader().get(powerUpID).orElse(null);
            if (powerUp != null) {
                powerUp.setCurrentLevel(level);
                final double multiplier = powerUp.getMultiplier();
                final StatType stat = powerUp.getStatToModify();
                modifiedStats.multiplyStat(stat, multiplier);
            }
        }
        return modifiedStats;
    }

    /**
     * Returns an attack factory based on the weapon ID.
     *
     * @param weaponID the ID of the weapon
     * @return an instance of AbstractAttackFactory corresponding to the weapon ID,
     *         or null if no matching factory is found
     */
    public AbstractAttackFactory getAttackFactory(final String weaponID) {
        return switch (weaponID) {
            case "weapons/magicWand" -> new MagicWandFactory(this);
            case "weapons/santaWater" -> new SantaWaterFactory(this);
            case "weapons/garlic" -> new GarlicFactory(this);
            case "weapons/knife" -> new KnifeFactory(this);
            default -> null;
        };
    }

    /**
     * Updates all entities in the game world based on the current tick time and
     * character direction.
     *
     * @param tickTime           the time elapsed since the last update
     * @param characterDirection the direction of the character
     */
    public void updateEntities(final long tickTime, final Point2D.Double characterDirection) {
        this.updateCharacter(tickTime, characterDirection);
        this.updateEnemies(tickTime);
        this.updateAttacks(tickTime);
        this.cleanupExpiredEntities();

        this.enemySpawner.update(tickTime);
    }

    private void updateCharacter(final long tickTime, final Point2D.Double characterDirection) {
        this.character.setGettingAttacked(false);
        this.character.setDirection(characterDirection);
        this.character.move(tickTime);
        this.character.updateWeapons(tickTime);
        CollisionManager.checkCharacterCollisions(this.character, this.enemies, this.collectibles);
    }

    private void updateEnemies(final long tickTime) {
        for (final Enemy enemy : enemies) {
            this.updateEnemy(enemy, tickTime);
        }
    }

    private void updateEnemy(final Enemy enemy, final long tickTime) {
        enemy.setGettingAttacked(false);

        final double deltaX = character.getPosition().getX() - enemy.getPosition().getX();
        final double deltaY = character.getPosition().getY() - enemy.getPosition().getY();
        final double distance = enemy.getDistance(character);

        final Point2D.Double enemyDirection = new Point2D.Double(deltaX / distance, deltaY / distance);
        enemy.setDirection(enemyDirection);

        final Point2D.Double enemyFuturePosition = enemy.getFuturePosition(tickTime);
        final boolean collisionWithOtherEntities = CollisionManager.checkEnemyCollisions(enemy, enemyFuturePosition,
                this.enemies, this.character);

        if (!collisionWithOtherEntities) {
            enemy.move(tickTime);
        }
    }

    private void updateAttacks(final long tickTime) {
        this.attacks.forEach(attack -> attack.execute(tickTime));
    }

    private void cleanupExpiredEntities() {
        this.attacks.removeIf(Attack::isExpired);

        final Iterator<Enemy> enemyIterator = enemies.iterator();
        while (enemyIterator.hasNext()) {
            final Enemy enemy = enemyIterator.next();
            if (enemy.getHealth() <= 0) {
                spawnRandomCollectible(enemy.getPosition());
                score.incrementKillCounter();
                enemyIterator.remove();
            }
        }
    }

    private void spawnRandomCollectible(final Point2D.Double position) {
        if (Math.random() >= this.config.getCollectibleSpawnChance()) {
            return;
        }

        final double coinProb = this.config.getCoinSpawnChance();
        final double foodProb = this.config.getFoodSpawnChance();
        final double expProb = this.config.getExperienceGemSpawnChance();

        final double total = coinProb + foodProb + expProb;
        final double rand = Math.random() * total;

        if (rand < coinProb) {
            this.addCollectible(new Coin(position));
        } else if (rand < coinProb + foodProb) {
            this.addCollectible(new Food(position));
        } else {
            this.addCollectible(new ExperienceGem(position));
        }
    }

    /**
     * Adds a new enemy to the game.
     *
     * @param enemy the enemy to add
     */
    public void addEnemy(final Enemy enemy) {
        this.enemies.add(enemy);
    }

    /**
     * Adds a new attack to the game.
     *
     * @param attack the attack to add
     */
    public void addAttack(final Attack attack) {
        this.attacks.add(attack);
    }

    /**
     * Adds a new collectible to the game.
     *
     * @param collectible the collectible to add
     */
    public void addCollectible(final Collectible collectible) {
        this.collectibles.add(collectible);
    }

    /**
     * Returns the character managed by this EntityManager.
     *
     * @return the character
     */
    public Character getCharacter() {
        return new Character(this.character);
    }

    /**
     * Returns a list of all enemies currently in the game.
     *
     * @return a list of enemies
     */
    public List<Living> getEnemies() {
        return List.copyOf(this.enemies);
    }

    /**
     * Returns a list of all attacks currently in the game.
     *
     * @return a list of attacks
     */
    public List<Attack> getAttacks() {
        return List.copyOf(this.attacks);
    }

    /**
     * Returns a list of all weapons currently available to the character.
     *
     * @return a list of weapons
     */
    public List<Weapon> getWeapons() {
        return this.character.getWeapons();
    }

    /**
     * Returns a list of all collectibles currently in the game.
     *
     * @return a list of collectibles
     */
    public List<Collectible> getCollectibles() {
        return List.copyOf(this.collectibles);
    }

    /**
     * Retrieves a weapon by its ID.
     *
     * @param weaponID the ID of the weapon to retrieve
     * @return the weapon with the specified ID, or null if not found
     */
    public Weapon getWeaponById(final String weaponID) {
        return this.levelUpManager.findWeaponById(this.character, weaponID);
    }

    /**
     * Levels up a weapon for the character.
     *
     * @param selectedWeapon the ID of the weapon to level up
     */
    public void levelUpWeapon(final String selectedWeapon) {
        this.levelUpManager.levelUpWeapon(this.character, selectedWeapon);
    }

    /**
     * Retrieves a list of random weapons available for level-up.
     *
     * @return a list of random weapons for level-up
     */
    public List<WeaponData> getRandomWeaponsForLevelUp() {
        return this.levelUpManager.getRandomLevelUpWeapons();
    }

    /**
     * Checks if the character has just levelled up.
     * This method resets the level-up flag after checking.
     *
     * @return true if the character has just levelled up, false otherwise
     */
    public boolean hasJustLevelledUp() {
        if (!this.character.hasJustLevelledUp()) {
            return false;
        }
        this.character.resetHasJustLevelledUp();
        return true;
    }
}
