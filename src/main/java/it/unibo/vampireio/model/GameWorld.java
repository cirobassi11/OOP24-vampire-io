package it.unibo.vampireio.model;

import it.unibo.vampireio.controller.GameController;
import java.awt.geom.Point2D;
import java.awt.Dimension;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Iterator;

public class GameWorld implements GameModel {

    private GameController gameController;
    
    private DataLoader dataLoader;
    private SaveManager saveManager;

    private final Dimension visualSize = new Dimension(1280, 720);

    private static double CHARACTER_RADIUS = 32;

    private boolean isGameOver;
    private Score score;

    private Character character;
    private List<Enemy> enemies;
    private List<Attack> attacks;
    private List<Collectible> collectibles;
    private EnemySpawner enemySpawner;

    
    public GameWorld(GameController gameController) {
        this.gameController = gameController;

        this.dataLoader = new DataLoader(this.gameController);
        this.saveManager = new SaveManager(this.gameController);        
    }

    @Override
    public boolean initGame(String selectedCharacter) {
        this.isGameOver = false;
        this.enemySpawner = new EnemySpawnerImpl(this);
        
        Optional<UnlockableCharacter> optionalSelectedUnlockableCharacter = this.dataLoader.getCharacterLoader().get(selectedCharacter);
        if(!optionalSelectedUnlockableCharacter.isPresent()) {
            return false;
        }
        UnlockableCharacter selectedUnlockableCharacter = optionalSelectedUnlockableCharacter.get();
        
        WeaponData defaultWeaponData = this.dataLoader.getWeaponLoader().get(selectedUnlockableCharacter.getDefaultWeapon()).get();
        AttackFactory attackFactory = null;

        switch(defaultWeaponData.getId()) {
            case "weapons/magicWand" -> attackFactory = new MagicWandFactory(this);
            case "weapons/santaWater" -> attackFactory = new SantaWaterFactory(this);
            case "weapons/garlic" -> attackFactory = new GarlicFactory(this);
            case "weapons/knife" -> attackFactory = new KnifeFactory(this);
            default -> {
                this.gameController.showError("Weapon not found");
                return false;
            }
        }

        Weapon defaultWeapon = new WeaponImpl(this, defaultWeaponData.getId(), defaultWeaponData.getDefaultCooldown(), defaultWeaponData.getDefaultAttacksPerCooldown(), attackFactory);

        Map<String, Integer> unlockedPowerUps = this.saveManager.getCurrentSave().getUnlockedPowerUps();
        for (Map.Entry<String, Integer> entry : unlockedPowerUps.entrySet()) {
            Optional<UnlockablePowerUp> powerupOpt = this.dataLoader.getPowerUpLoader().get(entry.getKey());
            powerupOpt.ifPresent(p -> p.setCurrentLevel(entry.getValue()));
        }

        Stats stats = applyBuffs(selectedUnlockableCharacter.getCharacterStats());

        this.character = new Character(
            selectedUnlockableCharacter.getId(),
            selectedUnlockableCharacter.getName(),
            stats,
            CHARACTER_RADIUS,
            defaultWeapon
        );
        
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
            
            // muove il personaggio (non dovrebbe sovrapporsi a nemici)
            this.character.setDirection(characterDirection);
            this.character.move(tickTime);
            this.character.updateWeapons(tickTime);

            // muove tutti i nemici (controllando anche che non si sovrappongano)
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

                //avoid collision with character
                boolean collisionWithCharacter = (enemyFuturePosition.distance(this.character.getPosition()) < 50);
                if (!collisionWithEnemies && !collisionWithCharacter) {
                    enemy.move(tickTime);
                }

                //attack the character
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
                        this.spawnRandomCollectible(enemy.getPosition());
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
            if (selectedUnlockableCharacter == null) {
                this.gameController.showError("Character not found");
                return false;
            }
            if (currentSave.getMoneyAmount() < selectedUnlockableCharacter.getPrice()) {
                this.gameController.showError("You don't have enough coins!");
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
    public List<UnlockablePowerUp> getUnlockablePowerups() {
        List<UnlockablePowerUp> unlockablePowerups = this.dataLoader.getPowerUpLoader().getAll();
        Map<String, Integer> unlockedPowerups = this.saveManager.getCurrentSave().getUnlockedPowerUps();

        List<UnlockablePowerUp> levelAdjustedPowerups = unlockablePowerups.stream()
            .peek(p -> p.setCurrentLevel(unlockedPowerups.getOrDefault(p.getId(), 0)))
            .toList();
        return levelAdjustedPowerups;
    }

    @Override
    public boolean buyPowerup(String selectedPowerUp) {
        if(selectedPowerUp != null) {
            int powerupPrice = this.getDataLoader().getPowerUpLoader().get(selectedPowerUp).get().getPrice();
            int moneyAmount = this.getCurrentSave().getMoneyAmount();
            
            Optional<UnlockablePowerUp> unlockablePowerup = this.getDataLoader().getPowerUpLoader().get(selectedPowerUp);
            
            if(!unlockablePowerup.isPresent()) {
                this.gameController.showError("Powerup not found!");
                return false;
            }

            Save currentSave = this.getCurrentSave();

            if(moneyAmount >= powerupPrice) {
                boolean enhanced = unlockablePowerup.get().enhance();
                currentSave.enhancePowerup(unlockablePowerup.get());
                if(!enhanced) {
                    this.gameController.showError("Max level reached!");
                    return false;
                }
                currentSave.incrementMoneyAmount(- unlockablePowerup.get().getPrice());
                this.saveManager.saveCurrentSave();
                return true;
            }
            else {
                this.gameController.showError("You don't have enough coins!");
            }
        }
        return false;
    }

    private Stats applyBuffs(Stats baseStats) {
    Stats modifiedStats = new Stats(baseStats);
    Map<String, Integer> unlockedPowerUps = saveManager.getCurrentSave().getUnlockedPowerUps();

    for (Map.Entry<String, Integer> entry : unlockedPowerUps.entrySet()) {
        String powerupID = entry.getKey();

        dataLoader.getPowerUpLoader().get(powerupID).ifPresent(unlockablePowerup -> {
            double multiplier = unlockablePowerup.getMultiplier();
            StatType statToModify = unlockablePowerup.getStatToModify();
            modifiedStats.multiplyStat(statToModify, multiplier);
        });
    }

    return modifiedStats;
}

    private void spawnRandomCollectible(Point2D.Double position) {
        if(position == null) {
            return;
        }

        if(Math.random() < 0.5) {
            return;
        }

        int random = (int) (Math.random() * 3);
        Collectible collectible = null;
        switch (random) {
            case 0 -> collectible = new Coin(position);
            case 1 -> collectible = new Food(position);
            case 2 -> collectible = new ExperienceGem(position);
        }
        if (collectible != null) {
            this.addCollectible(collectible);
        }
    }

    @Override
    public List<String> getSaveNames() {
        return this.saveManager.getSavesNames();
    }

    @Override
    public void createNewSave() {
        this.saveManager.createNewSave();
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
}
