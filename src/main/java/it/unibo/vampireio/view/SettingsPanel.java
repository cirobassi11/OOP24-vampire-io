package it.unibo.vampireio.view;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JPanel;

class SettingsPanel extends JPanel {

    SettingsPanel(GameViewImpl view) {
        this.setBackground(Color.YELLOW);
        
        JButton switchButton = new JButton("MAIN MENU");
        switchButton.addActionListener(e -> view.showScreen(GameViewImpl.MAIN_MENU));
        this.add(switchButton, BorderLayout.SOUTH);
    }
}
