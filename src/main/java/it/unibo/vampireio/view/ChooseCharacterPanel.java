package it.unibo.vampireio.view;

class ChooseCharacterPanel extends BasePanel {

    ChooseCharacterPanel(GameViewImpl view) {
        super(view);

        // SELEZIONE PERSONAGGIO

        this.addButton("CONFIRM", 0, 0, e -> {
            String selectedCharacter = "nomePersonaggio"; //DA PRENDERE DAL COMBOBOX
            this.view.getController().startGame(selectedCharacter);
            this.view.showScreen(GameViewImpl.GAME);
        });

        this.addButton("BACK", 0, 1, e -> this.view.showScreen(GameViewImpl.START));
    }
}