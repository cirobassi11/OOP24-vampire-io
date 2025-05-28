package it.unibo.vampireio.view;

import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import it.unibo.vampireio.controller.UnlockableCharacterData;
import it.unibo.vampireio.controller.UnlockablePowerupData;

import java.awt.event.ActionListener;

class UnlockableCharactersPanel extends AbstractBasePanel {
    private static final long serialVersionUID = 1L;

    private List<UnlockableCharacterData> unlockableCharactersData = List.of();
    private List<String> characterNames = List.of();

    private final JButton buyButton;
    private final JButton backButton;
    private final JList<String> charactersList;
    private final JLabel coinsLabel;
    private final JLabel descriptionLabel;
    private final JLabel priceLabel;

    UnlockableCharactersPanel(final GameViewImpl view) {
        super(view);

        this.coinsLabel = this.addLabel("", 0, 0);
        this.charactersList = this.addScrollableList(characterNames, 0, 1);
        this.descriptionLabel = this.addLabel("", 0, 2);
        this.priceLabel = this.addLabel("", 0, 3);
        this.buyButton = this.addButton("BUY", 0, 4);
        this.backButton = this.addButton("BACK", 0, 5);
    }

    void setUnlockableCharactersData(final List<UnlockableCharacterData> unlockableCharactersData) {
        this.unlockableCharactersData = unlockableCharactersData;
        if (unlockableCharactersData == null || unlockableCharactersData.isEmpty()) {
            this.characterNames = List.of();
        } else {
            this.characterNames = unlockableCharactersData.stream()
                    .map(UnlockableCharacterData::getName)
                    .toList();
        }
        this.charactersList.setListData(this.characterNames.toArray(new String[0]));
        this.charactersList.addListSelectionListener(e -> getCharacterInfo());
    }

    String getSelectedCharacter() {
        final int selectedIndex = this.charactersList.getSelectedIndex();
        if (selectedIndex < 0 || selectedIndex >= this.unlockableCharactersData.size()) {
            return null;
        }
        return this.unlockableCharactersData.get(selectedIndex).getId();
    }

    private void getCharacterInfo() {
        final int selectedIndex = this.charactersList.getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < this.unlockableCharactersData.size()) {
            final UnlockableCharacterData data = this.unlockableCharactersData.get(selectedIndex);
            this.descriptionLabel.setText("" + data.getDescription());
            this.priceLabel.setText("Price: " + data.getPrice());
        } else {
            this.descriptionLabel.setText("");
            this.priceLabel.setText("");
        }
    }

    void setBuyCharactersListener(final ActionListener listener) {
        this.buyButton.addActionListener(listener);
    }

    void setBackListener(final ActionListener listener) {
        this.backButton.addActionListener(listener);
    }

    void setCoinsAmount(final int coins) {
        this.coinsLabel.setText("Coins: " + coins);
    }

}
