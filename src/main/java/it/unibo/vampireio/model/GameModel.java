package it.unibo.vampireio.model;

import java.awt.Dimension;
import java.util.List;

public interface GameModel {
    void initGame(String selectedCharacter);
    void update(long tickTime);
    void addEnemy(Enemy enemy);
    void removeEnemy(Enemy enemy);
    void addCollectible(Collectible collectible);
    void removeCollectible(Collectible collectible);
    Dimension getVisualSize();
    Character getCharacter();
    List<Enemy> getEnemies();
    List<ProjectileAttack> getProjectileAttacks();
    List<AreaAttack> getAreaAttacks();
    List<Collectible> getCollectibles();
    List<UnlockablePowerUp> getUnlockablePowerUps();
    List<String> getSaveNames();
    void createNewSave();
    void loadSave(String selectedSaving);
    Save getCurrentSave();
    List<UnlockableCharacter> getUnlockableCharacters();
}
