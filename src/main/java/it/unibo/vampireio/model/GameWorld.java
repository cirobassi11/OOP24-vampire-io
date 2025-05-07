package it.unibo.vampireio.model;

import it.unibo.vampireio.controller.GameController;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.Dimension;
import java.awt.Rectangle;
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
    private static Shape ENTITY_SHAPE = new Rectangle(64, 64);

    private boolean isGameOver;
    private Score score;

    private Character character;
    private List<Enemy> enemies;
    private List<ProjectileAttack> projectileAttacks;
    private List<AreaAttack> areaAttacks;
    private List<Collectible> collectibles;
    private EnemySpawner enemySpawner;

    
    public GameWorld(GameController gameController) {
        this.gameController = gameController;

        this.dataLoader = new DataLoader(this.gameController);
        this.saveManager = new SaveManager(this.gameController);        
    }

    @Override
    public void initGame(String selectedCharacter) {
        this.isGameOver = false;
        this.enemySpawner = new EnemySpawnerImpl(this);
        UnlockableCharacter selectedUnlockableCharacter = this.dataLoader.getCharacterLoader().get(selectedCharacter).get();
        WeaponData defaultWeaponData = this.dataLoader.getWeaponLoader().get(selectedUnlockableCharacter.getDefaultWeapon()).get();
        Weapon defaultWeapon = new WeaponImpl(this, defaultWeaponData.getId(), defaultWeaponData.getProjectileId(), defaultWeaponData.getDefaultCooldown(), defaultWeaponData.getProjectilePerCooldown());

        this.character = new Character(
            selectedUnlockableCharacter.getId(),
            selectedUnlockableCharacter.getName(),
            selectedUnlockableCharacter.getCharacterStats(),
            GameWorld.ENTITY_SHAPE,
            defaultWeapon
        );
        
        this.enemies = new LinkedList<>();
        this.collectibles = new LinkedList<>();
        this.areaAttacks = new LinkedList<>();
        this.projectileAttacks = new LinkedList<>();

        this.score = new Score(selectedUnlockableCharacter.getName());
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

            // controlla collisioni con collezionabili
            Iterator<Collectible> iterator = this.collectibles.iterator();
            while (iterator.hasNext()) {
                Collectible collectible = iterator.next();
                if (collectible.getPosition().distance(this.character.getPosition()) <= 50 * this.character.getStats().getStat(StatType.MAGNET)) {
                    character.collect(collectible);
                    iterator.remove();
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
    public List<ProjectileAttack> getProjectileAttacks() {
        synchronized (this.projectileAttacks) {
            return this.projectileAttacks.parallelStream().toList();
        }
    }

    @Override
    public List<AreaAttack> getAreaAttacks() {
        synchronized (this.areaAttacks) {
            return this.areaAttacks.parallelStream().toList();
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
            int currentLevel = 0;
            Map<String, Integer> unlockedPowerups = this.getCurrentSave().getUnlockedPowerUps();
            if(unlockedPowerups != null && unlockedPowerups.containsKey(selectedPowerUp)) {
                currentLevel = unlockedPowerups.get(selectedPowerUp).intValue();
            }
            int maxLevel = this.getDataLoader().getPowerUpLoader().get(selectedPowerUp).get().getMaxLevel();
            int powerupPrice = this.getDataLoader().getPowerUpLoader().get(selectedPowerUp).get().getPrice();
            int moneyAmount = this.getCurrentSave().getMoneyAmount();
            
            Optional<UnlockablePowerUp> unlockablePowerup = this.getDataLoader().getPowerUpLoader().get(selectedPowerUp);
            
            if(!unlockablePowerup.isPresent()) {
                this.gameController.showError("Powerup not found!");
                return false;
            }

            if(moneyAmount >= powerupPrice) {
                boolean enhanced = unlockablePowerup.get().enhance();
                this.getCurrentSave().enhancePowerup(unlockablePowerup.get());
                if(!enhanced) {
                    this.gameController.showError("Max level reached!");
                    return false;
                }
                this.saveManager.saveCurrentSave();
                return true;
            }
            else {
                this.gameController.showError("You don't have enough coins!");
            }
        }
        return false;
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
