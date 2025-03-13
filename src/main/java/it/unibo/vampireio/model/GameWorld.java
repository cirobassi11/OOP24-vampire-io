package it.unibo.vampireio.model;

import java.awt.Dimension;
import java.util.Collections;
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
        this.enemies = Collections.synchronizedList(new LinkedList<>());
        this.projectileAttacks = Collections.synchronizedList(new LinkedList<>());
        this.areaAttacks = Collections.synchronizedList(new LinkedList<>());
        this.collectibles = Collections.synchronizedList(new LinkedList<>());
    }

    @Override
    public void update(long tickTime) { // TODO: add input
        System.out.println("AGGIORNAMENTO MODELLOOO");
        synchronized(this) {
            //muove il personaggio (non dovrebbe sovrapporsi a nemici)
            //controlla collisioni con nemici o positionable

            // muove tutti i nemici (controllando anche che non si sovrappongano)

            //spanwna i nemici fuori dalla visuale (visualSize)
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
            return Collections.unmodifiableList(this.enemies);
        }
    }

    @Override
    public List<ProjectileAttack> getProjectileAttacks() {
        synchronized (this.projectileAttacks) {
            return Collections.unmodifiableList(this.projectileAttacks);
        }
    }

    @Override
    public List<AreaAttack> getAreaAttacks() {
        synchronized (this.areaAttacks) {
            return Collections.unmodifiableList(this.areaAttacks);
        }
    }

    @Override
    public List<Collectible> getCollectibles() {
        synchronized (this.collectibles) {
            return Collections.unmodifiableList(this.collectibles);
        }
    }

    @Override
    public List<UnlockablePowerUp> getUnlockablePowerUps() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUnlockablePowerUps'");
    }
}
