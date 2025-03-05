package it.unibo.vampireio.model;

import java.awt.Dimension;
import java.util.Set;

public interface GameModel {
    void update();
    void addEnemy(Enemy enemy);
    void removeEnemy(Enemy enemy);
    void addCollectible(Collectible collectible);
    void removeCollectible(Collectible collectible);
    Dimension getVisualSize();
    Character getCharacter();
    Set<Enemy> getEnemies();
    Set<Collectible> getCollectibles();
}
