package it.unibo.vampireio.view;

import javax.swing.JButton;

class UnlockablePowerUpPanel extends BasePanel {
    UnlockablePowerUpPanel(GameViewImpl view) {
        super(view);
        
        JButton backButton = createButton("BACK", this.buttonSize);
        backButton.addActionListener(e -> view.showScreen(GameViewImpl.MAIN_MENU));
        this.add(backButton);
    }
}