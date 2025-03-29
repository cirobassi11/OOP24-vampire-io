package it.unibo.vampireio.view;

class SavingSelectionPanel extends BasePanel {
    
    SavingSelectionPanel(GameViewImpl view) {
        super(view);

        //lista salvataggi da selezionare

        this.addButton("SELECT", 0, e -> {
            //prende salvataggio selezionato e lo passa al controller
            view.showScreen(GameViewImpl.START);
        });

        this.addButton("BACK", 1, e -> view.showScreen(GameViewImpl.SAVING_MENU));
    }
}