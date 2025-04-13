package it.unibo.vampireio.view;

class UnlockableCharactersPanel extends BasePanel {
    UnlockableCharactersPanel(GameViewImpl view) {
        super(view);
        
        /*scrollablelist con tutti i characters*/

        this.addButton("BUY", 0, 0, e -> {});
        this.addButton("BACK", 0, 1, e -> this.view.showScreen(GameViewImpl.SHOP));
    }
}