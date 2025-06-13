package it.unibo.vampireio.view.panels;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import it.unibo.vampireio.view.manager.FrameManager;

/**
 * ShopPanel is a panel that represents the shop in the game.
 * It contains buttons for accessing the characters shop, power-ups shop, and going back.
 */
public final class ShopPanel extends AbstractBasePanel {
    private static final long serialVersionUID = 1L;

    private final JButton charactersButton;
    private final JButton powerUpsButton;
    private final JButton backButton;

    /**
     * Constructs a ShopPanel with the specified FrameManager.
     *
     * @param frameManager the FrameManager to manage frames
     */
    public ShopPanel(final FrameManager frameManager) {
        super(frameManager);

        this.charactersButton = this.addButton("CHARACTERS", 0, 0);
        this.powerUpsButton = this.addButton("POWERUPS", 0, 1);
        this.backButton = this.addButton("BACK", 0, 2);
    }

    /**
     * Sets the listener for the characters shop button.
     *
     * @param listener the ActionListener to be set for the characters shop button
     */
    public void setCharactersShopListener(final ActionListener listener) {
        this.charactersButton.addActionListener(listener);
    }

    /**
     * Sets the listener for the power-ups shop button.
     *
     * @param listener the ActionListener to be set for the power-ups shop button
     */
    public void setPowerUpsShopListener(final ActionListener listener) {
        this.powerUpsButton.addActionListener(listener);
    }

    /**
     * Sets the listener for the back button.
     *
     * @param listener the ActionListener to be set for the back button
     */
    public void setBackListener(final ActionListener listener) {
        this.backButton.addActionListener(listener);
    }
}
