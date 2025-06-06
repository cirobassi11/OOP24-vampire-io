package it.unibo.vampireio.model;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.List;

public interface GameModel {
    void setModelErrorListener(ModelErrorListener errorListener);
    boolean initGame(String selectedCharacter);
    boolean isGameOver();
    void update(long tickTime, Point2D.Double characterDirection);
    Dimension getVisualSize();
    long getElapsedTime();
    int getPlayerLevel();
    double getPlayerLevelPercentage();
    int getKillCounter();
    int getCoinCounter();
    Character getCharacter();
    List<Enemy> getEnemies();
    List<Attack> getAttacks();
    List<Weapon> getWeapons();
    List<Collectible> getCollectibles();
    List<String> getSaveNames();
    void createNewSave();
    void loadSave(String selectedSaving);
    Save getCurrentSave();
    List<UnlockableCharacter> getChoosableCharacters();
    List<UnlockableCharacter> getLockedCharacters();
    boolean buyCharacter(String selectedCharacter);
    List<UnlockablePowerUp> getUnlockablePowerUps();
    boolean buyPowerUp(String selectedPowerUp);
    boolean hasJustLevelledUp();
    List<WeaponData> getRandomLevelUpWeapons();
    void levelUpWeapon(String selectedWeapon);
    Score exitGame();
    Collection<Unlockable> getAllItems();
}
