package it.unibo.vampireio.view;

import java.util.List;
import javax.swing.JButton;
import javax.swing.JList;
import it.unibo.vampireio.controller.UnlockableCharacterData;
import java.awt.event.ActionListener;

class UnlockableCharactersPanel extends BasePanel {
    private List<UnlockableCharacterData> unlockableCharactersData = List.of();
    private List<String> characterNames = List.of();
    
    private JButton buyButton;
    private JButton backButton;
    private JList<String> charactersList;

    UnlockableCharactersPanel(GameViewImpl view) {
        super(view);

        /*scrollablelist con tutti i characters*/
        this.charactersList = this.addScrollableList(characterNames, 0, 0);
        this.buyButton = this.addButton("BUY", 0, 1);        
        this.backButton = this.addButton("BACK", 0, 2);
    }
    
    void setUnlockableCharactersData(List<UnlockableCharacterData> unlockableCharactersData) {
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
            this.charactersList.setListData(this.characterNames.toArray(new String[0]));
        }
    }

    String getSelectedCharacter() {
        int selectedIndex = this.charactersList.getSelectedIndex();
        if (selectedIndex < 0 || selectedIndex >= this.unlockableCharactersData.size()) {
            return null;
        }
        return this.unlockableCharactersData.get(selectedIndex).getId();
    }

    void setBuyCharactersListener(ActionListener listener) {
        this.buyButton.addActionListener(listener);
    }
    
    void setBackListener(ActionListener listener) {
        this.backButton.addActionListener(listener);
    }

}