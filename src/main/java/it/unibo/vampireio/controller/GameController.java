package it.unibo.vampireio.controller;

import java.util.List;

public interface GameController {
    void setRunning(boolean running);
    boolean isRunning();
    void startGame(String selectedCharacter);
    List<String> getSavingNames();
    void loadNewSaving();
    void loadSaving(String selectedSaving);
    List<ScoreData> getScores();
}
