package it.unibo.vampireio.view;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JPanel;

class EndGamePanel extends JPanel {

    EndGamePanel(GameViewImpl view) {
        this.setBackground(Color.BLACK);

        JButton mainMenuButton = new JButton("Main Menu");
        mainMenuButton.addActionListener(e -> view.showScreen(GameViewImpl.MAIN_MENU));
        this.add(mainMenuButton);
    }
}
