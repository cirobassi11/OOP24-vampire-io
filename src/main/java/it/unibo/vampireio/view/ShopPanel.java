package it.unibo.vampireio.view;

import javax.swing.JButton;
import java.awt.event.ActionListener;

class ShopPanel extends AbstractBasePanel {
    private static final long serialVersionUID = 1L;

    private final JButton charactersButton;
    private final JButton powerupsButton;
    private final JButton backButton;

    ShopPanel(final GameViewImpl view) {
        super(view);

        this.charactersButton = this.addButton("CHARACTERS", 0, 0);
        this.powerupsButton = this.addButton("POWERUPS", 0, 1);
        this.backButton = this.addButton("BACK", 0, 2);
    }

    void setCharactersShopListener(final ActionListener listener) {
        this.charactersButton.addActionListener(listener);
    }

    void setPowerupsShopListener(final ActionListener listener) {
        this.powerupsButton.addActionListener(listener);
    }

    void setBackListener(final ActionListener listener) {
        this.backButton.addActionListener(listener);
    }
}
