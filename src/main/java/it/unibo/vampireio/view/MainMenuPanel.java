package it.unibo.vampireio.view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

class MainMenuPanel extends JPanel {
    
    MainMenuPanel(GameViewImpl view) {
        this.setBackground(Color.RED);
        this.setLayout(new GridBagLayout());
        
        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> view.showScreen(GameViewImpl.GAME));
        this.add(startButton, new GridBagConstraints());

        JButton powerUpButton = new JButton("Power Up");
        powerUpButton.addActionListener(e -> view.showScreen(GameViewImpl.POWERUPS));
        this.add(powerUpButton, new GridBagConstraints());

        JButton settingsButton = new JButton("Settings");
        settingsButton.addActionListener(e -> view.showScreen(GameViewImpl.SETTINGS));
        this.add(settingsButton, new GridBagConstraints());
    }
}
