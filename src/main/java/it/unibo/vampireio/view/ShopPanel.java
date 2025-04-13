package it.unibo.vampireio.view;

class ShopPanel extends BasePanel {

    ShopPanel(GameViewImpl view) {
        super(view);

        this.addButton("CHARACTERS", 0, 0, e -> view.showScreen(GameViewImpl.UNLOCKABLE_CHARACTERS));
        this.addButton("POWER UPS", 0, 1, e -> view.showScreen(GameViewImpl.UNLOCKABLE_POWERUPS));
        this.addButton("BACK", 0, 2, e -> view.showScreen(GameViewImpl.START));
    }
}
