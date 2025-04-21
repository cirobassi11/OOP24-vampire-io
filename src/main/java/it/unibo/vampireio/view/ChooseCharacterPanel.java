package it.unibo.vampireio.view;

import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JList;
import javax.swing.JButton;
import it.unibo.vampireio.controller.UnlockableCharacterData;

class ChooseCharacterPanel extends BasePanel {

    private List<UnlockableCharacterData> unlockableCharactersData = List.of();
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

    void setCharacterData(List<UnlockableCharacterData> unlockableCharactersData) {
        this.unlockableCharactersData = unlockableCharactersData;
        if(unlockableCharactersData == null || unlockableCharactersData.isEmpty()) {
            this.characterNames = List.of();
            return;
        }
        else {
            this.unlockableCharactersData = unlockableCharactersData;
            this.characterNames = unlockableCharactersData.stream()
                    .map(UnlockableCharacterData::getName)
                    .toList();
            this.characterList.setListData(this.characterNames.toArray(new String[0]));
        }
    }

    String getSelectedCharacter() {
        int selectedIndex = this.characterList.getSelectedIndex();
        if (selectedIndex < 0 || selectedIndex >= this.unlockableCharactersData.size()) {
            return null;
        }
        return this.unlockableCharactersData.get(selectedIndex).getId();
    }

    void setConfirmCharacterListener(ActionListener listener) {
        this.confirmButton.addActionListener(listener);
    }

    void setBackListener(ActionListener listener) {
        this.backButton.addActionListener(listener);
    }
}