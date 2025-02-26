package it.unibo.vampireio;

import it.unibo.vampireio.controller.GameControllerImpl;
import it.unibo.vampireio.model.GameModelImpl;
import it.unibo.vampireio.view.GameViewImpl;

/**
 * The main class of the project.
 */
final class Main {

    private Main() {
    }

    /**
     * The main method of the project.
     *
     * @param args the command line arguments
     */
    public static void main(final String[] args) {
        new GameControllerImpl(new GameModelImpl(), new GameViewImpl());
    }
}
