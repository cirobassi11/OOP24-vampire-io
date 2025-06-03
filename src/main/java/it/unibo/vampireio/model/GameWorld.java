package it.unibo.vampireio.model;

import it.unibo.vampireio.controller.GameController;
import java.awt.geom.Point2D;
import java.awt.Dimension;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Collection;
import java.util.Iterator;

public class GameWorld implements GameModel {

    private GameController gameController;
    
    private DataLoader dataLoader;
    private SaveManager saveManager;

    private final Dimension visualSize = new Dimension(1280, 720);

    private boolean isGameOver;
    private Score score;

    private ConfigData configData;

    private Character character;
    private List<Enemy> enemies;
    private List<Attack> attacks;
    private List<Collectible> collectibles;
    private EnemySpawner enemySpawner;
    private WeaponRandomizer weaponRandomizer;

    private static final int LEVELUP_CHOICES = 3;
    
    public GameWorld(GameController gameController) {
        this.gameController = gameController;

        this.dataLoader = new DataLoader(this.gameController);
        this.saveManager = new SaveManager(this.gameController);

        ConfigData configData = this.dataLoader.getConfigLoader().get("").orElse(null);
        if (configData != null) {
            this.configData = configData;
        } else {
            this.gameController.showErrorWithExit("Config data not found!");
        }
    }

    @Override
    public boolean initGame(String selectedCharacter) {
        this.isGameOver = false;
        this.enemySpawner = new EnemySpawner(this, this.configData.getMaxGameDuration());
        
        Optional<UnlockableCharacter> optionalSelectedUnlockableCharacter = this.dataLoader.getCharacterLoader().get(selectedCharacter);
        if(!optionalSelectedUnlockableCharacter.isPresent()) {
            return false;
        }
        UnlockableCharacter selectedUnlockableCharacter = optionalSelectedUnlockableCharacter.get();
        
        Map<String, Integer> unlockedPowerups = this.saveManager.getCurrentSave().getUnlockedPowerups();
        for (Map.Entry<String, Integer> entry : unlockedPowerups.entrySet()) {
            Optional<UnlockablePowerup> powerupOpt = this.dataLoader.getPowerupLoader().get(entry.getKey());
            powerupOpt.ifPresent(p -> p.setCurrentLevel(entry.getValue()));
        }

        Stats stats = applyBuffs(selectedUnlockableCharacter.getCharacterStats());

        WeaponData defaultWeaponData = this.dataLoader.getWeaponLoader().get(selectedUnlockableCharacter.getDefaultWeapon()).get();
        AbstractAttackFactory attackFactory = this.getAttackFactory(defaultWeaponData.getId());

        Weapon defaultWeapon = new WeaponImpl(this,
                defaultWeaponData.getId(),
                (long) (defaultWeaponData.getDefaultCooldown() * (2 - stats.getStat(StatType.COOLDOWN))),
                defaultWeaponData.getDefaultAttacksPerCooldown(),
                attackFactory
            );

        this.character = new Character(
            selectedUnlockableCharacter.getId(),
            stats,
            selectedUnlockableCharacter.getRadius(),
            defaultWeapon,
            this.configData.getWeaponSlots()
        );
        
        this.weaponRandomizer = new WeaponRandomizer(this.dataLoader.getWeaponLoader().getAll().stream().map(WeaponData::getId).toList(), this.character);
        
        this.enemies = new LinkedList<>();
        this.collectibles = new LinkedList<>();
        this.attacks = new LinkedList<>();

        this.score = new Score(selectedUnlockableCharacter.getName());

        return true;
    }

    @Override
    public boolean isGameOver() {
        return this.isGameOver;
    }

    @Override
    public void update(long tickTime, Point2D.Double characterDirection) {
        synchronized(this) {
            this.score.incrementSessionTime(tickTime);
            this.character.setGettingAttacked(false);
            
            this.character.setDirection(characterDirection);
            this.character.move(tickTime);
            this.character.updateWeapons(tickTime);

            for (Enemy enemy : this.enemies) {
                enemy.setGettingAttacked(false);

                double deltaX = this.character.getPosition().getX() - enemy.getPosition().getX();
                double deltaY = this.character.getPosition().getY() - enemy.getPosition().getY();
                
                double distance = enemy.getDistance(this.character);

                Point2D.Double enemyDirection = new Point2D.Double(deltaX / distance, deltaY / distance);
                enemy.setDirection(enemyDirection);

                boolean collisionWithEnemies = false;
                Point2D.Double enemyFuturePosition = enemy.getFuturePosition(tickTime);
                
                for (Enemy otherEnemy : this.enemies) {
                    if (enemy != otherEnemy && enemyFuturePosition.distance(otherEnemy.getPosition()) < 15) {
                        collisionWithEnemies = true;
                        break;
                    }
                }

                boolean collisionWithCharacter = (enemyFuturePosition.distance(this.character.getPosition()) < 50);
                if (!collisionWithEnemies && !collisionWithCharacter) {
                    enemy.move(tickTime);
                }

                if (collisionWithCharacter) {
                    enemy.onCollision(this.character);
                }
            }
            
            synchronized(this.attacks) {
                for (Attack attack : this.attacks) {
                    attack.execute(tickTime);
                }
            }
            
            synchronized(this.attacks) {
                Iterator<Attack> attackIterator = this.attacks.iterator();
                while (attackIterator.hasNext()) {
                    Attack attack = attackIterator.next();
                    if (attack.isExpired()) {
                        attackIterator.remove();
                    }
                }
            }

            // controlla collisioni con collezionabili
            synchronized(this.collectibles) {
                Iterator<Collectible> collectibleIterator = this.collectibles.iterator();
                while (collectibleIterator.hasNext()) {
                    Collectible collectible = collectibleIterator.next();
                    if (collectible.getDistance(this.character) <= 50 * this.character.getStats().getStat(StatType.MAGNET)) {
                        character.collect(collectible);
                        collectibleIterator.remove();
                    }
                }
            }

            //elimina nemici morti
            synchronized (this.enemies) {
                Iterator<Enemy> enemyIterator = this.enemies.iterator();
                while (enemyIterator.hasNext()) {
                    Enemy enemy = enemyIterator.next();
                    if (enemy.getHealth() <= 0) {
                        this.spawnRandomCollectible(
                            enemy.getPosition(),
                            this.configData.getCollectibleSpawnChance(),
                            this.configData.getCoinSpawnChance(),
                            this.configData.getFoodSpawnChance(),
                            this.configData.getExperienceGemSpawnChance());
                        enemyIterator.remove();
                        this.score.incrementKillCounter();
                    }
                }
            }

            if(this.character.getHealth() <= 0) {
                this.isGameOver = true;
            }

            this.enemySpawner.update(tickTime);
        }
    }

    @Override
    public void addEnemy(Enemy enemy) {
        synchronized (this.enemies) {
            this.enemies.add(enemy);
        }
    }

    @Override
    public void removeEnemy(Enemy enemy) {
        synchronized (this.enemies) {
            this.enemies.remove(enemy);
        }
    }

    @Override
    public void addCollectible(Collectible collectible) {
        synchronized (this.collectibles) {
            this.collectibles.add(collectible);
        }
    }

    @Override
    public void removeCollectible(Collectible collectible) {
        synchronized (this.collectibles) {
            this.collectibles.remove(collectible);
        }
    }

    @Override
    public void addAttack(Attack attack) {
        synchronized (this.attacks) {
            this.attacks.add(attack);
        }
    }

    @Override
    public void removeAttack(Attack attack) {
        synchronized (this.attacks) {
            this.attacks.remove(attack);
        }
    }

    @Override
    public Dimension getVisualSize() {
        return this.visualSize;
    }

    @Override
    public Character getCharacter() {
        synchronized (this.character) {
            return this.character;
        }
    }

    @Override
    public List<Enemy> getEnemies() {
        synchronized (this.enemies) {
            return this.enemies.parallelStream().toList();
        }
    }

    @Override
    public List<Attack> getAttacks() {
        synchronized (this.attacks) {
            return this.attacks.parallelStream().toList();
        }
    }

    @Override
    public List<Weapon> getWeapons() {
        synchronized (this.character) {
            return this.character.getWeapons();
        }
    }

    @Override
    public List<Collectible> getCollectibles() {
        synchronized (this.collectibles) {
            return this.collectibles.parallelStream().toList();
        }
    }

    @Override
    public int getPlayerLevel() {
        return this.character.getLevel();
    }

    @Override
    public double getPlayerLevelPercentage() {
        return this.character.getLevelPercentage();
    }

    @Override
    public int getKillCounter() {
        return this.score.getKillCounter();
    }

    @Override
    public int getCoinCounter() {
        return this.character.getCoinCounter();
    }

    @Override
    public List<UnlockableCharacter> getChoosableCharacters() {
        List<UnlockableCharacter> unlockedCharacters = this.saveManager.getCurrentSave()
            .getUnlockedCharacters().stream()
            .map(id -> this.getDataLoader().getCharacterLoader().get(id).get())
            .toList();
        return unlockedCharacters;
    }

    @Override
    public List<UnlockableCharacter> getLockedCharacters() {
        List<UnlockableCharacter> unlockedCharacters = this.getChoosableCharacters();
        List<UnlockableCharacter> unlockableCharacters = this.dataLoader.getCharacterLoader().getAll();
        
        List<String> unlockedIds = unlockedCharacters.stream()
            .map(UnlockableCharacter::getId)
            .toList();

        List<UnlockableCharacter> lockedCharacters = unlockableCharacters.stream()
            .filter(c -> !unlockedIds.contains(c.getId()))
            .toList();
        return List.copyOf(lockedCharacters);
    }

    @Override
    public boolean buyCharacter(String selectedCharacter) {
        if(selectedCharacter != null) {
            Save currentSave = this.saveManager.getCurrentSave();
            UnlockableCharacter selectedUnlockableCharacter = this.getLockedCharacters().stream()
                .filter(c -> c.getId().equals(selectedCharacter))
                .findFirst()
                .orElse(null);
            if (selectedUnlockableCharacter == null || currentSave.getMoneyAmount() < selectedUnlockableCharacter.getPrice()) {
                return false;
            }
            currentSave.incrementMoneyAmount(- selectedUnlockableCharacter.getPrice());
            currentSave.addUnlockedCharacter(selectedUnlockableCharacter);
            this.saveManager.saveCurrentSave();
            return true;
        }
        return false;
    }

    @Override
    public List<UnlockablePowerup> getUnlockablePowerups() {
        List<UnlockablePowerup> unlockablePowerups = this.dataLoader.getPowerupLoader().getAll();
        Map<String, Integer> unlockedPowerups = this.saveManager.getCurrentSave().getUnlockedPowerups();

        List<UnlockablePowerup> levelAdjustedPowerups = unlockablePowerups.stream()
            .peek(p -> p.setCurrentLevel(unlockedPowerups.getOrDefault(p.getId(), 0)))
            .toList();
        return levelAdjustedPowerups;
    }

    @Override
    public boolean buyPowerup(String selectedPowerUp) {
        if(selectedPowerUp != null) {
            int powerupPrice = this.getDataLoader().getPowerupLoader().get(selectedPowerUp).get().getPrice();
            int moneyAmount = this.getCurrentSave().getMoneyAmount();
            Optional<UnlockablePowerup> unlockablePowerup = this.getDataLoader().getPowerupLoader().get(selectedPowerUp);
            if(!unlockablePowerup.isPresent()) {
                return false;
            }
            Save currentSave = this.getCurrentSave();
            if(moneyAmount >= powerupPrice) {
                boolean enhanced = unlockablePowerup.get().enhance();
                currentSave.enhancePowerup(unlockablePowerup.get());
                if(!enhanced) {
                    return false;
                }
                currentSave.incrementMoneyAmount(- unlockablePowerup.get().getPrice());
                this.saveManager.saveCurrentSave();
                return true;
            }
        }
        return false;
    }

    private Stats applyBuffs(Stats baseStats) {
        Stats modifiedStats = new Stats(baseStats);
        Map<String, Integer> unlockedPowerups = saveManager.getCurrentSave().getUnlockedPowerups();

        for (Map.Entry<String, Integer> entry : unlockedPowerups.entrySet()) {
            String powerupID = entry.getKey();

            dataLoader.getPowerupLoader().get(powerupID).ifPresent(unlockablePowerup -> {
                double multiplier = unlockablePowerup.getMultiplier();
                StatType statToModify = unlockablePowerup.getStatToModify();
                modifiedStats.multiplyStat(statToModify, multiplier);
            });
        }

        return modifiedStats;
    }

    private void spawnRandomCollectible(
            Point2D.Double position,
            double collectibleSpawnChance,
            double coinProbability,
            double foodProbability,
            double experienceGemProbability
    ) {
        if (position == null) {
            return;
        }

        if (Math.random() >= collectibleSpawnChance) {
            return;
        }

        double total = coinProbability + foodProbability + experienceGemProbability;
        double rand = Math.random() * total;

        if (rand < coinProbability) {
            this.addCollectible(new Coin(position));
        } else if (rand < coinProbability + foodProbability) {
            this.addCollectible(new Food(position));
        } else {
            this.addCollectible(new ExperienceGem(position));
        }
    }

    @Override
    public List<String> getSaveNames() {
        return this.saveManager.getSavesNames();
    }

    @Override
    public void createNewSave() {
        UnlockableCharacter defaultCharacter = this.dataLoader.getCharacterLoader().get(this.configData.getDefaultCharacterId()).orElse(null);
        if (defaultCharacter == null) {
            this.gameController.showErrorWithExit("Default character not found in config data!");
        }
        this.saveManager.createNewSave(defaultCharacter);
    }

    @Override
    public void loadSave(String selectedSave) {
        this.saveManager.loadSave(selectedSave);
    }

    @Override
    public Save getCurrentSave() {
        return this.saveManager.getCurrentSave();
    }

    @Override
    public long getElapsedTime() {
        if(this.score == null) {
            return 0;
        }
        return this.score.getSessionTime();
    }

    @Override
    public boolean hasJustLevelledUp() {
        return this.character.hasJustLevelledUp();
    }

    @Override
    public List<WeaponData> getRandomLevelUpWeapons() {
        return this.weaponRandomizer.getRandomWeapons(LEVELUP_CHOICES).stream()
            .map(weaponID -> this.dataLoader.getWeaponLoader().get(weaponID).orElse(null))
            .filter(data -> data != null)
            .toList();
    }

    @Override
    public void levelUpWeapon(String selectedWeapon) {
        Weapon weaponToLevelup = null;
        for (Weapon weapon : this.character.getWeapons()) {
            if(weapon.getId().equals(selectedWeapon)) {
                weaponToLevelup = weapon;
            }
        }
        if(weaponToLevelup != null) {
            weaponToLevelup.levelUp();
        } else {
            WeaponData newWeaponData = this.dataLoader.getWeaponLoader().get(selectedWeapon).orElse(null);
            Weapon newWeapon = new WeaponImpl(
                this,
                newWeaponData.getId(),
                newWeaponData.getDefaultCooldown(),
                newWeaponData.getDefaultAttacksPerCooldown(),
                this.getAttackFactory(newWeaponData.getId())
            );
            this.character.addWeapon(newWeapon);
        }
    }

    private AbstractAttackFactory getAttackFactory(String weaponID) {
        return switch (weaponID) {
            case "weapons/magicWand" -> new MagicWandFactory(this);
            case "weapons/santaWater" -> new SantaWaterFactory(this);
            case "weapons/garlic" -> new GarlicFactory(this);
            case "weapons/knife" -> new KnifeFactory(this);
            default -> null;
        };
    }

    Weapon getWeaponById(String weaponId) {
        List<Weapon> weapons = this.character.getWeapons();
        for (Weapon weapon : weapons) {
            if(weapon.getId().equals(weaponId)) {
                return weapon;
            }
        }
        return null;
    }
    
    @Override
    public Score exitGame() {
        this.score.setLevel(this.character.getLevel());
        this.score.setCoinCounter(this.character.getCoinCounter());
        this.saveManager.getCurrentSave().incrementMoneyAmount(getCoinCounter());
        this.saveManager.getCurrentSave().addScore(this.score);
        this.saveManager.saveCurrentSave();
        return this.score;
    }

    DataLoader getDataLoader() {
        return this.dataLoader;   
    }

    @Override
    public Collection<Unlockable> getAllItems() {
        final List<Unlockable> allItems = new LinkedList<>();
        allItems.addAll(this.dataLoader.getCharacterLoader().getAll());
        allItems.addAll(this.dataLoader.getPowerupLoader().getAll());
        return allItems;
    }
}
