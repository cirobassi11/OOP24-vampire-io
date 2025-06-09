package it.unibo.vampireio.controller;

import java.util.ArrayDeque;
import java.util.Deque;
import it.unibo.vampireio.view.GameView;

/**
 * ScreenManager is responsible for managing the screens in the game view.
 * It allows showing a new screen and navigating back to the previous screen.
 */
final class ScreenManager {

    private final GameView view;
    private final Deque<String> screenHistory = new ArrayDeque<>();

    /**
     * Constructs a ScreenManager with the specified GameView.
     *
     * @param view the GameView to manage screens for
     */
    ScreenManager(final GameView view) {
        this.view = view;
    }

    /**
     * Shows a new screen and adds it to the screen history.
     *
     * @param screen the name of the screen to show
     */
    void showScreen(final String screen) {
        this.screenHistory.push(screen);
        this.view.showScreen(screen);
    }

    /**
     * Navigates back to the previous screen if available.
     * If there are no previous screens, it does nothing.
     */
    void goBack() {
        if (!screenHistory.isEmpty()) {
            screenHistory.pop();
            if (!screenHistory.isEmpty()) {
                this.view.showScreen(screenHistory.peek());
            }
        }
    }
}
