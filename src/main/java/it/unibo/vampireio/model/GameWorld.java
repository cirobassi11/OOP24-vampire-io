package it.unibo.vampireio.model;

import java.awt.Dimension;
import java.util.LinkedList;
import java.util.List;

public class GameWorld implements GameModel {
    private Character character;
    private List<Enemy> enemies;
    private List<ProjectileAttack> projectileAttacks;
    private List<AreaAttack> areaAttacks;
    private List<Collectible> collectibles;

    private EnemySpawner enemySpawner;

    private SavingManager savingManager = new SavingManager();

    private final Dimension visualSize = new Dimension(1280, 720);

    @Override
    public void initGame(String selectedCharacter) {
        this.enemySpawner = new EnemySpawnerImpl(this);

        //this.character = DataLoader.getCharacterById(selectedCharacter);
        
        this.enemies = new LinkedList<>();

        this.collectibles = new LinkedList<>();

        this.areaAttacks = new LinkedList<>();
        this.projectileAttacks = new LinkedList<>();
    }

    @Override
    public void update(long tickTime) { // TODO: add input
        System.out.println("AGGIORNAMENTO MODELLOOO");
        synchronized(this) {
            //muove il personaggio (non dovrebbe sovrapporsi a nemici)
            //controlla collisioni con nemici o positionable

            // muove tutti i nemici (controllando anche che non si sovrappongano)

            //spanwna i nemici FUORI DALLA VISUALE
            this.enemySpawner.update(tickTime);
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
    public List<UnlockablePowerUp> getUnlockablePowerUps() {
        return List.of(); // TODO
    }

    @Override
    public List<String> getSavingsNames() {
        return this.savingManager.getSavingsNames();
    }

    @Override
    public void createNewSaving() {
        this.savingManager.createNewSaving();
    }

    @Override
    public void loadSaving(String selectedSaving) {
        this.savingManager.loadSaving(selectedSaving);
    }

    @Override
    public Saving getCurrentSaving() {
        return this.savingManager.getCurrentSaving();
    }
}
