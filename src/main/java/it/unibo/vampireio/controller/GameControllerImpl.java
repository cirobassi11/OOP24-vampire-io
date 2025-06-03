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

    private Thread modelThread;
    private Thread viewThread;

    private final InputHandler inputHandler;
    private final InputProcessor inputProcessor;
    private final ScreenManager screenManager;

    private boolean running = false;
    private boolean paused = false;

    private final Deque<String> screenHistory = new ArrayDeque<>();

    public GameControllerImpl() {
        this.view = new GameViewImpl();
        this.model = new GameWorld(this);
        this.inputHandler = new InputHandler();
        this.inputProcessor = new InputProcessor(this.inputHandler);
        this.screenManager = new ScreenManager(this.view);
        ListenerInitializer.initialize(this.view, this.model, this, this::resumeGame, this::stop, this.screenManager);
        this.screenManager.showScreen(GameViewImpl.SAVE_MENU);
        this.view.setPlayerInputListener(this.inputHandler);
    }

    public boolean startGame(String selectedCharacter) {
        if (this.model.initGame(selectedCharacter)) {
            this.running = true;
            this.paused = false;

            this.modelThread = new Thread(this::modelLoop);
            this.viewThread = new Thread(this::viewLoop);

            this.modelThread.start();
            this.viewThread.start();
            return true;
        } else {
            return false;
        }
    }

    private synchronized void pauseGame() {
        this.paused = true;
        this.inputHandler.clearPressedKeys();
    }

    private synchronized void resumeGame() {
        this.paused = false;
        notifyAll();
    }

    private synchronized void stop() {
        this.running = false;

        if (modelThread != null) {
            modelThread.interrupt();
        }
        if (viewThread != null) {
            viewThread.interrupt();
        }
    }

    private void modelLoop() {
        long tickTime = 1000 / TICK_RATE;

        while (this.running && !Thread.currentThread().isInterrupted()) {
            if (this.model.isGameOver()) {
                this.running = false;

                Score score = this.model.exitGame();
                ScoreData scoreData = new ScoreData(
                        score.getCharacterName(),
                        score.getSessionTime(),
                        score.getKillCounter(),
                        score.getLevel(),
                        score.getCoinCounter(),
                        score.getScore());
                this.view.setScore(scoreData);
                this.view.showScreen(GameViewImpl.END_GAME);
                this.inputHandler.clearPressedKeys();
                return;
            }
            if (this.model.hasJustLevelledUp()) {
                this.pauseGame();
                this.view.showScreen(GameViewImpl.ITEM_SELECTION);

                this.view.setItemsData(this.model.getRandomLevelUpWeapons().stream()
                        .map(item -> new ItemData(
                                item.getId(),
                                item.getName(),
                                item.getDescription()))
                        .collect(Collectors.toList()));
            }
            synchronized (this) {
                if (inputHandler.isKeyPressed(KeyEvent.VK_ESCAPE)) {
                    this.pauseGame();
                    this.view.showScreen(GameViewImpl.PAUSE);
                }
                while (this.paused) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
                if (!this.running) {
                    return;
                }
            }

            Point2D.Double direction = this.inputProcessor.computeDirection();

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

        while (this.running && !Thread.currentThread().isInterrupted()) {
            synchronized (this) {
                while (this.paused) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }

                if (!this.running) {
                    return;
                }
            }

            this.view.update(DataBuilder.getData(this.model));

            try {
                Thread.sleep(frameTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
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
