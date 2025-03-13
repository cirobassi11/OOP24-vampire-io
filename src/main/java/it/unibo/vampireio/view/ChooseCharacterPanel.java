package it.unibo.vampireio.view;

import java.awt.GridBagConstraints;
import javax.swing.JButton;

import it.unibo.vampireio.controller.GameController;

class ChooseCharacterPanel extends BasePanel {

    private GameController controller;

    ChooseCharacterPanel(GameViewImpl view, GameController controller) {
        super(view);
        this.controller = controller;

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new java.awt.Insets(10, 10, 30, 10);
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.SOUTH;

        // jcombobox con immagini https://www.youtube.com/watch?v=zzMSbaIEAQM

        gbc.gridy = 1;
        JButton confirmButton = createStyledButton("CONFIRM", this.buttonSize);
        confirmButton.addActionListener(e -> {
            String selectedCharacter = "nomePersonaggio"; //DA PRENDERE DAL COMBOBOX
            this.controller.startGame(selectedCharacter);
            this.view.showScreen(GameViewImpl.GAME);
        });
        this.add(confirmButton, gbc);

        gbc.gridy = 2;
        JButton backButton = createStyledButton("BACK", this.buttonSize);
        backButton.addActionListener(e -> view.showScreen(GameViewImpl.MAIN_MENU));

        this.add(backButton, gbc);
    }
}