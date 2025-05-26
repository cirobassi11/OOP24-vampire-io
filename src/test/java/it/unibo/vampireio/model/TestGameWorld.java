package it.unibo.vampireio.model;

import it.unibo.vampireio.controller.*;
import it.unibo.vampireio.view.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;  

class TestGameWorld {
    @BeforeEach
    void init() {
        GameController controller = new GameControllerImpl();
        GameModel model = new GameWorld(controller);
        GameView view = new GameViewImpl();
        // controller.setView(view);
    }
}
