package it.unibo.vampireio.model;

import java.awt.Dimension;
import java.util.HashSet;
import java.util.Set;

public class GameWorld implements GameModel {
    private Character character;
    private Set<Enemy> enemies;
    private Set<ProjectileAttack> projectileAttacks;
    private Set<AreaAttack> areaAttacks;
    private Set<Collectible> collectibles;

    public final Dimension visualSize = new Dimension(1280, 720);

    public GameWorld(String selectedCharacter) {
        /////////////////////////////////////////this.character = 
        this.enemies = new HashSet<>();
        this.collectibles = new HashSet<>();
    }

    @Override
    public void update() { //AGGIUNGERE INPUTTT
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
    public Set<Enemy> getEnemies() {
        return this.enemies;
    }

    @Override
    public Set<Collectible> getCollectibles() {
        return this.collectibles;
    }

    @Override
    public Set<UnlockablePowerUp> getUnlockablePowerUps() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUnlockablePowerUps'");
    }
}
