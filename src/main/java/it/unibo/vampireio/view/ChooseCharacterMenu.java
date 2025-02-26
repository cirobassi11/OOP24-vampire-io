package it.unibo.vampireio.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JPanel;

class ChooseCharacterMenu extends JPanel {
    
    ChooseCharacterMenu(GameViewImpl view) {
        this.setBackground(Color.MAGENTA);

        JButton switchButton = new JButton("CHOOSE");
        switchButton.addActionListener(e -> view.showScreen(GameViewImpl.GAME));
        this.add(switchButton, BorderLayout.SOUTH);
    }
}
