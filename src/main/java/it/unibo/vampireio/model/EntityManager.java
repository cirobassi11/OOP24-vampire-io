package it.unibo.vampireio.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Iterator;
import java.awt.geom.Point2D;

public class EntityManager {
    private Character character;
    private final List<Enemy> enemies = new LinkedList<>();
    private final List<Attack> attacks = new LinkedList<>();
    private final List<Collectible> collectibles = new LinkedList<>();
    private final EnemySpawner enemySpawner;
    private final ConfigData config;
    private final Score score;
    private final SaveManager saveManager;
    private final WeaponRandomizer weaponRandomizer;
    private final LevelUpManager levelUpManager;

    public EntityManager(
            final ConfigData config,
            final Score score,
            final SaveManager saveManager,
            final UnlockableCharacter selectedCharacter) {
        this.config = config;
        this.score = score;
        this.saveManager = saveManager;
        this.enemySpawner = new EnemySpawner(this, this.config.getMaxGameDuration());

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
        final Map<String, Integer> unlockedPowerUps = this.saveManager.getCurrentSave().getUnlockedPowerUps();
        for (final String powerUpID : unlockedPowerUps.keySet()) {
            final int level = unlockedPowerUps.get(powerUpID);
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

    AbstractAttackFactory getAttackFactory(final String weaponID) {
        return switch (weaponID) {
            case "weapons/magicWand" -> new MagicWandFactory(this);
            case "weapons/santaWater" -> new SantaWaterFactory(this);
            case "weapons/garlic" -> new GarlicFactory(this);
            case "weapons/knife" -> new KnifeFactory(this);
            default -> null;
        };
    }

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

    public void addEnemy(final Enemy enemy) {
        this.enemies.add(enemy);
    }

    public void addAttack(final Attack attack) {
        this.attacks.add(attack);
    }

    public void addCollectible(final Collectible collectible) {
        this.collectibles.add(collectible);
    }

    public Character getCharacter() {
        return this.character;
    }

    public List<Enemy> getEnemies() {
        return List.copyOf(this.enemies);
    }

    public List<Attack> getAttacks() {
        return List.copyOf(this.attacks);
    }

    public List<Weapon> getWeapons() {
        return this.character.getWeapons();
    }

    public List<Collectible> getCollectibles() {
        return List.copyOf(this.collectibles);
    }

    public Weapon getWeaponById(final String weaponID) {
        return this.levelUpManager.findWeaponById(this.character, weaponID);
    }

    public void levelUpWeapon(final String selectedWeapon) {
        this.levelUpManager.levelUpWeapon(this.character, selectedWeapon);
    }

    public List<WeaponData> getRandomWeaponsForLevelUp() {
        return this.levelUpManager.getRandomLevelUpWeapons();
    }
}
