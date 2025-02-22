package it.unibo.vampireio.controller;

import it.unibo.vampireio.model.GameModel;
import it.unibo.vampireio.model.GameModelImpl;
import it.unibo.vampireio.view.GameView;
import it.unibo.vampireio.view.GameViewImpl;

public class GameControllerImpl implements GameController {
    private final GameModel model;
    private final GameView view;
    
    public GameControllerImpl() {
        this.model = new GameModelImpl();
        this.view = new GameViewImpl();
    }
}
