package it.unibo.vampireio.controller;

import java.util.List;

public interface GameController {
    void setRunning(boolean running);
    boolean isRunning();
    void startGame(String selectedCharacter);
}
