package it.unibo.vampireio.view;

import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JList;
import javax.swing.event.ListSelectionListener;
import javax.swing.JButton;
import it.unibo.vampireio.controller.UnlockableItemData;

class ChooseCharacterPanel extends AbstractBasePanel {

    private List<UnlockableItemData> choosableCharactersData = List.of();
    private final JButton confirmButton;
    private final JButton backButton;
    private final JList<String> charactersList;

    ChooseCharacterPanel(final FrameManager frameManager) {
        super(frameManager);

        this.charactersList = this.addScrollableList(List.of(), 0, 0);
        this.confirmButton = this.addButton("CONFIRM", 0, 1);
        this.backButton = this.addButton("BACK", 0, 2);
        this.confirmButton.setEnabled(false);
    }

    void setChoosableCharactersData(final List<UnlockableItemData> choosableCharactersData) {
        this.choosableCharactersData = choosableCharactersData;
        List<String> characterNames;
        if (choosableCharactersData == null || choosableCharactersData.isEmpty()) {
            characterNames = List.of();
        } else {
            this.choosableCharactersData = choosableCharactersData;
            characterNames = choosableCharactersData.stream()
                    .map(UnlockableItemData::getName)
                    .toList();
            charactersList.setListData(characterNames.toArray(new String[0]));
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

    void disableConfirmCharacterButton() {
        this.confirmButton.setEnabled(false);
    }

    void enableConfirmCharacterButton() {
        this.confirmButton.setEnabled(true);
    }

    void setCharacterSelectionListener(final ListSelectionListener listener) {
        this.charactersList.addListSelectionListener(listener);
    }
}
