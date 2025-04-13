package it.unibo.vampireio.view;

import java.util.List;

class UnlockableCharactersPanel extends BasePanel {
    UnlockableCharactersPanel(GameViewImpl view) {
        super(view);
        
        /*scrollablelist con tutti i characters*/
        this.addScrollableList(List.of(), 0, 0);

        this.addButton("BUY", 0, 1, e -> {});
        this.addButton("BACK", 0, 2, e -> this.view.showScreen(GameViewImpl.SHOP));
    }
}