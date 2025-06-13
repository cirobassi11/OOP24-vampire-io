package it.unibo.vampireio.controller;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import it.unibo.vampireio.model.GameModel;
import it.unibo.vampireio.model.GameWorld;
import it.unibo.vampireio.view.GameView;
import it.unibo.vampireio.view.GameViewImpl;

/**
 * Implementation of the GameController interface.
 */
public final class GameControllerImpl implements GameController {

    private final GameModel model;
    private final GameView view;
    private final InputHandler inputHandler;
    private final InputProcessor inputProcessor;
    private final ScreenManager screenManager;
    private final GameLoopManager gameLoopManager;

    /**
     * Constructs a GameControllerImpl instance, initializing the model, view, input
     * handler,
     * input processor, screen manager, and game loop manager.
     * It also sets up error listeners for the model and view to handle error
     * messages.
     */
    public GameControllerImpl() {
        this.view = new GameViewImpl();
        this.model = new GameWorld();
        this.model.setModelErrorListener(this::showError);
        this.view.setViewErrorListener(this::showError);
        this.inputHandler = new InputHandler();
        this.inputProcessor = new InputProcessor(this.inputHandler);
        this.screenManager = new ScreenManager(this.view);
        this.gameLoopManager = new GameLoopManager(this.model, this.view, this.inputProcessor);
        ListenerInitializer.initialize(this.view, this.model, this, this.gameLoopManager, this.screenManager,
                this.inputHandler);
    }

    @SuppressFBWarnings(
        value = "DM_EXIT",
        justification = "Exiting the application on error is acceptable in this context."
    )
    @Override
    public void showError(final String errorMessage) {
        this.view.showError(errorMessage);
        System.exit(1);
    }
}
