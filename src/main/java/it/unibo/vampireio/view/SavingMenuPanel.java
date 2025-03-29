package it.unibo.vampireio.view;

class SavingMenuPanel extends BasePanel {
    
    SavingMenuPanel(GameViewImpl view) {
        super(view);

        this.addButton("NEW GAME", 0, e -> {
            //crea salvataggio
            
            //altro pannello "startMenu" (?)
            view.showScreen(GameViewImpl.START);

        });
        this.addButton("LOAD GAME", 1, e -> {
            view.showScreen(GameViewImpl.SAVING_SELECTION);
        });

        this.addButton("QUIT", 2, e -> view.quit());
    }
}