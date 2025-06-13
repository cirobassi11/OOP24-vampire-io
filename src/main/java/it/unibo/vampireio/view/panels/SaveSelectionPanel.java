package it.unibo.vampireio.view.panels;

import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JList;
import it.unibo.vampireio.view.manager.FrameManager;

/**
 * SaveSelectionPanel is a panel that allows the user to select a save from a
 * list.
 * It provides methods to set listeners for selecting a save and going back,
 * as well as updating the list of saves displayed.
 */
public final class SaveSelectionPanel extends AbstractBasePanel {
    private static final long serialVersionUID = 1L;

    private final JButton selectButton;
    private final JButton backButton;

    private final JList<String> savesList;

    /**
     * Constructs a SaveSelectionPanel with the specified FrameManager.
     *
     * @param frameManager the FrameManager to manage frames
     */
    public SaveSelectionPanel(final FrameManager frameManager) {
        super(frameManager);
        this.savesList = this.addScrollableList(List.of(), 0, 0);
        this.selectButton = this.addButton("SELECT", 0, 1);
        this.backButton = this.addButton("BACK", 0, 2);
    }

    /**
     * Sets the listener for the save selection button.
     *
     * @param listener the ActionListener to be set for the select button
     */
    public void setChooseSaveListener(final ActionListener listener) {
        this.selectButton.addActionListener(listener);
    }

    /**
     * Sets the listener for the back button.
     *
     * @param listener the ActionListener to be set for the back button
     */
    public void setBackListener(final ActionListener listener) {
        this.backButton.addActionListener(listener);
    }

    /**
     * Updates the saves list with the provided list of saves.
     *
     * @param saves the list of saves to display
     */
    public void updateSavesList(final List<String> saves) {
        this.savesList.setListData(saves.toArray(new String[0]));
    }

    /**
     * Returns the selected save from the saves list.
     *
     * @return the selected save as a String
     */
    public String getSelectedSave() {
        return this.savesList.getSelectedValue();
    }

}
