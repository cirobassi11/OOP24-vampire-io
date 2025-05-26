package it.unibo.vampireio.view;

import javax.swing.JButton;
import java.awt.event.ActionListener;

class ShopPanel extends BasePanel {

    private JButton charactersButton;
    private JButton powerUpsButton;
    private JButton backButton;

    ShopPanel(final GameViewImpl view) {
        super(view);

        this.charactersButton = this.addButton("CHARACTERS", 0, 0);
        this.powerUpsButton = this.addButton("POWERUPS", 0, 1);
        this.backButton = this.addButton("BACK", 0, 2);
    }

    void setCharactersShopListener(final ActionListener listener) {
        this.charactersButton.addActionListener(listener);
    }

    void setPowerUpsShopListener(final ActionListener listener) {
        this.powerUpsButton.addActionListener(listener);
    }

    void setBackListener(final ActionListener listener) {
        this.backButton.addActionListener(listener);
    }
}
