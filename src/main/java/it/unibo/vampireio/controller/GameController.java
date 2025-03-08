package it.unibo.vampireio.controller;

import java.util.List;

public interface GameController extends Runnable {
    void setRunning(boolean running);
    boolean isRunning();
    void startGame(String selectedCharacter);
    VisualSizeDTO getVisualSizeData();
    List<UnlockablePowerUpDTO> getUnlockablePowerUpData();
}
