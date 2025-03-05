package it.unibo.vampireio.controller;

import java.util.Set;

public interface GameController extends Runnable {
    void setRunning(boolean running);
    boolean isRunning();
    void startGame(String selectedCharacter);
    VisualSizeDTO getVisualSizeData();
    CharacterDTO getCharacterData();
    Set<EnemyDTO> getEnemiesData();
    Set<CollectibleDTO> getCollectiblesData();
    Set<UnlockablePowerUpDTO> getUnlockablePowerUpData();
}
