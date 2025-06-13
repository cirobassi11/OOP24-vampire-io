package it.unibo.vampireio.view.panels;

import java.awt.event.ActionListener;
import javax.swing.JButton;
import it.unibo.vampireio.view.manager.FrameManager;

/**
 * PausePanel is a panel that allows the user to pause the game.
 * It provides buttons to resume the game or exit to the main menu.
 */
public final class PausePanel extends AbstractBasePanel {
    private static final long serialVersionUID = 1L;

    private final JButton resumeButton;
    private final JButton exitButton;

    /**
     * Constructs a PausePanel with the specified FrameManager.
     *
     * @param frameManager the FrameManager to manage frames
     */
    public PausePanel(final FrameManager frameManager) {
        super(frameManager);
        this.resumeButton = this.addButton("RESUME", 0, 0);
        this.exitButton = this.addButton("EXIT", 0, 1);
    }

    /**
     * Sets the listener for the resume button.
     *
     * @param listener the ActionListener to be set for the resume button
     */
    public void setResumeListener(final ActionListener listener) {
        this.resumeButton.addActionListener(listener);
    }

    /**
     * Sets the listener for the exit button.
     *
     * @param listener the ActionListener to be set for the exit button
     */
    public void setExitListener(final ActionListener listener) {
        this.exitButton.addActionListener(listener);
    }

}
