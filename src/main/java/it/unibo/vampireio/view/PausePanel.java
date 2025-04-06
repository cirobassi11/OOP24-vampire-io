package it.unibo.vampireio.view;

class PausePanel extends BasePanel {
    
    PausePanel(GameViewImpl view) {
        super(view);
        
        this.addButton("CONTINUE", 0, 0, e -> {
            //DEVE FAR RIPARTIRE IL GIOCO
            view.showScreen(GameViewImpl.GAME);
        });

        this.addButton("EXIT", 0, 1, e -> {
            //DEVE TERMINARE IL GIOCO
            view.showScreen(GameViewImpl.END_GAME);
        });
    }
}
