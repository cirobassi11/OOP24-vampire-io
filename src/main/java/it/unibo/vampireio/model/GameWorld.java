package it.unibo.vampireio.model;

import it.unibo.vampireio.controller.GameController;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.List;

public class GameWorld implements GameModel {

    private GameController gameController;

    private final Dimension visualSize = new Dimension(1280, 720);
    static Shape ENTITY_SHAPE = new Rectangle(64, 64);

    private Character character;
    private List<Enemy> enemies;
    private List<ProjectileAttack> projectileAttacks;
    private List<AreaAttack> areaAttacks;
    private List<Collectible> collectibles;
    private EnemySpawner enemySpawner;

    private DataLoader dataLoader;
    private SaveManager saveManager;
    
    public GameWorld(GameController gameController) {
        this.gameController = gameController;

        this.dataLoader = new DataLoader(this.gameController);
        this.saveManager = new SaveManager(this.gameController);
    }

    @Override
    public void initGame(String selectedCharacter) {
        this.enemySpawner = new EnemySpawnerImpl(this);

        UnlockableCharacter selectedUnlockableCharacter = this.saveManager.getCurrentSave().getUnlockedCharacters() //SI POTREBBE FARE UN METODO DEL LOADER??
                .stream()
                .filter(character -> character.getId().equals(selectedCharacter))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Character not found"));///////////////////

        this.character = new Character(selectedCharacter, selectedUnlockableCharacter.getName(), selectedUnlockableCharacter.getCharacterStats(), GameWorld.ENTITY_SHAPE);         
        this.enemies = new LinkedList<>();
        this.collectibles = new LinkedList<>();
        this.areaAttacks = new LinkedList<>();
        this.projectileAttacks = new LinkedList<>();
    }

    @Override
    public void update(long tickTime, Point2D.Double characterDirection) {
        synchronized(this) {
            
            // muove il personaggio (non dovrebbe sovrapporsi a nemici)
            this.character.setDirection(characterDirection);
            this.character.move(tickTime);

            // muove tutti i nemici (controllando anche che non si sovrappongano)
            for (Enemy enemy : this.enemies) {
                double deltaX = this.character.getPosition().getX() - enemy.getPosition().getX();
                double deltaY = this.character.getPosition().getY() - enemy.getPosition().getY();
                
                double distance = enemy.getDistance(this.character);

                Point2D.Double enemyDirection = new Point2D.Double(deltaX / distance, deltaY / distance);
                enemy.setDirection(enemyDirection);

                boolean collision = false;
                Point2D.Double enemyFuturePosition = enemy.getFuturePosition(tickTime);
                
                for (Enemy otherEnemy : this.enemies) {
                    if (enemy != otherEnemy && enemyFuturePosition.distance(otherEnemy.getPosition()) < 15) {
                        collision = true;
                        break;
                    }
                }

                //avoid collision with character
                if (enemyFuturePosition.distance(this.character.getPosition()) < 50) {
                    collision = true;
                }

                if (!collision) {
                    enemy.move(tickTime);
                }

                enemy.onCollision(this.character);
            }

            // controlla collisioni con collezionabili
            for (Collectible collectible : this.collectibles) {
                //TODO
            }

            // spanwna i nemici FUORI DALLA VISUALE
            this.enemySpawner.update(tickTime);
        }
    }

    DataLoader getDataLoader() {
        return this.dataLoader;   
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
        return 100000; //TODO
    }

    @Override
    public int getCoinCounter() {
        return 100000; //TODO
    }

    @Override
    public List<UnlockableCharacter> getChoosableCharacters() {
        return List.copyOf(this.saveManager.getCurrentSave().getUnlockedCharacters());
    }

    @Override
    public List<UnlockableCharacter> getLockedCharacters() {
        List<UnlockableCharacter> unlockedCharacters = this.saveManager.getCurrentSave().getUnlockedCharacters();
        List<UnlockableCharacter> unlockableCharacters = this.dataLoader.getCharacterLoader().loadAllCharacters();
        
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

    @Override
    public List<UnlockablePowerUp> getUnlockedPowerUps(){
        return List.copyOf(this.saveManager.getCurrentSave().getUnlockedPowerUps());
    }

    @Override
    public List<UnlockablePowerUp> getLockedPowerUps() {
        List<UnlockablePowerUp> unlockedPowerUps = this.saveManager.getCurrentSave().getUnlockedPowerUps();
        List<UnlockablePowerUp> unlockablePowerUps = this.dataLoader.getPowerUpLoader().loadAllPowerUps();
        
        List<String> unlockedIds = unlockedPowerUps.stream()
            .map(UnlockablePowerUp::getId)
            .toList();

        List<UnlockablePowerUp> lockedPowerUps = unlockedPowerUps.stream()
            .filter(c -> !unlockedIds.contains(c.getId()))
            .toList();
        return List.copyOf(lockedPowerUps);
    }

    @Override
    public boolean buyPowerUp(String selectedPowerUp){
        Save currentSave = this.saveManager.getCurrentSave();
        UnlockablePowerUp selectedUnlockablePowerUp = this.getLockedPowerUps().stream()
            .filter(c -> c.getId().equals(selectedPowerUp))
            .findFirst()
            .orElse(null);
        if (selectedUnlockablePowerUp == null) {
            this.gameController.showError("PowerUp not found");
            return false;
        }
        if (currentSave.getMoneyAmount() < selectedUnlockablePowerUp.getPrice()) {
            this.gameController.showError("You don't have enough coins!");
            return false;
        }
        //if (currentSave.getUnlockedPowerUps()..... == /*max lvl*/ ) {
            //stop
        //}
        currentSave.incrementMoneyAmount(- selectedUnlockablePowerUp.getPrice());
        currentSave.addUnlockedPowerUp(selectedUnlockablePowerUp);
        this.saveManager.saveCurrentSave();
        return true;
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
}
