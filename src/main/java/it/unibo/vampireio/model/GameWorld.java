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

    public final Dimension visualSize = new Dimension(1280, 720);

    public GameWorld(String selectedCharacter) {
        ///player???
        this.character = CharacterLoader.loadCharacterById(selectedCharacter);
        this.enemies = new LinkedList<>();
        this.collectibles = new LinkedList<>();
    }

    @Override
    public void update() { // TODO: add input
        System.out.println("AGGIORNAMENTO MODELLOOO");
    
        //muove il personaggio (non dovrebbe sovrapporsi a nemici)
        //controlla collisioni con nemici o positionable

        // muove tutti i nemici (controllando anche che non si sovrappongano)

        //spanwna i nemici fuori dalla visuale (visualSize)
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
        return this.character;
    }

    @Override
    public List<Enemy> getEnemies() {
        return this.enemies;
    }

    @Override
    public List<ProjectileAttack> getProjectileAttacks() {
        return this.projectileAttacks;
    }

    @Override
    public List<Collectible> getCollectibles() {
        return this.collectibles;
    }

    @Override
<<<<<<< HEAD
    public Set<UnlockablePowerUp> getUnlockablePowerUps() {
=======
    public List<UnlockablePowerUp> getUnlockablePowerUps() {
>>>>>>> 513a20e7eba9f3f926fde5e1dbf76d32a06ade93
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUnlockablePowerUps'");
    }
}
