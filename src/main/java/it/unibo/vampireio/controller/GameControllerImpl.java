package it.unibo.vampireio.controller;

import it.unibo.vampireio.model.GameModel;
import it.unibo.vampireio.view.GameView;

public class GameControllerImpl implements GameController {
    private final GameModel model;
    private GameView view;

    private boolean running = true;

    private final int frameRate = /*60*/ 1;
    
    public GameControllerImpl(GameModel model) {
        this.model = model;
    }

    @Override
    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public void setView(GameView view) {
        this.view = view;
    }

    @Override
    public void startGame() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (this.isRunning()) {
            this.model.update();
            this.view.update(); //bisogna passargli qualcosa

            System.out.println("\n\nNUOVO CICLOO");

            try {
                Thread.sleep(1000 / this.frameRate);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
