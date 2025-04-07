package it.unibo.vampireio.view;

import java.util.List;

import javax.swing.JList;

class ChooseCharacterPanel extends BasePanel {

    ChooseCharacterPanel(GameViewImpl view) {
        super(view);

        JList<String> characterList = this.addScrollableList(List.of(), 0, 0);

        this.addButton("CONFIRM", 0, 1, e -> {
            String selectedCharacter = characterList.getSelectedValue();
            if (selectedCharacter != null) {
                this.view.getController().startGame(selectedCharacter);
                this.view.showScreen(GameViewImpl.GAME);
            }
        });

        this.addButton("BACK", 0, 2, e -> this.view.showScreen(GameViewImpl.START));
    }
}