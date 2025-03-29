package it.unibo.vampireio.view;

class UnlockablePowerUpPanel extends BasePanel {
    UnlockablePowerUpPanel(GameViewImpl view) {
        super(view);
        
        this.addButton("BACK", 0, e -> this.view.showScreen(GameViewImpl.START));
    }
}