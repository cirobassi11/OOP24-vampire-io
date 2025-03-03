package it.unibo.vampireio.controller;

import java.util.Set;

import it.unibo.vampireio.model.Character;
import it.unibo.vampireio.model.Enemy;
import it.unibo.vampireio.model.Collectible;
import it.unibo.vampireio.view.GameView;

public interface GameController extends Runnable {
    void setRunning(boolean running);
    boolean isRunning();
    void setView(GameView view);
    void startGame(String selectedCharacter);
    Character getCharacter();
    Set<Enemy> getEnemies();
    Set<Collectible> getCollectibles();
}
