package it.unibo.vampireio.view;

import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JList;
import javax.swing.JButton;
import it.unibo.vampireio.controller.CharacterData;

class ChooseCharacterPanel extends BasePanel {

    private List<CharacterData> charactersData = List.of();
    private List<String> characterNames = List.of();

    private JButton confirmButton;
    private JButton backButton;
    private JList<String> characterList;

    ChooseCharacterPanel(GameViewImpl view) {
        super(view);

        this.characterList = this.addScrollableList(this.characterNames, 0, 0);
        this.confirmButton = this.addButton("CONFIRM", 0, 1);
        this.backButton = this.addButton("BACK", 0, 2);
    }

    void setCharacterData(List<CharacterData> characters) {
        this.charactersData = characters;
        if(characters == null || characters.isEmpty()) {
            this.characterNames = List.of();
            return;
        }
        else {
            this.charactersData = characters;
            this.characterNames = characters.stream()
                    .map(CharacterData::getName)
                    .toList();
            this.characterList.setListData(this.characterNames.toArray(new String[0]));
        }
    }

    String getSelectedCharacter() {
        int selectedIndex = this.characterList.getSelectedIndex();
        if (selectedIndex < 0 || selectedIndex >= this.charactersData.size()) {
            return null;
        }
        return this.charactersData.get(selectedIndex).getId();
    }

    void setConfirmCharacterListener(ActionListener listener) {
        this.confirmButton.addActionListener(listener);
    }

    void setBackListener(ActionListener listener) {
        this.backButton.addActionListener(listener);
    }
}