package it.unibo.vampireio.view;

class SavingMenuPanel extends BasePanel {
    
    SavingMenuPanel(GameViewImpl view) {
        super(view);

        this.addButton("NEW GAME", 0, 0, e -> {
            this.view.getController().createNewSaving();
            view.showScreen(GameViewImpl.START);

        });
        this.addButton("LOAD SAVING", 0, 1, e -> {
            this.view.updateSavingsList();
            this.view.showScreen(GameViewImpl.SAVING_SELECTION);
        });

        this.addButton("QUIT", 0, 2, e -> view.quit());
    }
}