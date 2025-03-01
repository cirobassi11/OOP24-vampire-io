package it.unibo.vampireio.view;

import java.awt.GridBagConstraints;

import javax.swing.JButton;

class ChooseCharacterPanel extends BasePanel {
    ChooseCharacterPanel(GameViewImpl view) {
        super(view);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new java.awt.Insets(10, 10, 30, 10);
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.SOUTH;

        gbc.gridy = 0;
        JButton startButton = createStyledButton("CHOOSE", this.buttonSize);
        startButton.addActionListener(e -> {
            //view.getController().setCharacter(""); IMPOSTARE IL PERSONAGGIO
            view.getController().startGame();
            view.showScreen(GameViewImpl.GAME);
        });
        this.add(startButton, gbc);


        gbc.gridy = 1;
        JButton backButton = createStyledButton("BACK", this.buttonSize);
        backButton.addActionListener(e -> view.showScreen(GameViewImpl.MAIN_MENU));

        this.add(backButton, gbc);
    }
}