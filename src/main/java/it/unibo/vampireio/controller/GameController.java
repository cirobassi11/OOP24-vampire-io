package it.unibo.vampireio.controller;

import java.util.Set;

import it.unibo.vampireio.view.GameView;

public interface GameController extends Runnable {
    void setRunning(boolean running);
    boolean isRunning();
    void setView(GameView view);
    void startGame(String selectedCharacter);
    CharacterDTO getCharacterData();
    Set<EnemyDTO> getEnemiesData();
    Set<CollectibleDTO> getCollectiblesData();
    Set<UnlockablePowerUpDTO> getUnlockablePowerUpData();
}
