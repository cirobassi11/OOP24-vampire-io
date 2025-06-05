package it.unibo.vampireio.controller;

import it.unibo.vampireio.model.GameModel;
import it.unibo.vampireio.view.GameView;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.stream.Collectors;

public class GameLoopManager {

    private static final int UPDATE_RATE = 60;

    private final GameModel model;
    private final GameView view;
    private final InputHandler inputHandler;
    private final InputProcessor inputProcessor;

    private boolean running = false;
    private boolean paused = false;

    public GameLoopManager(GameModel model, GameView view, InputHandler inputHandler, InputProcessor inputProcessor) {
        this.model = model;
        this.view = view;
        this.inputHandler = inputHandler;
        this.inputProcessor = inputProcessor;
    }

    public boolean startGame(String selectedCharacter) {
        if (!this.model.initGame(selectedCharacter)) {
            return false;
        }

        running = true;
        paused = false;

        new Thread(this::runGameLoop).start();;

        return true;
    }

    public void pause() {
        paused = true;
        inputHandler.clearPressedKeys();
    }

    public void resume() {
        paused = false;
    }

    public void stop() {
        running = false;
    }

    private void runGameLoop() {
        final long updateInterval = 1000 / UPDATE_RATE;
        long lastUpdateTime = System.currentTimeMillis();

        while (running) {
            long currentTime = System.currentTimeMillis();
            long elapsed = currentTime - lastUpdateTime;

            if (elapsed >= updateInterval) {
                if (this.model.isGameOver()) {
                    running = false;
                    view.setCurrentScore(DataBuilder.getCurrentScore(this.model));
                    view.showScreen(GameView.END_GAME);
                    inputHandler.clearPressedKeys();
                    break;
                }

                if (this.model.hasJustLevelledUp()) {
                    pause();
                    view.setItemsData(this.model.getRandomLevelUpWeapons().stream()
                            .map(item -> new ItemData(item.getId(), item.getName(), item.getDescription()))
                            .collect(Collectors.toList()));
                    view.showScreen(GameView.ITEM_SELECTION);
                }

                if (inputHandler.isKeyPressed(KeyEvent.VK_ESCAPE)) {
                    pause();
                    view.showScreen(GameView.PAUSE);
                }

                if (!paused) {
                    Point2D.Double direction = inputProcessor.computeDirection();
                    this.model.update(updateInterval, direction);
                    view.update(DataBuilder.getData(this.model));
                }

                lastUpdateTime = currentTime;
            }

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}