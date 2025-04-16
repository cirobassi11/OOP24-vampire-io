package it.unibo.vampireio.view;

import javax.swing.JButton;

class ShopPanel extends BasePanel {

    private JButton charactersButton;
    private JButton powerUpsButton;
    private JButton backButton;

    ShopPanel(GameViewImpl view) {
        super(view);

        this.charactersButton = this.addButton("CHARACTERS", 0, 0);
        this.charactersButton.addActionListener(e -> {
            this.view.showScreen(GameViewImpl.UNLOCKABLE_CHARACTERS);
        });


        this.powerUpsButton = this.addButton("POWER UPS", 0, 1);
        this.powerUpsButton.addActionListener(e -> {
            this.view.showScreen(GameViewImpl.UNLOCKABLE_POWERUPS);
        });

        this.backButton = this.addButton("BACK", 0, 2);
        this.backButton.addActionListener(e -> {
            this.view.showScreen(GameViewImpl.START);
        });
    }
}
