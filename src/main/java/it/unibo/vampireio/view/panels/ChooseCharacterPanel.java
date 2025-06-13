package it.unibo.vampireio.view.panels;

import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JList;
import javax.swing.event.ListSelectionListener;
import javax.swing.JButton;
import it.unibo.vampireio.controller.data.UnlockableItemData;
import it.unibo.vampireio.view.manager.FrameManager;

/**
 * ChooseCharacterPanel is a panel that allows the user to choose a character
 * from a list of unlocked characters. It provides methods to set the list of
 * characters, get the selected character, and add listeners for button actions.
 */
public final class ChooseCharacterPanel extends AbstractBasePanel {
    private static final long serialVersionUID = 1L;

    private List<UnlockableItemData> choosableCharactersData;
    private final JButton confirmButton;
    private final JButton backButton;
    private final JList<String> charactersList;

    /**
     * Constructs a ChooseCharacterPanel with the specified FrameManager.
     *
     * @param frameManager the FrameManager to manage the frame
     */
    public ChooseCharacterPanel(final FrameManager frameManager) {
        super(frameManager);

        this.choosableCharactersData = List.of();
        this.charactersList = this.addScrollableList(List.of(), 0, 0);
        this.confirmButton = this.addButton("CONFIRM", 0, 1);
        this.backButton = this.addButton("BACK", 0, 2);
        this.confirmButton.setEnabled(false);
    }

    /**
     * Sets the list of choosable characters.
     *
     * @param choosableCharactersData the list of UnlockableItemData representing
     *                                the characters
     */
    public void setChoosableCharactersData(final List<UnlockableItemData> choosableCharactersData) {
        final List<String> characterNames;
        this.choosableCharactersData = List.copyOf(choosableCharactersData);
        if (choosableCharactersData != null && !choosableCharactersData.isEmpty()) {
            characterNames = choosableCharactersData.stream()
                    .map(UnlockableItemData::getName)
                    .toList();
            charactersList.setListData(characterNames.toArray(new String[0]));
        }
    }

    /**
     * Gets the ID of the selected character.
     *
     * @return the ID of the selected character, or null if no character is selected
     */
    public String getChoosedCharacter() {
        final int selectedIndex = this.charactersList.getSelectedIndex();
        if (selectedIndex < 0 || selectedIndex >= this.choosableCharactersData.size()) {
            return null;
        }
        return this.choosableCharactersData.get(selectedIndex).getId();
    }

    /**
     * Sets the listener for the confirm character button.
     *
     * @param listener the ActionListener to be notified when the confirm button is
     *                 pressed
     */
    public void setConfirmCharacterListener(final ActionListener listener) {
        this.confirmButton.addActionListener(listener);
    }

    /**
     * Sets the listener for the back button.
     *
     * @param listener the ActionListener to be notified when the back button is
     *                 pressed
     */
    public void setBackListener(final ActionListener listener) {
        this.backButton.addActionListener(listener);
    }

    /**
     * Disables the confirm button.
     */
    public void disableConfirmCharacterButton() {
        this.confirmButton.setEnabled(false);
    }

    /**
     * Enables the confirm button.
     */
    public void enableConfirmCharacterButton() {
        this.confirmButton.setEnabled(true);
    }

    /**
     * Sets a listener for character selection changes.
     *
     * @param listener the ListSelectionListener to be notified when the selection
     *                 changes
     */
    public void setCharacterSelectionListener(final ListSelectionListener listener) {
        this.charactersList.addListSelectionListener(listener);
    }
}
