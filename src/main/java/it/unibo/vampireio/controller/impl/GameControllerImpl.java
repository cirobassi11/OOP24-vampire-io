package it.unibo.vampireio.controller.impl;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.vampireio.controller.api.GameController;
import it.unibo.vampireio.model.api.GameModel;
import it.unibo.vampireio.model.impl.GameModelImpl;
import it.unibo.vampireio.view.impl.GameViewImpl;
import it.unibo.vampireio.view.api.GameView;
import it.unibo.vampireio.controller.manager.InputProcessor;
import it.unibo.vampireio.controller.manager.ScreenManager;
import it.unibo.vampireio.controller.manager.GameLoopManager;
import it.unibo.vampireio.controller.manager.ListenerInitializer;

/**
 * Implementation of the GameController interface.
 */
public final class GameControllerImpl implements GameController {

    private final GameModel model;
    private final GameView view;
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
        this.model = new GameModelImpl();
        this.model.setModelErrorListener(this::showError);
        this.view.setViewErrorListener(this::showError);
        this.inputProcessor = new InputProcessor();
        this.screenManager = new ScreenManager(this.view);
        this.gameLoopManager = new GameLoopManager(this.model, this.view, this.inputProcessor);
        ListenerInitializer.init(this.view, this.model, this, this.gameLoopManager, this.screenManager,
                this.inputProcessor);
    }

    @SuppressFBWarnings(value = "DM_EXIT", justification = "Exiting the application on error is acceptable in this context.")
    @Override
    public void showError(final String errorMessage) {
        this.view.showError(errorMessage);
        System.exit(1);
    }
}
