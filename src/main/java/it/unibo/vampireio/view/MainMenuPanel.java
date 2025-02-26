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
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.insets = new java.awt.Insets(10, 20, 10, 20);

        gbc.gridy = 0;
        JButton startButton = new JButton("PLAY");
        startButton.addActionListener(e -> view.showScreen(GameViewImpl.CHOOSE_CHARACTER));
        this.add(startButton, gbc);

        gbc.gridy = 1;
        JButton powerUpButton = new JButton("POWERUPS");
        powerUpButton.addActionListener(e -> view.showScreen(GameViewImpl.POWERUPS));
        this.add(powerUpButton, gbc);

        gbc.gridy = 2;
        JButton settingsButton = new JButton("SETTINGS");
        settingsButton.addActionListener(e -> view.showScreen(GameViewImpl.SETTINGS));
        this.add(settingsButton, gbc);
    }
}