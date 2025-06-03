package it.unibo.vampireio.controller;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;
import it.unibo.vampireio.model.Character;
import it.unibo.vampireio.model.Attack;
import it.unibo.vampireio.model.Collectible;
import it.unibo.vampireio.model.Enemy;
import it.unibo.vampireio.model.GameModel;
import it.unibo.vampireio.model.GameWorld;
import it.unibo.vampireio.model.Save;
import it.unibo.vampireio.model.Score;
import it.unibo.vampireio.model.Unlockable;
import it.unibo.vampireio.view.GameView;
import it.unibo.vampireio.view.GameViewImpl;

public class GameControllerImpl implements GameController {

    private static final int FRAME_RATE = 60;
    private static final int TICK_RATE = 60;

    private GameModel model;
    private GameView view;

    private final InputHandler inputHandler;
    private final InputProcessor inputProcessor;
    private final ScreenManager screenManager;
    private final GameLoopManager gameLoopManager;

    public GameControllerImpl() {
        this.view = new GameViewImpl();
        this.model = new GameWorld(this);
        this.inputHandler = new InputHandler();
        this.inputProcessor = new InputProcessor(this.inputHandler);
        this.screenManager = new ScreenManager(this.view);
        this.gameLoopManager = new GameLoopManager(this.model, this.view, this.inputHandler, this.inputProcessor);
        ListenerInitializer.initialize(this.view, this.model, this, this.gameLoopManager, this.screenManager);
        this.screenManager.showScreen(GameViewImpl.SAVE_MENU);
        this.view.setPlayerInputListener(this.inputHandler);
    }

    @Override
    public void showError(String message) {
        this.view.showError(message);
    }

    @Override
    public void showErrorWithExit(String message) {
        this.view.showError(message);
        System.exit(1);
    }
}
