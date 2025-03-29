package it.unibo.vampireio.view;

import it.unibo.vampireio.controller.GameController;

class ChooseCharacterPanel extends BasePanel {

    private GameController controller;

    ChooseCharacterPanel(GameViewImpl view, GameController controller) {
        super(view);
        this.controller = controller;

        // NUOVO PANNELLO CON GRIGLIA DI BUTTON

        this.addButton("CONFIRM", 0, e -> {
            String selectedCharacter = "nomePersonaggio"; //DA PRENDERE DAL COMBOBOX
            this.controller.startGame(selectedCharacter);
            this.view.showScreen(GameViewImpl.GAME);
        });

        this.addButton("BACK", 1, e -> this.view.showScreen(GameViewImpl.START));
    }
}