package it.unibo.vampireio.view;

import javax.swing.JButton;
import java.awt.event.ActionListener;

class ShopPanel extends BasePanel {

    private JButton charactersButton;
    private JButton powerUpsButton;
    private JButton backButton;

    ShopPanel(GameViewImpl view) {
        super(view);

        this.charactersButton = this.addButton("CHARACTERS", 0, 0);
        this.powerUpsButton = this.addButton("POWER UPS", 0, 1);
        this.backButton = this.addButton("BACK", 0, 2);
    }

    void setCharactersShopListener(ActionListener listener) {
        this.charactersButton.addActionListener(listener);
    }

    void setPowerUpsShopListener(ActionListener listener) {
        this.powerUpsButton.addActionListener(listener);
    }

    void setBackListener(ActionListener listener) {
        this.backButton.addActionListener(listener);
    }
}
