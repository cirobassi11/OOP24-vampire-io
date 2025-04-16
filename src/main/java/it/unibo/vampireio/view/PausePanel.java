package it.unibo.vampireio.view;

import javax.swing.JButton;

class PausePanel extends BasePanel {
    
    PausePanel(GameViewImpl view) {
        super(view);
        
        JButton continueBotton = this.addButton("CONTINUE", 0, 0);
        continueBotton.addActionListener(e -> {
            //DEVE FAR RIPARTIRE IL GIOCO
            view.showScreen(GameViewImpl.GAME);
        });

        JButton exitButton = this.addButton("EXIT", 0, 1);
        exitButton.addActionListener(e -> {
            //DEVE TERMINARE IL GIOCO
            view.showScreen(GameViewImpl.END_GAME);
        });
    }
}
