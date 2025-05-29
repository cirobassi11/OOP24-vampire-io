package it.unibo.vampireio.view;

import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JList;
import javax.swing.JButton;
import it.unibo.vampireio.controller.UnlockableCharacterData;
import it.unibo.vampireio.controller.UnlockableItemData;

class ChooseCharacterPanel extends AbstractBasePanel {

    private List<UnlockableItemData> choosableCharactersData = List.of();
    private List<String> characterNames = List.of();

    private JButton confirmButton;
    private JButton backButton;
    private JList<String> charactersList;

    ChooseCharacterPanel(final GameViewImpl view) {
        super(view);

        this.charactersList = this.addScrollableList(this.characterNames, 0, 0);
        this.confirmButton = this.addButton("CONFIRM", 0, 1);
        this.backButton = this.addButton("BACK", 0, 2);
    }

    void setChoosableCharactersData(final List<UnlockableItemData> choosableCharactersData) {
        this.choosableCharactersData = choosableCharactersData;
        if (choosableCharactersData == null || choosableCharactersData.isEmpty()) {
            this.characterNames = List.of();
            return;
        } else {
            this.choosableCharactersData = choosableCharactersData;
            this.characterNames = choosableCharactersData.stream()
                    .map(UnlockableItemData::getName)
                    .toList();
            this.charactersList.setListData(this.characterNames.toArray(new String[0]));
        }
    }

    String getChoosedCharacter() {
        final int selectedIndex = this.charactersList.getSelectedIndex();
        if (selectedIndex < 0 || selectedIndex >= this.choosableCharactersData.size()) {
            return null;
        }
        return this.choosableCharactersData.get(selectedIndex).getId();
    }

    void setConfirmCharacterListener(final ActionListener listener) {
        this.confirmButton.addActionListener(listener);
    }

    void setBackListener(final ActionListener listener) {
        this.backButton.addActionListener(listener);
    }
}
