package it.unibo.vampireio.view;

class UnlockablePowerUpsPanel extends BasePanel {
    UnlockablePowerUpsPanel(GameViewImpl view) {
        super(view);
        
        /*scrollablelist con tutti i powerup*/

        this.addButton("BUY", 0, 0, e -> {});
        this.addButton("BACK", 0, 1, e -> this.view.showScreen(GameViewImpl.SHOP));
    }
}