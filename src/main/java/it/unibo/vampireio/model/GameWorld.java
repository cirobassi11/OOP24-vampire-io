package it.unibo.vampireio.model;

import java.awt.Dimension;
import java.awt.geom.Point2D.Double;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GameWorld implements GameModel {
    private Character character;
    private Set<Enemy> enemies;
    private Set<Collectible> collectibles;

    public static final Dimension WORLD_SIZE = new Dimension(1600, 900);

    public GameWorld(String selectedCharacter) {
        /////////////////////////////////////////this.character = 
        this.enemies = new HashSet<>();
        this.collectibles = new HashSet<>();
    }

    @Override
    public void update() { //DA AGGIUNGERE INPUTTT
        System.out.println("AGGIORNAMENTO MODELLOOO");
    
        //muove il personaggio (non dovrebbe sovrapporsi a nemici)
        //controlla collisioni con nemici o positionable

        // muove tutti i nemici (controllando anche che non si sovrappongano)

        //spanwna i nemici fuori dalla visuale (world_size)
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
    public Map<String, Double> getPositionables() {
        Map<String, Double> positionables = new HashMap<>();
        positionables.put(character.getId(), this.character.getPosition());
        for (Enemy enemy : this.enemies) {
            positionables.put(enemy.getId(), enemy.getPosition());
        }
        for (Collectible collectible : this.collectibles) {
            positionables.put(collectible.getId(), collectible.getPosition());
        }
        return positionables;
    }
    
}
