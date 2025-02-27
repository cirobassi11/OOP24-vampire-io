package it.unibo.vampireio.view;

import javax.swing.JButton;

class PowerUpPanel extends BasePanel {
    PowerUpPanel(GameViewImpl view) {
        super(view);
        
        JButton backButton = createStyledButton("BACK", this.buttonSize);
        backButton.addActionListener(e -> view.showScreen(GameViewImpl.MAIN_MENU));

        this.add(backButton);
    }
}