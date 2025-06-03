package it.unibo.vampireio.controller;

import it.unibo.vampireio.model.GameModel;
import it.unibo.vampireio.view.GameView;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.stream.Collectors;

public class GameLoopManager {

    private static final int FRAME_RATE = 60;
    private static final int TICK_RATE = 60;

    private final GameModel model;
    private final GameView view;
    private final InputHandler inputHandler;
    private final InputProcessor inputProcessor;

    private Thread modelThread;
    private Thread viewThread;

    private volatile boolean running = false;
    private volatile boolean paused = false;

    public GameLoopManager(GameModel model, GameView view, InputHandler inputHandler, InputProcessor inputProcessor) {
        this.model = model;
        this.view = view;
        this.inputHandler = inputHandler;
        this.inputProcessor = inputProcessor;
    }

    public boolean startGame(String selectedCharacter) {
        if (!this.model.initGame(selectedCharacter)) return false;

        this.running = true;
        this.paused = false;

        modelThread = new Thread(this::modelLoop);
        viewThread = new Thread(this::viewLoop);

        modelThread.start();
        viewThread.start();

        return true;
    }

    public synchronized void pause() {
        paused = true;
        inputHandler.clearPressedKeys();
    }

    public synchronized void resume() {
        paused = false;
        notifyAll();
    }

    public synchronized void stop() {
        running = false;
        if (modelThread != null) modelThread.interrupt();
        if (viewThread != null) viewThread.interrupt();
    }

    private void modelLoop() {
        long tickTime = 1000 / TICK_RATE;

        while (running && !Thread.currentThread().isInterrupted()) {
            if (this.model.isGameOver()) {
                running = false;
                ScoreData score = DataBuilder.getCurrentScore(this.model);
                view.setCurrentScore(score);
                view.showScreen(GameView.END_GAME);
                inputHandler.clearPressedKeys();
                return;
            }

            if (this.model.hasJustLevelledUp()) {
                pause();
                view.setItemsData(this.model.getRandomLevelUpWeapons().stream()
                        .map(item -> new ItemData(item.getId(), item.getName(), item.getDescription()))
                        .collect(Collectors.toList()));
                view.showScreen(GameView.ITEM_SELECTION);
            }

            synchronized (this) {
                if (inputHandler.isKeyPressed(KeyEvent.VK_ESCAPE)) {
                    pause();
                    view.showScreen(GameView.PAUSE);
                }

                while (paused) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }

                if (!running) return;
            }

            Point2D.Double direction = inputProcessor.computeDirection();
            this.model.update(tickTime, direction);

            try {
                Thread.sleep(tickTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    private void viewLoop() {
        long frameTime = 1000 / FRAME_RATE;

        while (running && !Thread.currentThread().isInterrupted()) {
            synchronized (this) {
                while (paused) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }

                if (!running) return;
            }

            view.update(DataBuilder.getData(this.model));

            try {
                Thread.sleep(frameTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}