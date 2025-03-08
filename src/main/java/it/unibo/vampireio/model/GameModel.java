package it.unibo.vampireio.model;

import java.awt.Dimension;
import java.util.List;

public interface GameModel {
    void update();
    void addEnemy(Enemy enemy);
    void removeEnemy(Enemy enemy);
    void addCollectible(Collectible collectible);
    void removeCollectible(Collectible collectible);
    Dimension getVisualSize();
    Character getCharacter();
    List<Enemy> getEnemies();
    List<ProjectileAttack> getProjectileAttacks();
    List<Collectible> getCollectibles();
    List<UnlockablePowerUp> getUnlockablePowerUps();
}
