package it.unibo.vampireio.view;

import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JList;
import javax.swing.JButton;
import it.unibo.vampireio.controller.UnlockableCharacterData;

class ChooseCharacterPanel extends BasePanel {

    private List<UnlockableCharacterData> choosableCharactersData = List.of();
    private List<String> characterNames = List.of();

    private JButton confirmButton;
    private JButton backButton;
    private JList<String> charactersList;

    ChooseCharacterPanel(GameViewImpl view) {
        super(view);

        this.charactersList = this.addScrollableList(this.characterNames, 0, 0);
        this.confirmButton = this.addButton("CONFIRM", 0, 1);
        this.backButton = this.addButton("BACK", 0, 2);
    }

    void setChoosableCharactersData(List<UnlockableCharacterData> choosableCharactersData) {
        this.choosableCharactersData = choosableCharactersData;
        if(choosableCharactersData == null || choosableCharactersData.isEmpty()) {
            this.characterNames = List.of();
            return;
        }
        else {
            this.choosableCharactersData = choosableCharactersData;
            this.characterNames = choosableCharactersData.stream()
                    .map(UnlockableCharacterData::getName)
                    .toList();
            this.charactersList.setListData(this.characterNames.toArray(new String[0]));
        }
    }

    String getChoosedCharacter() {
        int selectedIndex = this.charactersList.getSelectedIndex();
        if (selectedIndex < 0 || selectedIndex >= this.choosableCharactersData.size()) {
            return null;
        }
        return this.choosableCharactersData.get(selectedIndex).getId();
    }

    void setConfirmCharacterListener(ActionListener listener) {
        this.confirmButton.addActionListener(listener);
    }

    void setBackListener(ActionListener listener) {
        this.backButton.addActionListener(listener);
    }
}