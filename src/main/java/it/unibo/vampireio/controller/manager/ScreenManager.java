package it.unibo.vampireio.controller.manager;

import java.util.ArrayDeque;
import java.util.Deque;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.vampireio.view.api.GameView;

/**
 * ScreenManager is responsible for managing the screens in the game view.
 * It allows showing a new screen and navigating back to the previous screen.
 */
public final class ScreenManager {

    private final GameView view;
    private final Deque<String> screenHistory = new ArrayDeque<>();

    /**
     * Constructs a ScreenManager with the specified GameView.
     *
     * @param view the GameView to manage screens for
     */
    @SuppressFBWarnings(
        value = "EI2", 
        justification = "The GameView instance is intentionally shared and is used in a controlled way within ScreenManager."
    )
    public ScreenManager(final GameView view) {
        this.view = view;
    }

    /**
     * Shows a new screen and adds it to the screen history.
     *
     * @param screen the name of the screen to show
     */
    public void showScreen(final String screen) {
        this.screenHistory.push(screen);
        this.view.showScreen(screen);
    }

    /**
     * Navigates back to the previous screen if available.
     * If there are no previous screens, it does nothing.
     */
    public void goBack() {
        if (!screenHistory.isEmpty()) {
            screenHistory.pop();
            if (!screenHistory.isEmpty()) {
                this.view.showScreen(screenHistory.peek());
            }
        }
    }
}
