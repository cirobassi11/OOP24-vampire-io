package it.unibo.vampireio.view;

import javax.swing.*;
import java.awt.*;

class MainMenuPanel extends BasePanel {
    
    MainMenuPanel(GameViewImpl view) {
        super(view);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 30, 10);
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.SOUTH;

        // Bottoni
        JButton startButton = createStyledButton("START", this.buttonSize);
        startButton.addActionListener(e -> view.showScreen(GameViewImpl.CHOOSE_CHARACTER));
        gbc.gridy = 0;
        buttonPanel.add(startButton, gbc);

        JButton powerUpButton = createStyledButton("POWERUPS", this.buttonSize);
        powerUpButton.addActionListener(e -> view.showScreen(GameViewImpl.POWERUPS));
        gbc.gridy = 1;
        buttonPanel.add(powerUpButton, gbc);

        JButton settingsButton = createStyledButton("SETTINGS", this.buttonSize);
        settingsButton.addActionListener(e -> view.showScreen(GameViewImpl.SETTINGS));
        gbc.gridy = 2;
        buttonPanel.add(settingsButton, gbc);

        this.add(buttonPanel);
    }
}