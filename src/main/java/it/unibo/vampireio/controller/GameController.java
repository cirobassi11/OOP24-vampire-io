package it.unibo.vampireio.controller;

import java.util.List;
import it.unibo.vampireio.model.Saving;

public interface GameController {
    void setRunning(boolean running);
    boolean isRunning();
    void startGame(String selectedCharacter);
}
