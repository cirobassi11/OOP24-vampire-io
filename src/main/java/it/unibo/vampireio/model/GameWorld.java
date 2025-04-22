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

    private List<UnlockableCharacter> unlockableCharacters;
    private List<UnlockablePowerUp> unlockablePowerUps;

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

        UnlockableCharacter selectedUnlockableCharacter = this.unlockableCharacters
                .stream()
                .filter(character -> character.getId().equals(selectedCharacter))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Character not found"));

        this.character = new Character(selectedCharacter, selectedUnlockableCharacter.getName(), selectedUnlockableCharacter.getCharacterStats(), GameWorld.ENTITY_SHAPE);         
        this.enemies = new LinkedList<>();
        this.collectibles = new LinkedList<>();
        this.areaAttacks = new LinkedList<>();
        this.projectileAttacks = new LinkedList<>();
    }

    @Override
    public void update(long tickTime) {
        synchronized(this) {
            
            //muove il personaggio (non dovrebbe sovrapporsi a nemici)
            this.character.setDirection(new Point2D.Double(0.15, 0.15));/////////
            this.character.move(tickTime);

            // muove tutti i nemici (controllando anche che non si sovrappongano)
            for (Enemy enemy : this.enemies) {
                enemy.move(tickTime);
                enemy.onCollision(this.character);
            }

            //controlla collisioni con collezionabili
            for (Collectible collectible : this.collectibles) {
                //TODO
            }

            //spanwna i nemici FUORI DALLA VISUALE
            //this.enemySpawner.update(tickTime);
        }
    }

    @Override
    public void addEnemy(Enemy enemy) {
        this.enemies.add(enemy);
    }

    @Override
    public void removeEnemy(Enemy enemy) {
        this.enemies.remove(enemy);
    }

    @Override
    public void addCollectible(Collectible collectible) {
        this.collectibles.add(collectible);
    }

    @Override
    public void removeCollectible(Collectible collectible) {
        this.collectibles.remove(collectible);
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
    public List<UnlockableCharacter> getUnlockableCharacters() {
        List<UnlockableCharacter> unlockableCharacters = this.dataLoader.getCharacterLoader().loadAllCharacters();
        this.unlockableCharacters = unlockableCharacters;
        return List.copyOf(unlockableCharacters);
    }

    @Override
    public List<UnlockablePowerUp> getUnlockablePowerUps() {
        List<UnlockablePowerUp> unlockablePowerUps = this.dataLoader.getPowerUpLoader().loadAllPowerUps();
        this.unlockablePowerUps = unlockablePowerUps;
        return List.copyOf(unlockablePowerUps);
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
