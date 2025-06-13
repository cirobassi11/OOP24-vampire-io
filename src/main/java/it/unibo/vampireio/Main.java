package it.unibo.vampireio;

import it.unibo.vampireio.controller.impl.GameControllerImpl;

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
        new GameControllerImpl();
    }
}
