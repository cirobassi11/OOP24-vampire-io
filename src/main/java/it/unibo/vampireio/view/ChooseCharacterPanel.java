package it.unibo.vampireio.view;

import it.unibo.vampireio.controller.GameController;

class ChooseCharacterPanel extends BasePanel {

    private GameController controller;

    ChooseCharacterPanel(GameViewImpl view, GameController controller) {
        super(view);
        this.controller = controller;

        // jcombobox con immagini https://www.youtube.com/watch?v=zzMSbaIEAQM

        this.addButton("CONFIRM", 0, e -> {
            String selectedCharacter = "nomePersonaggio"; //DA PRENDERE DAL COMBOBOX
            this.controller.startGame(selectedCharacter);
            this.view.showScreen(GameViewImpl.GAME);
        });

        this.addButton("BACK", 1, e -> this.view.showScreen(GameViewImpl.MAIN_MENU));
    }
}