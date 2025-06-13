package it.unibo.vampireio.view.panels;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import it.unibo.vampireio.view.manager.FrameManager;

/**
 * SaveMenuPanel is a panel that provides options for starting a new game or loading an existing game.
 * It contains buttons for these actions and allows setting listeners for button clicks.
 */
public final class SaveMenuPanel extends AbstractBasePanel {
    private static final long serialVersionUID = 1L;

    private final JButton newSaveButton;
    private final JButton loadButton;

    /**
     * Constructs a SaveMenuPanel with the specified FrameManager.
     *
     * @param frameManager the FrameManager to manage frames
     */
    public SaveMenuPanel(final FrameManager frameManager) {
        super(frameManager);
        this.newSaveButton = this.addButton("NEW GAME", 0, 0);
        this.loadButton = this.addButton("LOAD GAME", 0, 1);
    }

    /**
     * Sets the listener for the new game button.
     *
     * @param listener the ActionListener to be set for the new game button
     */
    public void setNewSaveListener(final ActionListener listener) {
        this.newSaveButton.addActionListener(listener);
    }

    /**
     * Sets the listener for the load game button.
     *
     * @param listener the ActionListener to be set for the load game button
     */
    public void setShowSaveListener(final ActionListener listener) {
        this.loadButton.addActionListener(listener);
    }

}
