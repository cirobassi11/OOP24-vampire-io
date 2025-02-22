package it.unibo.vampireio.view;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PowerUpPanel extends JPanel {
    public PowerUpPanel(GameViewImpl view) {
        this.setBackground(Color.GREEN);
        
        JButton mainMenuButton = new JButton("Main Menu");
        mainMenuButton.addActionListener(e -> view.showScreen(GameViewImpl.MAIN_MENU));
        this.add(mainMenuButton);
    }
}
