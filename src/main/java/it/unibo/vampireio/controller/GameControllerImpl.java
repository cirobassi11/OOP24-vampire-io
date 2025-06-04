package it.unibo.vampireio.controller;


import it.unibo.vampireio.model.GameModel;
import it.unibo.vampireio.model.GameWorld;
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
        this.model = new GameWorld();
        this.model.setModelListener(message -> this.showError(message));
        this.inputHandler = new InputHandler();
        this.inputProcessor = new InputProcessor(this.inputHandler);
        this.screenManager = new ScreenManager(this.view);
        this.gameLoopManager = new GameLoopManager(this.model, this.view, this.inputHandler, this.inputProcessor);
        ListenerInitializer.initialize(this.view, this.model, this, this.gameLoopManager, this.screenManager);
        this.screenManager.showScreen(GameViewImpl.SAVE_MENU);
        this.view.setPlayerInputListener(this.inputHandler);
    }

    @Override
    public void showError(final String errorMessage) {
        this.view.showError(errorMessage);
        System.exit(1);
    }
}
