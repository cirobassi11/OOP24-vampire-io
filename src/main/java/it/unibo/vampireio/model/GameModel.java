package it.unibo.vampireio.model;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.util.List;

public interface GameModel {
    void initGame(String selectedCharacter);
    void update(long tickTime, Point2D.Double characterDirection);
    void addEnemy(Enemy enemy);
    void removeEnemy(Enemy enemy);
    void addCollectible(Collectible collectible);
    void removeCollectible(Collectible collectible);
    Dimension getVisualSize();
    int getPlayerLevel();
    double getPlayerLevelPercentage();
    int getKillCounter();
    int getCoinCounter();
    Character getCharacter();
    List<Enemy> getEnemies();
    List<ProjectileAttack> getProjectileAttacks();
    List<AreaAttack> getAreaAttacks();
    List<Collectible> getCollectibles();
    List<String> getSaveNames();
    void createNewSave();
    void loadSave(String selectedSaving);
    Save getCurrentSave();
    List<UnlockableCharacter> getChoosableCharacters();
    List<UnlockableCharacter> getLockedCharacters();
    boolean buyCharacter(String selectedCharacter);
    List<UnlockablePowerUp> getUnlockablePowerups();
    boolean buyPowerup(String selectedPowerup);
    Score exitGame();
}
