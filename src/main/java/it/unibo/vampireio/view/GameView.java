package it.unibo.vampireio.view;

import it.unibo.vampireio.controller.GameController;

public interface GameView {
    GameController getController();
    void update();
}
