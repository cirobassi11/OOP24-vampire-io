package it.unibo.vampireio.controller;
public interface GameController {
    void setRunning(boolean running);
    boolean isRunning();
    void startGame(String selectedCharacter);
}
