package it.unibo.vampireio.controller.manager;

import java.awt.geom.Point2D;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.vampireio.controller.data.DataBuilder;
import it.unibo.vampireio.controller.data.ItemData;
import it.unibo.vampireio.model.api.GameModel;
import it.unibo.vampireio.view.api.GameView;

/**
 * Manages the game loop, handling updates to the game model and view.
 * It runs in a separate thread and processes input from the user.
 * The loop continues until the game is stopped or paused.
 */
public final class GameLoopManager {

    private static final int UPDATE_RATE = 60;

    private final GameModel model;
    private final GameView view;
    private final InputProcessor inputProcessor;

    private boolean running;
    private boolean paused;

    /**
     * Constructs a GameLoopManager with the specified model, view, input handler,
     * and input processor.
     *
     * @param model          the game model to manage
     * @param view           the game view to update
     * @param inputProcessor the input processor to compute movement directions
     */
    @SuppressFBWarnings(
        value = "EI2", 
        justification = "Model, view, and InputProcessor instances intentionally shared."
        )
    public GameLoopManager(
            final GameModel model,
            final GameView view,
            final InputProcessor inputProcessor) {
        this.model = model;
        this.view = view;
        this.inputProcessor = inputProcessor;
    }

    /**
     * Starts the game loop with the selected character.
     * Initializes the game model and starts the game loop in a new thread.
     *
     * @param selectedCharacter the character to start the game with
     * @return true if the game started successfully, false otherwise
     */
    boolean startGame(final String selectedCharacter) {
        if (!this.model.initGame(selectedCharacter)) {
            return false;
        }
        running = true;
        paused = false;
        new Thread(this::runGameLoop).start();
        return true;
    }

    /**
     * Pauses the game loop, clearing any pressed keys.
     * The game can be resumed later.
     */
    synchronized void pause() {
        this.inputProcessor.clear();
        paused = true;
    }

    /**
     * Resumes the game loop if it was paused.
     * The game continues from where it was paused.
     */
    void resume() {
        paused = false;
    }

    /**
     * Stops the game loop, ending the game.
     */
    void stop() {
        running = false;
    }

    /**
     * The main game loop that updates the game model and view.
     * It checks for user inputs, updates the game state, and renders the view.
     * The loop runs at a fixed update rate and continues until the game is over or
     * paused.
     */
    private void runGameLoop() {
        final long updateInterval = 1000 / UPDATE_RATE;
        long lastUpdateTime = System.currentTimeMillis();

        while (running) {
            final long currentTime = System.currentTimeMillis();
            final long elapsed = currentTime - lastUpdateTime;

            if (elapsed >= updateInterval) {
                if (this.model.isGameOver()) {
                    running = false;
                    view.setCurrentScore(DataBuilder.getCurrentScore(this.model));
                    view.showScreen(GameView.END_GAME);
                    synchronized (this) {
                        this.inputProcessor.clear();
                    }
                    break;
                }

                if (this.model.hasJustLevelledUp()) {
                    pause();
                    view.setItemsData(this.model.getRandomLevelUpWeapons().stream()
                            .map(item -> new ItemData(item.getId(), item.getName(), item.getDescription()))
                            .toList());
                    view.showScreen(GameView.ITEM_SELECTION);
                }

                synchronized (this) {
                    if (this.inputProcessor.isPauseRequested()) {
                        pause();
                        view.showScreen(GameView.PAUSE);
                    }
                }

                if (!paused) {
                    synchronized (this) {
                        final Point2D.Double direction = this.inputProcessor.computeDirection();
                        this.model.update(updateInterval, direction);
                        view.update(DataBuilder.getData(this.model));
                    }
                }

                lastUpdateTime = currentTime;
            }

            try {
                Thread.sleep(1);
            } catch (final InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
