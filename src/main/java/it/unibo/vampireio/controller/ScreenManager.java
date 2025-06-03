package it.unibo.vampireio.controller;

import java.util.ArrayDeque;
import java.util.Deque;
import it.unibo.vampireio.view.GameView;

public class ScreenManager {

    private final GameView view;
    private final Deque<String> screenHistory = new ArrayDeque<>();

    public ScreenManager(GameView view) {
        this.view = view;
    }

    public void showScreen(String screen) {
        this.screenHistory.push(screen);
        this.view.showScreen(screen);
    }

    public void goBack() {
        if (!screenHistory.isEmpty()) {
            screenHistory.pop();
            if (!screenHistory.isEmpty()) {
                this.view.showScreen(screenHistory.peek());
            }
        }
    }
} 