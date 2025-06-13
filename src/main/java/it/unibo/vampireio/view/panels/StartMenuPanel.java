package it.unibo.vampireio.view.panels;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import it.unibo.vampireio.view.manager.FrameManager;

/**
 * StartMenuPanel is a panel that represents the start menu of the game.
 * It contains buttons for starting the game, viewing the scoreboard, accessing
 * the shop, and loading a save.
 */
public final class StartMenuPanel extends AbstractBasePanel {
    private static final long serialVersionUID = 1L;

    private final JButton startButton;
    private final JButton scoreboardButton;
    private final JButton shopButton;
    private final JButton loadSaveButton;

    /**
     * Constructs a StartMenuPanel with the specified FrameManager.
     *
     * @param frameManager the FrameManager to manage frames
     */
    public StartMenuPanel(final FrameManager frameManager) {
        super(frameManager);

        this.startButton = this.addButton("START", 1, 0);
        this.scoreboardButton = this.addButton("SCOREBOARD", 0, 1);
        this.shopButton = this.addButton("SHOP", 1, 1);
        this.loadSaveButton = this.addButton("LOAD SAVE", 2, 1);
    }

    /**
     * Sets the listener for the start button.
     *
     * @param listener the ActionListener to be set for the start button
     */
    public void setStartListener(final ActionListener listener) {
        this.startButton.addActionListener(listener);
    }

    /**
     * Sets the listener for the scoreboard button.
     *
     * @param listener the ActionListener to be set for the scoreboard button
     */
    public void setScoreboardListener(final ActionListener listener) {
        this.scoreboardButton.addActionListener(listener);
    }

    /**
     * Sets the listener for the shop button.
     *
     * @param listener the ActionListener to be set for the shop button
     */
    public void setShopListener(final ActionListener listener) {
        this.shopButton.addActionListener(listener);
    }

    /**
     * Sets the listener for the load-save button.
     *
     * @param listener the ActionListener to be set for the load/save button
     */
    public void setLoadSaveListener(final ActionListener listener) {
        this.loadSaveButton.addActionListener(listener);
    }
}
